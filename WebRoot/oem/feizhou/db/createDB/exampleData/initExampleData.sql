/*==============================================================*/
/* 初始化组织数据                                                 */
/*==============================================================*/
ALTER TRIGGER trg_b_org_i_u DISABLE;
insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1, '0000', '组织根节点', '组织根节点', 0, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (2, null, '中联煤层气', '中联煤层气', 1323, 2);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (3, null, '大庆油田', null, 1322, 7);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (4, null, '太仓工厂', null, 27, 1);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (5, null, '长庆油田公司', null, 1322, 6);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (6, null, '潘河', null, 2, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (7, null, '柿庄南', null, 2, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (8, null, '柿庄北', null, 2, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (9, null, '出厂测试', null, 4, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (10, null, '疲劳测试', null, 4, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (12, null, '十厂', null, 5, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (13, null, '三厂', null, 5, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (22, null, '海拉尔', null, 1323, 3);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (23, null, '蓝焰煤层气', null, 1323, 4);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (24, null, '华北煤层气', null, 1323, 5);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (25, null, '吉林油田', null, 1322, 8);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (26, null, '延长油田', null, 1323, 9);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (27, null, '厂内测试', null, 1, 2);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (28, null, '泵设备', null, 222, 2);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (29, null, '管设备', null, 222, 10);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (42, null, '待出厂', null, 1, 3);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (67, null, '集输加热管', null, 29, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (68, null, '华北五厂', null, 67, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (69, null, '束21', null, 68, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (72, null, '晋古', null, 68, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (76, null, '华北一厂', null, 67, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (77, null, '同口', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (84, null, '高阳', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (88, null, '鄚北', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (90, null, '雁翔', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (92, null, '南马', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (97, null, '任北', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (162, null, '【供应商】东昊', null, 29, 50);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (182, null, '【供应商】杉建', null, 29, 51);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (202, null, '回收站', null, 1, 4);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (203, null, '美中能源', null, 1323, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (204, null, '智能加热管', null, 29, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (205, null, '三厂', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (206, null, '延长', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (207, null, '南泥湾', null, 206, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (208, null, '地埋加热管', null, 29, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (222, null, '现场设备', null, 1, 1);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (242, null, '水源井', null, 29, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (243, null, '长庆一厂', null, 242, 1);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (244, null, '长庆二厂', null, 242, 2);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (245, null, '长庆四厂', null, 242, 4);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (246, null, '长庆五厂', null, 242, 5);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (247, null, '长庆九厂', null, 242, 9);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (248, null, '长庆十厂', null, 242, 10);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (249, null, '长庆十一厂', null, 242, 11);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (250, null, '长庆十二厂', null, 242, 12);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (251, null, '王南', null, 243, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (252, null, '华池', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (253, null, '西峰', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (254, null, '艾家湾', null, 245, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (255, null, '麻南', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (256, null, '刘茆源', null, 247, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (257, null, '柔远', null, 248, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (258, null, '固城', null, 250, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (259, null, '方山', null, 249, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (260, null, '太白梁', null, 249, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (261, null, '城壕', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (262, null, '五谷城', null, 247, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (263, null, '冯地坑', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (264, null, '马家山东', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (265, null, '麻黄山北', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (266, null, '麻黄山西', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (267, null, '营盘山', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (268, null, '五蛟', null, 248, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (269, null, '南岭', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (282, null, '现场参数更新', null, 29, 1);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (302, null, '【供应商】多欧', null, 29, 52);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (322, null, '五厂', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (323, null, '冯地坑采油作业区', null, 322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (324, null, '堡子弯', null, 322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (325, null, '麻北', null, 322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (326, null, '马西', null, 322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (327, null, '十厂', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (328, null, '乔河', null, 327, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (382, null, '大庆六厂  ', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (383, null, '四区', null, 382, 4);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (402, null, '长庆六厂', null, 242, 6);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (403, null, '砖井作业区', null, 402, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (422, null, '苏中天然气', null, 242, 21);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (442, null, '长东', null, 243, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (443, null, '长庆页岩油', null, 242, 20);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (444, null, '西十转', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (445, null, '岭二转', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (446, null, '桐川', null, 249, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (447, null, '南梁', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (448, null, '彭阳', null, 249, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (449, null, '长庆三厂', null, 242, 3);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (450, null, '红井子', null, 449, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (451, null, '安五', null, 402, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (462, null, '长庆十厂', null, 208, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (463, null, '五蛟', null, 462, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (482, null, '大庆', null, 67, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (483, null, '华油3#环', null, 482, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (503, null, '岭北', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (504, null, '吉岘', null, 250, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (505, null, '乔川', null, 248, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (506, null, '苏格里气田', null, 242, 22);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (507, null, '长庆十一厂', null, 208, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (508, null, '方山', null, 507, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (522, null, '海南', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (523, null, '海南项目', null, 522, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (562, null, '加热电缆项目', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (563, null, '加热电缆', null, 562, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (564, null, '五里湾', null, 205, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (582, null, '大庆八厂', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (602, null, '庆城联合站', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (622, null, '长庆', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (623, null, '页岩油', null, 622, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (624, null, '大港油田', null, 67, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (625, null, '三厂', null, 624, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (642, null, '板桥', null, 250, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (662, null, '测试柜', null, 162, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (682, null, '胜利油田', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (683, null, '东辛', null, 682, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (702, null, '堡子湾', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (722, null, '马岭南', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (742, null, '未知井场', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (764, null, '五平台', null, 582, 5);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (765, null, '六平台', null, 582, 6);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (782, null, '长庆七厂', null, 242, 7);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (783, null, '洪德', null, 782, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (802, null, '技术服务部', null, 29, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (803, null, 'KD93备用模块', null, 802, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (822, null, '兴庄作业区', null, 402, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (823, null, '薛岔', null, 247, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (842, null, '岭三', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (862, null, '杨青', null, 247, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (882, null, '未知井场', null, 782, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (883, null, '未知井场', null, 402, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (902, null, '岭13联', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (922, null, '兰花煤层气', null, 1323, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (942, null, '十二平台', null, 582, 12);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (962, null, '十五平台', null, 582, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (963, null, '三平台', null, 582, 3);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (982, null, '铜川', null, 507, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1002, null, '元城', null, 248, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1022, null, '长庆四厂', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1023, null, '化子平', null, 1022, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1062, null, '大水坑', null, 449, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1082, null, '郑庄北区', null, 23, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1122, null, '嘉能项目部', null, 23, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1142, null, '十六平台', null, 582, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1162, null, '华庆', null, 462, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1182, null, '柔远', null, 462, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1202, null, '一区', null, 382, 1);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1222, null, '新集', null, 249, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1242, null, '产品柜', null, 162, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1262, null, '成庄工区', null, 23, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1264, null, '淮南矿业', null, 1323, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1265, null, '韩城煤层气', null, 1323, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1266, null, '富地柳林', null, 1323, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1282, null, '安边 ', null, 402, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1302, null, '铜川油气开发有限公司', null, 1322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1303, null, '渭北油田', null, 1302, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1304, null, '玉门油田', null, 1322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1305, null, '老君庙作业区L层西区', null, 1304, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1322, null, '油田隔膜泵', null, 28, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1323, null, '煤层气隔膜泵', null, 28, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1324, null, '元城', null, 462, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1342, null, '二区', null, 382, 2);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1362, null, '乔河', null, 462, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1382, null, '樊家川', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1402, null, '【供应商】生产自制', null, 29, 53);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1422, null, '环江', null, 782, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1423, null, '六区', null, 382, 6);


/*==============================================================*/
/* 初始化设备数据                                              */
/*==============================================================*/
ALTER TRIGGER trg_b_device_i DISABLE;

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (1, 9, '厂内测试', 3, 0, 'TCP Client', '00000000126', null, '01', null, 'instance105', 'alarminstance43', 'displayinstance103', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1001, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (2, 7, 'ZY-284H1', 3, 0, 'TCP Client', '00000000112', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 22, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (3, 6, 'PH-111H1', 3, 0, 'TCP Client', '00000000307', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (4, 6, 'PH-111H2', 3, 0, 'TCP Client', '00000000405', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (22, 24, 'DS-077平1', 3, 0, 'TCP Client', '00000000123', null, '01', null, 'instance85', 'alarminstance43', 'displayinstance83', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (23, 1122, 'QC-L-25', 3, 0, 'TCP Client', '00000000158', null, '01', null, 'instance85', 'alarminstance43', 'displayinstance83', null, null, null, null, null, null, '{}', to_date('06-09-2024 21:16:39', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (24, 6, 'PH-113H3', 3, 0, 'TCP Client', '00000000398', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 5, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (26, 6, 'PH-117H3', 3, 0, 'TCP Client', '00000000308', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 9, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (27, 202, 'PH-119H4-1', 3, 0, 'TCP Client', '00000000119', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (28, 7, 'TS-129H2', 3, 0, 'TCP Client', '00000000415', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 12, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (29, 7, 'TS-130H2', 3, 0, 'TCP Client', '00000000162', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 13, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (30, 7, 'TS-278H2', 3, 0, 'TCP Client', '00000000125', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 14, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (31, 7, 'TS-306H1', 3, 0, 'TCP Client', '00000000163', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 15, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (32, 7, 'TS-313H', 3, 0, 'TCP Client', '00000000297', null, '01', null, 'instance105', 'alarminstance64', 'displayinstance103', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 17, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (33, 8, 'SX-218H1', 3, 0, 'TCP Client', '00000000167', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 19, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (35, 8, 'SX-226H2', 3, 0, 'TCP Client', '00000000166', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 21, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (36, 8, 'SX-235H1', 3, 0, 'TCP Client', '00000000176', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 23, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (37, 22, 'DE-T2', 3, 0, 'TCP Client', '00000000187', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (38, 10, 'VSD-V07-199', 3, 1, 'TCP Client', '00000000199', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (39, 25, 'XFP221', 3, 1, 'TCP Client', '00000000198', null, '01', null, 'instance117', 'alarminstance43', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (40, 25, 'XFP59', 3, 1, 'TCP Client', '00000000185', null, '01', null, 'instance114', 'alarminstance43', 'displayinstance110', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (41, 26, '38109', 3, 1, 'TCP Client', '00000000129', null, '01', null, 'instance114', 'alarminstance64', 'displayinstance110', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (42, 26, '38370', 3, 1, 'TCP Client', '00000000122', null, '01', null, 'instance114', 'alarminstance43', 'displayinstance110', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (43, 3, '7P180-S160', 3, 1, 'TCP Client', '00000000114', null, '01', null, 'instance114', 'alarminstance43', 'displayinstance110', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (45, 13, '柴36-41', 3, 1, 'TCP Client', '00000000177', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 31, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (47, 13, '柴55-48', 3, 1, 'TCP Client', '00000000195', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 33, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (62, 7, 'SN-002H2', 3, 0, 'TCP Client', '00000000181', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 24, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (82, 6, 'PH-114H2', 3, 0, 'TCP Client', '00000000324', null, '01', null, 'instance108', 'alarminstance63', 'displayinstance105', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 6, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (101, 6, 'PH-116H3', 3, 0, 'TCP Client', '00000000414', null, '01', null, 'instance110', 'alarminstance63', 'displayinstance108', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 7, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (121, 7, 'TS-018H', 3, 0, 'TCP Client', '00000000043', null, '01', null, 'instance108', 'alarminstance43', 'displayinstance105', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (122, 7, 'TS-314H', 3, 0, 'TCP Client', '00000000341', null, '01', null, 'instance108', 'alarminstance43', 'displayinstance105', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 18, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (124, 6, 'PH-119H4', 3, 0, 'TCP Client', '00000000347', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 10, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (292, 42, 'VSD-V08-356', 3, 1, 'TCP Client', '00000000356', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (293, 42, 'VSD-V08-357', 3, 1, 'TCP Client', '00000000357', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (294, 42, 'VSD-V08-358', 3, 1, 'TCP Client', '00000000358', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (295, 42, 'VSD-V08-359', 3, 1, 'TCP Client', '00000000359', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (296, 4, '360', 3, 0, 'TCP Client', '00000000360', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('25-09-2024 20:12:35', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (297, 42, 'VSD-V08-361', 3, 1, 'TCP Client', '00000000361', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (298, 4, '362', 3, 0, 'TCP Client', '00000000362', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('25-09-2024 19:09:50', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (299, 42, 'VSD-V08-363', 3, 1, 'TCP Client', '00000000363', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (300, 7, 'ZY-360H1', 3, 0, 'TCP Client', '00000000364', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 27, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (301, 42, 'VSD-V08-365', 3, 1, 'TCP Client', '00000000365', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (303, 25, 'FP232', 3, 1, 'TCP Client', '00000000367', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (305, 8, 'SX-029H2', 3, 0, 'TCP Client', '00000000369', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 25, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (306, 25, 'FP19', 3, 1, 'TCP Client', '00000000370', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (307, 42, 'VSD-V08-371', 3, 1, 'TCP Client', '00000000371', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (308, 42, 'VSD-V08-372', 3, 1, 'TCP Client', '00000000372', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (309, 42, 'VSD-V08-373', 3, 1, 'TCP Client', '00000000373', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (310, 42, 'VSD-V08-374', 3, 1, 'TCP Client', '00000000374', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (312, 42, 'VSD-V08-376', 3, 1, 'TCP Client', '00000000376', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (314, 25, 'FP233', 3, 1, 'TCP Client', '00000000378', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (316, 25, 'FP62', 3, 1, 'TCP Client', '00000000380', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (318, 25, 'FP58', 3, 1, 'TCP Client', '00000000382', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (319, 25, '中5-034', 3, 1, 'TCP Client', '00000000383', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (320, 25, 'FP231', 3, 1, 'TCP Client', '00000000384', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (322, 25, '中+5-038', 3, 1, 'TCP Client', '00000000386', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (324, 42, 'VSD-V08-388', 3, 1, 'TCP Client', '00000000388', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (325, 25, 'FP336', 3, 1, 'TCP Client', '00000000389', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (326, 42, 'VSD-V08-390', 3, 1, 'TCP Client', '00000000390', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (327, 25, 'FP272', 3, 1, 'TCP Client', '00000000391', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (328, 25, 'FP222', 3, 1, 'TCP Client', '00000000392', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (329, 25, 'FP63', 3, 1, 'TCP Client', '00000000393', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (330, 25, '中5-36.1', 3, 1, 'TCP Client', '00000000394', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (331, 42, 'VSD-V08-395', 3, 1, 'TCP Client', '00000000395', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (332, 42, 'VSD-V08-396', 3, 1, 'TCP Client', '00000000396', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (333, 42, 'VSD-V08-397', 3, 1, 'TCP Client', '00000000397', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (341, 6, 'PH-102H1', 3, 0, 'TCP Client', '00000000286', null, '01', null, 'instance110', 'alarminstance43', 'displayinstance108', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 3, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (342, 6, 'PH-102H3', 3, 0, 'TCP Client', '00000000105', null, '01', null, 'instance110', 'alarminstance43', 'displayinstance108', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 4, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (344, 203, 'PZC12L-05', 3, 0, 'TCP Client', '00000000422', null, '01', null, 'instance109', 'alarminstance43', 'displayinstance107', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (345, 3, '7P83-54', 3, 1, 'TCP Client', '00000000127', null, '01', null, 'instance114', 'alarminstance43', 'displayinstance110', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (346, 3, '7P180-S158', 3, 1, 'TCP Client', '00000000131', null, '01', null, 'instance114', 'alarminstance43', 'displayinstance110', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (348, 12, '关130-201X', 3, 1, 'TCP Client', '00000000107', null, '01', null, 'instance111', 'alarminstance43', 'displayinstance106', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 27, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (352, 26, '南平33', 3, 1, 'TCP Client', '00000000188', null, '01', null, 'instance114', 'alarminstance43', 'displayinstance110', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (361, 8, 'SX01-02H', 3, 0, 'TCP Client', '00000000190', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 18, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (381, 7, 'TS-278H', 3, 0, 'TCP Client', '00000000192', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 19, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (382, 7, 'TS-278H5', 3, 0, 'TCP Client', '00000000214', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 20, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (383, 7, 'TS-706H2', 3, 0, 'TCP Client', '00000000193', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 21, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (401, 922, 'LH4-P04-L3', 3, 0, 'TCP Client', '00000000426', null, '01', null, 'instance119', 'alarminstance64', 'displayinstance115', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (402, 1264, 'PX2-1', 3, 0, 'TCP Client', '00000000215', null, '01', null, 'instance113', 'alarminstance43', 'displayinstance109', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (421, 1082, 'CZBL-29', 3, 0, 'TCP Client', '00000000168', null, '01', null, 'instance110', 'alarminstance43', 'displayinstance108', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (461, 7, 'ZY-614H2', 3, 0, 'TCP Client', '000000000390', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 23, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (501, 7, 'TS-707H2', 3, 0, 'TCP Client', '00000000385', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 25, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (502, 7, 'TS-709H2', 3, 0, 'TCP Client', '00000000437', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 26, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (521, 1122, 'QC-L-26', 3, 0, 'TCP Client', '00000000377', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (522, 1262, 'CZ-L09', 3, 1, 'TCP Client', '00000000431', null, '01', null, 'instance116', 'alarminstance64', 'displayinstance113', null, null, null, null, null, null, '{}', to_date('31-07-2024 16:17:32', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (525, 203, 'PZC04L-09', 3, 0, 'TCP Client', '00000000421', null, '01', null, 'instance85', 'alarminstance43', 'displayinstance83', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (526, 1265, 'HH01-01H', 3, 0, 'TCP Client', '00000000395', null, '01', null, 'instance113', 'alarminstance43', 'displayinstance109', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (527, 1266, 'FL-4-L24', 3, 0, 'TCP Client', '00000000196', null, '01', null, 'instance85', 'alarminstance43', 'displayinstance83', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (528, 1266, 'FL-4-L28', 3, 0, 'TCP Client', '00000000194', null, '01', null, 'instance85', 'alarminstance43', 'displayinstance83', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (529, 1266, 'FL-4-L80', 3, 0, 'TCP Client', '00000000179', null, '01', null, 'instance85', 'alarminstance43', 'displayinstance83', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (541, 1303, 'WB2-4H', 3, 1, 'TCP Client', '00000000423', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (542, 1303, 'WB2-3H', 3, 1, 'TCP Client', '00000000438', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (543, 1303, 'WB2-26-4', 3, 1, 'TCP Client', '00000000425', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (544, 1303, 'WB2-26-2', 3, 1, 'TCP Client', '00000000387', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (545, 1303, 'WB2-26-6', 3, 1, 'TCP Client', '00000000433', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (546, 1303, 'WB2-8-2', 3, 1, 'TCP Client', '00000000429', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (547, 1303, 'WB2-8-3', 3, 1, 'TCP Client', '00000000436', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (548, 1303, 'WB4P1', 3, 1, 'TCP Client', '00000000439', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (549, 1303, 'WB2-8-5', 3, 1, 'TCP Client', '00000000368', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (550, 1303, 'WB2-8-6', 3, 1, 'TCP Client', '00000000379', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (551, 1303, 'WB2-8-7', 3, 1, 'TCP Client', '00000000381', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (552, 1305, 'A154', 3, 1, 'TCP Client', '00000000443', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (553, 1305, '122', 3, 1, 'TCP Client', '00000000366', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (554, 25, 'FP277', 3, 1, 'TCP Client', '00000000359', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (561, 9, '远传通讯测试', 3, 0, 'TCP Client', '00000000800', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (581, 1264, 'GQ2-1', 3, 0, 'TCP Client', '00000000211', null, '01', null, 'instance85', 'alarminstance43', 'displayinstance83', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (601, 9, '465', 3, 0, 'TCP Client', '00000000465', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (602, 4, '466', 3, 0, 'TCP Client', '00000000466', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (603, 4, '467', 3, 0, 'TCP Client', '00000000467', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (604, 4, '474', 3, 0, 'TCP Client', '00000000474', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (605, 4, '475', 3, 0, 'TCP Client', '00000000475', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (606, 4, '468', 3, 0, 'TCP Client', '00000000468', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (607, 1264, 'GQ3-1', 3, 0, 'TCP Client', '00000000355', null, '01', null, 'instance113', 'alarminstance43', 'displayinstance109', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 3, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (608, 4, 'WB2-28-7', 3, 0, 'TCP Client', '00000000455', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('10-10-2024 18:13:50', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (609, 4, '470', 3, 0, 'TCP Client', '00000000470', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (610, 4, '472', 3, 0, 'TCP Client', '00000000472', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (611, 4, '464', 3, 0, 'TCP Client', '00000000464', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (612, 4, '454', 3, 0, 'TCP Client', '00000000454', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (613, 4, '456', 3, 0, 'TCP Client', '00000000456', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (614, 4, '457', 3, 0, 'TCP Client', '00000000457', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (615, 4, '458', 3, 0, 'TCP Client', '00000000458', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (616, 4, '459', 3, 0, 'TCP Client', '00000000459', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (617, 4, '460', 3, 0, 'TCP Client', '00000000460', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (618, 4, '461', 3, 0, 'TCP Client', '00000000461', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (619, 4, '462', 3, 0, 'TCP Client', '00000000462', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (620, 4, '463', 3, 0, 'TCP Client', '00000000563', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (621, 4, '469', 3, 0, 'TCP Client', '00000000469', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (622, 1122, 'QC-77-L1', 3, 0, 'TCP Client', '00000000471', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('06-09-2024 21:16:38', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 3, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (623, 4, '473', 3, 0, 'TCP Client', '00000000473', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (624, 4, '476', 3, 0, 'TCP Client', '00000000476', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (625, 4, '477', 3, 0, 'TCP Client', '00000000477', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (626, 4, '478', 3, 0, 'TCP Client', '00000000478', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (641, 27, '中联新系统测试', 3, 0, 'TCP Client', '00000000025', null, '01', null, 'instance105', 'alarminstance64', 'displayinstance103', null, null, null, null, null, null, '{}', to_date('06-08-2024 12:09:35', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (661, 6, 'PH75-11H', 3, 0, 'TCP Client', '00000000452', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (681, 6, 'PH74-11H1', 3, 0, 'TCP Client', '00000000213', null, '01', null, 'instance85', 'alarminstance43', 'displayinstance83', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (721, 1303, 'WB2-78-3', 3, 1, 'TCP Client', '00000000465', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (741, 1303, 'WB2-78-2', 3, 1, 'TCP Client', '00000000454', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:04', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (761, 1303, 'WB2-78-5', 3, 1, 'TCP Client', '00000000470', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:04', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (781, 1303, 'WB2-28-1', 3, 1, 'TCP Client', '00000000468', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:04', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (801, 1303, 'WB2-28-3', 3, 1, 'TCP Client', '00000000464', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:04', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (821, 1264, 'GQ2-2', 3, 0, 'TCP Client', '00000000030', null, '01', null, 'instance113', 'alarminstance43', 'displayinstance109', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:04', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 4, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (841, 1303, 'WB2-28-2', 3, 1, 'TCP Client', '00000000472', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('31-07-2024 09:04:16', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (861, 9, '480', 3, 0, 'TCP Client', '00000000480', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('21-08-2024 09:13:29', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (862, 4, '481', 3, 0, 'TCP Client', '00000000481', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('21-08-2024 09:13:29', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (863, 4, '482', 3, 0, 'TCP Client', '00000000482', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('21-08-2024 09:13:29', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (864, 4, '483', 3, 0, 'TCP Client', '00000000483', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('21-08-2024 09:13:29', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (865, 4, '484', 3, 0, 'TCP Client', '00000000484', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('21-08-2024 09:13:29', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (866, 4, '485', 3, 0, 'TCP Client', '00000000485', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('21-08-2024 09:13:29', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (867, 4, '486', 3, 0, 'TCP Client', '00000000486', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('21-08-2024 09:13:29', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10021, 29, '660样柜', 8, 1, 'TCP Client', '00000000337', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10041, 563, '加热电缆样柜', 8, 1, 'TCP Client', '00000000338', null, '01', null, 'instance139', 'alarminstance67', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10061, 204, 'KJR2112078', 8, 1, 'TCP Client', '00000000339', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 3, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10241, 242, 'KCS2106026', 9, 1, 'TCP Client', '00000000232', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10261, 662, '外部测试1', 8, 1, 'TCP Client', '00000001000', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10281, 504, 'N147S19', 9, 1, 'TCP Client', '00000000409', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10301, 1002, 'B84S2', 9, 1, 'TCP Client', '00000000410', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10302, 447, 'W86S57', 9, 1, 'TCP Client', '00000010001', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10303, 242, 'KCS2206053已检', 9, 1, 'TCP Client', '00000010003', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10304, 503, '里303', 9, 1, 'TCP Client', '00000010004', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10305, 266, 'H50S16', 9, 1, 'TCP Client', '00000010006', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10306, 702, 'L1S72', 9, 1, 'TCP Client', '00000010007', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10307, 444, 'X10ZS1', 9, 1, 'TCP Client', '00000010008', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10308, 742, 'H45S1', 9, 1, 'TCP Client', '00000010009', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10309, 242, 'ZH181S1', 9, 1, 'TCP Client', '00000010010', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10310, 448, 'ZBS195', 9, 1, 'TCP Client', '00000010011', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10311, 29, 'KCS2206062', 9, 1, 'TCP Client', '00000010012', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10312, 29, 'KCS2206063', 9, 1, 'TCP Client', '00000010013', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10313, 29, 'KCS2206064', 9, 1, 'TCP Client', '00000010014', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10314, 29, 'KCS2206065', 9, 1, 'TCP Client', '00000010015', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10321, 207, '阳910平2（撤）', 8, 1, 'TCP Client', '00000000183', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10322, 564, '柴26-33', 8, 1, 'TCP Client', '00000000254', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10323, 564, '柴35-44', 8, 1, 'TCP Client', '00000000326', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10324, 564, '柴38-45', 8, 1, 'TCP Client', '00000000251', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10325, 564, '柴55-48', 8, 1, 'TCP Client', '00000000253', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10326, 564, '柴36-41', 8, 1, 'TCP Client', '00000000255', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10343, 264, 'G143S8', 9, 1, 'TCP Client', '00000000142', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10344, 265, 'H122S10', 9, 1, 'TCP Client', '00000000139', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10345, 282, 'FDKS6', 9, 1, 'TCP Client', '11100000134', null, '01', null, 'instance205', 'alarminstance83', 'displayinstance206', null, null, null, null, null, null, '{}', to_date('04-09-2024 17:09:48', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10346, 264, 'L1S71', 9, 1, 'TCP Client', '00000000206', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10347, 266, 'H50S15', 9, 1, 'TCP Client', '00000000203', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10348, 267, 'H292S3', 9, 1, 'TCP Client', '00000000133', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10349, 256, 'G18S13', 9, 1, 'TCP Client', '00000000141', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10350, 262, '旗64-117S3', 9, 1, 'TCP Client', '00000000138', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10351, 261, 'SH177S9', 9, 1, 'TCP Client', '00000000137', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10352, 259, 'ZBS189', 9, 1, 'TCP Client', '00000000242', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10353, 259, 'ZBS182', 9, 1, 'TCP Client', '00000000135', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10354, 259, 'ZBS188', 9, 1, 'TCP Client', '00000000113', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10355, 260, 'ZBS186', 9, 1, 'TCP Client', '00000000136', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10356, 260, 'ZBS184', 9, 1, 'TCP Client', null, null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10357, 442, 'SHS84', 9, 1, 'TCP Client', '00000000092', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10358, 256, 'L47S4', 9, 1, 'TCP Client', '00000000246', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10359, 261, '陇页6', 9, 1, 'TCP Client', '00000018018', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10360, 253, '板12B12S1', 9, 1, 'TCP Client', '00000000241', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10361, 253, '板100', 9, 1, 'TCP Client', '00000000238', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10362, 258, 'N147S18', 9, 1, 'TCP Client', '00000000231', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10363, 254, 'AS58', 9, 1, 'TCP Client', '00000000217', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10364, 254, 'AS36', 9, 1, 'TCP Client', '00000000228', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10365, 254, 'AS61', 9, 1, 'TCP Client', '00000000222', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10366, 254, 'AS63', 9, 1, 'TCP Client', '00000000245', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10367, 251, 'WYS9', 9, 1, 'TCP Client', '00000000224', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10368, 251, 'XWYS17', 9, 1, 'TCP Client', '00000000230', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10369, 257, 'XB257S9', 9, 1, 'TCP Client', '00000000239', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10370, 255, 'XH116S10', 9, 1, 'TCP Client', '00000000244', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10371, 445, 'L2LS1', 9, 1, 'TCP Client', '00000000210', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10373, 255, 'XH57S7', 9, 1, 'TCP Client', '00000000140', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 150, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10374, 268, '483-S1', 9, 1, 'TCP Client', '00000000132', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10375, 269, '西299', 9, 1, 'TCP Client', '00000000249', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10376, 29, 'ZH179S4', 9, 1, 'TCP Client', null, null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10377, 255, 'H57S32', 9, 1, 'TCP Client', '00000000225', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10378, 325, '成32-52', 8, 1, 'TCP Client', '00000000317', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 101, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10379, 506, 'SZZS2', 9, 1, 'TCP Client', '00000000248', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10380, 504, 'N27S3', 9, 1, 'TCP Client', '00000000247', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10381, 446, 'ZBS191', 9, 1, 'TCP Client', '00000000243', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10382, 254, 'AS64', 9, 1, 'TCP Client', '00000000229', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10383, 742, 'L44ZS1', 9, 1, 'TCP Client', '00000000226', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10384, 72, '晋古14-36', 10, 1, 'TCP Client', '00000000296', null, '01', null, 'instance129', 'alarminstance65', 'displayinstance124', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10385, 72, '晋古14-70', 10, 1, 'TCP Client', '00000000294', null, '01', null, 'instance129', 'alarminstance65', 'displayinstance124', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 12, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10386, 72, '晋古14-79', 10, 1, 'TCP Client', '00000000295', null, '01', null, 'instance129', 'alarminstance65', 'displayinstance124', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 13, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10387, 69, '束21-7至束21', 10, 1, 'TCP Client', '00000000259', null, '01', null, 'instance129', 'alarminstance65', 'displayinstance124', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 14, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10388, 69, '束21至束21-2', 10, 1, 'TCP Client', '00000000262', null, '01', null, 'instance129', 'alarminstance65', 'displayinstance124', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 15, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10389, 77, '1#雁63-119-120', 10, 1, 'TCP Client', '00000000103', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 30, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10390, 77, '3#雁63-27-28-29', 10, 1, 'TCP Client', '00000000101', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 32, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10391, 77, '2#雁63-53-54', 10, 1, 'TCP Client', '00000000102', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 31, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10392, 282, '雁63-33阀组（弃）', 10, 1, 'TCP Client', '00000000079', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 39, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10393, 282, '雁63-6-12（弃）', 10, 1, 'TCP Client', '00000000099', null, '01', null, 'instance127', 'alarminstance65', 'displayinstance123', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 38, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10394, 282, '雁63-13（弃）', 10, 1, 'TCP Client', '00000000100', null, '01', null, 'instance127', 'alarminstance65', 'displayinstance123', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 37, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10395, 483, '华油3#环', 10, 1, 'TCP Client', '00000000407', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10396, 625, '小14-25-1', 10, 1, 'TCP Client', '00000000408', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10397, 84, '高30-204', 10, 1, 'TCP Client', '00000000097', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10398, 84, '高44-62', 10, 1, 'TCP Client', '00000000098', null, '01', null, 'instance130', 'alarminstance65', 'displayinstance120', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10399, 84, '高67', 10, 1, 'TCP Client', '00000000157', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10400, 88, '鄚40-10', 10, 1, 'TCP Client', '00000000095', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10401, 97, '文119-23', 10, 1, 'TCP Client', '00000000075', null, '01', null, 'instance125', 'alarminstance65', 'displayinstance126', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10402, 97, '文119-31', 10, 1, 'TCP Client', '00000000036', null, '01', null, 'instance120', 'alarminstance65', 'displayinstance125', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10403, 92, '西36-12', 10, 1, 'TCP Client', '00000000078', null, '01', null, 'instance128', 'alarminstance65', 'displayinstance119', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10404, 92, '西36-4', 10, 1, 'TCP Client', '00000000077', null, '01', null, 'instance130', 'alarminstance65', 'displayinstance120', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10405, 92, '西36-9（至2）', 10, 1, 'TCP Client', '00000000076', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10406, 92, '西6-9', 10, 1, 'TCP Client', '00000000096', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10407, 90, '雁59-11', 10, 1, 'TCP Client', '00000000094', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10408, 325, '沙20-82', 8, 1, 'TCP Client', '00000011001', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 103, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10409, 325, '沙19-7', 8, 1, 'TCP Client', '00000000302', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 102, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10410, 325, '沙20-81', 8, 1, 'TCP Client', '00000000327', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 104, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10411, 325, '沙16-11', 8, 1, 'TCP Client', '00000000314', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 105, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10412, 325, '塬33-96', 8, 1, 'TCP Client', '00000000321', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 106, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10413, 325, '塬34-97', 8, 1, 'TCP Client', '00000011002', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 107, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10414, 325, '塬33-91', 8, 1, 'TCP Client', '00000011003', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 108, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10421, 523, '永8-27X', 8, 1, 'TCP Client', '00000011004', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1000, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10422, 623, 'H60-22', 8, 1, 'TCP Client', '00000011006', null, '01', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1000, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10423, 623, 'H60-18', 8, 1, 'TCP Client', '00000011005', null, '01', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1000, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10441, 450, 'Y427S1', 9, 1, 'TCP Client', '00000010016', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10442, 254, 'AS66', 9, 1, 'TCP Client', '00000010017', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10462, 323, '地加85-473/地加85-494', 8, 1, 'TCP Client', '00000000204', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10463, 323, '地69-59/地70-59/地69-58', 8, 1, 'TCP Client', '00000000208', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10467, 323, '地加85-493/地加87-473/地加87-491/地加87-472', 8, 1, 'TCP Client', '00000000207', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10470, 323, '盐70-36/盐70-37', 8, 1, 'TCP Client', '00000000205', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10472, 324, '堡18-47', 8, 1, 'TCP Client', '00000000303', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 109, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10473, 324, '堡33-18', 8, 1, 'TCP Client', '00000000299', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10474, 324, '官19-23', 8, 1, 'TCP Client', '00000000325', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 110, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10475, 324, '官11-29', 8, 1, 'TCP Client', '00000000322', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 112, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10476, 324, '耿31-14', 8, 1, 'TCP Client', '00000000329', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10477, 324, '堡25-45', 8, 1, 'TCP Client', '00000000330', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10478, 324, '堡19-47', 8, 1, 'TCP Client', '00000000313', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 111, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10479, 325, '塬48-88（作废）', 8, 1, 'TCP Client', '00000000316', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10480, 326, '堡33-27', 8, 1, 'TCP Client', '00000000328', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10481, 326, '地236-53（作废）', 8, 1, 'TCP Client', '00000000315', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10482, 326, '堡33-25', 8, 1, 'TCP Client', '00000000320', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10483, 207, '南平33', 8, 1, 'TCP Client', '00000000188', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10484, 328, '关129-201X', 8, 1, 'TCP Client', '00000000037', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10485, 328, '关18-78', 8, 1, 'TCP Client', '00000000108', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10486, 328, '关19-76', 8, 1, 'TCP Client', '00000000104', null, '01', null, null, null, null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10487, 328, '关19-78', 8, 1, 'TCP Client', '00000000124', null, '01', null, null, null, null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10489, 204, 'KJR2111074', 8, 1, 'TCP Client', '00000000331', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10490, 383, '喇9-2618', 8, 1, 'TCP Client', '00000000235', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10491, 204, 'KJR2111075', 8, 1, 'TCP Client', '00000000332', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10492, 204, 'KJR2111076', 8, 1, 'TCP Client', '00000000333', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10493, 204, 'KJR2111077', 8, 1, 'TCP Client', '00000000334', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10494, 207, '阳910平2', 8, 1, 'TCP Client', '00000000335', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10495, 204, 'KJR2202008', 8, 1, 'TCP Client', '00000000336', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10496, 204, 'KJR2204013', 8, 1, 'TCP Client', '00000000357', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10497, 1202, '喇7-3225', 8, 1, 'TCP Client', '00000000396', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10498, 1342, '喇3-PS2311', 8, 1, 'TCP Client', '00000000397', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10499, 1202, '8-Ps3237', 8, 1, 'TCP Client', '00000000399', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10500, 1423, '5-PS3717', 8, 1, 'TCP Client', '00000000400', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('06-09-2024 14:19:40', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10501, 204, 'KJR2204018', 8, 1, 'TCP Client', '00000000401', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10502, 1423, '5-PS3327', 8, 1, 'TCP Client', '00000000406', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('06-09-2024 14:19:40', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10521, 208, '镇262-407', 8, 1, 'TCP Client', '00000000261', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10522, 982, '镇平378-10', 8, 1, 'TCP Client', '00000000278', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10523, 208, '孟103-69', 8, 1, 'TCP Client', '00000000288', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10524, 208, '镇168-6', 8, 1, 'TCP Client', '00000000263', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10525, 208, '桐299-19', 8, 1, 'TCP Client', '00000000291', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10526, 463, '里183-7', 8, 1, 'TCP Client', '00000018011', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 107, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10527, 463, '里183-35', 8, 1, 'TCP Client', '00000000289', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 135, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10528, 463, '里183-13', 8, 1, 'TCP Client', '00000018012', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 113, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10529, 463, '里183-76-1', 8, 1, 'TCP Client', '00000018014', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 176, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10530, 463, '里183-1', 8, 1, 'TCP Client', '00000018010', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 101, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10531, 208, '元276', 8, 1, 'TCP Client', '00000000068', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10532, 208, '铁8-111', 8, 1, 'TCP Client', '00000000071', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10533, 208, '芦82-59', 8, 1, 'TCP Client', '00000000072', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10534, 208, '阳60-62', 8, 1, 'TCP Client', '00000000073', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10535, 208, '铁88-96', 8, 1, 'TCP Client', '00000000080', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10536, 208, '涧116-25井', 8, 1, 'TCP Client', '00000000081', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10537, 208, '铁6-199', 8, 1, 'TCP Client', '00000000082', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10538, 208, '元200-54', 8, 1, 'TCP Client', '00000000083', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10539, 208, '洼92-96井', 8, 1, 'TCP Client', '00000000084', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10540, 208, '铁232-46井', 8, 1, 'TCP Client', '00000000085', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10541, 208, '苗76-9A', 8, 1, 'TCP Client', '00000000086', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10542, 208, '安155-6井', 8, 1, 'TCP Client', '00000000088', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10543, 208, '铁93-95井', 8, 1, 'TCP Client', '00000000090', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10544, 508, '山139井', 8, 1, 'TCP Client', '00000000110', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10545, 208, '山137-2井', 8, 1, 'TCP Client', '00000000143', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10546, 508, '山139-2井', 8, 1, 'TCP Client', '00000000144', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10547, 508, '山130-10井', 8, 1, 'TCP Client', '00000000146', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10548, 508, '山136-02井', 8, 1, 'TCP Client', '00000000147', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10549, 208, '山139', 8, 1, 'TCP Client', '00000000148', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10550, 208, '白451', 8, 1, 'TCP Client', '00000000149', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10551, 508, '关85-202井', 8, 1, 'TCP Client', '00000000150', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10552, 208, '白398-09', 8, 1, 'TCP Client', '00000000151', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10553, 208, '白300-51X', 8, 1, 'TCP Client', '00000000152', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10554, 208, '白298-56X', 8, 1, 'TCP Client', '00000000153', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10555, 208, '白398-012', 8, 1, 'TCP Client', '00000000154', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10556, 208, '白299-64X', 8, 1, 'TCP Client', '00000000155', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10557, 208, '涧116-25', 8, 1, 'TCP Client', '00000000256', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10558, 208, '康7-5', 8, 1, 'TCP Client', '00000000233', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10559, 208, '姬78', 8, 1, 'TCP Client', '00000000234', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10560, 208, '涧116-9', 8, 1, 'TCP Client', '00000000236', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10561, 208, '镇平277-8', 8, 1, 'TCP Client', '00000000237', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10562, 208, '苗84-11', 8, 1, 'TCP Client', '00000000038', null, '01', null, 'instance132', 'alarminstance67', 'displayinstance141', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10563, 208, '学63-9', 8, 1, 'TCP Client', '00000000046', null, '01', null, 'instance132', 'alarminstance67', 'displayinstance141', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10564, 208, '学49-71', 8, 1, 'TCP Client', '00000000047', null, '01', null, 'instance132', 'alarminstance67', 'displayinstance141', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10565, 208, '阳46-66', 8, 1, 'TCP Client', '00000000049', null, '01', null, 'instance132', 'alarminstance67', 'displayinstance141', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10566, 208, '铁76-92', 8, 1, 'TCP Client', '00000000053', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10567, 208, '元267侏罗', 8, 1, 'TCP Client', '00000000054', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10568, 208, '元267三叠', 8, 1, 'TCP Client', '00000000055', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10569, 208, '涧163-04', 8, 1, 'TCP Client', '00000000056', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10570, 208, '铁83-87', 8, 1, 'TCP Client', '00000000057', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10571, 208, '涧148-6', 8, 1, 'TCP Client', '00000000059', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10572, 208, '元230-7', 8, 1, 'TCP Client', '00000000060', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10573, 208, '铁9-86', 8, 1, 'TCP Client', '00000000070', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10574, 208, '铁57-103', 8, 1, 'TCP Client', '00000000074', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10575, 208, '新52-103', 8, 1, 'TCP Client', '00000000050', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10576, 208, '新46-104', 8, 1, 'TCP Client', '00000000058', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10577, 208, '铁77-105', 8, 1, 'TCP Client', '00000000061', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10601, 259, 'ZBS194', 9, 1, 'TCP Client', '00000010021', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10602, 253, 'X89S7', 9, 1, 'TCP Client', '00000010022', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10603, 242, 'KCS2209064已检', 9, 1, 'TCP Client', '00000010023', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10604, 403, 'A28S1', 9, 1, 'TCP Client', '00000010024', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10605, 264, 'L1S71(替换)', 9, 1, 'TCP Client', '00000010025', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10606, 450, 'H39S9', 9, 1, 'TCP Client', '00000010026', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10607, 451, 'H154s27(作废)', 9, 1, 'TCP Client', '00000010027', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10608, 261, 'QB23S02(作废)', 9, 1, 'TCP Client', '00000010028', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10609, 242, 'KCS2209072', 9, 1, 'TCP Client', '00000010029', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10622, 1142, '22-斜80', 8, 1, 'TCP Client', '00000000350', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10624, 1142, '20-斜82', 8, 1, 'TCP Client', '00000000351', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10641, 662, '外部测试2', 8, 1, 'TCP Client', '00000001001', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10661, 266, 'KCS2106028', 9, 1, 'TCP Client', '00000000227', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10681, 242, '14S38', 9, 1, 'TCP Client', null, null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10701, 463, '里183-08', 8, 1, 'TCP Client', '00000011007', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 108, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10702, 463, '里183-31', 8, 1, 'TCP Client', '00000011008', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('23-10-2024 14:14:54', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 131, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10703, 463, '里482-1', 8, 1, 'TCP Client', '00000011009', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10704, 942, '25-斜83', 8, 1, 'TCP Client', '00000011010', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10705, 962, '21-斜83', 8, 1, 'TCP Client', '00000011011', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10706, 962, '23-斜83', 8, 1, 'TCP Client', '00000011012', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10707, 942, '24-斜82', 8, 1, 'TCP Client', '00000011013', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10708, 942, '28-斜82', 8, 1, 'TCP Client', '00000011014', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 13, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10709, 962, '23-斜81', 8, 1, 'TCP Client', '00000011015', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10721, 942, '27-斜81', 8, 1, 'TCP Client', '00000011016', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10722, 963, '36-斜79', 8, 1, 'TCP Client', '00000011017', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 12, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10723, 942, '27-斜83', 8, 1, 'TCP Client', '00000011018', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 12, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10724, 962, '22-斜84', 8, 1, 'TCP Client', '00000011019', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 10, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10725, 1142, '21-斜81', 8, 1, 'TCP Client', '00000011020', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10726, 963, '35-斜76', 8, 1, 'TCP Client', '00000011021', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10727, 963, '34-斜77', 8, 1, 'TCP Client', '00000011022', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10728, 963, '37-斜78', 8, 1, 'TCP Client', '00000011023', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 13, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10729, 765, '30-斜83', 8, 1, 'TCP Client', '00000011024', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 3, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10730, 963, '36-斜77', 8, 1, 'TCP Client', '00000011025', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10731, 1142, '19-斜83', 8, 1, 'TCP Client', '00000011026', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10732, 765, '31-斜84', 8, 1, 'TCP Client', '00000011027', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 4, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10741, 67, '新疆200m', 10, 1, 'TCP Client', '00000000109', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10765, 242, 'KCS2210073', 9, 1, 'TCP Client', '00000013001', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10766, 252, 'B455S6', 9, 1, 'TCP Client', '00000013002', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10767, 254, 'AS65(替换)', 9, 1, 'TCP Client', '00000018003', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10768, 252, 'B455S7', 9, 1, 'TCP Client', '00000013004', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10769, 1222, 'ZBS197', 9, 1, 'TCP Client', '00000013005', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10770, 242, 'KCS2210078', 9, 1, 'TCP Client', '00000013006', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10771, 602, 'QCLS1', 9, 1, 'TCP Client', '00000013007', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10772, 443, 'L3LS1', 9, 1, 'TCP Client', '00000013008', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10781, 442, 'SHS36', 9, 1, 'TCP Client', '00000000091', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10782, 442, 'SHS64', 9, 1, 'TCP Client', '00000000093', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10783, 261, 'SH177S8', 9, 1, 'TCP Client', null, null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10784, 261, 'SH177S4', 9, 1, 'TCP Client', null, null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10801, 282, 'KD93测试模块', 8, 1, 'TCP Client', '10000000001', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10841, 504, 'ZH179S4', 9, 1, 'TCP Client', null, null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10861, 623, 'H40-4', 8, 1, 'TCP Client', '00000018017', null, '1', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10862, 623, 'H60-9', 8, 1, 'TCP Client', '00000011029', null, '1', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10863, 623, 'H60-14', 8, 1, 'TCP Client', '00000018013', null, '1', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10864, 1023, '化96-39', 8, 1, 'TCP Client', '00000011031', null, '1', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10881, 302, 'KJR2301118', 8, 1, 'TCP Client', '00000011032', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10882, 463, '里482扩', 8, 1, 'TCP Client', '00000011033', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10883, 463, '里183-12', 8, 1, 'TCP Client', '00000011034', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 112, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10901, 683, 'DXY13X73', 8, 1, 'TCP Client', '00000011035', null, '01', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10902, 302, 'KJR2303121', 8, 1, 'TCP Client', '00000011036', null, '01', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10903, 302, 'KJR2303122', 8, 1, 'TCP Client', '00000011037', null, '01', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10904, 302, 'KJR2303123', 8, 1, 'TCP Client', '00000011038', null, '01', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10905, 302, 'KJR2303124', 8, 1, 'TCP Client', '00000011039', null, '01', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10906, 302, 'KJR2303125', 8, 1, 'TCP Client', '00000011040', null, '01', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10921, 463, '里183-10', 8, 1, 'TCP Client', '00000011041', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 110, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10941, 67, 'ceshi', 10, 1, 'TCP Client', '00000011000', null, '01', null, 'instance121', 'alarminstance65', 'displayinstance121', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10961, 1242, 'KJR2303128', 8, 1, 'TCP Client', '32000000001', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10962, 1242, 'KJR2303129', 8, 1, 'TCP Client', '32000000002', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10963, 1242, 'KJR2303130', 8, 1, 'TCP Client', '32000000003', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 3, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10964, 1242, 'KJR2303131', 8, 1, 'TCP Client', '32000000004', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 4, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10965, 1242, 'KJR2303132', 8, 1, 'TCP Client', '32000000005', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 5, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10966, 1242, 'KJR2303133', 8, 1, 'TCP Client', '32000000006', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 6, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10967, 1182, '白270-04', 8, 1, 'TCP Client', '32000000007', null, '01', null, 'instance152', 'alarminstance67', 'displayinstance147', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 7, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10968, 1324, '元东14-151井', 8, 1, 'TCP Client', '32000000008', null, '01', null, 'instance152', 'alarminstance67', 'displayinstance147', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 8, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10969, 1362, '关136-145', 8, 1, 'TCP Client', '32000000009', null, '01', null, 'instance152', 'alarminstance67', 'displayinstance147', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10970, 1242, 'KJR2303137', 8, 1, 'TCP Client', '32000000010', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 10, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10971, 463, '里183-109', 8, 1, 'TCP Client', '32000000011', null, '01', null, 'instance152', 'alarminstance67', 'displayinstance147', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10972, 1242, 'KJR2303139', 8, 1, 'TCP Client', '32000000012', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 12, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10973, 1242, 'KJR2303140', 8, 1, 'TCP Client', '32000000013', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 13, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10974, 1242, 'KJR2303141', 8, 1, 'TCP Client', '32000000014', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 14, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10975, 1242, 'KJR2303142', 8, 1, 'TCP Client', '32000000015', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 15, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10981, 302, 'KCS2303081', 9, 1, 'TCP Client', '00000013009', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10982, 450, 'Y34S4', 9, 1, 'TCP Client', '00000013010', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10983, 722, 'L8S2', 9, 1, 'TCP Client', '00000013011', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10984, 267, 'H292S4', 9, 1, 'TCP Client', '00000013012', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10985, 253, '镇481', 9, 1, 'TCP Client', '00000013013', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10986, 823, 'ASW410S18', 9, 1, 'TCP Client', '00000013014', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10987, 1002, 'Y284S30', 9, 1, 'TCP Client', '00000013015', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10988, 822, 'H145S16', 9, 1, 'TCP Client', '00000013016', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10989, 450, 'XH39S1', 9, 1, 'TCP Client', '00000013017', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10990, 823, 'W410S30', 9, 1, 'TCP Client', '00000013018', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11001, 842, 'L3LS3', 9, 1, 'TCP Client', '00000013019', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11002, 448, 'ZBS198(旧)', 9, 1, 'TCP Client', '00000013020', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11003, 182, 'KCS2303093', 9, 1, 'TCP Client', '00000013021', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11004, 253, 'B73S9（西377-96）', 9, 1, 'TCP Client', '00000018002', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11005, 783, 'B299S3', 9, 1, 'TCP Client', '00000013023', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11006, 463, '元546井', 8, 1, 'TCP Client', '00000011042', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11007, 1162, '庆平37', 8, 1, 'TCP Client', '00000011043', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11008, 1162, '庆平20', 8, 1, 'TCP Client', '00000011044', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11009, 1162, '庆平19', 8, 1, 'TCP Client', '00000011045', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11010, 508, '山137-02（新）', 8, 1, 'TCP Client', '00000011046', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11021, 764, '33-斜78', 8, 1, 'TCP Client', '00000000346', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11022, 764, '32-斜79', 8, 1, 'TCP Client', '00000000344', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11023, 764, '34-斜79', 8, 1, 'TCP Client', '00000000342', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 3, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11024, 764, '34-斜80', 8, 1, 'TCP Client', '00000000345', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11025, 764, '32-斜81', 8, 1, 'TCP Client', '00000000343', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 12, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11026, 764, '33-斜82', 8, 1, 'TCP Client', '00000000353', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 13, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11027, 765, '29-斜84', 8, 1, 'TCP Client', '00000000352', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11028, 765, '32-斜83', 8, 1, 'TCP Client', '00000000349', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11041, 662, '外部测试3', 8, 1, 'TCP Client', '00000001002', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11042, 84, '高77-9x', 10, 1, 'TCP Client', '00000015001', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11061, 77, '5#雁63-6-12', 10, 1, 'TCP Client', '00000015002', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 34, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11062, 77, '雁63-33阀组', 10, 1, 'TCP Client', '00000015003', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 35, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11063, 77, '4#雁63-13', 10, 1, 'TCP Client', '00000015004', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 33, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11081, 268, 'L183S10', 10, 1, 'TCP Client', '00000018001', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 301, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11082, 803, 'KD93-2', 10, 1, 'TCP Client', null, null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 302, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11083, 803, 'KD93-3', 10, 1, 'TCP Client', null, null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 303, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11084, 261, 'QB23S02', 10, 1, 'TCP Client', '00000018004', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:59:15', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 304, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11085, 448, 'ZBS198', 9, 1, 'TCP Client', '00000018005', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 305, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11086, 803, 'KD93-6', 10, 1, 'TCP Client', '00000018006', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 306, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11087, 803, 'KD93-7', 10, 1, 'TCP Client', '00000018007', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 307, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11088, 803, 'KD93-8', 10, 1, 'TCP Client', '00000018008', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 308, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11089, 803, 'KD93-9', 10, 1, 'TCP Client', '00000018009', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 309, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11090, 803, 'KD93-10', 10, 1, 'TCP Client', null, null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 310, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11092, 803, 'KD93-12', 10, 1, 'TCP Client', null, null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 312, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11094, 803, 'KD93-14', 10, 1, 'TCP Client', null, null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 314, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11095, 803, 'KD93-15', 10, 1, 'TCP Client', '00000018015', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 315, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11096, 803, 'KD93-16', 10, 1, 'TCP Client', '00000018016', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 316, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11101, 642, 'ZH219S7', 9, 1, 'TCP Client', '00000013024', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11102, 448, 'ZBS201(旧)', 9, 1, 'TCP Client', '00000013025', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('08-10-2024 10:47:36', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11103, 403, 'A8S4', 9, 1, 'TCP Client', '00000013026', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11104, 252, 'B283S2', 9, 1, 'TCP Client', '00000013027', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11105, 264, 'G143S9', 9, 1, 'TCP Client', '00000013028', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11106, 182, 'KCS2309107', 9, 1, 'TCP Client', '00000013029', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11107, 1382, '环82S15', 9, 1, 'TCP Client', '00000013030', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11108, 1282, 'A8S1（作废）', 9, 1, 'TCP Client', '00000013031', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11121, 268, '281S13', 9, 1, 'TCP Client', '00000013032', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11122, 302, 'KCS2309096', 9, 1, 'TCP Client', '00000013033', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11123, 448, 'ZBS201', 9, 1, 'TCP Client', '00000013034', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('08-10-2024 10:48:15', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11124, 902, 'L13ZS1', 9, 1, 'TCP Client', '00000013035', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11125, 268, 'L183S10（作废）', 9, 1, 'TCP Client', '00000013036', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11126, 882, 'G73S6', 9, 1, 'TCP Client', '00000013037', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11127, 862, 'wS6', 9, 1, 'TCP Client', '00000013038', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11161, 302, 'KCS2311110', 9, 1, 'TCP Client', '00000013039', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11162, 302, 'KCS2311111', 9, 1, 'TCP Client', '00000013040', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11163, 783, 'B299S4', 9, 1, 'TCP Client', '00000013041', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11164, 1062, 'Y191S1', 9, 1, 'TCP Client', '00000013042', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11165, 302, 'KCS2311114', 9, 1, 'TCP Client', '00000013043', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11181, 266, 'Y44S1', 9, 1, 'TCP Client', '00000013044', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11182, 264, 'G60S20', 9, 1, 'TCP Client', '00000013045', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11183, 264, 'G60S21', 9, 1, 'TCP Client', '00000013046', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11201, 1422, 'L38S30', 9, 1, 'TCP Client', '00000013047', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11202, 1282, 'Y166S6', 9, 1, 'TCP Client', '00000013048', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11203, 450, 'H48S30', 9, 1, 'TCP Client', '00000013049', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11221, 302, 'KCS2312118', 9, 1, 'TCP Client', '00000013050', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11222, 302, 'KCS2312119', 9, 1, 'TCP Client', '00000013051', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11223, 302, 'KCS2312120', 9, 1, 'TCP Client', '00000013052', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11224, 451, 'H154S27', 9, 1, 'TCP Client', '00000013053', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11225, 302, 'KCS2312122', 9, 1, 'TCP Client', '00000013054', null, '1', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11242, 803, 'KD93-21', 10, 1, 'TCP Client', '00000018021', null, '01', null, 'instance131', 'alarminstance67', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 321, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11261, 182, 'KJR2312163', 10, 1, 'TCP Client', '00000015005', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11262, 625, '小17-6', 10, 1, 'TCP Client', '00000015006', null, '01', null, 'instance153', 'alarminstance125', 'displayinstance118', null, null, null, null, null, null, '{}', to_date('24-09-2024 13:26:23', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11263, 182, 'KJR2312165', 10, 1, 'TCP Client', '00000015007', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11281, 263, 'G19S36', 9, 1, 'TCP Client', '00000013055', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11282, 182, 'KCS2401127', 9, 1, 'TCP Client', '00000013056', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11283, 182, 'KCS2401128', 9, 1, 'TCP Client', '00000013057', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11284, 182, 'KCS2401129', 9, 1, 'TCP Client', '00000013058', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11301, 302, 'KJR2401167', 10, 1, 'TCP Client', '00000015008', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11302, 302, 'KJR2401168', 10, 1, 'TCP Client', '00000015009', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11321, 1242, 'KJR2401166', 8, 1, 'TCP Client', '32000000016', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 16, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11361, 326, '地236-53', 8, 1, 'TCP Client', '00000018022', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11362, 325, '塬48-88', 8, 1, 'TCP Client', '00000018026', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11363, 182, 'KJR2402169', 10, 1, 'TCP Client', '00000015010', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11364, 182, 'KJR2402170', 10, 1, 'TCP Client', '00000015011', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11365, 182, 'KJR2402171', 10, 1, 'TCP Client', '00000015012', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11366, 182, 'KJR2402172', 10, 1, 'TCP Client', '00000015013', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11381, 266, 'XY44S1', 9, 1, 'TCP Client', '00000013059', null, '01', null, 'instance123', 'alarminstance104', 'displayinstance144', null, null, null, null, null, null, '{}', to_date('23-09-2024 11:37:41', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11382, 182, 'KCS2404131', 9, 1, 'TCP Client', '00000013060', null, '01', null, 'instance123', 'alarminstance104', 'displayinstance144', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11401, 802, 'KD93-27', 10, 1, 'TCP Client', '00000018027', null, '01', null, 'instance124', 'alarminstance67', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 327, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11402, 802, 'KD93-23', 10, 1, 'TCP Client', '00000018023', null, '01', null, 'instance124', 'alarminstance67', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 323, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11403, 802, 'KD93-24', 10, 1, 'TCP Client', '00000018024', null, '01', null, 'instance124', 'alarminstance67', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 324, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11404, 1282, 'A8S1', 10, 1, 'TCP Client', '00000018025', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 325, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11421, 1402, 'KCS2404132', 9, 1, 'TCP Client', '00000013061', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:03:47', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11441, 1242, 'KJR2405178', 8, 1, 'TCP Client', '32000000017', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11442, 1242, 'KJR2405179', 8, 1, 'TCP Client', '32000000018', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11461, 1402, 'KCS2404133', 9, 1, 'TCP Client', '00000013062', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:03:47', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11462, 1402, 'KCS2404134', 9, 1, 'TCP Client', '00000013063', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:03:47', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11463, 1402, 'KCS2404135', 9, 1, 'TCP Client', '00000013064', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:03:47', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11464, 1402, 'KCS2404136', 9, 1, 'TCP Client', '00000013065', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:03:47', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11481, 803, '387', 10, 1, 'TCP Client', '00000000387', null, '01', null, 'instance146', 'alarminstance67', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('21-08-2024 09:43:13', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11501, 182, 'KJR2407275', 10, 1, 'TCP Client', '00000015014', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11502, 182, 'KJR2407276', 10, 1, 'TCP Client', '00000015015', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11503, 182, 'KJR2407277', 10, 1, 'TCP Client', '00000015016', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11504, 182, 'KJR2407278', 10, 1, 'TCP Client', '00000015017', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11505, 182, 'KJR2407279', 10, 1, 'TCP Client', '00000015018', null, '01', null, 'instance124', 'alarminstance123', 'displayinstance132', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11506, 1402, 'KJR2407280', 10, 1, 'TCP Client', '00000015019', null, '01', null, 'instance205', 'alarminstance124', 'displayinstance206', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:03:47', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (170577, 27, '375', 3, 0, 'TCP Client', '00000000375', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('25-09-2024 13:33:18', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, null, 0);

/*==============================================================*/
/* 初始化辅件设备数据                                                 */
/*==============================================================*/
ALTER TRIGGER trg_b_auxiliary2master_i DISABLE;

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1426, 2, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1427, 2, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1428, 2, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1429, 2, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1346, 3, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1347, 3, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1348, 3, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1353, 4, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1354, 4, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1355, 4, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1474, 22, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1475, 22, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1476, 22, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1477, 22, 104, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1478, 22, 94, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1502, 23, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1503, 23, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1504, 23, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1505, 23, 123, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1506, 23, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1367, 24, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1368, 24, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1369, 24, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1375, 26, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1376, 26, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1377, 26, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1383, 28, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1384, 28, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1385, 28, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1386, 29, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1387, 29, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1388, 29, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1389, 30, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1390, 30, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1391, 30, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1392, 31, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1393, 31, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1394, 31, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1905, 32, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1906, 32, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1907, 32, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1404, 33, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1405, 33, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1406, 33, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1413, 35, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1414, 35, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1415, 35, 94, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1416, 35, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1430, 36, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1431, 36, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1432, 36, 87, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1433, 36, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1469, 37, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1470, 37, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1471, 37, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1472, 37, 123, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1473, 37, 94, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1743, 39, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1744, 39, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1745, 39, 125, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1746, 39, 94, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1747, 40, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1748, 40, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1749, 40, 126, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1685, 41, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1686, 41, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1687, 42, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1688, 42, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1689, 42, 125, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (240, 43, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (241, 43, 124, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (260, 44, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (261, 44, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (262, 44, 125, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1677, 45, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1678, 45, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1679, 45, 125, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (257, 46, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (258, 46, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (259, 46, 125, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1680, 47, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1681, 47, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (263, 48, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (264, 48, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (265, 48, 125, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1882, 62, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1883, 62, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1884, 62, 87, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1885, 62, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1370, 82, 102, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1371, 82, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1372, 82, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1373, 101, 103, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1374, 101, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1380, 121, 102, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1381, 121, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1382, 121, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1401, 122, 102, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1402, 122, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1403, 122, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1378, 124, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1379, 124, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1462, 300, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1463, 300, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1464, 300, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1465, 300, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1846, 305, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1847, 305, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1848, 305, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1849, 305, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1695, 306, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1696, 306, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1703, 314, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1704, 314, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1713, 316, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1714, 316, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1711, 318, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1712, 318, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1755, 319, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1756, 319, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1699, 320, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1700, 320, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1753, 322, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1754, 322, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1709, 325, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1710, 325, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1705, 327, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1706, 327, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1728, 328, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1729, 328, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1717, 329, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1718, 329, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1759, 330, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1760, 330, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1356, 341, 103, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1357, 341, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1358, 341, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1363, 342, 103, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1364, 342, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1365, 342, 104, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1366, 342, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1500, 344, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1501, 344, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1690, 346, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1691, 346, 124, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (313, 347, 145, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1576, 348, 103, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (310, 349, 145, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1750, 352, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1751, 352, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1752, 352, 123, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1398, 361, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1399, 361, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1400, 361, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1802, 373, 145, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (786, 374, 145, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1407, 381, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1408, 381, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1409, 381, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1410, 382, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1411, 382, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1412, 382, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1417, 383, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1418, 383, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1419, 383, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1420, 383, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1421, 383, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1492, 401, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1493, 401, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1494, 401, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1495, 402, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1496, 402, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1497, 402, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1498, 402, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1499, 402, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1466, 421, 103, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1467, 421, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1468, 421, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1434, 461, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1435, 461, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1436, 461, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1437, 461, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1450, 501, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1451, 501, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1452, 501, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1453, 501, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1458, 502, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1459, 502, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1460, 502, 163, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1461, 502, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1349, 521, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1350, 521, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1351, 521, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1352, 521, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1342, 522, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1343, 522, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1344, 522, 123, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1345, 522, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1276, 523, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1277, 523, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1282, 524, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1283, 524, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1488, 526, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1489, 526, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1490, 526, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1491, 526, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1479, 527, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1480, 527, 91, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1481, 527, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1482, 528, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1483, 528, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1484, 528, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1485, 529, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1486, 529, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1487, 529, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1507, 541, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1508, 541, 125, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1509, 541, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1519, 542, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1520, 542, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1521, 542, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1730, 543, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1731, 543, 125, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1732, 543, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1982, 544, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1983, 544, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1984, 544, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1733, 545, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1734, 545, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1735, 545, 163, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1736, 545, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1534, 546, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1535, 546, 162, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1536, 546, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1537, 547, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1538, 547, 162, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1539, 547, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1740, 548, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1741, 548, 125, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1742, 548, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1540, 549, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1541, 549, 162, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1542, 549, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1543, 550, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1544, 550, 162, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1545, 550, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1737, 551, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1738, 551, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1739, 551, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1725, 552, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1726, 552, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1727, 552, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1722, 553, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1723, 553, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1724, 553, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1707, 554, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1708, 554, 183, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1782, 581, 83, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1783, 581, 84, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1784, 581, 22, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1785, 581, 104, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1786, 581, 94, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (2000, 622, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (2001, 622, 91, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (2002, 622, 88, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (2003, 622, 94, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1926, 721, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1927, 721, 90, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1928, 721, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1942, 741, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1943, 741, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1962, 761, 85, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (1963, 761, 96, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (608, 10241, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (633, 10281, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (612, 10302, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (630, 10304, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (329, 10351, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (618, 10358, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (423, 10362, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (306, 10363, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (305, 10364, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (783, 10365, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (308, 10366, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (330, 10367, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (802, 10371, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (317, 10372, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (785, 10379, 142, '0,0,0');

insert into TBL_AUXILIARY2MASTER (ID, MASTERID, AUXILIARYID, MATRIX)
values (636, 10380, 142, '0,0,0');

/*==============================================================*/
/* 初始化设备附加信息数据                                                 */
/*==============================================================*/
ALTER TRIGGER trg_b_DEVICEADDINFO_i DISABLE;

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1034, 1, '测试', '11', '单位');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1035, 1, '测试', '11', '单位');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1036, 1, '测试', '11', '单位');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1037, 1, '测试', '11', '单位');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (400, 2, '泵编号', 'BGM2312050', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (401, 2, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (402, 2, '电机编号', 'M230080（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (403, 2, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (404, 2, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (405, 2, '阀座批次', '大阀座TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (406, 2, '导流罩长度', '29.38', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (407, 2, '传感器编号', 'YZ23004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (408, 2, '变频控制柜编号', 'KBP2206010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (409, 2, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (410, 2, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (411, 2, '泵挂深度', '830.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (136, 3, '泵编号', 'BGM2306020', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (137, 3, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (138, 3, '电机编号', 'M230061（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (139, 3, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (140, 3, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (141, 3, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (142, 3, '导流罩长度', '20', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (143, 3, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (144, 3, '变频控制柜编号', 'KBP2011017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (145, 3, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (146, 3, '泄压接箍泄漏量', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (147, 3, '泵挂位置', '792', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (160, 4, '泵编号', 'BGM2306021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (161, 4, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (162, 4, '电机编号', 'M230055（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (163, 4, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (164, 4, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (165, 4, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (166, 4, '导流罩长度', '47.3', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (167, 4, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (168, 4, '变频控制柜编号', 'KBP2105018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (169, 4, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (170, 4, '泄压接箍泄漏量', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (171, 4, '泵挂位置', '824', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (495, 22, '泵编号', 'BGM2107033', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (496, 22, '泵规格型号', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (497, 22, '电机编号', 'M210192', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (498, 22, '胶囊批次', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (499, 22, '隔膜批次', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (500, 22, '阀座批次', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (501, 22, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (502, 22, '传感器编号', 'CZB2004002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (503, 22, '变频控制柜编号', 'KBP2010008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (504, 22, '载波控制柜编号', 'KZB2010008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (505, 22, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4310, 23, '泵编号', 'BGM2304017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4311, 23, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4312, 23, '电机编号', 'M210183(XFM220816)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4313, 23, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4314, 23, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4315, 23, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4316, 23, '导流罩长度', '29.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4317, 23, '传感器编号', 'CZB2011007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4318, 23, '变频控制柜编号', 'KBP2011011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4319, 23, '载波控制柜编号', 'KZB2011015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4320, 23, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4321, 23, '泵挂深度', '658.93', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (196, 24, '泵编号', 'BGM2212509', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (197, 24, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (198, 24, '电机编号', 'M210180(XFM220822)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (199, 24, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (200, 24, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (201, 24, '阀座批次', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (202, 24, '导流罩长度', '71.15', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (203, 24, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (204, 24, '变频控制柜编号', 'KBP2010006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (205, 24, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (206, 24, '泄压接箍泄漏量', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (207, 24, '泵挂位置', '747.47', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (232, 26, '泵编号', 'BGM2212508', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (233, 26, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (234, 26, '电机编号', 'M200284(XFM220815)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (235, 26, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (236, 26, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (237, 26, '阀座批次', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (238, 26, '导流罩长度', '20', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (239, 26, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (240, 26, '变频控制柜编号', 'KBP2104001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (241, 26, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (242, 26, '泄压接箍泄漏量', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (243, 26, '泵挂位置', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (268, 28, '泵编号', 'BGM2212507', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (269, 28, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (270, 28, '电机编号', 'M190190(XFM220819)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (271, 28, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (272, 28, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (273, 28, '阀座批次', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (274, 28, '导流罩长度', '19.92', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (275, 28, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (276, 28, '变频控制柜编号', 'KBP2010010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (277, 28, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (278, 28, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (279, 28, '泵挂位置', '827', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (280, 29, '泵编号', 'BGM1912001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (281, 29, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (282, 29, '电机编号', 'D190902-4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (283, 29, '胶囊批次', 'NX01-HSN-20191101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (284, 29, '隔膜批次', 'MY01-DJ-20190301', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (285, 29, '阀座批次', 'TK01-YHG-20191101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (286, 29, '导流罩长度', '25', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (287, 29, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (288, 29, '变频控制柜编号', 'KBP2011016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (289, 29, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (290, 29, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (291, 29, '泵挂位置', '865', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (292, 30, '泵编号', 'BGM2207029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (293, 30, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (294, 30, '电机编号', 'M220299', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (295, 30, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (296, 30, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (297, 30, '阀座批次', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (298, 30, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (299, 30, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (300, 30, '变频控制柜编号', 'KBP2011012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (301, 30, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (302, 30, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (303, 30, '泵挂深度', '866.98', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (304, 31, '泵编号', 'BGM2207034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (305, 31, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (306, 31, '电机编号', 'M220311', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (307, 31, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (308, 31, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (309, 31, '阀座批次', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (310, 31, '导流罩长度', '19.3', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (311, 31, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (312, 31, '变频控制柜编号', 'KBP2104004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (313, 31, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (314, 31, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (315, 31, '泵挂深度', '916.07', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1062, 32, '泵编号', 'BGM2110056', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1063, 32, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1064, 32, '电机编号', 'M210413', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1065, 32, '胶囊批次', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1066, 32, '隔膜批次', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1067, 32, '阀座批次', 'TK02-YGM-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1068, 32, '导流罩长度', '10', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1069, 32, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1070, 32, '变频控制柜编号', 'KBP2011015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1071, 32, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1072, 32, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1073, 32, '泵挂深度', '1115', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (340, 33, '泵编号', 'BGM2306022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (341, 33, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (342, 33, '电机编号', 'M230058（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (343, 33, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (344, 33, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (345, 33, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (346, 33, '导流罩长度', '20.44', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (347, 33, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (348, 33, '变频控制柜编号', 'KBP2104002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (349, 33, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (350, 33, '泄压接箍泄漏量', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (351, 33, '泵挂位置', '1194.66', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (376, 35, '泵编号', 'BGM2106019', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (377, 35, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (378, 35, '电机编号', 'M210177', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (379, 35, '胶囊批次', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (380, 35, '隔膜批次', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (381, 35, '阀座批次', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (382, 35, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (383, 35, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (384, 35, '变频控制柜编号', 'KBP2011019', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (385, 35, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (386, 35, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (387, 35, '泵挂位置', '1063', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (412, 36, '泵编号', 'BGM2110058', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (413, 36, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (414, 36, '电机编号', 'M210407', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (415, 36, '胶囊批次', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (416, 36, '隔膜批次', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (417, 36, '阀座批次', 'TK02-YGM-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (418, 36, '导流罩长度', '19', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (419, 36, '传感器编号', 'CZB2110019', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (420, 36, '变频控制柜编号', 'KBP2105024', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (421, 36, '载波控制柜编号', 'KZB2011012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (422, 36, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (423, 36, '泵挂位置', '1280', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (484, 37, '泵编号', 'BGM2107029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (485, 37, '泵规格型号', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (486, 37, '电机编号', 'M210181', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (487, 37, '胶囊批次', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (488, 37, '隔膜批次', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (489, 37, '阀座批次', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (490, 37, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (491, 37, '传感器编号', 'CZB2104009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (492, 37, '变频控制柜编号', 'KBP2105010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (493, 37, '载波控制柜编号', 'KZB2104001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (494, 37, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (956, 39, '泵编号', 'BGM2105013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (957, 39, '泵规格型号', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (958, 39, '电机编号', 'M210069', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (959, 39, '胶囊批次', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (960, 39, '隔膜批次', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (961, 39, '阀座批次', 'TK02-YGM-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (962, 39, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (963, 39, '传感器编号', 'CZB2104005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (964, 39, '变频控制柜编号', 'KBP2105008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (965, 39, '载波控制柜编号', 'KZB2011018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (966, 39, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (967, 40, '泵编号', 'BGM2106021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (968, 40, '泵规格型号', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (969, 40, '电机编号', 'M210179', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (970, 40, '胶囊批次', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (971, 40, '隔膜批次', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (972, 40, '阀座批次', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (973, 40, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (974, 40, '传感器编号', 'CZB2011006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (975, 40, '变频控制柜编号', 'KBP2105009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (976, 40, '载波控制柜编号', 'KZB2010010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (977, 40, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (730, 41, '泵编号', 'BGM2210048', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (731, 41, '泵规格型号', 'JM5-7-1700FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (732, 41, '电机编号', 'M210398(XFM220645)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (733, 41, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (734, 41, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (735, 41, '阀座批次', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (736, 41, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (737, 41, '传感器编号', 'CZB2209032', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (738, 41, '变频控制柜编号', 'KBP2011013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (739, 41, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (740, 41, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (741, 42, '泵编号', 'BGM2012029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (742, 42, '泵规格型号', 'JM5-7-1500F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (743, 42, '电机编号', 'M200280', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (744, 42, '胶囊批次', 'NX01-HSN-20200401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (745, 42, '隔膜批次', 'RS01-XXX-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (746, 42, '阀座批次', 'TK02-YGM-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (747, 42, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (748, 42, '传感器编号', 'CZB2011008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (749, 42, '变频控制柜编号', 'KBP2010009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (750, 42, '载波控制柜编号', 'KZB2104002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (751, 42, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1, 43, '泵编号', 'BGM2011018（20023）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2, 43, '泵规格型号', 'JM5-7-1500F（QYDB114-5/1700A-GM)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3, 43, '电机编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4, 43, '胶囊批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (5, 43, '隔膜批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (6, 43, '阀座批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (7, 43, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (8, 43, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (9, 43, '变频控制柜编号', 'KBP2010001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (10, 43, '载波控制柜编号', 'KZB2010001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (11, 43, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (67, 44, '泵编号', 'BGM2110060', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (68, 44, '泵规格型号', 'JM5-5-1700F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (69, 44, '电机编号', 'M200285(XFM220116)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (70, 44, '胶囊批次', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (71, 44, '隔膜批次', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (72, 44, '阀座批次', 'TK02-YGM-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (73, 44, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (74, 44, '传感器编号', 'CZB2109017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (75, 44, '变频控制柜编号', 'KBP2105027', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (76, 44, '载波控制柜编号', 'KZB2106007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (77, 44, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (708, 45, '泵编号', 'BGM2209042', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (709, 45, '泵规格型号', 'JM5-7-1500FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (710, 45, '电机编号', 'M210416', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (711, 45, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (712, 45, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (713, 45, '阀座批次', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (714, 45, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (715, 45, '传感器编号', 'CZB2011003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (716, 45, '变频控制柜编号', 'KBP2105026', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (717, 45, '载波控制柜编号', 'KZB2106010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (718, 45, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (56, 46, '泵编号', 'BGM2203013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (57, 46, '泵规格型号', 'JM5-7-1500F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (58, 46, '电机编号', 'M210419', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (59, 46, '胶囊批次', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (60, 46, '隔膜批次', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (61, 46, '阀座批次', 'TK03-YHG-20220301', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (62, 46, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (63, 46, '传感器编号', 'CZB2011010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (64, 46, '变频控制柜编号', 'KBP2107028', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (65, 46, '载波控制柜编号', 'KZB2106014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (66, 46, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (719, 47, '泵编号', 'BGM2108039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (720, 47, '泵规格型号', 'EDN5-4-1600', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (721, 47, '电机编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (722, 47, '胶囊批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (723, 47, '隔膜批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (724, 47, '阀座批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (725, 47, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (726, 47, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (727, 47, '变频控制柜编号', 'KBP2105023', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (728, 47, '载波控制柜编号', 'KZB2106008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (729, 47, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (78, 48, '泵编号', 'BGM2108044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (79, 48, '泵规格型号', 'JM5-7-1500F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (80, 48, '电机编号', 'D181209-4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (81, 48, '胶囊批次', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (82, 48, '隔膜批次', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (83, 48, '阀座批次', 'TK02-YGM-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (84, 48, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (85, 48, '传感器编号', '438253', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (86, 48, '变频控制柜编号', 'KBP2105026', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (87, 48, '载波控制柜编号', 'KZB2106010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (88, 48, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1050, 62, '泵编号', 'BGM2203011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1051, 62, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1052, 62, '电机编号', 'M210399', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1053, 62, '胶囊批次', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1054, 62, '隔膜批次', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1055, 62, '阀座批次', 'TK03-YHG-20220301', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1056, 62, '导流罩长度', '24', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1057, 62, '传感器编号', 'CZB2203004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1058, 62, '变频控制柜编号', 'KBP2107035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1059, 62, '载波控制柜编号', 'KZB2011016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1060, 62, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1061, 62, '泵挂位置', '988.37', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (208, 82, '泵编号', 'BGM2401506', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (209, 82, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (210, 82, '电机编号', 'M210404(XFM220807)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (211, 82, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (212, 82, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (213, 82, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (214, 82, '导流罩长度', '48.75', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (215, 82, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (216, 82, '变频控制柜编号', '201907007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (217, 82, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (218, 82, '泄压接箍泄漏量', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (219, 82, '泵挂位置', '670', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (220, 101, '泵编号', 'BGM2306508', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (221, 101, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (222, 101, '电机编号', 'M210405(XFM220808)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (223, 101, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (224, 101, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (225, 101, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (226, 101, '导流罩长度', '61.03', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (227, 101, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (228, 101, '变频控制柜编号', '201907006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (229, 101, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (230, 101, '泄压接箍泄漏量', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (231, 101, '泵挂位置', '723.73', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (256, 121, '泵编号', 'BGM2104010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (257, 121, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (258, 121, '电机编号', 'M210071', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (259, 121, '胶囊批次', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (260, 121, '隔膜批次', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (261, 121, '阀座批次', 'TK02-YGM-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (262, 121, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (263, 121, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (264, 121, '变频控制柜编号', '201907002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (265, 121, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (266, 121, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (267, 121, '泵挂位置', '980', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (328, 122, '泵编号', 'BGM2111068', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (329, 122, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (330, 122, '电机编号', 'M210427', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (331, 122, '胶囊批次', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (332, 122, '隔膜批次', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (333, 122, '阀座批次', 'TK02-YGM-20220101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (334, 122, '导流罩长度', '19.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (335, 122, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (336, 122, '变频控制柜编号', '201907004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (337, 122, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (338, 122, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (339, 122, '泵挂深度', '990', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (244, 124, '泵编号', 'BGM2205022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (245, 124, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (246, 124, '电机编号', 'M210195(XFM220112)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (247, 124, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (248, 124, '隔膜批次', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (249, 124, '阀座批次', 'TK02-YGM-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (250, 124, '导流罩长度', '23.87', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (251, 124, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (252, 124, '变频控制柜编号', 'KBP2010007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (253, 124, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (254, 124, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (255, 124, '泵挂位置', '646.97', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (460, 300, '泵编号', 'BGM2303011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (461, 300, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (462, 300, '电机编号', 'M220568', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (463, 300, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (464, 300, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (465, 300, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (466, 300, '导流罩长度', '52.67', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (467, 300, '传感器编号', 'CZB2303017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (468, 300, '变频控制柜编号', 'KBP2208034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (469, 300, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (470, 300, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (471, 300, '泵挂深度', '807.78', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (785, 303, '泵编号', 'BGM2211062', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (786, 303, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (787, 303, '电机编号', 'M220561', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (788, 303, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (789, 303, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (790, 303, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (791, 303, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (792, 303, '传感器编号', 'CZB2211048', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (793, 303, '变频控制柜编号', 'KBP2206008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (794, 303, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (795, 303, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1038, 305, '泵编号', 'BGM2304013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1039, 305, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1040, 305, '电机编号', 'M220575', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1041, 305, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1042, 305, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1043, 305, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1044, 305, '导流罩长度', '48.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1045, 305, '传感器编号', 'CZB2203003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1046, 305, '变频控制柜编号', 'KBP2207022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1047, 305, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1048, 305, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1049, 305, '泵挂位置', '1251', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (763, 306, '泵编号', 'BGM2308510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (764, 306, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (765, 306, '电机编号', 'M210415（M230201）带PT100热电阻引线', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (766, 306, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (767, 306, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (768, 306, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (769, 306, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (770, 306, '传感器编号', 'CZB2308502', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (771, 306, '变频控制柜编号', 'KBP2206005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (772, 306, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (773, 306, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (796, 314, '泵编号', 'BGM2211060', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (797, 314, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (798, 314, '电机编号', 'M220313', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (799, 314, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (800, 314, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (801, 314, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (802, 314, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (803, 314, '传感器编号', 'CZB2211043', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (804, 314, '变频控制柜编号', 'KBP2206007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (805, 314, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (806, 314, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (851, 316, '泵编号', 'BGM2211061', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (852, 316, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (853, 316, '电机编号', 'M220319', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (854, 316, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (855, 316, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (856, 316, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (857, 316, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (858, 316, '传感器编号', 'CZB2211046', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (859, 316, '变频控制柜编号', 'KBP2207023', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (860, 316, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (861, 316, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (840, 318, '泵编号', 'BGM2211057', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (841, 318, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (842, 318, '电机编号', 'M220320', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (843, 318, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (844, 318, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (845, 318, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (846, 318, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (847, 318, '传感器编号', 'CZB2210040', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (848, 318, '变频控制柜编号', 'KBP2207021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (849, 318, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (850, 318, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1000, 319, '泵编号', 'BGM2211063', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1001, 319, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1002, 319, '电机编号', 'M220558', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1003, 319, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1004, 319, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1005, 319, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1006, 319, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1007, 319, '传感器编号', 'CZB2211047', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1008, 319, '变频控制柜编号', 'KBP2207027', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1009, 319, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1010, 319, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (774, 320, '泵编号', 'BGM2210050', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (775, 320, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (776, 320, '电机编号', 'M220297', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (777, 320, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (778, 320, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (779, 320, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (780, 320, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (781, 320, '传感器编号', 'CZB2209036', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (782, 320, '变频控制柜编号', 'KBP2206007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (783, 320, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (784, 320, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (989, 322, '泵编号', 'BGM2210051', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (990, 322, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (991, 322, '电机编号', 'M220312', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (992, 322, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (993, 322, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (994, 322, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (995, 322, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (996, 322, '传感器编号', 'CZB2209035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (997, 322, '变频控制柜编号', 'KBP2206003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (998, 322, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (999, 322, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (829, 325, '泵编号', 'BGM2210053', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (830, 325, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (831, 325, '电机编号', 'M220305', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (832, 325, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (833, 325, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (834, 325, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (835, 325, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (836, 325, '传感器编号', 'CZB2210039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (837, 325, '变频控制柜编号', 'KBP2206009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (838, 325, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (839, 325, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (807, 327, '泵编号', 'BGM2210054', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (808, 327, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (809, 327, '电机编号', 'M220315', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (810, 327, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (811, 327, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (812, 327, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (813, 327, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (814, 327, '传感器编号', 'CZB2210041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (815, 327, '变频控制柜编号', 'KBP2207024', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (816, 327, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (817, 327, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (897, 328, '泵编号', 'BGM2211059', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (898, 328, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (899, 328, '电机编号', 'M220309', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (900, 328, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (901, 328, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (902, 328, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (903, 328, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (904, 328, '传感器编号', 'CZB2211044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (905, 328, '变频控制柜编号', 'KBP2207028', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (906, 328, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (907, 328, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (862, 329, '泵编号', 'BGM2308509', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (863, 329, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (864, 329, '电机编号', 'M210186(M230196)（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (865, 329, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (866, 329, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (867, 329, '阀座批次', '大阀座TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (868, 329, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (869, 329, '传感器编号', 'CZB2211045', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (870, 329, '变频控制柜编号', 'KBP2206004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (871, 329, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (872, 329, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1011, 330, '泵编号', 'BGM2211056', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1012, 330, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1013, 330, '电机编号', 'M220300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1014, 330, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1015, 330, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1016, 330, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1017, 330, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1018, 330, '传感器编号', 'CZB2210038', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1019, 330, '变频控制柜编号', 'KBP2206003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1020, 330, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1021, 330, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (172, 341, '泵编号', 'BGM2212505', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (173, 341, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (174, 341, '电机编号', 'M210073(XFM220640)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (175, 341, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (176, 341, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (177, 341, '阀座批次', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (178, 341, '导流罩长度', '51.93', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (179, 341, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (180, 341, '变频控制柜编号', '201907006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (181, 341, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (182, 341, '泄压接箍泄漏量', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (183, 341, '泵挂位置', '857.55', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (184, 342, '泵编号', 'BGM2006013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (185, 342, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (186, 342, '电机编号', 'M190198', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (187, 342, '胶囊批次', 'NX01-HSN-20191101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (188, 342, '隔膜批次', 'JZ01-DJ-20190901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (189, 342, '阀座批次', 'TK02-YGM-20200401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (190, 342, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (191, 342, '传感器编号', 'CZB2006001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (192, 342, '变频控制柜编号', 'KBP2006002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (193, 342, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (194, 342, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (195, 342, '泵挂位置', '798', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (89, 343, '泵编号', 'BGM2401503', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (90, 343, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (91, 343, '电机编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (92, 343, '胶囊批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (93, 343, '隔膜批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (94, 343, '阀座批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (95, 343, '导流罩长度', '38.6m（4根油管）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (96, 343, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (97, 343, '变频控制柜编号', 'KBP2105016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (98, 343, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (99, 343, '泄压接箍泄漏量', '0.6mm', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (590, 344, '泵编号', 'BGM2212504', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (591, 344, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (592, 344, '电机编号', 'M200281(XFM220820)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (593, 344, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (594, 344, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (595, 344, '阀座批次', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (596, 344, '导流罩长度', '34.4', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (597, 344, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (598, 344, '变频控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (599, 344, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (600, 344, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (601, 344, '泵挂深度', '795', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (697, 345, '泵编号', 'BGM2012026（20027）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (698, 345, '泵规格型号', 'JM5-10-1200F（QYDB114-10/1200A-GM)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (699, 345, '电机编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (700, 345, '胶囊批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (701, 345, '隔膜批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (702, 345, '阀座批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (703, 345, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (704, 345, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (705, 345, '变频控制柜编号', 'KBP2010004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (706, 345, '载波控制柜编号', 'KZB2010004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (707, 345, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (752, 346, '泵编号', 'BGM2011019（20024）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (753, 346, '泵规格型号', 'JM5-7-1500F（QYDB114-5/1700A-GM)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (754, 346, '电机编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (755, 346, '胶囊批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (756, 346, '隔膜批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (757, 346, '阀座批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (758, 346, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (759, 346, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (760, 346, '变频控制柜编号', 'KBP2010002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (761, 346, '载波控制柜编号', 'KZB2010002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (762, 346, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (12, 347, '泵编号', 'BGM2011022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (13, 347, '泵规格型号', 'JM5-7-1500F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (14, 347, '电机编号', 'M190195', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (15, 347, '胶囊批次', 'NX01-HSN-20200401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (16, 347, '隔膜批次', 'RS01-XXX-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (17, 347, '阀座批次', 'TK02-YGM-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (18, 347, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (19, 347, '传感器编号', 'CZB2011004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (20, 347, '变频控制柜编号', 'KBP2006003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (21, 347, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (22, 347, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (686, 348, '泵编号', 'BGM2009015(20020)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (687, 348, '泵规格型号', 'END5-4-1800-P1-TM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (688, 348, '电机编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (689, 348, '胶囊批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (690, 348, '隔膜批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (691, 348, '阀座批次', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (692, 348, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (693, 348, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (694, 348, '变频控制柜编号', 'KBP2006004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (695, 348, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (696, 348, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (23, 349, '泵编号', 'BGM2110061', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (24, 349, '泵规格型号', 'JM5-3.5-2000F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (25, 349, '电机编号', 'M210075(XFM220122)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (26, 349, '胶囊批次', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (27, 349, '隔膜批次', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (28, 349, '阀座批次', 'TK02-YGM-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (29, 349, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (30, 349, '传感器编号', 'CZB2104010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (31, 349, '变频控制柜编号', 'KBP2006001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (32, 349, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (33, 349, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (34, 350, '泵编号', '201907002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (35, 350, '泵规格型号', 'JM5-5-1700F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (36, 350, '电机编号', 'D190905-4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (37, 350, '胶囊批次', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (38, 350, '隔膜批次', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (39, 350, '阀座批次', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (40, 350, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (41, 350, '传感器编号', '438409', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (42, 350, '变频控制柜编号', '201907005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (43, 350, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (44, 350, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (45, 351, '泵编号', 'EP1909002(BGM2105015)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (46, 351, '泵规格型号', 'JM5-3.5-2000F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (47, 351, '电机编号', 'D181202-4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (48, 351, '胶囊批次', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (49, 351, '隔膜批次', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (50, 351, '阀座批次', 'TK02-YGM-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (51, 351, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (52, 351, '传感器编号', 'CZB2104007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (53, 351, '变频控制柜编号', 'KBP2006005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (54, 351, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (55, 351, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (978, 352, '泵编号', 'BGM2106017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (979, 352, '泵规格型号', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (980, 352, '电机编号', 'M210176', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (981, 352, '胶囊批次', 'NX01-HSN-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (982, 352, '隔膜批次', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (983, 352, '阀座批次', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (984, 352, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (985, 352, '传感器编号', 'CZB2008001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (986, 352, '变频控制柜编号', 'KBP2105011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (987, 352, '载波控制柜编号', 'KZB2104003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (988, 352, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (316, 361, '泵编号', 'BGM2302003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (317, 361, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (318, 361, '电机编号', 'M220567', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (319, 361, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (320, 361, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (321, 361, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (322, 361, '导流罩长度', '34.1', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (323, 361, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (324, 361, '变频控制柜编号', 'KBP2105017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (325, 361, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (326, 361, '泄压接箍泄漏量', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (327, 361, '泵挂位置', '1140', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (352, 381, '泵编号', 'BGM2207035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (353, 381, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (354, 381, '电机编号', 'M220308', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (355, 381, '胶囊批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (356, 381, '隔膜批次', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (357, 381, '阀座批次', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (358, 381, '导流罩长度', '33.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (359, 381, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (360, 381, '变频控制柜编号', 'KBP2105022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (361, 381, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (362, 381, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (363, 381, '泵挂位置', '983', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (364, 382, '泵编号', 'BGM2401504', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (365, 382, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (366, 382, '电机编号', 'M210412', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (367, 382, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (368, 382, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (369, 382, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (370, 382, '导流罩长度', '24.1', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (371, 382, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (372, 382, '变频控制柜编号', 'KBP2107034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (373, 382, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (374, 382, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (375, 382, '泵挂位置', '890.56', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (388, 383, '泵编号', 'BGM2304018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (389, 383, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (390, 383, '电机编号', 'M210423(XFM220812)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (391, 383, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (392, 383, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (393, 383, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (394, 383, '导流罩长度', '52.76', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (395, 383, '传感器编号', 'CZB2303022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (396, 383, '变频控制柜编号', 'KBP2105013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (397, 383, '载波控制柜编号', 'KZB2104006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (398, 383, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (399, 383, '泵挂深度', '968.3', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (554, 401, '泵编号', 'BGM2308033', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (555, 401, '泵规格型号', 'JM-15-850D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (556, 401, '电机编号', 'M230069（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (557, 401, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (558, 401, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (559, 401, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (560, 401, '导流罩长度', '29', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (561, 401, '传感器编号', 'CZB2308033', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (562, 401, '变频控制柜编号', 'KBP2306010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (563, 401, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (564, 401, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (565, 401, '泵挂深度', '461.07', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (566, 402, '泵编号', 'BGM2301001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (567, 402, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (568, 402, '电机编号', 'M220576', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (569, 402, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (570, 402, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (571, 402, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (572, 402, '导流罩长度', '44.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (573, 402, '传感器编号', 'CZB2208023', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (574, 402, '变频控制柜编号', 'KBP2107032', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (575, 402, '载波控制柜编号', 'KZB2011013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (576, 402, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (577, 402, '泵挂深度', '943', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (472, 421, '泵编号', 'BGM2303009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (473, 421, '泵规格型号', 'JM5-10-1200', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (474, 421, '电机编号', 'M220574', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (475, 421, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (476, 421, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (477, 421, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (478, 421, '导流罩长度', '49.05', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (479, 421, '传感器编号', 'CZB2303018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (480, 421, '变频控制柜编号', 'KBP2107006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (481, 421, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (482, 421, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (483, 421, '泵挂位置', '1157', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (424, 461, '泵编号', 'BGM2304012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (425, 461, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (426, 461, '电机编号', 'M220571', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (427, 461, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (428, 461, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (429, 461, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (430, 461, '导流罩长度', '47.67', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (431, 461, '传感器编号', 'CZB2303020', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (432, 461, '变频控制柜编号', 'KBP2208037', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (433, 461, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (434, 461, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (435, 461, '泵挂深度', '1014', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (436, 501, '泵编号', 'BGM2303008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (437, 501, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (438, 501, '电机编号', 'M220562', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (439, 501, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (440, 501, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (441, 501, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (442, 501, '导流罩长度', '24.24', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (443, 501, '传感器编号', 'CZB2303021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (444, 501, '变频控制柜编号', 'KBP2208035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (445, 501, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (446, 501, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (447, 501, '泵挂深度', '968.3', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (448, 502, '泵编号', 'BGM2306026', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (449, 502, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (450, 502, '电机编号', 'M230074（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (451, 502, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (452, 502, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (453, 502, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (454, 502, '导流罩长度', '19.92', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (455, 502, '传感器编号', 'YZ23002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (456, 502, '变频控制柜编号', 'KBP2306002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (457, 502, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (458, 502, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (459, 502, '泵挂深度', '968.3', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (148, 521, '泵编号', 'BGM2302007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (149, 521, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (150, 521, '电机编号', 'M220563', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (151, 521, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (152, 521, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (153, 521, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (154, 521, '导流罩长度', '43.9', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (155, 521, '传感器编号', 'CZB2208022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (156, 521, '变频控制柜编号', 'KBP2208032', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (157, 521, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (158, 521, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (159, 521, '泵挂深度', '856', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3941, 522, '泵编号', 'BGM2107026', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3942, 522, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3943, 522, '电机编号', 'M220560', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3944, 522, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3945, 522, '隔膜批次', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3946, 522, '阀座批次', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3947, 522, '导流罩长度', '48.75', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3948, 522, '传感器编号', 'CZB2104008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3949, 522, '变频控制柜编号', 'KBP2306004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3950, 522, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3951, 522, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3952, 522, '泵挂位置', '559.97', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (100, 523, '泵编号', 'BGM2212504', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (101, 523, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (102, 523, '电机编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (103, 523, '胶囊批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (104, 523, '隔膜批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (105, 523, '阀座批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (106, 523, '导流罩长度', '34.4', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (107, 523, '传感器编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (108, 523, '变频控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (109, 523, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (110, 523, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (111, 523, '泵挂位置', '795', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (112, 524, '泵编号', 'BGM2306506', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (113, 524, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (114, 524, '电机编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (115, 524, '胶囊批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (116, 524, '隔膜批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (117, 524, '阀座批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (118, 524, '导流罩长度', '53.255', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (119, 524, '传感器编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (120, 524, '变频控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (121, 524, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (122, 524, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (123, 524, '泵挂位置', '578.55', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (578, 525, '泵编号', 'BGM2306506', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (579, 525, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (580, 525, '电机编号', 'M210074(XFM220818)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (581, 525, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (582, 525, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (583, 525, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (584, 525, '导流罩长度', '53.255', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (585, 525, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (586, 525, '变频控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (587, 525, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (588, 525, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (589, 525, '泵挂深度', '578.55', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (542, 526, '泵编号', 'BGM2304014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (543, 526, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (544, 526, '电机编号', 'M220559', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (545, 526, '胶囊批次', '电机胶囊NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (546, 526, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (547, 526, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (548, 526, '导流罩长度', '23.46', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (549, 526, '传感器编号', 'CZB2202002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (550, 526, '变频控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (551, 526, '载波控制柜编号', 'KZB2106011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (552, 526, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (553, 526, '泵挂深度', '943', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (506, 527, '泵编号', 'BGM2302006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (507, 527, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (508, 527, '电机编号', 'M220573', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (509, 527, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (510, 527, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (511, 527, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (512, 527, '导流罩长度', '53.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (513, 527, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (514, 527, '变频控制柜编号', 'KBP2105025', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (515, 527, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (516, 527, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (517, 527, '泵挂深度', '1050', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (518, 528, '泵编号', 'BGM2306023', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (519, 528, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (520, 528, '电机编号', 'M230075（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (521, 528, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (522, 528, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (523, 528, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (524, 528, '导流罩长度', '30.6', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (525, 528, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (526, 528, '变频控制柜编号', 'KBP2105012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (527, 528, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (528, 528, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (529, 528, '泵挂深度', '737.33', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (530, 529, '泵编号', 'BGM2306025', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (531, 529, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (532, 529, '电机编号', 'M230066（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (533, 529, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (534, 529, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (535, 529, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (536, 529, '导流罩长度', '48.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (537, 529, '传感器编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (538, 529, '变频控制柜编号', 'KBP2105020', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (539, 529, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (540, 529, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (541, 529, '泵挂深度', '560', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (614, 541, '泵编号', 'BGM2301503', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (615, 541, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (616, 541, '电机编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (617, 541, '胶囊批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (618, 541, '隔膜批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (619, 541, '阀座批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (620, 541, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (621, 541, '传感器编号', '438253', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (622, 541, '变频控制柜编号', 'KBP2306011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (623, 541, '载波控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (624, 541, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (625, 541, '泵挂深度', '710', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (626, 542, '泵编号', 'BGM2309039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (627, 542, '泵规格型号', 'JM5-15-850FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (628, 542, '电机编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (629, 542, '胶囊批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (630, 542, '隔膜批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (631, 542, '阀座批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (632, 542, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (633, 542, '传感器编号', 'CZB2308032', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (634, 542, '变频控制柜编号', 'KBP2306008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (635, 542, '载波控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (636, 542, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (637, 542, '泵挂深度', '692', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (908, 543, '泵编号', 'BGM2301504', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (909, 543, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (910, 543, '电机编号', 'M210430(XFM220813)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (911, 543, '胶囊批次', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (912, 543, '隔膜批次', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (913, 543, '阀座批次', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (914, 543, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (915, 543, '传感器编号', 'CZB2104010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (916, 543, '变频控制柜编号', 'KBP2306005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (917, 543, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (918, 543, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (919, 543, '泵挂深度', '704', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1074, 544, '泵编号', 'BGM2311047', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1075, 544, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1076, 544, '电机编号', 'M230056（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1077, 544, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1078, 544, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1079, 544, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1080, 544, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1081, 544, '传感器编号', 'CZB2311040', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1082, 544, '变频控制柜编号', 'KBP2208036', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1083, 544, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1084, 544, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1085, 544, '泵挂深度', '582', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (920, 545, '泵编号', 'BGM2311044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (921, 545, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (922, 545, '电机编号', 'M230071（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (923, 545, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (924, 545, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (925, 545, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (926, 545, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (927, 545, '传感器编号', 'CZB2311039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (928, 545, '变频控制柜编号', 'KBP2306006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (929, 545, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (930, 545, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (931, 545, '泵挂深度', '723.6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (638, 546, '泵编号', 'BGM2311049', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (639, 546, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (640, 546, '电机编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (641, 546, '胶囊批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (642, 546, '隔膜批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (643, 546, '阀座批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (644, 546, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (645, 546, '传感器编号', 'CZB2311044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (646, 546, '变频控制柜编号', 'KBP2306009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (647, 546, '载波控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (648, 546, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (649, 546, '泵挂深度', '782', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (650, 547, '泵编号', 'BGM2311048', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (651, 547, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (652, 547, '电机编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (653, 547, '胶囊批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (654, 547, '隔膜批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (655, 547, '阀座批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (656, 547, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (657, 547, '传感器编号', 'CZB2311043', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (658, 547, '变频控制柜编号', 'KBP2306007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (659, 547, '载波控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (660, 547, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (661, 547, '泵挂深度', '615', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (944, 548, '泵编号', 'BGM2205015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (945, 548, '泵规格型号', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (946, 548, '电机编号', 'M210420', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (947, 548, '胶囊批次', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (948, 548, '隔膜批次', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (949, 548, '阀座批次', 'TK02-YGM-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (950, 548, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (951, 548, '传感器编号', '438401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (952, 548, '变频控制柜编号', 'KBP2206003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (953, 548, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (954, 548, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (955, 548, '泵挂深度', '810', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (662, 549, '泵编号', 'BGM2311045', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (663, 549, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (664, 549, '电机编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (665, 549, '胶囊批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (666, 549, '隔膜批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (667, 549, '阀座批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (668, 549, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (669, 549, '传感器编号', 'CZB2311041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (670, 549, '变频控制柜编号', 'KBP2307029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (671, 549, '载波控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (672, 549, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (673, 549, '泵挂深度', '670', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (674, 550, '泵编号', 'BGM2311042', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (675, 550, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (676, 550, '电机编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (677, 550, '胶囊批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (678, 550, '隔膜批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (679, 550, '阀座批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (680, 550, '导流罩长度', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (681, 550, '传感器编号', 'CZB2309503', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (682, 550, '变频控制柜编号', 'KBP2207030', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (683, 550, '载波控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (684, 550, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (685, 550, '泵挂深度', '615', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (932, 551, '泵编号', 'BGM2311046', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (933, 551, '泵规格型号', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (934, 551, '电机编号', 'M230051（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (935, 551, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (936, 551, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (937, 551, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (938, 551, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (939, 551, '传感器编号', 'CZB2311042', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (940, 551, '变频控制柜编号', 'KBP2208033', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (941, 551, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (942, 551, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (943, 551, '泵挂深度', '670', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (885, 552, '泵编号', 'BGM2309034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (886, 552, '泵规格型号', 'JM5-20-700FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (887, 552, '电机编号', 'M230067（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (888, 552, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (889, 552, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (890, 552, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (891, 552, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (892, 552, '传感器编号', 'CZB2308031', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (893, 552, '变频控制柜编号', 'KBP2208038', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (894, 552, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (895, 552, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (896, 552, '泵挂深度', '700', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (873, 553, '泵编号', 'BGM2308032', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (874, 553, '泵规格型号', 'JM5-20-700FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (875, 553, '电机编号', 'M230060（带PT100热电阻引线）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (876, 553, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (877, 553, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (878, 553, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (879, 553, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (880, 553, '传感器编号', 'CZB2308029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (881, 553, '变频控制柜编号', 'KBP2208031', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (882, 553, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (883, 553, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (884, 553, '泵挂深度', '730', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (818, 554, '泵编号', 'BGM2311043', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (819, 554, '泵规格型号', 'JM5-10-1200FG-S', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (820, 554, '电机编号', 'SD202012001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (821, 554, '胶囊批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (822, 554, '隔膜批次', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (823, 554, '阀座批次', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (824, 554, '导流罩长度', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (825, 554, '传感器编号', 'CZB2310037', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (826, 554, '变频控制柜编号', 'KBP2211041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (827, 554, '载波控制柜编号', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (828, 554, '泄压接箍泄漏量', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1022, 581, '泵编号', 'BGM2304016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1023, 581, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1024, 581, '电机编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1025, 581, '胶囊批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1026, 581, '隔膜批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1027, 581, '阀座批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1028, 581, '导流罩长度', '49', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1029, 581, '传感器编号', 'CZB2004001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1030, 581, '变频控制柜编号', 'KBP2107029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1031, 581, '载波控制柜编号', 'KZB2106015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1032, 581, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1033, 581, '泵挂深度', '1130', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4322, 622, '泵编号', 'BGM2406029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4323, 622, '泵规格型号', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4324, 622, '电机编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4325, 622, '胶囊批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4326, 622, '隔膜批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4327, 622, '阀座批次', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4328, 622, '导流罩长度', '53', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4329, 622, '传感器编号', 'CZB2404022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4330, 622, '变频控制柜编号', 'KBP2407027', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4331, 622, '载波控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4332, 622, '泄压接箍泄漏量', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4333, 622, '泵挂深度', '658.93', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2380, 10041, '控制柜编号', 'KJR2111078', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2381, 10041, '控制柜型号', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2382, 10041, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2383, 10041, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2384, 10041, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2385, 10041, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2386, 10041, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2387, 10041, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2388, 10041, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1905, 10061, '控制柜编号', 'KJR2112078', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1906, 10061, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1907, 10061, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1908, 10061, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1909, 10061, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1910, 10061, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1911, 10061, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1912, 10061, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1913, 10061, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1086, 10081, '控制柜编号', 'KJR2110067', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1087, 10081, 'IO模块型号', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1088, 10081, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1089, 10081, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1090, 10081, 'PLC地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1091, 10081, 'HMI地址', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1092, 10081, 'IO模块地址', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1093, 10081, '信号箱地址', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1094, 10081, '4G模块地址', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1095, 10082, '控制柜编号', 'KJR2110068', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1096, 10082, 'IO模块型号', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1097, 10082, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1098, 10082, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1099, 10082, 'PLC地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1100, 10082, 'HMI地址', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1101, 10082, 'IO模块地址', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1102, 10082, '信号箱地址', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1103, 10082, '4G模块地址', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1104, 10083, '控制柜编号', 'KJR2110069', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1105, 10083, 'IO模块型号', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1106, 10083, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1107, 10083, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1108, 10083, 'PLC地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1109, 10083, 'HMI地址', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1110, 10083, 'IO模块地址', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1111, 10083, '信号箱地址', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1112, 10083, '4G模块地址', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1113, 10084, '控制柜编号', 'KJR2109051', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1114, 10084, 'IO模块型号', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1115, 10084, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1116, 10084, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1117, 10084, 'PLC地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1118, 10084, 'HMI地址', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1119, 10084, 'IO模块地址', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1120, 10084, '信号箱地址', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1121, 10084, '4G模块地址', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1122, 10085, '控制柜编号', 'KJR2110066', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1123, 10085, 'IO模块型号', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1124, 10085, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1125, 10085, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1126, 10085, 'PLC地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1127, 10085, 'HMI地址', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1128, 10085, 'IO模块地址', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1129, 10085, '信号箱地址', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1130, 10085, '4G模块地址', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1131, 10121, '控制柜编号', 'KJR2005016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1132, 10121, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1133, 10121, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1134, 10121, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1135, 10121, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1136, 10121, 'PLC地址', '192.168.8.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1137, 10121, 'HMI地址', '192.168.8.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1138, 10121, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1139, 10121, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1140, 10121, '4G模块地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1141, 10122, '控制柜编号', 'KJR2005018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1142, 10122, '控制柜型号', 'JRC380-150-G1J3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1143, 10122, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1144, 10122, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1145, 10122, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1146, 10122, 'PLC地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1147, 10122, 'HMI地址', '192.168.8.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1148, 10122, 'IO模块地址', '192.168.8.18', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1149, 10122, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1150, 10122, '4G模块地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1151, 10123, '控制柜编号', 'KJR2005015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1152, 10123, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1153, 10123, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1154, 10123, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1155, 10123, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1156, 10123, 'PLC地址', '192.168.8.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1157, 10123, 'HMI地址', '192.168.8.15', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1158, 10123, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1159, 10123, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1160, 10123, '4G模块地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1161, 10124, '控制柜编号', 'KJR203001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1162, 10124, '控制柜型号', 'JRC380-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1163, 10124, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1164, 10124, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1165, 10124, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1166, 10124, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1167, 10124, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1168, 10124, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1169, 10124, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1170, 10124, '4G模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1171, 10125, '控制柜编号', 'KJR2005011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1172, 10125, '控制柜型号', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1173, 10125, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1174, 10125, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1175, 10125, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1176, 10125, 'PLC地址', '192.168.8.22', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1177, 10125, 'HMI地址', '192.168.8.23', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1178, 10125, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1179, 10125, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1180, 10125, '4G模块地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1181, 10126, '控制柜编号', 'KJR2005012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1182, 10126, '控制柜型号', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1183, 10126, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1184, 10126, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1185, 10126, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1186, 10126, 'PLC地址', '192.168.8.19', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1187, 10126, 'HMI地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1188, 10126, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1189, 10126, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1190, 10126, '4G模块地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1208, 10181, '控制柜编号', 'KJR2103005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1209, 10181, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1210, 10181, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1211, 10181, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1212, 10181, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1213, 10181, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1214, 10181, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1215, 10181, 'IO模块地址', '192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1216, 10181, '信号箱地址', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1217, 10181, '4G模块地址', '192.168.6.20/192.168.6.21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1198, 10182, '控制柜编号', 'KJR2103005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1199, 10182, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1200, 10182, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1201, 10182, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1202, 10182, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1203, 10182, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1204, 10182, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1205, 10182, 'IO模块地址', '192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1206, 10182, '信号箱地址', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1207, 10182, '4G模块地址', '192.168.6.20/192.168.6.21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1232, 10224, '控制柜编号', 'KJR2005017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1233, 10224, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1234, 10224, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1235, 10224, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1236, 10224, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1237, 10224, 'PLC地址', '192.168.6.100', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1238, 10224, 'HMI地址', '192.168.6.102', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1239, 10224, 'IO模块地址', '192.168.6.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1240, 10224, '信号箱地址', '192.168.6.106', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1241, 10224, '4G模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1242, 10225, '控制柜编号', 'KJR2005013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1243, 10225, '控制柜型号', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1244, 10225, 'IO模块型号', 'HJ3209D/HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1245, 10225, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1246, 10225, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1247, 10225, 'PLC地址', '192.168.6.103', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1248, 10225, 'HMI地址', '192.168.6.110', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1249, 10225, 'IO模块地址', '192.168.6.104/192.168.6.105', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1250, 10225, '信号箱地址', '192.168.6.107', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1251, 10225, '4G模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1252, 10226, '控制柜编号', 'KJR2006025', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1253, 10226, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1254, 10226, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1255, 10226, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1256, 10226, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1257, 10226, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1258, 10226, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1259, 10226, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1260, 10226, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1261, 10226, '4G模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1396, 10227, '控制柜编号', 'KJR2005014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1397, 10227, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1398, 10227, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1399, 10227, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1400, 10227, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1401, 10227, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1402, 10227, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1403, 10227, 'IO模块地址', '192.168.6.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1404, 10227, '信号箱地址', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1405, 10227, '4G模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1296, 10228, '控制柜编号', 'KJR2005008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1297, 10228, '控制柜型号', 'JRC380-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1298, 10228, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1299, 10228, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1300, 10228, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1301, 10228, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1302, 10228, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1303, 10228, 'IO模块地址', '192.168.6.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1304, 10228, '信号箱地址', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1305, 10228, '4G模块地址', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1286, 10229, '控制柜编号', 'KJR203002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1287, 10229, '控制柜型号', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1288, 10229, 'IO模块型号', 'HJ3209D/HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1289, 10229, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1290, 10229, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1291, 10229, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1292, 10229, 'HMI地址', '192.168.8.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1293, 10229, 'IO模块地址', '192.168.8.91/192.168.8.93', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1294, 10229, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1295, 10229, '4G模块地址', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1266, 10230, '控制柜编号', 'KJR203003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1267, 10230, '控制柜型号', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1268, 10230, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1269, 10230, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1270, 10230, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1271, 10230, 'PLC地址', '192.168.8.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1272, 10230, 'HMI地址', '192.168.8.15', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1273, 10230, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1274, 10230, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1275, 10230, '4G模块地址', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1276, 10231, '控制柜编号', 'KJR203004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1277, 10231, '控制柜型号', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1278, 10231, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1279, 10231, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1280, 10231, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1281, 10231, 'PLC地址', '192.168.8.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1282, 10231, 'HMI地址', '192.168.8.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1283, 10231, 'IO模块地址', '192.168.8.92', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1284, 10231, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1285, 10231, '4G模块地址', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1264, 10232, '控制柜编号', '201908001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1265, 10232, '控制柜型号', 'CHBP-75kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1262, 10233, '控制柜编号', '201908002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1263, 10233, '控制柜型号', 'CHBP-55kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1386, 10234, '控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1387, 10234, '控制柜型号', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1388, 10234, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1389, 10234, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1390, 10234, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1391, 10234, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1392, 10234, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1393, 10234, 'IO模块地址', '192.168.6.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1394, 10234, '信号箱地址', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1395, 10234, '4G模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1757, 10241, '控制柜编号', 'KCS2106026', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1758, 10241, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1759, 10241, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1760, 10241, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1761, 10241, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1762, 10241, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1763, 10241, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1984, 10281, '控制柜编号', 'KCS2206052', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1985, 10281, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1986, 10281, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1987, 10281, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1988, 10281, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1989, 10281, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1990, 10281, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3149, 10301, '控制柜编号', 'KCS2206055', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3150, 10301, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3151, 10301, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3152, 10301, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3153, 10301, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3154, 10301, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3155, 10301, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3156, 10301, '井厂', '川平99-102', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1778, 10302, '控制柜编号', 'KCS2206051', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1779, 10302, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1780, 10302, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1781, 10302, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1782, 10302, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1783, 10302, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1784, 10302, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3691, 10303, '控制柜编号', 'KCS2206053', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3692, 10303, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3693, 10303, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3694, 10303, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3695, 10303, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3696, 10303, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3697, 10303, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1968, 10304, '控制柜编号', 'KCS2206054', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1969, 10304, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1970, 10304, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1971, 10304, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1972, 10304, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1973, 10304, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1974, 10304, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2965, 10305, '控制柜编号', 'KCS2206056', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2966, 10305, '控制柜型号', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2967, 10305, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2968, 10305, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2969, 10305, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2970, 10305, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2971, 10305, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2373, 10306, '控制柜编号', 'KCS2206057', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2374, 10306, '控制柜型号', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2375, 10306, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2376, 10306, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2377, 10306, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2378, 10306, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2379, 10306, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2183, 10307, '控制柜编号', 'KCS2206058', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2184, 10307, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2185, 10307, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2186, 10307, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2187, 10307, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2188, 10307, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2189, 10307, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1764, 10308, '控制柜编号', 'KCS2206059', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1765, 10308, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1766, 10308, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1767, 10308, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1768, 10308, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1769, 10308, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1770, 10308, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1998, 10309, '控制柜编号', 'KCS2206060', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1999, 10309, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2000, 10309, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2001, 10309, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2002, 10309, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2003, 10309, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2004, 10309, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1785, 10310, '控制柜编号', 'KCS2206061', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1786, 10310, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1787, 10310, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1788, 10310, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1789, 10310, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1790, 10310, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1791, 10310, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1792, 10311, '控制柜编号', 'KCS2206062', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1306, 10312, '控制柜编号', 'KCS2206063', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1307, 10313, '控制柜编号', 'KCS2206064', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1308, 10314, '控制柜编号', 'KCS2206065', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1807, 10321, '控制柜编号', 'KJR2104011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1808, 10321, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1809, 10321, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1810, 10321, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1811, 10321, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1812, 10321, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2285, 10322, '控制柜编号', 'KJR2108035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2286, 10322, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2287, 10322, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2288, 10322, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2289, 10322, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2290, 10322, 'HMI地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2291, 10322, '4G模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1671, 10323, '控制柜编号', 'KJR2108042', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1672, 10323, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1673, 10323, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1674, 10323, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1675, 10323, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1676, 10323, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1677, 10323, '4G模块地址', '192.168.2.120', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1218, 10324, '控制柜编号', 'KJR2108034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1219, 10324, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1220, 10324, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1221, 10324, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1222, 10324, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1223, 10324, 'HMI地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1224, 10324, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1225, 10325, '控制柜编号', 'KJR2108038', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1226, 10325, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1227, 10325, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1228, 10325, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1229, 10325, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1230, 10325, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1231, 10325, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2632, 10326, '控制柜编号', 'KJR2108037', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2633, 10326, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2634, 10326, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2635, 10326, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2636, 10326, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2637, 10326, 'HMI地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2638, 10326, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1716, 10343, '控制柜编号', 'KCS2009009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1717, 10343, '控制柜型号', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1718, 10343, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1719, 10343, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1720, 10343, 'PLC地址', '11.5.10.54', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1721, 10343, 'HMI地址', '11.5.10.55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1722, 10343, '4G模块地址', '11.5.10.51', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1494, 10344, '控制柜编号', 'KCS2107033', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1495, 10344, '控制柜型号', 'CSC380-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1496, 10344, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1497, 10344, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1498, 10344, 'PLC地址', '11.5.26.91', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1499, 10344, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1500, 10344, '4G模块地址', '11.5.26.90', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4297, 10345, '控制柜编号', 'KCS2005003(KCS2005001)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2012, 10346, '控制柜编号', '（KCS2105017）KCS2209068', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2013, 10346, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2014, 10346, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2015, 10346, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2016, 10346, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2017, 10346, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2018, 10346, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1508, 10347, '控制柜编号', 'KCS2105018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1509, 10347, '控制柜型号', 'CSC380-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1510, 10347, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1511, 10347, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1512, 10347, 'PLC地址', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1513, 10347, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1514, 10347, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1501, 10348, '控制柜编号', 'KCS2009012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1502, 10348, '控制柜型号', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1503, 10348, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1504, 10348, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1505, 10348, 'PLC地址', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1506, 10348, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1507, 10348, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1487, 10349, '控制柜编号', 'KCS2009008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1488, 10349, '控制柜型号', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1489, 10349, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1490, 10349, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1491, 10349, 'PLC地址', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1492, 10349, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1493, 10349, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1459, 10350, '控制柜编号', 'KCS2009007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1460, 10350, '控制柜型号', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1461, 10350, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1462, 10350, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1463, 10350, 'PLC地址', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1464, 10350, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1465, 10350, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1516, 10351, '控制柜编号', 'KCS2009013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1517, 10351, '控制柜型号', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1518, 10351, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1519, 10351, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1520, 10351, 'PLC地址', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1521, 10351, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1522, 10351, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1646, 10352, '控制柜编号', 'KCS2108041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1647, 10352, '控制柜型号', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1648, 10352, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1649, 10352, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1650, 10352, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1651, 10352, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1652, 10352, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1416, 10353, '控制柜型号', 'CSC380-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1417, 10353, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1418, 10353, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1419, 10353, 'PLC地址', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1420, 10353, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1421, 10353, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1422, 10354, '控制柜编号', 'KCS2009011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1423, 10354, '控制柜型号', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1424, 10354, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1425, 10354, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1426, 10354, 'PLC地址', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1427, 10354, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1428, 10354, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1723, 10355, '控制柜编号', 'KCS2009015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1724, 10355, '控制柜型号', 'CSC380-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1725, 10355, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1726, 10355, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1727, 10355, 'PLC地址', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1728, 10355, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1729, 10355, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1959, 10356, '控制柜编号', 'KCS2009014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1960, 10356, '控制柜型号', 'CSC380-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1961, 10356, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1962, 10356, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1963, 10356, 'PLC地址', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1964, 10356, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1965, 10356, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1967, 10357, '控制柜编号', 'KCS2005002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1800, 10358, '控制柜编号', 'KCS2108043', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1801, 10358, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1802, 10358, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1803, 10358, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1804, 10358, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1805, 10358, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1806, 10358, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2647, 10359, '控制柜编号', 'KCS2108039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2648, 10359, '控制柜型号', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2649, 10359, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2650, 10359, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2651, 10359, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2652, 10359, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2653, 10359, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2484, 10360, '控制柜编号', 'KCS2108045', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2485, 10360, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2486, 10360, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2487, 10360, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2488, 10360, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2489, 10360, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2490, 10360, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2491, 10360, '井下机组', '45kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1332, 10361, '控制柜编号', 'KCS2108040', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1333, 10361, '控制柜型号', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1334, 10361, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1335, 10361, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1336, 10361, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1337, 10361, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1338, 10361, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1702, 10362, '控制柜编号', 'KCS2106028', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1703, 10362, '控制柜型号', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1704, 10362, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1705, 10362, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1706, 10362, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1707, 10362, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1708, 10362, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1473, 10363, '控制柜编号', 'KCS2106019', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1474, 10363, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1475, 10363, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1476, 10363, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1477, 10363, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1478, 10363, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1479, 10363, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1466, 10364, '控制柜编号', 'KCS2106020', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1467, 10364, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1468, 10364, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1469, 10364, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1470, 10364, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1471, 10364, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1472, 10364, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2504, 10365, '控制柜编号', 'KCS2106021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2505, 10365, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2506, 10365, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2507, 10365, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2508, 10365, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2509, 10365, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2510, 10365, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1480, 10366, '控制柜编号', 'KCS2108044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1481, 10366, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1482, 10366, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1483, 10366, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1484, 10366, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1485, 10366, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1486, 10366, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1523, 10367, '控制柜编号', 'KCS2106022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1524, 10367, '控制柜型号', 'CSC660-45', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1525, 10367, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1526, 10367, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1527, 10367, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1528, 10367, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1529, 10367, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1323, 10368, '控制柜编号', 'KCS2106023', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1324, 10368, '控制柜型号', 'CSC660-45', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1325, 10368, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1326, 10368, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1327, 10368, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1328, 10368, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1329, 10368, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1309, 10369, '控制柜编号', 'KCS2108042', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1310, 10369, '控制柜型号', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1311, 10369, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1312, 10369, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1313, 10369, 'PLC地址', '11.10.13.108', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1314, 10369, 'HMI地址', '11.10.13.107', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1315, 10369, '4G模块地址', '11.10.13.115', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1316, 10370, '控制柜编号', 'KCS2108047', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1317, 10370, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1318, 10370, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1319, 10370, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1320, 10370, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1321, 10370, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1322, 10370, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2532, 10371, '控制柜编号', 'KCS2106025', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2533, 10371, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2534, 10371, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2535, 10371, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2536, 10371, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2537, 10371, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2538, 10371, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1515, 10372, '控制柜编号', 'KCS2106029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3806, 10373, '控制柜编号', 'KCS2009004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3807, 10373, '控制柜型号', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3808, 10373, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3809, 10373, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3810, 10373, 'PLC地址', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3811, 10373, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3812, 10373, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2525, 10374, '控制柜编号', 'KCS2009010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2526, 10374, '控制柜型号', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2527, 10374, '变频器型号', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2528, 10374, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2529, 10374, 'PLC地址', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2530, 10374, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2531, 10374, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1639, 10375, '控制柜编号', 'KCS2108046', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1640, 10375, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1641, 10375, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1642, 10375, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1643, 10375, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1644, 10375, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1645, 10375, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1330, 10376, '控制柜编号', 'KCS2106024', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1331, 10376, '控制柜型号', 'CSC660-45', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1977, 10377, '控制柜编号', 'KCS2106030', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1978, 10377, '控制柜型号', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1979, 10377, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1980, 10377, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1981, 10377, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1982, 10377, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1983, 10377, '4G模块地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1191, 10378, '控制柜编号', 'KJR2112084', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1192, 10378, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1193, 10378, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1194, 10378, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1195, 10378, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1196, 10378, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1197, 10378, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2518, 10379, '控制柜编号', 'KCS2108050', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2519, 10379, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2520, 10379, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2521, 10379, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2522, 10379, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2523, 10379, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2524, 10379, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1991, 10380, '控制柜编号', 'KCS2108048', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1992, 10380, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1993, 10380, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1994, 10380, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1995, 10380, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1996, 10380, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1997, 10380, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1771, 10381, '控制柜编号', 'KCS2108049', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1772, 10381, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1773, 10381, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1774, 10381, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1775, 10381, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1776, 10381, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1777, 10381, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2397, 10382, '控制柜编号', 'KCS2107031', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2398, 10382, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2399, 10382, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2400, 10382, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2401, 10382, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2402, 10382, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2403, 10382, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1748, 10383, '控制柜编号', 'KCS2106027', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1749, 10383, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1750, 10383, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1751, 10383, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1752, 10383, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1753, 10383, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1754, 10383, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3755, 10384, '控制柜编号', 'KJR2110067', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3756, 10384, 'IO模块型号', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3757, 10384, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3758, 10384, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3759, 10384, 'PLC地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3760, 10384, 'HMI地址', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3761, 10384, 'IO模块地址', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3762, 10384, '信号箱地址', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3763, 10384, '4G模块地址', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3764, 10384, '通讯方式', '利用70的8300组网', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3418, 10385, '控制柜编号', 'KJR2110068', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3419, 10385, 'IO模块型号', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3420, 10385, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3421, 10385, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3422, 10385, 'PLC地址', '192.168.6.41', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3423, 10385, 'HMI地址', '192.168.6.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3424, 10385, 'IO模块地址', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3425, 10385, '信号箱地址', '192.168.6.43', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3426, 10385, '4G模块地址', '192.168.6.40', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3427, 10385, '通讯方式', '利用70的8300组网', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1339, 10386, '控制柜编号', 'KJR2110069', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1340, 10386, 'IO模块型号', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1341, 10386, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1342, 10386, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1343, 10386, 'PLC地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1344, 10386, 'HMI地址', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1345, 10386, 'IO模块地址', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1346, 10386, '信号箱地址', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1347, 10386, '4G模块地址', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1348, 10387, '控制柜编号', 'KJR2109051', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1349, 10387, 'IO模块型号', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1350, 10387, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1351, 10387, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1352, 10387, 'PLC地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1353, 10387, 'HMI地址', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1354, 10387, 'IO模块地址', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1355, 10387, '信号箱地址', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1356, 10387, '4G模块地址', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1357, 10388, '控制柜编号', 'KJR2110066', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1358, 10388, 'IO模块型号', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1359, 10388, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1360, 10388, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1361, 10388, 'PLC地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1362, 10388, 'HMI地址', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1363, 10388, 'IO模块地址', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1364, 10388, '信号箱地址', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1365, 10388, '4G模块地址', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3369, 10389, '控制柜编号', 'KJR2005016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3370, 10389, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3371, 10389, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3372, 10389, '调功器型号', 'TH', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3373, 10389, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3374, 10389, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3375, 10389, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3376, 10389, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3377, 10389, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3378, 10389, '4G模块地址', '192.168.2.15', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3389, 10390, '控制柜编号', 'KJR2005018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3390, 10390, '控制柜型号', 'JRC380-150-G1J3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3391, 10390, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3392, 10390, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3393, 10390, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3394, 10390, 'PLC地址', '192.168.2.6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3395, 10390, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3396, 10390, 'IO模块地址', '192.168.2.24', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3397, 10390, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3398, 10390, '4G模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3379, 10391, '控制柜编号', 'KJR2005015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3380, 10391, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3381, 10391, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3382, 10391, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3383, 10391, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3384, 10391, 'PLC地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3385, 10391, 'HMI地址', '192.168.2.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3386, 10391, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3387, 10391, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3388, 10391, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1813, 10392, '控制柜编号', 'KJR203001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1814, 10392, '控制柜型号', 'JRC380-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1815, 10392, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1816, 10392, '调功器型号', 'SR', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1817, 10392, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1818, 10392, 'PLC地址', '192.168.6.83', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1819, 10392, 'HMI地址', '192.168.6.87', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1820, 10392, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1821, 10392, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1822, 10392, '4G模块地址', '192.168.6.85', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1366, 10393, '控制柜编号', 'KJR2005011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1367, 10393, '控制柜型号', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1368, 10393, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1369, 10393, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1370, 10393, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1371, 10393, 'PLC地址', '192.168.8.22', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1372, 10393, 'HMI地址', '192.168.8.23', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1373, 10393, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1374, 10393, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1375, 10393, '4G模块地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1376, 10394, '控制柜编号', 'KJR2005012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1377, 10394, '控制柜型号', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1378, 10394, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1379, 10394, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1380, 10394, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1381, 10394, 'PLC地址', '192.168.8.19', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1382, 10394, 'HMI地址', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1383, 10394, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1384, 10394, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1385, 10394, '4G模块地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2166, 10395, '控制柜编号', 'KJR2103005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2167, 10395, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2168, 10395, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2169, 10395, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2170, 10395, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2171, 10395, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2172, 10395, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2173, 10395, 'IO模块地址', '192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2174, 10395, '信号箱地址', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2175, 10395, '4G模块地址', '192.168.6.20/192.168.6.21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2190, 10396, '控制柜编号', 'KJR2103006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2191, 10396, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2192, 10396, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2193, 10396, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2194, 10396, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2195, 10396, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2196, 10396, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2197, 10396, 'IO模块地址', '192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2198, 10396, '信号箱地址', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2199, 10396, '4G模块地址', '192.168.6.20/192.168.6.21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2200, 10397, '控制柜编号', 'KJR2005017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2201, 10397, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2202, 10397, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2203, 10397, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2204, 10397, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2205, 10397, 'PLC地址', '192.168.6.100', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2206, 10397, 'HMI地址', '192.168.6.102', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2207, 10397, 'IO模块地址', '192.168.6.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2208, 10397, '信号箱地址', '192.168.6.106', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2209, 10397, '4G模块地址', '192.168.6.108', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2132, 10398, '控制柜编号', 'KJR2005013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2133, 10398, '控制柜型号', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2134, 10398, 'IO模块型号', 'HJ3209D/HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2135, 10398, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2136, 10398, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2137, 10398, 'PLC地址', '192.168.6.103', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2138, 10398, 'HMI地址', '192.168.6.110', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2139, 10398, 'IO模块地址', '192.168.6.104/192.168.6.105', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2140, 10398, '信号箱地址', '192.168.6.107', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2141, 10398, '4G模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2142, 10398, '采控实例有待商榷', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2210, 10399, '控制柜编号', 'KJR2006025', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2211, 10399, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2212, 10399, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2213, 10399, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2214, 10399, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2215, 10399, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2216, 10399, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2217, 10399, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2218, 10399, '信号箱地址', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2219, 10399, '4G模块地址', '192.168.6.21/192.168.6.22', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1823, 10400, '控制柜编号', 'KJR2005014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1824, 10400, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1825, 10400, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1826, 10400, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1827, 10400, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1828, 10400, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1829, 10400, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1830, 10400, 'IO模块地址', '192.168.6.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1831, 10400, '信号箱地址', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1832, 10400, '4G模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2905, 10401, '控制柜编号', '201908002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2906, 10401, '控制柜型号', 'CHBP-55kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2907, 10402, '控制柜编号', '201908001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2908, 10402, '控制柜型号', 'CHBP-75kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3673, 10403, '控制柜编号', 'KJR203003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3674, 10403, '控制柜型号', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3675, 10403, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3676, 10403, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3677, 10403, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3678, 10403, 'PLC地址', '192.168.8.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3679, 10403, 'HMI地址', '192.168.8.15', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3680, 10403, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3681, 10403, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3682, 10403, '4G模块地址', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2711, 10404, '控制柜编号', 'KJR203004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2712, 10404, '控制柜型号', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2713, 10404, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2714, 10404, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2715, 10404, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2716, 10404, 'PLC地址', '192.168.8.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2717, 10404, 'HMI地址', '192.168.8.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2718, 10404, 'IO模块地址', '192.168.8.92', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2719, 10404, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2720, 10404, '4G模块地址', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2721, 10404, '采控实例有待商榷', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1738, 10405, '控制柜编号', 'KJR203002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1739, 10405, '控制柜型号', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1740, 10405, 'IO模块型号', 'HJ3209D/HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1741, 10405, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1742, 10405, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1743, 10405, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1744, 10405, 'HMI地址', '192.168.8.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1745, 10405, 'IO模块地址', '192.168.8.91/192.168.8.93', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1746, 10405, '信号箱地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1747, 10405, '4G模块地址', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1833, 10406, '控制柜编号', 'KJR2005008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1834, 10406, '控制柜型号', 'JRC380-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1835, 10406, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1836, 10406, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1837, 10406, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1838, 10406, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1839, 10406, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1840, 10406, 'IO模块地址', '192.168.6.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1841, 10406, '信号箱地址', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1842, 10406, '4G模块地址', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1406, 10407, '控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1407, 10407, '控制柜型号', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1408, 10407, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1409, 10407, '调功器型号', 'SR系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1410, 10407, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1411, 10407, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1412, 10407, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1413, 10407, 'IO模块地址', '192.168.6.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1414, 10407, '信号箱地址', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1415, 10407, '4G模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1429, 10408, '控制柜编号', 'KJR2108041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1430, 10408, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1431, 10408, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1432, 10408, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1433, 10408, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1434, 10408, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1435, 10408, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2625, 10409, '控制柜编号', 'KJR2108044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2626, 10409, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2627, 10409, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2628, 10409, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2629, 10409, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2630, 10409, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2631, 10409, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1436, 10410, '控制柜编号', 'KJR2111070', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1437, 10410, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1438, 10410, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1439, 10410, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1440, 10410, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1441, 10410, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1442, 10410, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1443, 10410, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1444, 10410, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1445, 10411, '控制柜编号', 'KJR2112081', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1446, 10411, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1447, 10411, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1448, 10411, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1449, 10411, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1450, 10411, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1451, 10411, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1452, 10412, '控制柜编号', 'KJR2112086', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1453, 10412, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1454, 10412, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1455, 10412, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1456, 10412, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1457, 10412, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1458, 10412, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1862, 10413, '控制柜编号', 'KJR2112085', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1863, 10413, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1864, 10413, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1865, 10413, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1866, 10413, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1867, 10413, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1868, 10413, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3165, 10414, '控制柜编号', 'KJR2108040', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3166, 10414, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3167, 10414, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3168, 10414, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3169, 10414, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3170, 10414, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3171, 10414, '4G模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2157, 10421, '控制柜编号', 'KJR2207019', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2158, 10421, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2159, 10421, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2160, 10421, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2161, 10421, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2162, 10421, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2163, 10421, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2164, 10421, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2165, 10421, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3893, 10422, '控制柜编号', 'KJR2207021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3894, 10422, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3895, 10422, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3896, 10422, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3897, 10422, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3898, 10422, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3899, 10422, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3900, 10422, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3901, 10422, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3902, 10422, '加热周期', '周二、周五', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2764, 10423, '控制柜编号', 'KJR2207020', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2765, 10423, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2766, 10423, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2767, 10423, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2768, 10423, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2769, 10423, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2770, 10423, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2771, 10423, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2772, 10423, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2005, 10441, '控制柜编号', 'KCS2207066', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2006, 10441, '控制柜型号', 'CSC380-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2007, 10441, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2008, 10441, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2009, 10441, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2010, 10441, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2011, 10441, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2404, 10442, '控制柜编号', 'KCS2207067', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2405, 10442, '控制柜型号', 'CSC380-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2406, 10442, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2407, 10442, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2408, 10442, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2409, 10442, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2410, 10442, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1530, 10461, '控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1531, 10461, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1532, 10461, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1533, 10461, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1534, 10461, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1535, 10461, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1536, 10461, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1537, 10461, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1538, 10461, '4G模块地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1853, 10462, '控制柜编号', 'KGR2105015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1854, 10462, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1855, 10462, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1856, 10462, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1857, 10462, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1858, 10462, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1859, 10462, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1860, 10462, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1861, 10462, '4G模块地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2654, 10463, '控制柜编号', 'KGR2105012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2655, 10463, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2656, 10463, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2657, 10463, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2658, 10463, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2659, 10463, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2660, 10463, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2661, 10463, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2662, 10463, '4G模块地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1539, 10464, '控制柜编号', 'KGR2105012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1540, 10464, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1541, 10464, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1542, 10464, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1543, 10464, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1544, 10464, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1545, 10464, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1546, 10464, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1547, 10464, '4G模块地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1611, 10465, '控制柜编号', 'KGR2105012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1612, 10465, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1613, 10465, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1614, 10465, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1615, 10465, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1616, 10465, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1617, 10465, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1618, 10465, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1619, 10465, '4G模块地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1548, 10466, '控制柜编号', 'KGR2105015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1549, 10466, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1550, 10466, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1551, 10466, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1552, 10466, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1553, 10466, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1554, 10466, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1555, 10466, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1556, 10466, '4G模块地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2896, 10467, '控制柜编号', 'KGR2105013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2897, 10467, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2898, 10467, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2899, 10467, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2900, 10467, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2901, 10467, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2902, 10467, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2903, 10467, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2904, 10467, '4G模块地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1557, 10468, '控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1558, 10468, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1559, 10468, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1560, 10468, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1561, 10468, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1562, 10468, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1563, 10468, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1564, 10468, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1565, 10468, '4G模块地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1566, 10469, '控制柜编号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1567, 10469, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1568, 10469, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1569, 10469, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1570, 10469, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1571, 10469, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1572, 10469, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1573, 10469, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1574, 10469, '4G模块地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1620, 10470, '控制柜编号', 'KGR2105014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1621, 10470, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1622, 10470, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1623, 10470, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1624, 10470, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1625, 10470, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1626, 10470, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1627, 10470, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1628, 10470, '4G模块地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1575, 10471, '控制柜编号', 'KGR2105014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1576, 10471, '控制柜型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1577, 10471, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1578, 10471, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1579, 10471, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1580, 10471, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1581, 10471, 'HMI地址', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1582, 10471, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1583, 10471, '4G模块地址', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3854, 10472, '控制柜编号', 'KJR2108043', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3855, 10472, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3856, 10472, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3857, 10472, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3858, 10472, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3859, 10472, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3860, 10472, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3861, 10472, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3862, 10472, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3873, 10473, '控制柜编号', 'KJR2108039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3874, 10473, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3875, 10473, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3876, 10473, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3877, 10473, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3878, 10473, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3879, 10473, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3880, 10473, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3881, 10473, '4G模块地址', '192.168.2.123', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3882, 10473, '原井号', '堡34-26', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1653, 10474, '控制柜编号', 'KJR2112080', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1654, 10474, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1655, 10474, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1656, 10474, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1657, 10474, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1658, 10474, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1659, 10474, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1660, 10474, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1661, 10474, '4G模块地址', '192.168.2.87', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3863, 10475, '控制柜编号', 'KJR2112087', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3864, 10475, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3865, 10475, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3866, 10475, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3867, 10475, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3868, 10475, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3869, 10475, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3870, 10475, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3871, 10475, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3872, 10475, '原井号', '官19-21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1629, 10476, '控制柜编号', 'KJR2111072', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1630, 10476, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1631, 10476, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1632, 10476, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1633, 10476, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1634, 10476, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1635, 10476, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1636, 10476, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1637, 10476, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1584, 10477, '控制柜编号', 'KJR2111073', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1585, 10477, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1586, 10477, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1587, 10477, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1588, 10477, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1589, 10477, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1590, 10477, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1591, 10477, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1592, 10477, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1662, 10478, '控制柜编号', 'KJR2112079', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1663, 10478, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1664, 10478, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1665, 10478, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1666, 10478, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1667, 10478, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1668, 10478, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1669, 10478, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1670, 10478, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3651, 10479, '控制柜编号', 'KJR2112083', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3652, 10479, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3653, 10479, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3654, 10479, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3655, 10479, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3656, 10479, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3657, 10479, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1602, 10480, '控制柜编号', 'KJR2111071', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1603, 10480, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1604, 10480, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1605, 10480, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1606, 10480, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1607, 10480, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1608, 10480, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1609, 10480, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1610, 10480, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3484, 10481, '控制柜编号', 'KJR2112082', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3485, 10481, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3486, 10481, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3487, 10481, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3488, 10481, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3489, 10481, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3490, 10481, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3491, 10481, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3492, 10481, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1593, 10482, '控制柜编号', 'KJR2112088', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1594, 10482, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1595, 10482, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1596, 10482, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1597, 10482, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1598, 10482, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1599, 10482, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1600, 10482, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1601, 10482, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1638, 10483, '控制柜编号', 'KBP2105011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1730, 10484, '控制柜编号', '201907005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2131, 10485, '控制柜编号', '210907008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2130, 10487, '控制柜编号', '210907008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1878, 10489, '控制柜编号', 'KJR2111074', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1879, 10489, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1880, 10489, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1881, 10489, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1882, 10489, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1883, 10489, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1884, 10489, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1885, 10489, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1886, 10489, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3713, 10490, '控制柜编号', 'KJR2108036', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3714, 10490, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3715, 10490, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3716, 10490, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3717, 10490, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3718, 10490, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3719, 10490, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3720, 10490, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3721, 10490, '4G模块地址', '192.168.2.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1887, 10491, '控制柜编号', 'KJR2111075', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1888, 10491, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1889, 10491, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1890, 10491, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1891, 10491, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1892, 10491, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1893, 10491, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1894, 10491, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1895, 10491, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1896, 10492, '控制柜编号', 'KJR2111076', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1897, 10492, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1898, 10492, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1899, 10492, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1900, 10492, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1901, 10492, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1902, 10492, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1903, 10492, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1904, 10492, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3465, 10493, '控制柜编号', 'KJR2111077', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3466, 10493, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3467, 10493, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3468, 10493, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3469, 10493, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3470, 10493, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3471, 10493, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3472, 10493, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3473, 10493, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1869, 10494, '控制柜编号', 'KJR2202011（07）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1870, 10494, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1871, 10494, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1872, 10494, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1873, 10494, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1874, 10494, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1875, 10494, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1876, 10494, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1877, 10494, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1914, 10495, '控制柜编号', 'KJR2202008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1915, 10495, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1916, 10495, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1917, 10495, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1918, 10495, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1919, 10495, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1920, 10495, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1921, 10495, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1922, 10495, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1923, 10496, '控制柜编号', 'KJR2204013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1924, 10496, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1925, 10496, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1926, 10496, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1927, 10496, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1928, 10496, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1929, 10496, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1930, 10496, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1931, 10496, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3731, 10497, '控制柜编号', 'KJR2204014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3732, 10497, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3733, 10497, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3734, 10497, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3735, 10497, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3736, 10497, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3737, 10497, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3738, 10497, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3739, 10497, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3437, 10498, '控制柜编号', 'KJR2204015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3438, 10498, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3439, 10498, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3440, 10498, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3441, 10498, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3442, 10498, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3443, 10498, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3444, 10498, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3445, 10498, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3428, 10499, '控制柜编号', 'KJR2204016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3429, 10499, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3430, 10499, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3431, 10499, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3432, 10499, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3433, 10499, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3434, 10499, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3435, 10499, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3436, 10499, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1932, 10500, '控制柜编号', 'KJR2204017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1933, 10500, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1934, 10500, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1935, 10500, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1936, 10500, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1937, 10500, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1938, 10500, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1939, 10500, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1940, 10500, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1941, 10501, '控制柜编号', 'KJR2204018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1942, 10501, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1943, 10501, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1944, 10501, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1945, 10501, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1946, 10501, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1947, 10501, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1948, 10501, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1949, 10501, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4301, 10502, '控制柜编号', 'KJR2206089', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4302, 10502, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4303, 10502, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4304, 10502, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4305, 10502, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4306, 10502, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4307, 10502, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4308, 10502, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4309, 10502, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2555, 10521, '控制柜编号', 'KJR2110045', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2556, 10521, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2557, 10521, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2558, 10521, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2559, 10521, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2560, 10521, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2561, 10521, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2562, 10521, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2813, 10522, '控制柜编号', 'KJR2110046', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2814, 10522, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2815, 10522, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2816, 10522, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2817, 10522, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2818, 10522, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2819, 10522, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2820, 10522, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2046, 10523, '控制柜编号', 'KJR2110047', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2047, 10523, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2048, 10523, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2049, 10523, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2050, 10523, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2051, 10523, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2052, 10523, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2053, 10523, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2054, 10524, '控制柜编号', 'KJR2110048', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2055, 10524, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2056, 10524, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2057, 10524, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2058, 10524, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2059, 10524, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2060, 10524, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2061, 10524, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2062, 10525, '控制柜编号', 'KJR2110049', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2063, 10525, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2064, 10525, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2065, 10525, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2066, 10525, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2067, 10525, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2068, 10525, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2069, 10525, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2781, 10526, '控制柜编号', 'KJR2110050', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2782, 10526, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2783, 10526, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2784, 10526, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2785, 10526, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2786, 10526, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2787, 10526, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2788, 10526, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2917, 10527, '控制柜编号', 'KJR2110051', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2918, 10527, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2919, 10527, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2920, 10527, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2921, 10527, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2922, 10527, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2923, 10527, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2924, 10527, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2740, 10528, '控制柜编号', 'KJR2110052', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2741, 10528, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2742, 10528, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2743, 10528, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2744, 10528, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2745, 10528, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2746, 10528, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2747, 10528, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2773, 10529, '控制柜编号', 'KJR2110053', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2774, 10529, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2775, 10529, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2776, 10529, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2777, 10529, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2778, 10529, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2779, 10529, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2780, 10529, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2909, 10530, '控制柜编号', 'KJR2110054', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2910, 10530, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2911, 10530, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2912, 10530, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2913, 10530, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2914, 10530, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2915, 10530, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2916, 10530, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2070, 10531, '控制柜编号', 'KJR1912006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2071, 10531, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2072, 10531, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2073, 10531, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2074, 10532, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2075, 10532, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2076, 10532, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1694, 10533, '控制柜编号', 'KJR1912002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1695, 10533, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1696, 10533, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1697, 10533, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1698, 10533, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1699, 10533, 'PLC地址', '11.8.11.115', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1700, 10533, 'HMI地址', '11.8.11.', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1701, 10533, '4G模块地址', '11.8.11.130', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2077, 10534, '控制柜编号', 'KJR1912003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2078, 10534, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2079, 10534, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2080, 10534, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2262, 10535, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2263, 10535, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2264, 10535, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2239, 10536, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2240, 10536, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2241, 10536, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2259, 10537, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2260, 10537, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2261, 10537, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2271, 10538, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2272, 10538, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2273, 10538, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2268, 10539, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2269, 10539, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2270, 10539, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2256, 10540, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2257, 10540, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2258, 10540, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2081, 10541, '控制柜编号', 'KJR2001001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2082, 10541, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2083, 10541, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2084, 10541, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2501, 10542, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2502, 10542, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2503, 10542, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2265, 10543, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2266, 10543, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2267, 10543, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2030, 10544, '控制柜编号', 'KJR2012034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2031, 10544, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2032, 10544, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2033, 10544, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2250, 10545, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2251, 10545, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2252, 10545, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2034, 10546, '控制柜编号', 'KJR2011031', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2035, 10546, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2036, 10546, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2037, 10546, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2038, 10547, '控制柜编号', 'KJR2012035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2039, 10547, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2040, 10547, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2041, 10547, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2042, 10548, '控制柜编号', 'KJR2012036', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2043, 10548, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2044, 10548, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2045, 10548, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2253, 10549, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2254, 10549, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2255, 10549, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2232, 10550, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2233, 10550, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2234, 10550, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2026, 10551, '控制柜编号', 'KJR2012037', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2027, 10551, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2028, 10551, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2029, 10551, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2229, 10552, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2230, 10552, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2231, 10552, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1686, 10553, '控制柜编号', 'KJR2012041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1687, 10553, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1688, 10553, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1689, 10553, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1690, 10553, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1691, 10553, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1692, 10553, 'HMI地址', '192.168.2.', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1693, 10553, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2220, 10554, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2221, 10554, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2222, 10554, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2226, 10555, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2227, 10555, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2228, 10555, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2223, 10556, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2224, 10556, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2225, 10556, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2085, 10557, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2086, 10557, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2087, 10557, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2246, 10558, '控制柜编号', 'KJR2103003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2247, 10558, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2248, 10558, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2249, 10558, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2235, 10559, '控制柜编号', 'KJR2103002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2236, 10559, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2237, 10559, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2238, 10559, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2242, 10560, '控制柜编号', 'KJR2103004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2243, 10560, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2244, 10560, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2245, 10560, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2274, 10561, '控制柜编号', 'KJR2103001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2275, 10561, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2276, 10561, '批次', '第三批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2277, 10561, '调功器型号', '宏施HM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1678, 10562, '控制柜编号', 'JR20190002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1679, 10562, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1680, 10562, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1681, 10562, '调功器型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1682, 10562, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1683, 10562, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1684, 10562, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1685, 10562, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2088, 10563, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2089, 10563, '批次', '第一批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2090, 10563, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3190, 10564, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3191, 10564, '批次', '第一批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3192, 10564, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2091, 10565, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2092, 10565, '批次', '第一批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2093, 10565, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2094, 10566, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2095, 10566, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2096, 10566, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2097, 10567, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2098, 10567, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2099, 10567, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2100, 10568, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2101, 10568, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2102, 10568, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2103, 10569, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2104, 10569, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2105, 10569, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2106, 10570, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2107, 10570, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2108, 10570, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2109, 10571, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2110, 10571, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2111, 10571, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2112, 10572, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2113, 10572, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2114, 10572, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2115, 10573, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2116, 10573, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2117, 10573, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2118, 10574, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2119, 10574, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2120, 10574, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2121, 10575, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2122, 10575, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2123, 10575, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2124, 10576, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2125, 10576, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2126, 10576, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2127, 10577, 'PLC地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2128, 10577, '批次', '第二批', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2129, 10577, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2364, 10581, '控制柜编号', 'KJR2202004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2365, 10581, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2366, 10581, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2367, 10581, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2368, 10581, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2369, 10581, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2370, 10581, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2371, 10581, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2372, 10581, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1793, 10601, '控制柜编号', 'KCS2209062', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1794, 10601, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1795, 10601, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1796, 10601, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1797, 10601, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1798, 10601, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1799, 10601, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2618, 10602, '控制柜编号', 'KCS2209063', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2619, 10602, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2620, 10602, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2621, 10602, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2622, 10602, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2623, 10602, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2624, 10602, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1709, 10603, '控制柜编号', 'KCS2209064', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1710, 10603, '控制柜型号', 'CSC380-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1711, 10603, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1712, 10603, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1713, 10603, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1714, 10603, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1715, 10603, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1731, 10604, '控制柜编号', 'KCS2209065', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1732, 10604, '控制柜型号', 'CSC380-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1733, 10604, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1734, 10604, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1735, 10604, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1736, 10604, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1737, 10604, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2469, 10605, '控制柜编号', 'KCS2209068', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2470, 10605, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2471, 10605, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2472, 10605, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2473, 10605, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2474, 10605, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2475, 10605, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2511, 10606, '控制柜编号', 'KCS2209069', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2512, 10606, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2513, 10606, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2514, 10606, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2515, 10606, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2516, 10606, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2517, 10606, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3813, 10607, '控制柜编号', 'KCS2209070', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3814, 10607, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3815, 10607, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3816, 10607, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3817, 10607, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3818, 10607, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3819, 10607, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2950, 10608, '控制柜编号', 'KCS2209071', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2951, 10608, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2952, 10608, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2953, 10608, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2954, 10608, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2955, 10608, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2956, 10608, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2278, 10609, '控制柜编号', 'KCS2209072', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2279, 10609, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2280, 10609, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2281, 10609, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2282, 10609, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2283, 10609, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2284, 10609, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2337, 10621, '控制柜编号', 'KJR2202002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2338, 10621, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2339, 10621, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2340, 10621, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2341, 10621, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2342, 10621, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2343, 10621, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2344, 10621, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2345, 10621, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3456, 10622, '控制柜编号', 'KJR2202001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3457, 10622, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3458, 10622, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3459, 10622, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3460, 10622, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3461, 10622, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3462, 10622, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3463, 10622, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3464, 10622, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2328, 10623, '控制柜编号', 'KJR2202003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2329, 10623, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2330, 10623, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2331, 10623, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2332, 10623, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2333, 10623, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2334, 10623, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2335, 10623, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2336, 10623, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3349, 10624, '控制柜编号', 'KJR2202005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3350, 10624, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3351, 10624, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3352, 10624, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3353, 10624, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3354, 10624, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3355, 10624, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3356, 10624, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3357, 10624, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3358, 10624, '加热时段', '周一、周三、周五、周日', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2355, 10625, '控制柜编号', 'KJR2110006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2356, 10625, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2357, 10625, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2358, 10625, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2359, 10625, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2360, 10625, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2361, 10625, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2362, 10625, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2363, 10625, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2301, 10626, '控制柜编号', 'KJR2110007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2302, 10626, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2303, 10626, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2304, 10626, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2305, 10626, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2306, 10626, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2307, 10626, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2308, 10626, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2309, 10626, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2346, 10627, '控制柜编号', 'KJR2110008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2347, 10627, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2348, 10627, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2349, 10627, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2350, 10627, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2351, 10627, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2352, 10627, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2353, 10627, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2354, 10627, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2319, 10628, '控制柜编号', 'KJR2110009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2320, 10628, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2321, 10628, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2322, 10628, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2323, 10628, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2324, 10628, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2325, 10628, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2326, 10628, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2327, 10628, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2310, 10629, '控制柜编号', 'KJR2110010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2311, 10629, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2312, 10629, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2313, 10629, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2314, 10629, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2315, 10629, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2316, 10629, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2317, 10629, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2318, 10629, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3706, 10661, '控制柜编号', 'KCS2106028', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3707, 10661, '控制柜型号', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3708, 10661, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3709, 10661, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3710, 10661, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3711, 10661, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3712, 10661, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3061, 10681, '控制柜编号', 'KCS2008008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2821, 10701, '控制柜编号', 'KJR2210070', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2822, 10701, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2823, 10701, 'IO模块型号', '西门子模块', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2824, 10701, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2825, 10701, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2826, 10701, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2827, 10701, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2828, 10701, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4453, 10702, '控制柜编号', 'KJR230111', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4454, 10702, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4455, 10702, 'IO模块型号', 'HJ5209P16;HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4456, 10702, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4457, 10702, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4458, 10702, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4459, 10702, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4460, 10702, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4461, 10702, 'IO模块地址', '192.168.2.13;192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4462, 10702, '控制柜CPU', '单片机（样机）', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3256, 10703, '控制柜编号', 'KJR2210072', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3257, 10703, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3258, 10703, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3259, 10703, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3260, 10703, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3261, 10703, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3262, 10703, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3263, 10703, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3446, 10704, '控制柜编号', 'KJR2210093', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3447, 10704, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3448, 10704, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3449, 10704, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3450, 10704, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3451, 10704, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3452, 10704, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3453, 10704, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3454, 10704, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3455, 10704, '加热时段', '周二、周四、周六', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3740, 10705, '控制柜编号', 'KJR2210091', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3741, 10705, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3742, 10705, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3743, 10705, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3744, 10705, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3745, 10705, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3746, 10705, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3747, 10705, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3748, 10705, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3749, 10705, '加热时段', '周一、周三、周五、周日', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3339, 10706, '控制柜编号', 'KJR2210092', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3340, 10706, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3341, 10706, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3342, 10706, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3343, 10706, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3344, 10706, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3345, 10706, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3346, 10706, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3347, 10706, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3348, 10706, '加热时段', '周二、周四、周六', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3279, 10707, '控制柜编号', 'KJR2210090', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3280, 10707, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3281, 10707, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3282, 10707, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3283, 10707, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3284, 10707, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3285, 10707, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3286, 10707, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3287, 10707, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3288, 10707, '加热时段', '周一、周三、周五、周日', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3289, 10708, '控制柜编号', 'KJR2210094', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3290, 10708, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3291, 10708, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3292, 10708, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3293, 10708, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3294, 10708, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3295, 10708, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3296, 10708, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3297, 10708, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3298, 10708, '加热时段', '周二、周五、周日', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3319, 10709, '控制柜编号', 'KJR2210095', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3320, 10709, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3321, 10709, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3322, 10709, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3323, 10709, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3324, 10709, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3325, 10709, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3326, 10709, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3327, 10709, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3328, 10709, '加热时段', '周二、周四、周六', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3309, 10721, '控制柜编号', 'KJR2210096', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3310, 10721, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3311, 10721, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3312, 10721, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3313, 10721, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3314, 10721, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3315, 10721, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3316, 10721, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3317, 10721, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3318, 10721, '加热时段', '周三、周六', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3883, 10722, '控制柜编号', 'KJR2210097', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3884, 10722, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3885, 10722, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3886, 10722, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3887, 10722, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3888, 10722, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3889, 10722, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3890, 10722, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3891, 10722, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3892, 10722, '加热时段', '周二、周四、周六', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3299, 10723, '控制柜编号', 'KJR2210098', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3300, 10723, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3301, 10723, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3302, 10723, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3303, 10723, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3304, 10723, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3305, 10723, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3306, 10723, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3307, 10723, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3308, 10723, '加热时段', '周一、周四', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3329, 10724, '控制柜编号', 'KJR2210099', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3330, 10724, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3331, 10724, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3332, 10724, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3333, 10724, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3334, 10724, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3335, 10724, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3336, 10724, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3337, 10724, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3338, 10724, '加热时段', '周一、周三、周五、周日', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3359, 10725, '控制柜编号', 'KJR2210100', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3360, 10725, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3361, 10725, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3362, 10725, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3363, 10725, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3364, 10725, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3365, 10725, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3366, 10725, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3367, 10725, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3368, 10725, '加热时段', '周二、周四、周六', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2869, 10726, '控制柜编号', 'KJR2210101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2870, 10726, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2871, 10726, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2872, 10726, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2873, 10726, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2874, 10726, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2875, 10726, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2876, 10726, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2877, 10726, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2878, 10726, '加热时段', '周一、周四', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2859, 10727, '控制柜编号', 'KJR2210102', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2860, 10727, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2861, 10727, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2862, 10727, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2863, 10727, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2864, 10727, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2865, 10727, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2866, 10727, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2867, 10727, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2868, 10727, '加热时段', '周一、周三、周五', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3269, 10728, '控制柜编号', 'KJR2210103', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3270, 10728, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3271, 10728, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3272, 10728, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3273, 10728, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3274, 10728, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3275, 10728, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3276, 10728, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3277, 10728, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3278, 10728, '加热时段', '周一、周三、周五、周日', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2839, 10729, '控制柜编号', 'KJR2210104', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2840, 10729, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2841, 10729, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2842, 10729, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2843, 10729, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2844, 10729, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2845, 10729, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2846, 10729, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2847, 10729, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2848, 10729, '运行周期', '周三.六', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3180, 10730, '控制柜编号', 'KJR2210105', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3181, 10730, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3182, 10730, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3183, 10730, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3184, 10730, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3185, 10730, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3186, 10730, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3187, 10730, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3188, 10730, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3189, 10730, '加热时段', '周二、周四、周六', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3722, 10731, '控制柜编号', 'KJR2210106', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3723, 10731, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3724, 10731, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3725, 10731, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3726, 10731, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3727, 10731, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3728, 10731, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3729, 10731, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3730, 10731, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2849, 10732, '控制柜编号', 'KJR2210107', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2850, 10732, '控制柜型号', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2851, 10732, '调功器型号', 'SH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2852, 10732, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2853, 10732, 'IO模块型号', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2854, 10732, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2855, 10732, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2856, 10732, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2857, 10732, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2858, 10732, '运行周期', '周日', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1843, 10741, '控制柜编号', 'KJR2001009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1844, 10741, '控制柜型号', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1845, 10741, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1846, 10741, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1847, 10741, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1848, 10741, 'PLC地址', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1849, 10741, 'HMI地址', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1850, 10741, 'IO模块地址', '192.168.6.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1851, 10741, '信号箱地址', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1852, 10741, '4G模块地址', '192.168.6.20/192.168.6.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2570, 10765, '控制柜编号', 'KCS2210073', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2571, 10765, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2572, 10765, '变频器型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2573, 10765, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2574, 10765, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2575, 10765, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2576, 10765, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2019, 10766, '控制柜编号', 'KCS2210074', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2020, 10766, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2021, 10766, '变频器型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2022, 10766, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2023, 10766, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2024, 10766, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2025, 10766, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2611, 10767, '控制柜编号', 'KCS2210075', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2612, 10767, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2613, 10767, '变频器型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2614, 10767, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2615, 10767, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2616, 10767, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2617, 10767, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2563, 10768, '控制柜编号', 'KCS2210076', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2564, 10768, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2565, 10768, '变频器型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2566, 10768, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2567, 10768, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2568, 10768, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2569, 10768, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2143, 10769, '控制柜编号', 'KCS2210077', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2144, 10769, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2145, 10769, '变频器型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2146, 10769, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2147, 10769, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2148, 10769, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2149, 10769, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2150, 10770, '控制柜编号', 'KCS2210078', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2151, 10770, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2152, 10770, '变频器型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2153, 10770, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2154, 10770, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2155, 10770, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2156, 10770, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2176, 10771, '控制柜编号', 'KCS2210079', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2177, 10771, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2178, 10771, '变频器型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2179, 10771, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2180, 10771, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2181, 10771, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2182, 10771, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3644, 10772, '控制柜编号', 'KCS2210080', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3645, 10772, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3646, 10772, '变频器型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3647, 10772, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3648, 10772, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3649, 10772, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3650, 10772, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1966, 10781, '控制柜编号', 'KCS2005003(KCS2005001)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1755, 10782, '控制柜编号', 'KCS2005003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1756, 10783, '控制柜编号', 'G2019112801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1975, 10841, '控制柜编号', 'KCS2106024', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1976, 10841, '控制柜型号', 'CSC660-45', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4341, 10861, '控制柜编号', 'KJR2211113', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4342, 10861, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4343, 10861, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4344, 10861, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4345, 10861, 'IO模块型号', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4346, 10861, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4347, 10861, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4348, 10861, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4349, 10861, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4350, 10861, '加热周期', '全周期/HJ在线', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3820, 10862, '控制柜编号', 'KJR2211114', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3821, 10862, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3822, 10862, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3823, 10862, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3824, 10862, 'IO模块型号', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3825, 10862, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3826, 10862, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3827, 10862, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3828, 10862, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4351, 10863, '控制柜编号', 'KJR2211115', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4352, 10863, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4353, 10863, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4354, 10863, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4355, 10863, 'IO模块型号', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4356, 10863, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4357, 10863, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4358, 10863, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4359, 10863, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4360, 10863, '加热周期', '无应用', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2933, 10864, '控制柜编号', 'KJR2211116', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2934, 10864, '控制柜型号', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2935, 10864, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2936, 10864, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2937, 10864, 'IO模块型号', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2938, 10864, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2939, 10864, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2940, 10864, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2941, 10864, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3078, 10881, '控制柜编号', 'KJR2301118', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3079, 10881, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3080, 10881, 'IO模块型号', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3081, 10881, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3082, 10881, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3083, 10881, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3084, 10881, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3085, 10881, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3086, 10881, 'IO模块地址', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2887, 10882, '控制柜编号', 'KJR2301119', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2888, 10882, '控制柜型号', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2889, 10882, 'IO模块型号', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2890, 10882, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2891, 10882, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2892, 10882, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2893, 10882, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2894, 10882, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2895, 10882, 'IO模块地址', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2731, 10883, '控制柜编号', 'KJR2301120', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2732, 10883, '控制柜型号', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2733, 10883, 'IO模块型号', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2734, 10883, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2735, 10883, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2736, 10883, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2737, 10883, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2738, 10883, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2739, 10883, 'IO模块地址', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2292, 10901, '控制柜编号', 'KJR2211117', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2293, 10901, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2294, 10901, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2295, 10901, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2296, 10901, 'IO模块型号', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2297, 10901, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2298, 10901, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2299, 10901, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2300, 10901, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3096, 10902, '控制柜编号', 'KJR2303121', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3097, 10902, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3098, 10902, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3099, 10902, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3100, 10902, 'IO模块型号', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3101, 10902, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3102, 10902, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3103, 10902, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3104, 10902, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3105, 10903, '控制柜编号', 'KJR2303122', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3106, 10903, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3107, 10903, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3108, 10903, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3109, 10903, 'IO模块型号', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3110, 10903, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3111, 10903, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3112, 10903, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3113, 10903, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3114, 10904, '控制柜编号', 'KJR2303123', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3115, 10904, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3116, 10904, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3117, 10904, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3118, 10904, 'IO模块型号', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3119, 10904, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3120, 10904, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3121, 10904, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3122, 10904, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3123, 10905, '控制柜编号', 'KJR2303124', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3124, 10905, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3125, 10905, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3126, 10905, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3127, 10905, 'IO模块型号', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3128, 10905, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3129, 10905, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3130, 10905, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3131, 10905, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3132, 10906, '控制柜编号', 'KJR2303125', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3133, 10906, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3134, 10906, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3135, 10906, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3136, 10906, 'IO模块型号', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3137, 10906, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3138, 10906, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3139, 10906, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3140, 10906, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2722, 10921, '控制柜编号', 'KJR2302126', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2723, 10921, '控制柜型号', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2724, 10921, 'IO模块型号', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2725, 10921, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2726, 10921, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2727, 10921, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2728, 10921, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2729, 10921, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2730, 10921, 'IO模块地址', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3629, 10961, '控制柜编号', 'KJR2303128', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3630, 10961, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3631, 10961, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3632, 10961, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3633, 10961, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2996, 10962, '控制柜编号', 'KJR2303129', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2997, 10962, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2998, 10962, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2999, 10962, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3000, 10962, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3001, 10963, '控制柜编号', 'KJR2303130', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3002, 10963, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3003, 10963, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3004, 10963, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3005, 10963, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3006, 10964, '控制柜编号', 'KJR2303131', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3007, 10964, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3008, 10964, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3009, 10964, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3010, 10964, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3011, 10965, '控制柜编号', 'KJR2303132', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3012, 10965, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3013, 10965, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3014, 10965, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3015, 10965, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3016, 10966, '控制柜编号', 'KJR2303133', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3017, 10966, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3018, 10966, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3019, 10966, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3020, 10966, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3639, 10967, '控制柜编号', 'KJR2303134', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3640, 10967, '控制柜型号', 'JRC380-STM-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3641, 10967, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3642, 10967, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3643, 10967, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3634, 10968, '控制柜编号', 'KJR2303135', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3635, 10968, '控制柜型号', 'JRC380-STM-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3636, 10968, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3637, 10968, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3638, 10968, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3750, 10969, '控制柜编号', 'KJR2303136', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3751, 10969, '控制柜型号', 'JRC380-STM-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3752, 10969, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3753, 10969, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3754, 10969, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3021, 10970, '控制柜编号', 'KJR2303137', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3022, 10970, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3023, 10970, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3024, 10970, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3025, 10970, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3264, 10971, '控制柜编号', 'KJR2303138', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3265, 10971, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3266, 10971, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3267, 10971, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3268, 10971, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3026, 10972, '控制柜编号', 'KJR2303139', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3027, 10972, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3028, 10972, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3029, 10972, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3030, 10972, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3031, 10973, '控制柜编号', 'KJR2303140', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3032, 10973, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3033, 10973, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3034, 10973, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3035, 10973, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3036, 10974, '控制柜编号', 'KJR2303141', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3037, 10974, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3038, 10974, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3039, 10974, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3040, 10974, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3041, 10975, '控制柜编号', 'KJR2303142', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3042, 10975, '控制柜型号', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3043, 10975, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3044, 10975, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3045, 10975, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2797, 10981, '控制柜编号', 'KCS2303081', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2798, 10981, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2799, 10981, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2800, 10981, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2801, 10981, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2802, 10981, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2803, 10981, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2804, 10981, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2805, 10982, '控制柜编号', 'KCS2303082', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2806, 10982, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2807, 10982, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2808, 10982, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2809, 10982, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2810, 10982, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2811, 10982, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2812, 10982, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2476, 10983, '控制柜编号', 'KCS2303083', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2477, 10983, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2478, 10983, '变频器型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2479, 10983, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2480, 10983, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2481, 10983, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2482, 10983, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2483, 10983, '井下机组', '22kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2547, 10984, '控制柜编号', 'KCS2303084', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2548, 10984, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2549, 10984, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2550, 10984, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2551, 10984, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2552, 10984, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2553, 10984, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2554, 10984, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4361, 10985, '控制柜编号', 'KCS2303085', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4362, 10985, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4363, 10985, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4364, 10985, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4365, 10985, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4366, 10985, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4367, 10985, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4368, 10985, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2663, 10986, '控制柜编号', 'KCS2303086', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2664, 10986, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2665, 10986, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2666, 10986, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2667, 10986, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2668, 10986, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2669, 10986, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2670, 10986, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2942, 10987, '控制柜编号', 'KCS2303087', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2943, 10987, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2944, 10987, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2945, 10987, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2946, 10987, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2947, 10987, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2948, 10987, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2949, 10987, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2595, 10988, '控制柜编号', 'KCS2303088', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2596, 10988, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2597, 10988, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2598, 10988, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2599, 10988, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2600, 10988, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2601, 10988, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2602, 10988, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2411, 10989, '控制柜编号', 'KCS2303089', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2412, 10989, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2413, 10989, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2414, 10989, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2415, 10989, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2416, 10989, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2417, 10989, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2418, 10989, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2603, 10990, '控制柜编号', 'KCS2303090', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2604, 10990, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2605, 10990, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2606, 10990, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2607, 10990, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2608, 10990, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2609, 10990, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2610, 10990, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2389, 11001, '控制柜编号', 'KCS2303091', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2390, 11001, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2391, 11001, '软起动型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2392, 11001, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2393, 11001, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2394, 11001, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2395, 11001, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2396, 11001, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2577, 11002, '控制柜编号', 'KCS2303092', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2578, 11002, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2579, 11002, '软起动型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2580, 11002, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2581, 11002, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2582, 11002, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2583, 11002, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2584, 11002, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2925, 11003, '控制柜编号', 'KCS2303093', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2926, 11003, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2927, 11003, '软起动型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2928, 11003, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2929, 11003, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2930, 11003, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2931, 11003, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2932, 11003, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2639, 11004, '控制柜编号', 'KCS2303094', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2640, 11004, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2641, 11004, '软起动型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2642, 11004, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2643, 11004, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2644, 11004, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2645, 11004, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2646, 11004, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2539, 11005, '控制柜编号', 'KCS2303095', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2540, 11005, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2541, 11005, '软起动型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2542, 11005, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2543, 11005, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2544, 11005, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2545, 11005, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2546, 11005, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3221, 11006, '控制柜编号', 'KJR2303143', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3222, 11006, '控制柜型号', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3223, 11006, 'IO模块型号', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3224, 11006, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3225, 11006, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3226, 11006, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3227, 11006, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3228, 11006, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3229, 11006, 'IO模块地址', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3247, 11007, '控制柜编号', 'KJR2303144', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3248, 11007, '控制柜型号', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3249, 11007, 'IO模块型号', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3250, 11007, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3251, 11007, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3252, 11007, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3253, 11007, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3254, 11007, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3255, 11007, 'IO模块地址', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3087, 11008, '控制柜编号', 'KJR2303145', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3088, 11008, '控制柜型号', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3089, 11008, 'IO模块型号', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3090, 11008, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3091, 11008, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3092, 11008, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3093, 11008, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3094, 11008, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3095, 11008, 'IO模块地址', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3238, 11009, '控制柜编号', 'KJR2303146', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3239, 11009, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3240, 11009, 'IO模块型号', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3241, 11009, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3242, 11009, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3243, 11009, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3244, 11009, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3245, 11009, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3246, 11009, 'IO模块地址', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3829, 11010, '控制柜编号', 'KJR2303147', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3830, 11010, '控制柜型号', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3831, 11010, 'IO模块型号', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3832, 11010, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3833, 11010, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3834, 11010, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3835, 11010, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3836, 11010, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3837, 11010, 'IO模块地址', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2419, 11021, '控制柜编号', 'KJR2110006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2420, 11021, '控制柜型号', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2421, 11021, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2422, 11021, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2423, 11021, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2424, 11021, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2425, 11021, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2426, 11021, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2427, 11021, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2428, 11021, '加热时段', '周三、周六', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2429, 11022, '控制柜编号', 'KJR2110008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2430, 11022, '控制柜型号', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2431, 11022, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2432, 11022, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2433, 11022, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2434, 11022, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2435, 11022, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2436, 11022, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2437, 11022, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2438, 11022, '加热时段', '周二、周五', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2439, 11023, '控制柜编号', 'KJR2110009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2440, 11023, '控制柜型号', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2441, 11023, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2442, 11023, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2443, 11023, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2444, 11023, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2445, 11023, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2446, 11023, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2447, 11023, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2448, 11023, '加热时段', '周一、周四', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2449, 11024, '控制柜编号', 'KJR2110007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2450, 11024, '控制柜型号', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2451, 11024, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2452, 11024, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2453, 11024, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2454, 11024, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2455, 11024, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2456, 11024, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2457, 11024, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2458, 11024, '加热时段', '周二、周五', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2459, 11025, '控制柜编号', 'KJR2110010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2460, 11025, '控制柜型号', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2461, 11025, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2462, 11025, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2463, 11025, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2464, 11025, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2465, 11025, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2466, 11025, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2467, 11025, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2468, 11025, '加热时段', '周三、周六', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2829, 11026, '控制柜编号', 'KJR2202003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2830, 11026, '控制柜型号', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2831, 11026, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2832, 11026, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2833, 11026, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2834, 11026, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2835, 11026, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2836, 11026, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2837, 11026, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2838, 11026, '加热时段', '周一、周四', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3193, 11027, '控制柜编号', 'KJR2202002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3194, 11027, '控制柜型号', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3195, 11027, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3196, 11027, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3197, 11027, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3198, 11027, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3199, 11027, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3200, 11027, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3201, 11027, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3202, 11027, '运行周期', '周一、三、五', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3203, 11028, '控制柜编号', 'KJR2202004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3204, 11028, '控制柜型号', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3205, 11028, 'IO模块型号', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3206, 11028, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3207, 11028, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3208, 11028, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3209, 11028, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3210, 11028, 'IO模块地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3211, 11028, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3212, 11028, '运行周期', '周二、四、六', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2585, 11042, '控制柜编号', 'KJR2306157', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2586, 11042, '控制柜型号', 'JRC380-200-G1J1(不锈钢柜体)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2587, 11042, 'IO模块型号', 'HJ5209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2588, 11042, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2589, 11042, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2590, 11042, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2591, 11042, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2592, 11042, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2593, 11042, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2594, 11042, '管长', '450M', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3408, 11061, '控制柜编号', 'KJR2304153', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3409, 11061, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3410, 11061, 'IO模块型号', 'HJ5209A/HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3411, 11061, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3412, 11061, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3413, 11061, 'PLC地址', '192.168.2.8', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3414, 11061, 'HMI地址', '192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3415, 11061, 'IO模块地址', '192.168.2.22/192.168.2.23', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3416, 11061, '4G模块地址', '192.168.2.19', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3417, 11061, '信号箱', '有线传输', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2492, 11062, '控制柜编号', 'KJR2304154', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2493, 11062, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2494, 11062, 'IO模块型号', 'HJ5209A/HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2495, 11062, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2496, 11062, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2497, 11062, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2498, 11062, 'HMI地址', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2499, 11062, 'IO模块地址', '192.168.2.15/192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2500, 11062, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3399, 11063, '控制柜编号', 'KJR2304155', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3400, 11063, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3401, 11063, 'IO模块型号', 'HJ5209A/HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3402, 11063, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3403, 11063, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3404, 11063, 'PLC地址', '192.168.2.7', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3405, 11063, 'HMI地址', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3406, 11063, 'IO模块地址', '192.168.2.20/192.168.2.21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3407, 11063, '4G模块地址', '192.168.2.18', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3046, 11081, '控制柜编号', 'KCS2309099', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3047, 11081, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3048, 11081, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3049, 11081, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3050, 11081, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3051, 11081, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3052, 11081, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3053, 11081, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4369, 11084, '控制柜编号', 'KCS2209071', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4370, 11084, '控制柜型号', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4371, 11084, '变频器型号', '英威腾GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4372, 11084, '4G模块型号', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4373, 11084, 'PLC地址', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4374, 11084, 'HMI地址', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4375, 11084, '4G模块地址', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3846, 11085, '控制柜编号', 'KCS2303092', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3847, 11085, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3848, 11085, '软起动型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3849, 11085, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3850, 11085, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3851, 11085, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3852, 11085, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3853, 11085, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2695, 11101, '控制柜编号', 'KCS2309102', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2696, 11101, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2697, 11101, '软起动型号', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2698, 11101, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2699, 11101, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2700, 11101, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2701, 11101, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2702, 11101, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4423, 11102, '控制柜编号', 'KCS2309097', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4424, 11102, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4425, 11102, '软起动型号', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4426, 11102, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4427, 11102, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4428, 11102, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4429, 11102, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4430, 11102, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4431, 11102, '备注', '由103更换为097', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2756, 11103, '控制柜编号', 'KCS2309104', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2757, 11103, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2758, 11103, '软起动型号', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2759, 11103, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2760, 11103, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2761, 11103, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2762, 11103, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2763, 11103, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3789, 11104, '控制柜编号', 'KCS2309105', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3790, 11104, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3791, 11104, '软起动型号', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3792, 11104, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3793, 11104, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3794, 11104, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3795, 11104, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3796, 11104, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2703, 11105, '控制柜编号', 'KCS2309106', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2704, 11105, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2705, 11105, '软起动型号', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2706, 11105, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2707, 11105, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2708, 11105, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2709, 11105, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2710, 11105, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2879, 11106, '控制柜编号', 'KCS2309107', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2880, 11106, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2881, 11106, '软起动型号', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2882, 11106, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2883, 11106, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2884, 11106, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2885, 11106, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2886, 11106, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3773, 11107, '控制柜编号', 'KCS2309108', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3774, 11107, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3775, 11107, '软起动型号', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3776, 11107, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3777, 11107, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3778, 11107, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3779, 11107, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3780, 11107, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3765, 11108, '控制柜编号', 'KCS2309109', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3766, 11108, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3767, 11108, '软起动型号', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3768, 11108, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3769, 11108, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3770, 11108, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3771, 11108, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3772, 11108, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2679, 11121, '控制柜编号', 'KCS2309095', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2680, 11121, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2681, 11121, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2682, 11121, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2683, 11121, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2684, 11121, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2685, 11121, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2686, 11121, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2789, 11122, '控制柜编号', 'KCS2309096', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2790, 11122, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2791, 11122, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2792, 11122, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2793, 11122, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2794, 11122, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2795, 11122, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2796, 11122, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4432, 11123, '控制柜编号', 'KCS2309097', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4433, 11123, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4434, 11123, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4435, 11123, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4436, 11123, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4437, 11123, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4438, 11123, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4439, 11123, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3157, 11124, '控制柜编号', 'KCS2309098', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3158, 11124, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3159, 11124, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3160, 11124, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3161, 11124, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3162, 11124, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3163, 11124, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3164, 11124, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2957, 11125, '控制柜编号', 'KCS2309099', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2958, 11125, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2959, 11125, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2960, 11125, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2961, 11125, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2962, 11125, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2963, 11125, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2964, 11125, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2748, 11126, '控制柜编号', 'KCS2309100', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2749, 11126, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2750, 11126, '软起动型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2751, 11126, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2752, 11126, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2753, 11126, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2754, 11126, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2755, 11126, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2671, 11127, '控制柜编号', 'KCS2309101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2672, 11127, '控制柜型号', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2673, 11127, '软起动型号', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2674, 11127, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2675, 11127, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2676, 11127, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2677, 11127, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2678, 11127, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3781, 11161, '控制柜编号', 'KCS2311110', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3782, 11161, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3783, 11161, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3784, 11161, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3785, 11161, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3786, 11161, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3787, 11161, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3788, 11161, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3062, 11162, '控制柜编号', 'KCS2311111', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3063, 11162, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3064, 11162, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3065, 11162, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3066, 11162, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3067, 11162, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3068, 11162, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3069, 11162, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2979, 11163, '控制柜编号', 'KCS2311112', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2980, 11163, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2981, 11163, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2982, 11163, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2983, 11163, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2984, 11163, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2985, 11163, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2986, 11163, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3141, 11164, '控制柜编号', 'KCS2311113', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3142, 11164, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3143, 11164, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3144, 11164, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3145, 11164, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3146, 11164, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3147, 11164, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3148, 11164, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3070, 11165, '控制柜编号', 'KCS2311114', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3071, 11165, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3072, 11165, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3073, 11165, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3074, 11165, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3075, 11165, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3076, 11165, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3077, 11165, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2972, 11181, '控制柜编号', 'KCS2311115', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2973, 11181, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2974, 11181, '变频器型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2975, 11181, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2976, 11181, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2977, 11181, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2978, 11181, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3213, 11182, '控制柜编号', 'KCS2311116', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3214, 11182, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3215, 11182, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3216, 11182, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3217, 11182, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3218, 11182, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3219, 11182, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3220, 11182, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3172, 11183, '控制柜编号', 'KCS2311117', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3173, 11183, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3174, 11183, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3175, 11183, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3176, 11183, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3177, 11183, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3178, 11183, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3179, 11183, '井下机组', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3838, 11201, '控制柜编号', 'KCS2312123', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3839, 11201, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3840, 11201, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3841, 11201, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3842, 11201, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3843, 11201, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3844, 11201, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3845, 11201, '井下机组', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3493, 11202, '控制柜编号', 'KCS2312124', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3494, 11202, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3495, 11202, '软起动型号', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3496, 11202, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3497, 11202, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3498, 11202, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3499, 11202, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3500, 11202, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3230, 11203, '控制柜编号', 'KCS2312125', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3231, 11203, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3232, 11203, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3233, 11203, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3234, 11203, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3235, 11203, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3236, 11203, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3237, 11203, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3565, 11221, '控制柜编号', 'KCS2312118', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3566, 11221, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3567, 11221, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3568, 11221, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3569, 11221, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3570, 11221, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3571, 11221, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3572, 11221, '井下机组', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3573, 11222, '控制柜编号', 'KCS2312119', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3574, 11222, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3575, 11222, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3576, 11222, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3577, 11222, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3578, 11222, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3579, 11222, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3580, 11222, '井下机组', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3581, 11223, '控制柜编号', 'KCS2312120', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3582, 11223, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3583, 11223, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3584, 11223, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3585, 11223, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3586, 11223, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3587, 11223, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3588, 11223, '井下机组', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3798, 11224, '控制柜编号', 'KCS2312121', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3799, 11224, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3800, 11224, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3801, 11224, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3802, 11224, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3803, 11224, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3804, 11224, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3805, 11224, '井下机组', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3589, 11225, '控制柜编号', 'KCS2312122', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3590, 11225, '控制柜型号', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3591, 11225, '软起动型号', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3592, 11225, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3593, 11225, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3594, 11225, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3595, 11225, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3596, 11225, '井下机组', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4403, 11261, '控制柜编号', 'KJR2312163', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4404, 11261, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4405, 11261, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4406, 11261, '调功器型号', 'TH系列(新)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4407, 11261, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4408, 11261, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4409, 11261, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4410, 11261, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4411, 11261, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4412, 11261, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4413, 11262, '控制柜编号', 'KJR2312164', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4414, 11262, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4415, 11262, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4416, 11262, '调功器型号', 'TH系列(新)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4417, 11262, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4418, 11262, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4419, 11262, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4420, 11262, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4421, 11262, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4422, 11262, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3525, 11263, '控制柜编号', 'KJR2312165', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3526, 11263, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3527, 11263, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3528, 11263, '调功器型号', 'TH系列(新)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3529, 11263, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3530, 11263, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3531, 11263, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3532, 11263, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3533, 11263, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3534, 11263, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3683, 11281, '控制柜编号', 'KCS2401126', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3684, 11281, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3685, 11281, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3686, 11281, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3687, 11281, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3688, 11281, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3689, 11281, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3690, 11281, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3597, 11282, '控制柜编号', 'KCS2401127', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3598, 11282, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3599, 11282, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3600, 11282, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3601, 11282, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3602, 11282, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3603, 11282, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3604, 11282, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3605, 11283, '控制柜编号', 'KCS2401128', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3606, 11283, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3607, 11283, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3608, 11283, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3609, 11283, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3610, 11283, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3611, 11283, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3612, 11283, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3613, 11284, '控制柜编号', 'KCS2401129', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3614, 11284, '控制柜型号', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3615, 11284, '软起动型号', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3616, 11284, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3617, 11284, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3618, 11284, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3619, 11284, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3620, 11284, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3545, 11301, '控制柜编号', 'KJR2312167', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3546, 11301, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3547, 11301, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3548, 11301, '调功器型号', 'TH系列(新)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3549, 11301, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3550, 11301, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3551, 11301, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3552, 11301, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3553, 11301, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3554, 11301, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4393, 11302, '控制柜编号', 'KJR2312168', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4394, 11302, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4395, 11302, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4396, 11302, '调功器型号', 'TH系列(新)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4397, 11302, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4398, 11302, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4399, 11302, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4400, 11302, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4401, 11302, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4402, 11302, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3501, 11321, '控制柜编号', 'KJR2401166', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3502, 11321, '控制柜型号', 'JRC660-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3503, 11321, '调功器型号', 'SH300系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3504, 11321, 'PLC地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3505, 11321, 'HMI地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3506, 11361, '控制柜编号', 'KJR2112082', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3507, 11361, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3508, 11361, 'IO模块型号', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3509, 11361, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3510, 11361, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3511, 11361, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3512, 11361, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3513, 11361, 'IO模块地址', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3514, 11361, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3658, 11362, '控制柜编号', 'KJR2112083', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3659, 11362, '控制柜型号', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3660, 11362, '调功器型号', 'TH系列', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3661, 11362, '4G模块型号', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3662, 11362, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3663, 11362, 'HMI地址', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3664, 11362, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3961, 11363, '控制柜编号', 'KJR2402169', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3962, 11363, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3963, 11363, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3964, 11363, '调功器型号', 'TH系列(新)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3965, 11363, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3966, 11363, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3967, 11363, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3968, 11363, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3969, 11363, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3970, 11363, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3971, 11364, '控制柜编号', 'KJR2402170', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3972, 11364, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3973, 11364, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3974, 11364, '调功器型号', 'TH系列(新)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3975, 11364, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3976, 11364, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3977, 11364, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3978, 11364, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3979, 11364, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3980, 11364, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3981, 11365, '控制柜编号', 'KJR2402171', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3982, 11365, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3983, 11365, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3984, 11365, '调功器型号', 'TH系列(新)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3985, 11365, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3986, 11365, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3987, 11365, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3988, 11365, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3989, 11365, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3990, 11365, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3991, 11366, '控制柜编号', 'KJR2402172', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3992, 11366, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3993, 11366, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3994, 11366, '调功器型号', 'TH系列(新)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3995, 11366, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3996, 11366, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3997, 11366, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3998, 11366, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3999, 11366, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4000, 11366, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4376, 11381, '控制柜编号', 'KCS2404130', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4377, 11381, '控制柜型号', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4378, 11381, '变频器型号', 'GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4379, 11381, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4380, 11381, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4381, 11381, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4382, 11381, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4383, 11381, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3665, 11382, '控制柜编号', 'KCS2404131', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3666, 11382, '控制柜型号', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3667, 11382, '变频器型号', 'GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3668, 11382, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3669, 11382, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3670, 11382, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3671, 11382, '4G模块地址', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3672, 11382, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4188, 11421, '控制柜编号', 'KCS2404132', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4189, 11421, '控制柜型号', 'CSC380-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4190, 11421, '软起动型号', 'NJR5-110/ZX3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4191, 11421, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4192, 11421, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4193, 11421, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4194, 11421, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4195, 11421, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4196, 11421, '其他', 'ST30', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4197, 11461, '控制柜编号', 'KCS2404133', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4198, 11461, '控制柜型号', 'CSC380-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4199, 11461, '软起动型号', 'NJR5-110/ZX3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4200, 11461, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4201, 11461, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4202, 11461, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4203, 11461, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4204, 11461, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4205, 11461, '其他', 'ST30', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4206, 11462, '控制柜编号', 'KCS2404134', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4207, 11462, '控制柜型号', 'CSC380-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4208, 11462, '软起动型号', 'NJR5-110/ZX3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4209, 11462, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4210, 11462, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4211, 11462, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4212, 11462, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4213, 11462, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4214, 11462, '其他', 'ST30', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4215, 11463, '控制柜编号', 'KCS2404135', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4216, 11463, '控制柜型号', 'CSC380-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4217, 11463, '软起动型号', 'NJR5-110/ZX3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4218, 11463, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4219, 11463, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4220, 11463, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4221, 11463, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4222, 11463, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4223, 11463, '其他', 'ST30', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4384, 11464, '控制柜编号', 'KCS2404136', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4385, 11464, '控制柜型号', 'CSC380-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4386, 11464, '软起动型号', 'NJR5-110/ZX3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4387, 11464, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4388, 11464, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4389, 11464, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4390, 11464, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4391, 11464, '井下机组', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4392, 11464, '其他', 'ST30', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4166, 11501, '控制柜编号', 'KJR2407275', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4167, 11501, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4168, 11501, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4169, 11501, '调功器型号', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4170, 11501, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4171, 11501, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4172, 11501, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4173, 11501, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4174, 11501, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4175, 11501, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4176, 11501, '其他', 'ST30/G3C载波模块', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4155, 11502, '控制柜编号', 'KJR2407276', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4156, 11502, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4157, 11502, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4158, 11502, '调功器型号', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4159, 11502, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4160, 11502, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4161, 11502, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4162, 11502, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4163, 11502, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4164, 11502, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4165, 11502, '其他', 'ST30/G3C载波模块', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4144, 11503, '控制柜编号', 'KJR2407277', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4145, 11503, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4146, 11503, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4147, 11503, '调功器型号', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4148, 11503, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4149, 11503, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4150, 11503, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4151, 11503, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4152, 11503, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4153, 11503, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4154, 11503, '其他', 'ST30/G3C载波模块', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4133, 11504, '控制柜编号', 'KJR2407278', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4134, 11504, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4135, 11504, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4136, 11504, '调功器型号', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4137, 11504, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4138, 11504, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4139, 11504, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4140, 11504, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4141, 11504, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4142, 11504, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4143, 11504, '其他', 'ST30/G3C载波模块', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4122, 11505, '控制柜编号', 'KJR2407279', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4123, 11505, '控制柜型号', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4124, 11505, 'IO模块型号', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4125, 11505, '调功器型号', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4126, 11505, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4127, 11505, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4128, 11505, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4129, 11505, 'IO模块地址', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4130, 11505, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4131, 11505, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4132, 11505, '其他', 'ST30/G3C载波模块', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4285, 11506, '控制柜编号', 'KJR2407280', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4286, 11506, '控制柜型号', 'JRC660-150-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4287, 11506, 'IO模块型号', 'HJ3209D/HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4288, 11506, '调功器型号', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4289, 11506, '4G模块型号', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4290, 11506, 'PLC地址', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4291, 11506, 'HMI地址', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4292, 11506, 'IO模块地址', '192.168.2.17/192.168.2.18', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4293, 11506, '信号箱地址', '有线通讯', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4294, 11506, '4G模块地址', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4295, 11506, '其他', 'S71200/G3C载波模块', null);

commit;

ALTER TRIGGER trg_b_org_i_u ENABLE;
ALTER TRIGGER trg_b_device_i ENABLE;
ALTER TRIGGER trg_b_auxiliary2master_i ENABLE;
ALTER TRIGGER trg_b_DEVICEADDINFO_i ENABLE;

exit;