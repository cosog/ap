/*==============================================================*/
/* ��ʼ����֯����                                                 */
/*==============================================================*/
ALTER TRIGGER trg_b_org_i_u DISABLE;
insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1, '0000', '��֯���ڵ�', '��֯���ڵ�', 0, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (2, null, '����ú����', '����ú����', 1323, 2);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (3, null, '��������', null, 1322, 7);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (4, null, '̫�ֹ���', null, 27, 1);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (5, null, '�������﹫˾', null, 1322, 6);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (6, null, '�˺�', null, 2, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (7, null, '��ׯ��', null, 2, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (8, null, '��ׯ��', null, 2, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (9, null, '��������', null, 4, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (10, null, 'ƣ�Ͳ���', null, 4, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (12, null, 'ʮ��', null, 5, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (13, null, '����', null, 5, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (22, null, '������', null, 1323, 3);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (23, null, '����ú����', null, 1323, 4);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (24, null, '����ú����', null, 1323, 5);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (25, null, '��������', null, 1322, 8);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (26, null, '�ӳ�����', null, 1323, 9);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (27, null, '���ڲ���', null, 1, 2);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (28, null, '���豸', null, 222, 2);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (29, null, '���豸', null, 222, 10);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (42, null, '������', null, 1, 3);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (67, null, '������ȹ�', null, 29, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (68, null, '�����峧', null, 67, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (69, null, '��21', null, 68, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (72, null, '����', null, 68, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (76, null, '����һ��', null, 67, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (77, null, 'ͬ��', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (84, null, '����', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (88, null, '�|��', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (90, null, '����', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (92, null, '����', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (97, null, '�α�', null, 76, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (162, null, '����Ӧ�̡����', null, 29, 50);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (182, null, '����Ӧ�̡�ɼ��', null, 29, 51);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (202, null, '����վ', null, 1, 4);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (203, null, '������Դ', null, 1323, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (204, null, '���ܼ��ȹ�', null, 29, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (205, null, '����', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (206, null, '�ӳ�', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (207, null, '������', null, 206, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (208, null, '������ȹ�', null, 29, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (222, null, '�ֳ��豸', null, 1, 1);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (242, null, 'ˮԴ��', null, 29, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (243, null, '����һ��', null, 242, 1);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (244, null, '�������', null, 242, 2);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (245, null, '�����ĳ�', null, 242, 4);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (246, null, '�����峧', null, 242, 5);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (247, null, '����ų�', null, 242, 9);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (248, null, '����ʮ��', null, 242, 10);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (249, null, '����ʮһ��', null, 242, 11);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (250, null, '����ʮ����', null, 242, 12);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (251, null, '����', null, 243, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (252, null, '����', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (253, null, '����', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (254, null, '������', null, 245, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (255, null, '����', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (256, null, '����Դ', null, 247, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (257, null, '��Զ', null, 248, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (258, null, '�̳�', null, 250, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (259, null, '��ɽ', null, 249, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (260, null, '̫����', null, 249, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (261, null, '�Ǻ�', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (262, null, '��ȳ�', null, 247, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (263, null, '��ؿ�', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (264, null, '���ɽ��', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (265, null, '���ɽ��', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (266, null, '���ɽ��', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (267, null, 'Ӫ��ɽ', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (268, null, '����', null, 248, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (269, null, '����', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (282, null, '�ֳ���������', null, 29, 1);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (302, null, '����Ӧ�̡���ŷ', null, 29, 52);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (322, null, '�峧', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (323, null, '��ؿӲ�����ҵ��', null, 322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (324, null, '������', null, 322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (325, null, '�鱱', null, 322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (326, null, '����', null, 322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (327, null, 'ʮ��', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (328, null, '�Ǻ�', null, 327, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (382, null, '��������  ', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (383, null, '����', null, 382, 4);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (402, null, '��������', null, 242, 6);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (403, null, 'ש����ҵ��', null, 402, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (422, null, '������Ȼ��', null, 242, 21);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (442, null, '����', null, 243, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (443, null, '����ҳ����', null, 242, 20);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (444, null, '��ʮת', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (445, null, '���ת', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (446, null, 'ͩ��', null, 249, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (447, null, '����', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (448, null, '����', null, 249, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (449, null, '��������', null, 242, 3);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (450, null, '�쾮��', null, 449, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (451, null, '����', null, 402, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (462, null, '����ʮ��', null, 208, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (463, null, '����', null, 462, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (482, null, '����', null, 67, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (483, null, '����3#��', null, 482, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (503, null, '�뱱', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (504, null, '���', null, 250, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (505, null, '�Ǵ�', null, 248, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (506, null, '�ո�������', null, 242, 22);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (507, null, '����ʮһ��', null, 208, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (508, null, '��ɽ', null, 507, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (522, null, '����', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (523, null, '������Ŀ', null, 522, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (562, null, '���ȵ�����Ŀ', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (563, null, '���ȵ���', null, 562, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (564, null, '������', null, 205, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (582, null, '����˳�', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (602, null, '�������վ', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (622, null, '����', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (623, null, 'ҳ����', null, 622, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (624, null, '�������', null, 67, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (625, null, '����', null, 624, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (642, null, '����', null, 250, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (662, null, '���Թ�', null, 162, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (682, null, 'ʤ������', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (683, null, '����', null, 682, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (702, null, '������', null, 246, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (722, null, '������', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (742, null, 'δ֪����', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (764, null, '��ƽ̨', null, 582, 5);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (765, null, '��ƽ̨', null, 582, 6);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (782, null, '�����߳�', null, 242, 7);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (783, null, '���', null, 782, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (802, null, '��������', null, 29, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (803, null, 'KD93����ģ��', null, 802, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (822, null, '��ׯ��ҵ��', null, 402, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (823, null, 'Ѧ��', null, 247, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (842, null, '����', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (862, null, '����', null, 247, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (882, null, 'δ֪����', null, 782, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (883, null, 'δ֪����', null, 402, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (902, null, '��13��', null, 443, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (922, null, '����ú����', null, 1323, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (942, null, 'ʮ��ƽ̨', null, 582, 12);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (962, null, 'ʮ��ƽ̨', null, 582, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (963, null, '��ƽ̨', null, 582, 3);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (982, null, 'ͭ��', null, 507, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1002, null, 'Ԫ��', null, 248, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1022, null, '�����ĳ�', null, 204, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1023, null, '����ƽ', null, 1022, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1062, null, '��ˮ��', null, 449, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1082, null, '֣ׯ����', null, 23, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1122, null, '������Ŀ��', null, 23, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1142, null, 'ʮ��ƽ̨', null, 582, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1162, null, '����', null, 462, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1182, null, '��Զ', null, 462, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1202, null, 'һ��', null, 382, 1);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1222, null, '�¼�', null, 249, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1242, null, '��Ʒ��', null, 162, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1262, null, '��ׯ����', null, 23, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1264, null, '���Ͽ�ҵ', null, 1323, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1265, null, '����ú����', null, 1323, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1266, null, '��������', null, 1323, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1282, null, '���� ', null, 402, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1302, null, 'ͭ�������������޹�˾', null, 1322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1303, null, 'μ������', null, 1302, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1304, null, '��������', null, 1322, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1305, null, '�Ͼ�����ҵ��L������', null, 1304, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1322, null, '�����Ĥ��', null, 28, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1323, null, 'ú������Ĥ��', null, 28, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1324, null, 'Ԫ��', null, 462, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1342, null, '����', null, 382, 2);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1362, null, '�Ǻ�', null, 462, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1382, null, '���Ҵ�', null, 244, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1402, null, '����Ӧ�̡���������', null, 29, 53);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1422, null, '����', null, 782, null);

insert into tbl_org (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (1423, null, '����', null, 382, 6);


/*==============================================================*/
/* ��ʼ���豸����                                              */
/*==============================================================*/
ALTER TRIGGER trg_b_device_i DISABLE;

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (1, 9, '���ڲ���', 3, 0, 'TCP Client', '00000000126', null, '01', null, 'instance105', 'alarminstance43', 'displayinstance103', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1001, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (2, 7, 'ZY-284H1', 3, 0, 'TCP Client', '00000000112', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 22, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (3, 6, 'PH-111H1', 3, 0, 'TCP Client', '00000000307', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (4, 6, 'PH-111H2', 3, 0, 'TCP Client', '00000000405', null, '01', null, 'instance115', 'alarminstance64', 'displayinstance112', null, null, null, null, null, null, '{}', to_date('26-08-2024 11:15:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 0, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (22, 24, 'DS-077ƽ1', 3, 0, 'TCP Client', '00000000123', null, '01', null, 'instance85', 'alarminstance43', 'displayinstance83', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (45, 13, '��36-41', 3, 1, 'TCP Client', '00000000177', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 31, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (47, 13, '��55-48', 3, 1, 'TCP Client', '00000000195', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 33, 0);

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
values (319, 25, '��5-034', 3, 1, 'TCP Client', '00000000383', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (320, 25, 'FP231', 3, 1, 'TCP Client', '00000000384', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (322, 25, '��+5-038', 3, 1, 'TCP Client', '00000000386', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (330, 25, '��5-36.1', 3, 1, 'TCP Client', '00000000394', null, '01', null, 'instance117', 'alarminstance64', 'displayinstance116', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (348, 12, '��130-201X', 3, 1, 'TCP Client', '00000000107', null, '01', null, 'instance111', 'alarminstance43', 'displayinstance106', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 27, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (352, 26, '��ƽ33', 3, 1, 'TCP Client', '00000000188', null, '01', null, 'instance114', 'alarminstance43', 'displayinstance110', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (561, 9, 'Զ��ͨѶ����', 3, 0, 'TCP Client', '00000000800', null, '01', null, 'instance106', 'alarminstance64', 'displayinstance117', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:18:03', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (641, 27, '������ϵͳ����', 3, 0, 'TCP Client', '00000000025', null, '01', null, 'instance105', 'alarminstance64', 'displayinstance103', null, null, null, null, null, null, '{}', to_date('06-08-2024 12:09:35', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (10021, 29, '660����', 8, 1, 'TCP Client', '00000000337', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10041, 563, '���ȵ�������', 8, 1, 'TCP Client', '00000000338', null, '01', null, 'instance139', 'alarminstance67', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10061, 204, 'KJR2112078', 8, 1, 'TCP Client', '00000000339', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 3, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10241, 242, 'KCS2106026', 9, 1, 'TCP Client', '00000000232', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10261, 662, '�ⲿ����1', 8, 1, 'TCP Client', '00000001000', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10281, 504, 'N147S19', 9, 1, 'TCP Client', '00000000409', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10301, 1002, 'B84S2', 9, 1, 'TCP Client', '00000000410', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10302, 447, 'W86S57', 9, 1, 'TCP Client', '00000010001', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10303, 242, 'KCS2206053�Ѽ�', 9, 1, 'TCP Client', '00000010003', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10304, 503, '��303', 9, 1, 'TCP Client', '00000010004', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (10321, 207, '��910ƽ2������', 8, 1, 'TCP Client', '00000000183', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10322, 564, '��26-33', 8, 1, 'TCP Client', '00000000254', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10323, 564, '��35-44', 8, 1, 'TCP Client', '00000000326', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10324, 564, '��38-45', 8, 1, 'TCP Client', '00000000251', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10325, 564, '��55-48', 8, 1, 'TCP Client', '00000000253', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10326, 564, '��36-41', 8, 1, 'TCP Client', '00000000255', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (10350, 262, '��64-117S3', 9, 1, 'TCP Client', '00000000138', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (10359, 261, '¤ҳ6', 9, 1, 'TCP Client', '00000018018', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10360, 253, '��12B12S1', 9, 1, 'TCP Client', '00000000241', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10361, 253, '��100', 9, 1, 'TCP Client', '00000000238', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (10375, 269, '��299', 9, 1, 'TCP Client', '00000000249', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10376, 29, 'ZH179S4', 9, 1, 'TCP Client', null, null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10377, 255, 'H57S32', 9, 1, 'TCP Client', '00000000225', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10378, 325, '��32-52', 8, 1, 'TCP Client', '00000000317', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 101, 0);

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
values (10384, 72, '����14-36', 10, 1, 'TCP Client', '00000000296', null, '01', null, 'instance129', 'alarminstance65', 'displayinstance124', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10385, 72, '����14-70', 10, 1, 'TCP Client', '00000000294', null, '01', null, 'instance129', 'alarminstance65', 'displayinstance124', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 12, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10386, 72, '����14-79', 10, 1, 'TCP Client', '00000000295', null, '01', null, 'instance129', 'alarminstance65', 'displayinstance124', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 13, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10387, 69, '��21-7����21', 10, 1, 'TCP Client', '00000000259', null, '01', null, 'instance129', 'alarminstance65', 'displayinstance124', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 14, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10388, 69, '��21����21-2', 10, 1, 'TCP Client', '00000000262', null, '01', null, 'instance129', 'alarminstance65', 'displayinstance124', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 15, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10389, 77, '1#��63-119-120', 10, 1, 'TCP Client', '00000000103', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 30, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10390, 77, '3#��63-27-28-29', 10, 1, 'TCP Client', '00000000101', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 32, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10391, 77, '2#��63-53-54', 10, 1, 'TCP Client', '00000000102', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 31, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10392, 282, '��63-33���飨����', 10, 1, 'TCP Client', '00000000079', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 39, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10393, 282, '��63-6-12������', 10, 1, 'TCP Client', '00000000099', null, '01', null, 'instance127', 'alarminstance65', 'displayinstance123', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 38, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10394, 282, '��63-13������', 10, 1, 'TCP Client', '00000000100', null, '01', null, 'instance127', 'alarminstance65', 'displayinstance123', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 37, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10395, 483, '����3#��', 10, 1, 'TCP Client', '00000000407', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10396, 625, 'С14-25-1', 10, 1, 'TCP Client', '00000000408', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10397, 84, '��30-204', 10, 1, 'TCP Client', '00000000097', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10398, 84, '��44-62', 10, 1, 'TCP Client', '00000000098', null, '01', null, 'instance130', 'alarminstance65', 'displayinstance120', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10399, 84, '��67', 10, 1, 'TCP Client', '00000000157', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10400, 88, '�|40-10', 10, 1, 'TCP Client', '00000000095', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10401, 97, '��119-23', 10, 1, 'TCP Client', '00000000075', null, '01', null, 'instance125', 'alarminstance65', 'displayinstance126', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10402, 97, '��119-31', 10, 1, 'TCP Client', '00000000036', null, '01', null, 'instance120', 'alarminstance65', 'displayinstance125', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10403, 92, '��36-12', 10, 1, 'TCP Client', '00000000078', null, '01', null, 'instance128', 'alarminstance65', 'displayinstance119', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10404, 92, '��36-4', 10, 1, 'TCP Client', '00000000077', null, '01', null, 'instance130', 'alarminstance65', 'displayinstance120', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10405, 92, '��36-9����2��', 10, 1, 'TCP Client', '00000000076', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10406, 92, '��6-9', 10, 1, 'TCP Client', '00000000096', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10407, 90, '��59-11', 10, 1, 'TCP Client', '00000000094', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10408, 325, 'ɳ20-82', 8, 1, 'TCP Client', '00000011001', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 103, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10409, 325, 'ɳ19-7', 8, 1, 'TCP Client', '00000000302', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 102, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10410, 325, 'ɳ20-81', 8, 1, 'TCP Client', '00000000327', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 104, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10411, 325, 'ɳ16-11', 8, 1, 'TCP Client', '00000000314', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 105, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10412, 325, 'ܫ33-96', 8, 1, 'TCP Client', '00000000321', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 106, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10413, 325, 'ܫ34-97', 8, 1, 'TCP Client', '00000011002', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 107, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10414, 325, 'ܫ33-91', 8, 1, 'TCP Client', '00000011003', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 108, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10421, 523, '��8-27X', 8, 1, 'TCP Client', '00000011004', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1000, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10422, 623, 'H60-22', 8, 1, 'TCP Client', '00000011006', null, '01', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1000, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10423, 623, 'H60-18', 8, 1, 'TCP Client', '00000011005', null, '01', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1000, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10441, 450, 'Y427S1', 9, 1, 'TCP Client', '00000010016', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10442, 254, 'AS66', 9, 1, 'TCP Client', '00000010017', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10462, 323, '�ؼ�85-473/�ؼ�85-494', 8, 1, 'TCP Client', '00000000204', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10463, 323, '��69-59/��70-59/��69-58', 8, 1, 'TCP Client', '00000000208', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10467, 323, '�ؼ�85-493/�ؼ�87-473/�ؼ�87-491/�ؼ�87-472', 8, 1, 'TCP Client', '00000000207', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10470, 323, '��70-36/��70-37', 8, 1, 'TCP Client', '00000000205', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10472, 324, '��18-47', 8, 1, 'TCP Client', '00000000303', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 109, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10473, 324, '��33-18', 8, 1, 'TCP Client', '00000000299', null, '01', null, 'instance154', 'alarminstance66', 'displayinstance134', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10474, 324, '��19-23', 8, 1, 'TCP Client', '00000000325', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 110, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10475, 324, '��11-29', 8, 1, 'TCP Client', '00000000322', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 112, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10476, 324, '��31-14', 8, 1, 'TCP Client', '00000000329', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10477, 324, '��25-45', 8, 1, 'TCP Client', '00000000330', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10478, 324, '��19-47', 8, 1, 'TCP Client', '00000000313', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 111, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10479, 325, 'ܫ48-88�����ϣ�', 8, 1, 'TCP Client', '00000000316', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10480, 326, '��33-27', 8, 1, 'TCP Client', '00000000328', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10481, 326, '��236-53�����ϣ�', 8, 1, 'TCP Client', '00000000315', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10482, 326, '��33-25', 8, 1, 'TCP Client', '00000000320', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10483, 207, '��ƽ33', 8, 1, 'TCP Client', '00000000188', null, '01', null, 'instance139', 'alarminstance66', 'displayinstance135', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10484, 328, '��129-201X', 8, 1, 'TCP Client', '00000000037', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10485, 328, '��18-78', 8, 1, 'TCP Client', '00000000108', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10486, 328, '��19-76', 8, 1, 'TCP Client', '00000000104', null, '01', null, null, null, null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10487, 328, '��19-78', 8, 1, 'TCP Client', '00000000124', null, '01', null, null, null, null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10489, 204, 'KJR2111074', 8, 1, 'TCP Client', '00000000331', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10490, 383, '��9-2618', 8, 1, 'TCP Client', '00000000235', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10491, 204, 'KJR2111075', 8, 1, 'TCP Client', '00000000332', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10492, 204, 'KJR2111076', 8, 1, 'TCP Client', '00000000333', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10493, 204, 'KJR2111077', 8, 1, 'TCP Client', '00000000334', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10494, 207, '��910ƽ2', 8, 1, 'TCP Client', '00000000335', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10495, 204, 'KJR2202008', 8, 1, 'TCP Client', '00000000336', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10496, 204, 'KJR2204013', 8, 1, 'TCP Client', '00000000357', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10497, 1202, '��7-3225', 8, 1, 'TCP Client', '00000000396', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10498, 1342, '��3-PS2311', 8, 1, 'TCP Client', '00000000397', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10499, 1202, '8-Ps3237', 8, 1, 'TCP Client', '00000000399', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10500, 1423, '5-PS3717', 8, 1, 'TCP Client', '00000000400', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('06-09-2024 14:19:40', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10501, 204, 'KJR2204018', 8, 1, 'TCP Client', '00000000401', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10502, 1423, '5-PS3327', 8, 1, 'TCP Client', '00000000406', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('06-09-2024 14:19:40', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10521, 208, '��262-407', 8, 1, 'TCP Client', '00000000261', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10522, 982, '��ƽ378-10', 8, 1, 'TCP Client', '00000000278', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10523, 208, '��103-69', 8, 1, 'TCP Client', '00000000288', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10524, 208, '��168-6', 8, 1, 'TCP Client', '00000000263', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10525, 208, 'ͩ299-19', 8, 1, 'TCP Client', '00000000291', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10526, 463, '��183-7', 8, 1, 'TCP Client', '00000018011', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 107, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10527, 463, '��183-35', 8, 1, 'TCP Client', '00000000289', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 135, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10528, 463, '��183-13', 8, 1, 'TCP Client', '00000018012', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 113, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10529, 463, '��183-76-1', 8, 1, 'TCP Client', '00000018014', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 176, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10530, 463, '��183-1', 8, 1, 'TCP Client', '00000018010', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 101, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10531, 208, 'Ԫ276', 8, 1, 'TCP Client', '00000000068', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10532, 208, '��8-111', 8, 1, 'TCP Client', '00000000071', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10533, 208, '«82-59', 8, 1, 'TCP Client', '00000000072', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10534, 208, '��60-62', 8, 1, 'TCP Client', '00000000073', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10535, 208, '��88-96', 8, 1, 'TCP Client', '00000000080', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10536, 208, '��116-25��', 8, 1, 'TCP Client', '00000000081', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10537, 208, '��6-199', 8, 1, 'TCP Client', '00000000082', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10538, 208, 'Ԫ200-54', 8, 1, 'TCP Client', '00000000083', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10539, 208, '��92-96��', 8, 1, 'TCP Client', '00000000084', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10540, 208, '��232-46��', 8, 1, 'TCP Client', '00000000085', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10541, 208, '��76-9A', 8, 1, 'TCP Client', '00000000086', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10542, 208, '��155-6��', 8, 1, 'TCP Client', '00000000088', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10543, 208, '��93-95��', 8, 1, 'TCP Client', '00000000090', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10544, 508, 'ɽ139��', 8, 1, 'TCP Client', '00000000110', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10545, 208, 'ɽ137-2��', 8, 1, 'TCP Client', '00000000143', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10546, 508, 'ɽ139-2��', 8, 1, 'TCP Client', '00000000144', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10547, 508, 'ɽ130-10��', 8, 1, 'TCP Client', '00000000146', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10548, 508, 'ɽ136-02��', 8, 1, 'TCP Client', '00000000147', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10549, 208, 'ɽ139', 8, 1, 'TCP Client', '00000000148', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10550, 208, '��451', 8, 1, 'TCP Client', '00000000149', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10551, 508, '��85-202��', 8, 1, 'TCP Client', '00000000150', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10552, 208, '��398-09', 8, 1, 'TCP Client', '00000000151', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10553, 208, '��300-51X', 8, 1, 'TCP Client', '00000000152', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10554, 208, '��298-56X', 8, 1, 'TCP Client', '00000000153', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10555, 208, '��398-012', 8, 1, 'TCP Client', '00000000154', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10556, 208, '��299-64X', 8, 1, 'TCP Client', '00000000155', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10557, 208, '��116-25', 8, 1, 'TCP Client', '00000000256', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10558, 208, '��7-5', 8, 1, 'TCP Client', '00000000233', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10559, 208, '��78', 8, 1, 'TCP Client', '00000000234', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10560, 208, '��116-9', 8, 1, 'TCP Client', '00000000236', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10561, 208, '��ƽ277-8', 8, 1, 'TCP Client', '00000000237', null, '01', null, 'instance133', 'alarminstance67', 'displayinstance140', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10562, 208, '��84-11', 8, 1, 'TCP Client', '00000000038', null, '01', null, 'instance132', 'alarminstance67', 'displayinstance141', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10563, 208, 'ѧ63-9', 8, 1, 'TCP Client', '00000000046', null, '01', null, 'instance132', 'alarminstance67', 'displayinstance141', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10564, 208, 'ѧ49-71', 8, 1, 'TCP Client', '00000000047', null, '01', null, 'instance132', 'alarminstance67', 'displayinstance141', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10565, 208, '��46-66', 8, 1, 'TCP Client', '00000000049', null, '01', null, 'instance132', 'alarminstance67', 'displayinstance141', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10566, 208, '��76-92', 8, 1, 'TCP Client', '00000000053', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10567, 208, 'Ԫ267٪��', 8, 1, 'TCP Client', '00000000054', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10568, 208, 'Ԫ267����', 8, 1, 'TCP Client', '00000000055', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10569, 208, '��163-04', 8, 1, 'TCP Client', '00000000056', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10570, 208, '��83-87', 8, 1, 'TCP Client', '00000000057', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10571, 208, '��148-6', 8, 1, 'TCP Client', '00000000059', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10572, 208, 'Ԫ230-7', 8, 1, 'TCP Client', '00000000060', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10573, 208, '��9-86', 8, 1, 'TCP Client', '00000000070', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10574, 208, '��57-103', 8, 1, 'TCP Client', '00000000074', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10575, 208, '��52-103', 8, 1, 'TCP Client', '00000000050', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10576, 208, '��46-104', 8, 1, 'TCP Client', '00000000058', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10577, 208, '��77-105', 8, 1, 'TCP Client', '00000000061', null, '01', null, null, 'alarminstance67', null, null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10601, 259, 'ZBS194', 9, 1, 'TCP Client', '00000010021', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10602, 253, 'X89S7', 9, 1, 'TCP Client', '00000010022', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10603, 242, 'KCS2209064�Ѽ�', 9, 1, 'TCP Client', '00000010023', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10604, 403, 'A28S1', 9, 1, 'TCP Client', '00000010024', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10605, 264, 'L1S71(�滻)', 9, 1, 'TCP Client', '00000010025', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10606, 450, 'H39S9', 9, 1, 'TCP Client', '00000010026', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10607, 451, 'H154s27(����)', 9, 1, 'TCP Client', '00000010027', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10608, 261, 'QB23S02(����)', 9, 1, 'TCP Client', '00000010028', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10609, 242, 'KCS2209072', 9, 1, 'TCP Client', '00000010029', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10622, 1142, '22-б80', 8, 1, 'TCP Client', '00000000350', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10624, 1142, '20-б82', 8, 1, 'TCP Client', '00000000351', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10641, 662, '�ⲿ����2', 8, 1, 'TCP Client', '00000001001', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10661, 266, 'KCS2106028', 9, 1, 'TCP Client', '00000000227', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10681, 242, '14S38', 9, 1, 'TCP Client', null, null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10701, 463, '��183-08', 8, 1, 'TCP Client', '00000011007', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 108, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10702, 463, '��183-31', 8, 1, 'TCP Client', '00000011008', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('23-10-2024 14:14:54', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 131, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10703, 463, '��482-1', 8, 1, 'TCP Client', '00000011009', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10704, 942, '25-б83', 8, 1, 'TCP Client', '00000011010', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10705, 962, '21-б83', 8, 1, 'TCP Client', '00000011011', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10706, 962, '23-б83', 8, 1, 'TCP Client', '00000011012', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10707, 942, '24-б82', 8, 1, 'TCP Client', '00000011013', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10708, 942, '28-б82', 8, 1, 'TCP Client', '00000011014', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 13, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10709, 962, '23-б81', 8, 1, 'TCP Client', '00000011015', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10721, 942, '27-б81', 8, 1, 'TCP Client', '00000011016', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10722, 963, '36-б79', 8, 1, 'TCP Client', '00000011017', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 12, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10723, 942, '27-б83', 8, 1, 'TCP Client', '00000011018', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 12, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10724, 962, '22-б84', 8, 1, 'TCP Client', '00000011019', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 10, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10725, 1142, '21-б81', 8, 1, 'TCP Client', '00000011020', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10726, 963, '35-б76', 8, 1, 'TCP Client', '00000011021', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10727, 963, '34-б77', 8, 1, 'TCP Client', '00000011022', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10728, 963, '37-б78', 8, 1, 'TCP Client', '00000011023', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 13, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10729, 765, '30-б83', 8, 1, 'TCP Client', '00000011024', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 3, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10730, 963, '36-б77', 8, 1, 'TCP Client', '00000011025', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10731, 1142, '19-б83', 8, 1, 'TCP Client', '00000011026', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10732, 765, '31-б84', 8, 1, 'TCP Client', '00000011027', null, '01', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 4, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10741, 67, '�½�200m', 10, 1, 'TCP Client', '00000000109', null, '01', null, 'instance126', 'alarminstance65', 'displayinstance122', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10765, 242, 'KCS2210073', 9, 1, 'TCP Client', '00000013001', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10766, 252, 'B455S6', 9, 1, 'TCP Client', '00000013002', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10767, 254, 'AS65(�滻)', 9, 1, 'TCP Client', '00000018003', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (10801, 282, 'KD93����ģ��', 8, 1, 'TCP Client', '10000000001', null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10841, 504, 'ZH179S4', 9, 1, 'TCP Client', null, null, '01', null, 'instance146', 'alarminstance68', 'displayinstance143', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10861, 623, 'H40-4', 8, 1, 'TCP Client', '00000018017', null, '1', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10862, 623, 'H60-9', 8, 1, 'TCP Client', '00000011029', null, '1', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10863, 623, 'H60-14', 8, 1, 'TCP Client', '00000018013', null, '1', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10864, 1023, '��96-39', 8, 1, 'TCP Client', '00000011031', null, '1', null, 'instance142', 'alarminstance67', 'displayinstance128', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10881, 302, 'KJR2301118', 8, 1, 'TCP Client', '00000011032', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10882, 463, '��482��', 8, 1, 'TCP Client', '00000011033', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10883, 463, '��183-12', 8, 1, 'TCP Client', '00000011034', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 112, 0);

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
values (10921, 463, '��183-10', 8, 1, 'TCP Client', '00000011041', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 110, 0);

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
values (10967, 1182, '��270-04', 8, 1, 'TCP Client', '32000000007', null, '01', null, 'instance152', 'alarminstance67', 'displayinstance147', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 7, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10968, 1324, 'Ԫ��14-151��', 8, 1, 'TCP Client', '32000000008', null, '01', null, 'instance152', 'alarminstance67', 'displayinstance147', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 8, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10969, 1362, '��136-145', 8, 1, 'TCP Client', '32000000009', null, '01', null, 'instance152', 'alarminstance67', 'displayinstance147', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10970, 1242, 'KJR2303137', 8, 1, 'TCP Client', '32000000010', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 10, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (10971, 463, '��183-109', 8, 1, 'TCP Client', '32000000011', null, '01', null, 'instance152', 'alarminstance67', 'displayinstance147', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

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
values (10985, 253, '��481', 9, 1, 'TCP Client', '00000013013', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (11002, 448, 'ZBS198(��)', 9, 1, 'TCP Client', '00000013020', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11003, 182, 'KCS2303093', 9, 1, 'TCP Client', '00000013021', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11004, 253, 'B73S9����377-96��', 9, 1, 'TCP Client', '00000018002', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11005, 783, 'B299S3', 9, 1, 'TCP Client', '00000013023', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11006, 463, 'Ԫ546��', 8, 1, 'TCP Client', '00000011042', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11007, 1162, '��ƽ37', 8, 1, 'TCP Client', '00000011043', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11008, 1162, '��ƽ20', 8, 1, 'TCP Client', '00000011044', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11009, 1162, '��ƽ19', 8, 1, 'TCP Client', '00000011045', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11010, 508, 'ɽ137-02���£�', 8, 1, 'TCP Client', '00000011046', null, '01', null, 'instance136', 'alarminstance67', 'displayinstance127', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11021, 764, '33-б78', 8, 1, 'TCP Client', '00000000346', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11022, 764, '32-б79', 8, 1, 'TCP Client', '00000000344', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11023, 764, '34-б79', 8, 1, 'TCP Client', '00000000342', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 3, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11024, 764, '34-б80', 8, 1, 'TCP Client', '00000000345', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 11, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11025, 764, '32-б81', 8, 1, 'TCP Client', '00000000343', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 12, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11026, 764, '33-б82', 8, 1, 'TCP Client', '00000000353', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 13, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11027, 765, '29-б84', 8, 1, 'TCP Client', '00000000352', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 1, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11028, 765, '32-б83', 8, 1, 'TCP Client', '00000000349', null, '1', null, 'instance145', 'alarminstance67', 'displayinstance131', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 2, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11041, 662, '�ⲿ����3', 8, 1, 'TCP Client', '00000001002', null, '01', null, 'instance151', 'alarminstance67', 'displayinstance149', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11042, 84, '��77-9x', 10, 1, 'TCP Client', '00000015001', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11061, 77, '5#��63-6-12', 10, 1, 'TCP Client', '00000015002', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 34, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11062, 77, '��63-33����', 10, 1, 'TCP Client', '00000015003', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 35, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11063, 77, '4#��63-13', 10, 1, 'TCP Client', '00000015004', null, '01', null, 'instance131', 'alarminstance65', 'displayinstance133', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 33, 0);

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
values (11102, 448, 'ZBS201(��)', 9, 1, 'TCP Client', '00000013025', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('08-10-2024 10:47:36', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11103, 403, 'A8S4', 9, 1, 'TCP Client', '00000013026', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11104, 252, 'B283S2', 9, 1, 'TCP Client', '00000013027', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11105, 264, 'G143S9', 9, 1, 'TCP Client', '00000013028', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11106, 182, 'KCS2309107', 9, 1, 'TCP Client', '00000013029', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:05:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11107, 1382, '��82S15', 9, 1, 'TCP Client', '00000013030', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11108, 1282, 'A8S1�����ϣ�', 9, 1, 'TCP Client', '00000013031', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11121, 268, '281S13', 9, 1, 'TCP Client', '00000013032', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11122, 302, 'KCS2309096', 9, 1, 'TCP Client', '00000013033', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 17:04:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11123, 448, 'ZBS201', 9, 1, 'TCP Client', '00000013034', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('08-10-2024 10:48:15', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11124, 902, 'L13ZS1', 9, 1, 'TCP Client', '00000013035', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11125, 268, 'L183S10�����ϣ�', 9, 1, 'TCP Client', '00000013036', null, '01', null, 'instance148', 'alarminstance103', 'displayinstance145', null, null, null, null, null, null, '{}', to_date('23-09-2024 10:57:59', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (11262, 625, 'С17-6', 10, 1, 'TCP Client', '00000015006', null, '01', null, 'instance153', 'alarminstance125', 'displayinstance118', null, null, null, null, null, null, '{}', to_date('24-09-2024 13:26:23', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
values (11361, 326, '��236-53', 8, 1, 'TCP Client', '00000018022', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

insert into tbl_device (ID, ORGID, DEVICENAME, DEVICETYPE, APPLICATIONSCENARIOS, TCPTYPE, SIGNINID, IPPORT, SLAVE, PEAKDELAY, INSTANCECODE, ALARMINSTANCECODE, DISPLAYINSTANCECODE, REPORTINSTANCECODE, VIDEOURL1, VIDEOURL2, VIDEOKEYID1, VIDEOKEYID2, VIDEOACCESSTOKEN, PRODUCTIONDATA, PRODUCTIONDATAUPDATETIME, PUMPINGMODELID, STROKE, BALANCEINFO, STATUS, SORTNUM, CALCULATETYPE)
values (11362, 325, 'ܫ48-88', 8, 1, 'TCP Client', '00000018026', null, '01', null, 'instance141', 'alarminstance67', 'displayinstance137', null, null, null, null, null, null, '{}', to_date('16-07-2024 18:21:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, 1, 9999, 0);

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
/* ��ʼ�������豸����                                                 */
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
/* ��ʼ���豸������Ϣ����                                                 */
/*==============================================================*/
ALTER TRIGGER trg_b_DEVICEADDINFO_i DISABLE;

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1034, 1, '����', '11', '��λ');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1035, 1, '����', '11', '��λ');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1036, 1, '����', '11', '��λ');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1037, 1, '����', '11', '��λ');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (400, 2, '�ñ��', 'BGM2312050', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (401, 2, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (402, 2, '������', 'M230080����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (403, 2, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (404, 2, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (405, 2, '��������', '����TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (406, 2, '�����ֳ���', '29.38', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (407, 2, '���������', 'YZ23004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (408, 2, '��Ƶ���ƹ���', 'KBP2206010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (409, 2, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (410, 2, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (411, 2, '�ù����', '830.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (136, 3, '�ñ��', 'BGM2306020', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (137, 3, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (138, 3, '������', 'M230061����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (139, 3, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (140, 3, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (141, 3, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (142, 3, '�����ֳ���', '20', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (143, 3, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (144, 3, '��Ƶ���ƹ���', 'KBP2011017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (145, 3, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (146, 3, 'йѹ�ӹ�й©��', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (147, 3, '�ù�λ��', '792', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (160, 4, '�ñ��', 'BGM2306021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (161, 4, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (162, 4, '������', 'M230055����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (163, 4, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (164, 4, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (165, 4, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (166, 4, '�����ֳ���', '47.3', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (167, 4, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (168, 4, '��Ƶ���ƹ���', 'KBP2105018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (169, 4, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (170, 4, 'йѹ�ӹ�й©��', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (171, 4, '�ù�λ��', '824', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (495, 22, '�ñ��', 'BGM2107033', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (496, 22, '�ù���ͺ�', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (497, 22, '������', 'M210192', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (498, 22, '��������', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (499, 22, '��Ĥ����', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (500, 22, '��������', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (501, 22, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (502, 22, '���������', 'CZB2004002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (503, 22, '��Ƶ���ƹ���', 'KBP2010008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (504, 22, '�ز����ƹ���', 'KZB2010008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (505, 22, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4310, 23, '�ñ��', 'BGM2304017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4311, 23, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4312, 23, '������', 'M210183(XFM220816)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4313, 23, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4314, 23, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4315, 23, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4316, 23, '�����ֳ���', '29.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4317, 23, '���������', 'CZB2011007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4318, 23, '��Ƶ���ƹ���', 'KBP2011011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4319, 23, '�ز����ƹ���', 'KZB2011015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4320, 23, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4321, 23, '�ù����', '658.93', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (196, 24, '�ñ��', 'BGM2212509', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (197, 24, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (198, 24, '������', 'M210180(XFM220822)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (199, 24, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (200, 24, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (201, 24, '��������', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (202, 24, '�����ֳ���', '71.15', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (203, 24, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (204, 24, '��Ƶ���ƹ���', 'KBP2010006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (205, 24, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (206, 24, 'йѹ�ӹ�й©��', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (207, 24, '�ù�λ��', '747.47', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (232, 26, '�ñ��', 'BGM2212508', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (233, 26, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (234, 26, '������', 'M200284(XFM220815)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (235, 26, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (236, 26, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (237, 26, '��������', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (238, 26, '�����ֳ���', '20', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (239, 26, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (240, 26, '��Ƶ���ƹ���', 'KBP2104001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (241, 26, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (242, 26, 'йѹ�ӹ�й©��', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (243, 26, '�ù�λ��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (268, 28, '�ñ��', 'BGM2212507', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (269, 28, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (270, 28, '������', 'M190190(XFM220819)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (271, 28, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (272, 28, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (273, 28, '��������', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (274, 28, '�����ֳ���', '19.92', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (275, 28, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (276, 28, '��Ƶ���ƹ���', 'KBP2010010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (277, 28, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (278, 28, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (279, 28, '�ù�λ��', '827', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (280, 29, '�ñ��', 'BGM1912001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (281, 29, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (282, 29, '������', 'D190902-4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (283, 29, '��������', 'NX01-HSN-20191101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (284, 29, '��Ĥ����', 'MY01-DJ-20190301', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (285, 29, '��������', 'TK01-YHG-20191101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (286, 29, '�����ֳ���', '25', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (287, 29, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (288, 29, '��Ƶ���ƹ���', 'KBP2011016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (289, 29, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (290, 29, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (291, 29, '�ù�λ��', '865', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (292, 30, '�ñ��', 'BGM2207029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (293, 30, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (294, 30, '������', 'M220299', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (295, 30, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (296, 30, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (297, 30, '��������', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (298, 30, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (299, 30, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (300, 30, '��Ƶ���ƹ���', 'KBP2011012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (301, 30, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (302, 30, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (303, 30, '�ù����', '866.98', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (304, 31, '�ñ��', 'BGM2207034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (305, 31, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (306, 31, '������', 'M220311', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (307, 31, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (308, 31, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (309, 31, '��������', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (310, 31, '�����ֳ���', '19.3', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (311, 31, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (312, 31, '��Ƶ���ƹ���', 'KBP2104004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (313, 31, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (314, 31, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (315, 31, '�ù����', '916.07', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1062, 32, '�ñ��', 'BGM2110056', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1063, 32, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1064, 32, '������', 'M210413', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1065, 32, '��������', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1066, 32, '��Ĥ����', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1067, 32, '��������', 'TK02-YGM-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1068, 32, '�����ֳ���', '10', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1069, 32, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1070, 32, '��Ƶ���ƹ���', 'KBP2011015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1071, 32, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1072, 32, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1073, 32, '�ù����', '1115', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (340, 33, '�ñ��', 'BGM2306022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (341, 33, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (342, 33, '������', 'M230058����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (343, 33, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (344, 33, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (345, 33, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (346, 33, '�����ֳ���', '20.44', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (347, 33, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (348, 33, '��Ƶ���ƹ���', 'KBP2104002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (349, 33, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (350, 33, 'йѹ�ӹ�й©��', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (351, 33, '�ù�λ��', '1194.66', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (376, 35, '�ñ��', 'BGM2106019', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (377, 35, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (378, 35, '������', 'M210177', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (379, 35, '��������', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (380, 35, '��Ĥ����', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (381, 35, '��������', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (382, 35, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (383, 35, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (384, 35, '��Ƶ���ƹ���', 'KBP2011019', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (385, 35, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (386, 35, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (387, 35, '�ù�λ��', '1063', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (412, 36, '�ñ��', 'BGM2110058', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (413, 36, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (414, 36, '������', 'M210407', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (415, 36, '��������', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (416, 36, '��Ĥ����', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (417, 36, '��������', 'TK02-YGM-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (418, 36, '�����ֳ���', '19', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (419, 36, '���������', 'CZB2110019', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (420, 36, '��Ƶ���ƹ���', 'KBP2105024', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (421, 36, '�ز����ƹ���', 'KZB2011012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (422, 36, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (423, 36, '�ù�λ��', '1280', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (484, 37, '�ñ��', 'BGM2107029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (485, 37, '�ù���ͺ�', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (486, 37, '������', 'M210181', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (487, 37, '��������', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (488, 37, '��Ĥ����', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (489, 37, '��������', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (490, 37, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (491, 37, '���������', 'CZB2104009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (492, 37, '��Ƶ���ƹ���', 'KBP2105010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (493, 37, '�ز����ƹ���', 'KZB2104001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (494, 37, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (956, 39, '�ñ��', 'BGM2105013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (957, 39, '�ù���ͺ�', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (958, 39, '������', 'M210069', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (959, 39, '��������', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (960, 39, '��Ĥ����', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (961, 39, '��������', 'TK02-YGM-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (962, 39, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (963, 39, '���������', 'CZB2104005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (964, 39, '��Ƶ���ƹ���', 'KBP2105008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (965, 39, '�ز����ƹ���', 'KZB2011018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (966, 39, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (967, 40, '�ñ��', 'BGM2106021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (968, 40, '�ù���ͺ�', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (969, 40, '������', 'M210179', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (970, 40, '��������', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (971, 40, '��Ĥ����', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (972, 40, '��������', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (973, 40, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (974, 40, '���������', 'CZB2011006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (975, 40, '��Ƶ���ƹ���', 'KBP2105009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (976, 40, '�ز����ƹ���', 'KZB2010010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (977, 40, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (730, 41, '�ñ��', 'BGM2210048', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (731, 41, '�ù���ͺ�', 'JM5-7-1700FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (732, 41, '������', 'M210398(XFM220645)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (733, 41, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (734, 41, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (735, 41, '��������', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (736, 41, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (737, 41, '���������', 'CZB2209032', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (738, 41, '��Ƶ���ƹ���', 'KBP2011013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (739, 41, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (740, 41, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (741, 42, '�ñ��', 'BGM2012029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (742, 42, '�ù���ͺ�', 'JM5-7-1500F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (743, 42, '������', 'M200280', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (744, 42, '��������', 'NX01-HSN-20200401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (745, 42, '��Ĥ����', 'RS01-XXX-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (746, 42, '��������', 'TK02-YGM-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (747, 42, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (748, 42, '���������', 'CZB2011008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (749, 42, '��Ƶ���ƹ���', 'KBP2010009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (750, 42, '�ز����ƹ���', 'KZB2104002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (751, 42, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1, 43, '�ñ��', 'BGM2011018��20023��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2, 43, '�ù���ͺ�', 'JM5-7-1500F��QYDB114-5/1700A-GM)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3, 43, '������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4, 43, '��������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (5, 43, '��Ĥ����', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (6, 43, '��������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (7, 43, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (8, 43, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (9, 43, '��Ƶ���ƹ���', 'KBP2010001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (10, 43, '�ز����ƹ���', 'KZB2010001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (11, 43, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (67, 44, '�ñ��', 'BGM2110060', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (68, 44, '�ù���ͺ�', 'JM5-5-1700F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (69, 44, '������', 'M200285(XFM220116)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (70, 44, '��������', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (71, 44, '��Ĥ����', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (72, 44, '��������', 'TK02-YGM-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (73, 44, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (74, 44, '���������', 'CZB2109017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (75, 44, '��Ƶ���ƹ���', 'KBP2105027', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (76, 44, '�ز����ƹ���', 'KZB2106007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (77, 44, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (708, 45, '�ñ��', 'BGM2209042', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (709, 45, '�ù���ͺ�', 'JM5-7-1500FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (710, 45, '������', 'M210416', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (711, 45, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (712, 45, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (713, 45, '��������', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (714, 45, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (715, 45, '���������', 'CZB2011003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (716, 45, '��Ƶ���ƹ���', 'KBP2105026', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (717, 45, '�ز����ƹ���', 'KZB2106010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (718, 45, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (56, 46, '�ñ��', 'BGM2203013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (57, 46, '�ù���ͺ�', 'JM5-7-1500F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (58, 46, '������', 'M210419', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (59, 46, '��������', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (60, 46, '��Ĥ����', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (61, 46, '��������', 'TK03-YHG-20220301', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (62, 46, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (63, 46, '���������', 'CZB2011010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (64, 46, '��Ƶ���ƹ���', 'KBP2107028', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (65, 46, '�ز����ƹ���', 'KZB2106014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (66, 46, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (719, 47, '�ñ��', 'BGM2108039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (720, 47, '�ù���ͺ�', 'EDN5-4-1600', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (721, 47, '������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (722, 47, '��������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (723, 47, '��Ĥ����', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (724, 47, '��������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (725, 47, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (726, 47, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (727, 47, '��Ƶ���ƹ���', 'KBP2105023', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (728, 47, '�ز����ƹ���', 'KZB2106008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (729, 47, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (78, 48, '�ñ��', 'BGM2108044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (79, 48, '�ù���ͺ�', 'JM5-7-1500F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (80, 48, '������', 'D181209-4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (81, 48, '��������', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (82, 48, '��Ĥ����', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (83, 48, '��������', 'TK02-YGM-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (84, 48, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (85, 48, '���������', '438253', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (86, 48, '��Ƶ���ƹ���', 'KBP2105026', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (87, 48, '�ز����ƹ���', 'KZB2106010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (88, 48, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1050, 62, '�ñ��', 'BGM2203011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1051, 62, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1052, 62, '������', 'M210399', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1053, 62, '��������', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1054, 62, '��Ĥ����', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1055, 62, '��������', 'TK03-YHG-20220301', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1056, 62, '�����ֳ���', '24', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1057, 62, '���������', 'CZB2203004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1058, 62, '��Ƶ���ƹ���', 'KBP2107035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1059, 62, '�ز����ƹ���', 'KZB2011016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1060, 62, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1061, 62, '�ù�λ��', '988.37', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (208, 82, '�ñ��', 'BGM2401506', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (209, 82, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (210, 82, '������', 'M210404(XFM220807)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (211, 82, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (212, 82, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (213, 82, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (214, 82, '�����ֳ���', '48.75', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (215, 82, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (216, 82, '��Ƶ���ƹ���', '201907007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (217, 82, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (218, 82, 'йѹ�ӹ�й©��', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (219, 82, '�ù�λ��', '670', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (220, 101, '�ñ��', 'BGM2306508', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (221, 101, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (222, 101, '������', 'M210405(XFM220808)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (223, 101, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (224, 101, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (225, 101, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (226, 101, '�����ֳ���', '61.03', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (227, 101, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (228, 101, '��Ƶ���ƹ���', '201907006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (229, 101, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (230, 101, 'йѹ�ӹ�й©��', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (231, 101, '�ù�λ��', '723.73', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (256, 121, '�ñ��', 'BGM2104010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (257, 121, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (258, 121, '������', 'M210071', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (259, 121, '��������', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (260, 121, '��Ĥ����', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (261, 121, '��������', 'TK02-YGM-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (262, 121, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (263, 121, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (264, 121, '��Ƶ���ƹ���', '201907002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (265, 121, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (266, 121, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (267, 121, '�ù�λ��', '980', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (328, 122, '�ñ��', 'BGM2111068', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (329, 122, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (330, 122, '������', 'M210427', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (331, 122, '��������', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (332, 122, '��Ĥ����', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (333, 122, '��������', 'TK02-YGM-20220101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (334, 122, '�����ֳ���', '19.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (335, 122, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (336, 122, '��Ƶ���ƹ���', '201907004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (337, 122, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (338, 122, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (339, 122, '�ù����', '990', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (244, 124, '�ñ��', 'BGM2205022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (245, 124, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (246, 124, '������', 'M210195(XFM220112)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (247, 124, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (248, 124, '��Ĥ����', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (249, 124, '��������', 'TK02-YGM-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (250, 124, '�����ֳ���', '23.87', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (251, 124, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (252, 124, '��Ƶ���ƹ���', 'KBP2010007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (253, 124, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (254, 124, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (255, 124, '�ù�λ��', '646.97', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (460, 300, '�ñ��', 'BGM2303011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (461, 300, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (462, 300, '������', 'M220568', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (463, 300, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (464, 300, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (465, 300, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (466, 300, '�����ֳ���', '52.67', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (467, 300, '���������', 'CZB2303017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (468, 300, '��Ƶ���ƹ���', 'KBP2208034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (469, 300, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (470, 300, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (471, 300, '�ù����', '807.78', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (785, 303, '�ñ��', 'BGM2211062', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (786, 303, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (787, 303, '������', 'M220561', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (788, 303, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (789, 303, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (790, 303, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (791, 303, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (792, 303, '���������', 'CZB2211048', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (793, 303, '��Ƶ���ƹ���', 'KBP2206008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (794, 303, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (795, 303, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1038, 305, '�ñ��', 'BGM2304013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1039, 305, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1040, 305, '������', 'M220575', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1041, 305, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1042, 305, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1043, 305, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1044, 305, '�����ֳ���', '48.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1045, 305, '���������', 'CZB2203003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1046, 305, '��Ƶ���ƹ���', 'KBP2207022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1047, 305, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1048, 305, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1049, 305, '�ù�λ��', '1251', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (763, 306, '�ñ��', 'BGM2308510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (764, 306, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (765, 306, '������', 'M210415��M230201����PT100�ȵ�������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (766, 306, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (767, 306, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (768, 306, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (769, 306, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (770, 306, '���������', 'CZB2308502', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (771, 306, '��Ƶ���ƹ���', 'KBP2206005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (772, 306, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (773, 306, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (796, 314, '�ñ��', 'BGM2211060', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (797, 314, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (798, 314, '������', 'M220313', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (799, 314, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (800, 314, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (801, 314, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (802, 314, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (803, 314, '���������', 'CZB2211043', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (804, 314, '��Ƶ���ƹ���', 'KBP2206007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (805, 314, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (806, 314, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (851, 316, '�ñ��', 'BGM2211061', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (852, 316, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (853, 316, '������', 'M220319', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (854, 316, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (855, 316, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (856, 316, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (857, 316, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (858, 316, '���������', 'CZB2211046', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (859, 316, '��Ƶ���ƹ���', 'KBP2207023', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (860, 316, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (861, 316, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (840, 318, '�ñ��', 'BGM2211057', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (841, 318, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (842, 318, '������', 'M220320', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (843, 318, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (844, 318, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (845, 318, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (846, 318, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (847, 318, '���������', 'CZB2210040', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (848, 318, '��Ƶ���ƹ���', 'KBP2207021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (849, 318, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (850, 318, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1000, 319, '�ñ��', 'BGM2211063', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1001, 319, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1002, 319, '������', 'M220558', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1003, 319, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1004, 319, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1005, 319, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1006, 319, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1007, 319, '���������', 'CZB2211047', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1008, 319, '��Ƶ���ƹ���', 'KBP2207027', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1009, 319, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1010, 319, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (774, 320, '�ñ��', 'BGM2210050', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (775, 320, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (776, 320, '������', 'M220297', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (777, 320, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (778, 320, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (779, 320, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (780, 320, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (781, 320, '���������', 'CZB2209036', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (782, 320, '��Ƶ���ƹ���', 'KBP2206007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (783, 320, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (784, 320, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (989, 322, '�ñ��', 'BGM2210051', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (990, 322, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (991, 322, '������', 'M220312', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (992, 322, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (993, 322, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (994, 322, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (995, 322, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (996, 322, '���������', 'CZB2209035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (997, 322, '��Ƶ���ƹ���', 'KBP2206003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (998, 322, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (999, 322, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (829, 325, '�ñ��', 'BGM2210053', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (830, 325, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (831, 325, '������', 'M220305', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (832, 325, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (833, 325, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (834, 325, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (835, 325, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (836, 325, '���������', 'CZB2210039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (837, 325, '��Ƶ���ƹ���', 'KBP2206009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (838, 325, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (839, 325, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (807, 327, '�ñ��', 'BGM2210054', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (808, 327, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (809, 327, '������', 'M220315', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (810, 327, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (811, 327, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (812, 327, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (813, 327, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (814, 327, '���������', 'CZB2210041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (815, 327, '��Ƶ���ƹ���', 'KBP2207024', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (816, 327, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (817, 327, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (897, 328, '�ñ��', 'BGM2211059', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (898, 328, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (899, 328, '������', 'M220309', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (900, 328, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (901, 328, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (902, 328, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (903, 328, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (904, 328, '���������', 'CZB2211044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (905, 328, '��Ƶ���ƹ���', 'KBP2207028', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (906, 328, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (907, 328, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (862, 329, '�ñ��', 'BGM2308509', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (863, 329, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (864, 329, '������', 'M210186(M230196)����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (865, 329, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (866, 329, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (867, 329, '��������', '����TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (868, 329, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (869, 329, '���������', 'CZB2211045', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (870, 329, '��Ƶ���ƹ���', 'KBP2206004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (871, 329, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (872, 329, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1011, 330, '�ñ��', 'BGM2211056', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1012, 330, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1013, 330, '������', 'M220300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1014, 330, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1015, 330, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1016, 330, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1017, 330, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1018, 330, '���������', 'CZB2210038', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1019, 330, '��Ƶ���ƹ���', 'KBP2206003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1020, 330, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1021, 330, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (172, 341, '�ñ��', 'BGM2212505', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (173, 341, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (174, 341, '������', 'M210073(XFM220640)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (175, 341, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (176, 341, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (177, 341, '��������', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (178, 341, '�����ֳ���', '51.93', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (179, 341, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (180, 341, '��Ƶ���ƹ���', '201907006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (181, 341, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (182, 341, 'йѹ�ӹ�й©��', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (183, 341, '�ù�λ��', '857.55', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (184, 342, '�ñ��', 'BGM2006013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (185, 342, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (186, 342, '������', 'M190198', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (187, 342, '��������', 'NX01-HSN-20191101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (188, 342, '��Ĥ����', 'JZ01-DJ-20190901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (189, 342, '��������', 'TK02-YGM-20200401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (190, 342, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (191, 342, '���������', 'CZB2006001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (192, 342, '��Ƶ���ƹ���', 'KBP2006002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (193, 342, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (194, 342, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (195, 342, '�ù�λ��', '798', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (89, 343, '�ñ��', 'BGM2401503', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (90, 343, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (91, 343, '������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (92, 343, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (93, 343, '��Ĥ����', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (94, 343, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (95, 343, '�����ֳ���', '38.6m��4���͹ܣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (96, 343, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (97, 343, '��Ƶ���ƹ���', 'KBP2105016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (98, 343, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (99, 343, 'йѹ�ӹ�й©��', '0.6mm', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (590, 344, '�ñ��', 'BGM2212504', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (591, 344, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (592, 344, '������', 'M200281(XFM220820)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (593, 344, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (594, 344, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (595, 344, '��������', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (596, 344, '�����ֳ���', '34.4', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (597, 344, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (598, 344, '��Ƶ���ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (599, 344, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (600, 344, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (601, 344, '�ù����', '795', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (697, 345, '�ñ��', 'BGM2012026��20027��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (698, 345, '�ù���ͺ�', 'JM5-10-1200F��QYDB114-10/1200A-GM)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (699, 345, '������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (700, 345, '��������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (701, 345, '��Ĥ����', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (702, 345, '��������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (703, 345, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (704, 345, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (705, 345, '��Ƶ���ƹ���', 'KBP2010004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (706, 345, '�ز����ƹ���', 'KZB2010004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (707, 345, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (752, 346, '�ñ��', 'BGM2011019��20024��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (753, 346, '�ù���ͺ�', 'JM5-7-1500F��QYDB114-5/1700A-GM)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (754, 346, '������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (755, 346, '��������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (756, 346, '��Ĥ����', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (757, 346, '��������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (758, 346, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (759, 346, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (760, 346, '��Ƶ���ƹ���', 'KBP2010002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (761, 346, '�ز����ƹ���', 'KZB2010002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (762, 346, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (12, 347, '�ñ��', 'BGM2011022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (13, 347, '�ù���ͺ�', 'JM5-7-1500F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (14, 347, '������', 'M190195', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (15, 347, '��������', 'NX01-HSN-20200401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (16, 347, '��Ĥ����', 'RS01-XXX-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (17, 347, '��������', 'TK02-YGM-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (18, 347, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (19, 347, '���������', 'CZB2011004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (20, 347, '��Ƶ���ƹ���', 'KBP2006003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (21, 347, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (22, 347, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (686, 348, '�ñ��', 'BGM2009015(20020)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (687, 348, '�ù���ͺ�', 'END5-4-1800-P1-TM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (688, 348, '������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (689, 348, '��������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (690, 348, '��Ĥ����', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (691, 348, '��������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (692, 348, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (693, 348, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (694, 348, '��Ƶ���ƹ���', 'KBP2006004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (695, 348, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (696, 348, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (23, 349, '�ñ��', 'BGM2110061', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (24, 349, '�ù���ͺ�', 'JM5-3.5-2000F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (25, 349, '������', 'M210075(XFM220122)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (26, 349, '��������', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (27, 349, '��Ĥ����', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (28, 349, '��������', 'TK02-YGM-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (29, 349, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (30, 349, '���������', 'CZB2104010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (31, 349, '��Ƶ���ƹ���', 'KBP2006001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (32, 349, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (33, 349, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (34, 350, '�ñ��', '201907002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (35, 350, '�ù���ͺ�', 'JM5-5-1700F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (36, 350, '������', 'D190905-4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (37, 350, '��������', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (38, 350, '��Ĥ����', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (39, 350, '��������', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (40, 350, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (41, 350, '���������', '438409', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (42, 350, '��Ƶ���ƹ���', '201907005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (43, 350, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (44, 350, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (45, 351, '�ñ��', 'EP1909002(BGM2105015)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (46, 351, '�ù���ͺ�', 'JM5-3.5-2000F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (47, 351, '������', 'D181202-4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (48, 351, '��������', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (49, 351, '��Ĥ����', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (50, 351, '��������', 'TK02-YGM-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (51, 351, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (52, 351, '���������', 'CZB2104007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (53, 351, '��Ƶ���ƹ���', 'KBP2006005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (54, 351, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (55, 351, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (978, 352, '�ñ��', 'BGM2106017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (979, 352, '�ù���ͺ�', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (980, 352, '������', 'M210176', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (981, 352, '��������', 'NX01-HSN-20201101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (982, 352, '��Ĥ����', 'NX01-HSN-20210401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (983, 352, '��������', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (984, 352, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (985, 352, '���������', 'CZB2008001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (986, 352, '��Ƶ���ƹ���', 'KBP2105011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (987, 352, '�ز����ƹ���', 'KZB2104003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (988, 352, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (316, 361, '�ñ��', 'BGM2302003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (317, 361, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (318, 361, '������', 'M220567', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (319, 361, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (320, 361, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (321, 361, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (322, 361, '�����ֳ���', '34.1', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (323, 361, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (324, 361, '��Ƶ���ƹ���', 'KBP2105017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (325, 361, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (326, 361, 'йѹ�ӹ�й©��', '0.6', 'mm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (327, 361, '�ù�λ��', '1140', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (352, 381, '�ñ��', 'BGM2207035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (353, 381, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (354, 381, '������', 'M220308', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (355, 381, '��������', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (356, 381, '��Ĥ����', 'NX01-HSN-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (357, 381, '��������', 'TK02-YGM-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (358, 381, '�����ֳ���', '33.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (359, 381, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (360, 381, '��Ƶ���ƹ���', 'KBP2105022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (361, 381, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (362, 381, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (363, 381, '�ù�λ��', '983', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (364, 382, '�ñ��', 'BGM2401504', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (365, 382, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (366, 382, '������', 'M210412', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (367, 382, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (368, 382, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (369, 382, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (370, 382, '�����ֳ���', '24.1', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (371, 382, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (372, 382, '��Ƶ���ƹ���', 'KBP2107034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (373, 382, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (374, 382, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (375, 382, '�ù�λ��', '890.56', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (388, 383, '�ñ��', 'BGM2304018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (389, 383, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (390, 383, '������', 'M210423(XFM220812)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (391, 383, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (392, 383, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (393, 383, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (394, 383, '�����ֳ���', '52.76', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (395, 383, '���������', 'CZB2303022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (396, 383, '��Ƶ���ƹ���', 'KBP2105013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (397, 383, '�ز����ƹ���', 'KZB2104006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (398, 383, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (399, 383, '�ù����', '968.3', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (554, 401, '�ñ��', 'BGM2308033', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (555, 401, '�ù���ͺ�', 'JM-15-850D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (556, 401, '������', 'M230069����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (557, 401, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (558, 401, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (559, 401, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (560, 401, '�����ֳ���', '29', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (561, 401, '���������', 'CZB2308033', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (562, 401, '��Ƶ���ƹ���', 'KBP2306010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (563, 401, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (564, 401, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (565, 401, '�ù����', '461.07', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (566, 402, '�ñ��', 'BGM2301001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (567, 402, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (568, 402, '������', 'M220576', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (569, 402, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (570, 402, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (571, 402, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (572, 402, '�����ֳ���', '44.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (573, 402, '���������', 'CZB2208023', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (574, 402, '��Ƶ���ƹ���', 'KBP2107032', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (575, 402, '�ز����ƹ���', 'KZB2011013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (576, 402, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (577, 402, '�ù����', '943', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (472, 421, '�ñ��', 'BGM2303009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (473, 421, '�ù���ͺ�', 'JM5-10-1200', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (474, 421, '������', 'M220574', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (475, 421, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (476, 421, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (477, 421, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (478, 421, '�����ֳ���', '49.05', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (479, 421, '���������', 'CZB2303018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (480, 421, '��Ƶ���ƹ���', 'KBP2107006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (481, 421, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (482, 421, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (483, 421, '�ù�λ��', '1157', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (424, 461, '�ñ��', 'BGM2304012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (425, 461, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (426, 461, '������', 'M220571', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (427, 461, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (428, 461, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (429, 461, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (430, 461, '�����ֳ���', '47.67', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (431, 461, '���������', 'CZB2303020', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (432, 461, '��Ƶ���ƹ���', 'KBP2208037', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (433, 461, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (434, 461, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (435, 461, '�ù����', '1014', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (436, 501, '�ñ��', 'BGM2303008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (437, 501, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (438, 501, '������', 'M220562', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (439, 501, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (440, 501, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (441, 501, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (442, 501, '�����ֳ���', '24.24', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (443, 501, '���������', 'CZB2303021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (444, 501, '��Ƶ���ƹ���', 'KBP2208035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (445, 501, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (446, 501, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (447, 501, '�ù����', '968.3', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (448, 502, '�ñ��', 'BGM2306026', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (449, 502, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (450, 502, '������', 'M230074����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (451, 502, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (452, 502, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (453, 502, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (454, 502, '�����ֳ���', '19.92', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (455, 502, '���������', 'YZ23002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (456, 502, '��Ƶ���ƹ���', 'KBP2306002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (457, 502, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (458, 502, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (459, 502, '�ù����', '968.3', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (148, 521, '�ñ��', 'BGM2302007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (149, 521, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (150, 521, '������', 'M220563', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (151, 521, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (152, 521, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (153, 521, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (154, 521, '�����ֳ���', '43.9', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (155, 521, '���������', 'CZB2208022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (156, 521, '��Ƶ���ƹ���', 'KBP2208032', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (157, 521, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (158, 521, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (159, 521, '�ù����', '856', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3941, 522, '�ñ��', 'BGM2107026', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3942, 522, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3943, 522, '������', 'M220560', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3944, 522, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3945, 522, '��Ĥ����', 'NX01-HSN-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3946, 522, '��������', 'TK02-YGM-20210601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3947, 522, '�����ֳ���', '48.75', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3948, 522, '���������', 'CZB2104008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3949, 522, '��Ƶ���ƹ���', 'KBP2306004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3950, 522, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3951, 522, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3952, 522, '�ù�λ��', '559.97', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (100, 523, '�ñ��', 'BGM2212504', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (101, 523, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (102, 523, '������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (103, 523, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (104, 523, '��Ĥ����', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (105, 523, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (106, 523, '�����ֳ���', '34.4', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (107, 523, '���������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (108, 523, '��Ƶ���ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (109, 523, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (110, 523, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (111, 523, '�ù�λ��', '795', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (112, 524, '�ñ��', 'BGM2306506', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (113, 524, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (114, 524, '������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (115, 524, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (116, 524, '��Ĥ����', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (117, 524, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (118, 524, '�����ֳ���', '53.255', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (119, 524, '���������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (120, 524, '��Ƶ���ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (121, 524, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (122, 524, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (123, 524, '�ù�λ��', '578.55', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (578, 525, '�ñ��', 'BGM2306506', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (579, 525, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (580, 525, '������', 'M210074(XFM220818)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (581, 525, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (582, 525, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (583, 525, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (584, 525, '�����ֳ���', '53.255', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (585, 525, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (586, 525, '��Ƶ���ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (587, 525, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (588, 525, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (589, 525, '�ù����', '578.55', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (542, 526, '�ñ��', 'BGM2304014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (543, 526, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (544, 526, '������', 'M220559', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (545, 526, '��������', '�������NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (546, 526, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (547, 526, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (548, 526, '�����ֳ���', '23.46', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (549, 526, '���������', 'CZB2202002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (550, 526, '��Ƶ���ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (551, 526, '�ز����ƹ���', 'KZB2106011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (552, 526, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (553, 526, '�ù����', '943', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (506, 527, '�ñ��', 'BGM2302006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (507, 527, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (508, 527, '������', 'M220573', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (509, 527, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (510, 527, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (511, 527, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (512, 527, '�����ֳ���', '53.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (513, 527, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (514, 527, '��Ƶ���ƹ���', 'KBP2105025', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (515, 527, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (516, 527, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (517, 527, '�ù����', '1050', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (518, 528, '�ñ��', 'BGM2306023', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (519, 528, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (520, 528, '������', 'M230075����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (521, 528, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (522, 528, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (523, 528, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (524, 528, '�����ֳ���', '30.6', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (525, 528, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (526, 528, '��Ƶ���ƹ���', 'KBP2105012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (527, 528, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (528, 528, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (529, 528, '�ù����', '737.33', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (530, 529, '�ñ��', 'BGM2306025', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (531, 529, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (532, 529, '������', 'M230066����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (533, 529, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (534, 529, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (535, 529, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (536, 529, '�����ֳ���', '48.5', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (537, 529, '���������', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (538, 529, '��Ƶ���ƹ���', 'KBP2105020', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (539, 529, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (540, 529, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (541, 529, '�ù����', '560', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (614, 541, '�ñ��', 'BGM2301503', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (615, 541, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (616, 541, '������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (617, 541, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (618, 541, '��Ĥ����', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (619, 541, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (620, 541, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (621, 541, '���������', '438253', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (622, 541, '��Ƶ���ƹ���', 'KBP2306011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (623, 541, '�ز����ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (624, 541, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (625, 541, '�ù����', '710', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (626, 542, '�ñ��', 'BGM2309039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (627, 542, '�ù���ͺ�', 'JM5-15-850FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (628, 542, '������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (629, 542, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (630, 542, '��Ĥ����', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (631, 542, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (632, 542, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (633, 542, '���������', 'CZB2308032', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (634, 542, '��Ƶ���ƹ���', 'KBP2306008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (635, 542, '�ز����ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (636, 542, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (637, 542, '�ù����', '692', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (908, 543, '�ñ��', 'BGM2301504', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (909, 543, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (910, 543, '������', 'M210430(XFM220813)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (911, 543, '��������', 'NX01-HSN-20220601', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (912, 543, '��Ĥ����', 'NX01-HSN-20220701', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (913, 543, '��������', 'TK02-YGM-20220801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (914, 543, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (915, 543, '���������', 'CZB2104010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (916, 543, '��Ƶ���ƹ���', 'KBP2306005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (917, 543, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (918, 543, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (919, 543, '�ù����', '704', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1074, 544, '�ñ��', 'BGM2311047', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1075, 544, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1076, 544, '������', 'M230056����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1077, 544, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1078, 544, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1079, 544, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1080, 544, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1081, 544, '���������', 'CZB2311040', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1082, 544, '��Ƶ���ƹ���', 'KBP2208036', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1083, 544, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1084, 544, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1085, 544, '�ù����', '582', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (920, 545, '�ñ��', 'BGM2311044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (921, 545, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (922, 545, '������', 'M230071����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (923, 545, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (924, 545, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (925, 545, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (926, 545, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (927, 545, '���������', 'CZB2311039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (928, 545, '��Ƶ���ƹ���', 'KBP2306006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (929, 545, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (930, 545, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (931, 545, '�ù����', '723.6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (638, 546, '�ñ��', 'BGM2311049', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (639, 546, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (640, 546, '������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (641, 546, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (642, 546, '��Ĥ����', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (643, 546, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (644, 546, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (645, 546, '���������', 'CZB2311044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (646, 546, '��Ƶ���ƹ���', 'KBP2306009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (647, 546, '�ز����ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (648, 546, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (649, 546, '�ù����', '782', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (650, 547, '�ñ��', 'BGM2311048', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (651, 547, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (652, 547, '������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (653, 547, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (654, 547, '��Ĥ����', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (655, 547, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (656, 547, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (657, 547, '���������', 'CZB2311043', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (658, 547, '��Ƶ���ƹ���', 'KBP2306007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (659, 547, '�ز����ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (660, 547, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (661, 547, '�ù����', '615', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (944, 548, '�ñ��', 'BGM2205015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (945, 548, '�ù���ͺ�', 'JM5-10-1200F', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (946, 548, '������', 'M210420', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (947, 548, '��������', 'NX01-HSN-20210801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (948, 548, '��Ĥ����', 'NX01-HSN-20210901', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (949, 548, '��������', 'TK02-YGM-20220501', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (950, 548, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (951, 548, '���������', '438401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (952, 548, '��Ƶ���ƹ���', 'KBP2206003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (953, 548, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (954, 548, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (955, 548, '�ù����', '810', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (662, 549, '�ñ��', 'BGM2311045', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (663, 549, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (664, 549, '������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (665, 549, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (666, 549, '��Ĥ����', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (667, 549, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (668, 549, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (669, 549, '���������', 'CZB2311041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (670, 549, '��Ƶ���ƹ���', 'KBP2307029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (671, 549, '�ز����ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (672, 549, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (673, 549, '�ù����', '670', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (674, 550, '�ñ��', 'BGM2311042', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (675, 550, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (676, 550, '������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (677, 550, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (678, 550, '��Ĥ����', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (679, 550, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (680, 550, '�����ֳ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (681, 550, '���������', 'CZB2309503', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (682, 550, '��Ƶ���ƹ���', 'KBP2207030', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (683, 550, '�ز����ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (684, 550, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (685, 550, '�ù����', '615', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (932, 551, '�ñ��', 'BGM2311046', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (933, 551, '�ù���ͺ�', 'JM5-10-1200FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (934, 551, '������', 'M230051����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (935, 551, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (936, 551, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (937, 551, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (938, 551, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (939, 551, '���������', 'CZB2311042', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (940, 551, '��Ƶ���ƹ���', 'KBP2208033', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (941, 551, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (942, 551, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (943, 551, '�ù����', '670', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (885, 552, '�ñ��', 'BGM2309034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (886, 552, '�ù���ͺ�', 'JM5-20-700FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (887, 552, '������', 'M230067����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (888, 552, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (889, 552, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (890, 552, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (891, 552, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (892, 552, '���������', 'CZB2308031', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (893, 552, '��Ƶ���ƹ���', 'KBP2208038', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (894, 552, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (895, 552, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (896, 552, '�ù����', '700', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (873, 553, '�ñ��', 'BGM2308032', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (874, 553, '�ù���ͺ�', 'JM5-20-700FG', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (875, 553, '������', 'M230060����PT100�ȵ������ߣ�', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (876, 553, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (877, 553, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (878, 553, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (879, 553, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (880, 553, '���������', 'CZB2308029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (881, 553, '��Ƶ���ƹ���', 'KBP2208031', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (882, 553, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (883, 553, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (884, 553, '�ù����', '730', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (818, 554, '�ñ��', 'BGM2311043', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (819, 554, '�ù���ͺ�', 'JM5-10-1200FG-S', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (820, 554, '������', 'SD202012001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (821, 554, '��������', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (822, 554, '��Ĥ����', 'NX01-HSN-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (823, 554, '��������', 'TK02-YGM-20230401', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (824, 554, '�����ֳ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (825, 554, '���������', 'CZB2310037', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (826, 554, '��Ƶ���ƹ���', 'KBP2211041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (827, 554, '�ز����ƹ���', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (828, 554, 'йѹ�ӹ�й©��', '/', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1022, 581, '�ñ��', 'BGM2304016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1023, 581, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1024, 581, '������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1025, 581, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1026, 581, '��Ĥ����', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1027, 581, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1028, 581, '�����ֳ���', '49', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1029, 581, '���������', 'CZB2004001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1030, 581, '��Ƶ���ƹ���', 'KBP2107029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1031, 581, '�ز����ƹ���', 'KZB2106015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1032, 581, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1033, 581, '�ù����', '1130', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4322, 622, '�ñ��', 'BGM2406029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4323, 622, '�ù���ͺ�', 'JM5-10-1200D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4324, 622, '������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4325, 622, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4326, 622, '��Ĥ����', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4327, 622, '��������', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4328, 622, '�����ֳ���', '53', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4329, 622, '���������', 'CZB2404022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4330, 622, '��Ƶ���ƹ���', 'KBP2407027', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4331, 622, '�ز����ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4332, 622, 'йѹ�ӹ�й©��', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4333, 622, '�ù����', '658.93', 'm');

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2380, 10041, '���ƹ���', 'KJR2111078', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2381, 10041, '���ƹ��ͺ�', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2382, 10041, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2383, 10041, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2384, 10041, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2385, 10041, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2386, 10041, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2387, 10041, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2388, 10041, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1905, 10061, '���ƹ���', 'KJR2112078', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1906, 10061, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1907, 10061, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1908, 10061, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1909, 10061, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1910, 10061, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1911, 10061, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1912, 10061, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1913, 10061, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1086, 10081, '���ƹ���', 'KJR2110067', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1087, 10081, 'IOģ���ͺ�', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1088, 10081, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1089, 10081, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1090, 10081, 'PLC��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1091, 10081, 'HMI��ַ', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1092, 10081, 'IOģ���ַ', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1093, 10081, '�ź����ַ', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1094, 10081, '4Gģ���ַ', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1095, 10082, '���ƹ���', 'KJR2110068', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1096, 10082, 'IOģ���ͺ�', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1097, 10082, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1098, 10082, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1099, 10082, 'PLC��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1100, 10082, 'HMI��ַ', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1101, 10082, 'IOģ���ַ', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1102, 10082, '�ź����ַ', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1103, 10082, '4Gģ���ַ', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1104, 10083, '���ƹ���', 'KJR2110069', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1105, 10083, 'IOģ���ͺ�', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1106, 10083, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1107, 10083, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1108, 10083, 'PLC��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1109, 10083, 'HMI��ַ', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1110, 10083, 'IOģ���ַ', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1111, 10083, '�ź����ַ', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1112, 10083, '4Gģ���ַ', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1113, 10084, '���ƹ���', 'KJR2109051', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1114, 10084, 'IOģ���ͺ�', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1115, 10084, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1116, 10084, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1117, 10084, 'PLC��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1118, 10084, 'HMI��ַ', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1119, 10084, 'IOģ���ַ', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1120, 10084, '�ź����ַ', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1121, 10084, '4Gģ���ַ', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1122, 10085, '���ƹ���', 'KJR2110066', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1123, 10085, 'IOģ���ͺ�', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1124, 10085, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1125, 10085, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1126, 10085, 'PLC��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1127, 10085, 'HMI��ַ', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1128, 10085, 'IOģ���ַ', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1129, 10085, '�ź����ַ', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1130, 10085, '4Gģ���ַ', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1131, 10121, '���ƹ���', 'KJR2005016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1132, 10121, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1133, 10121, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1134, 10121, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1135, 10121, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1136, 10121, 'PLC��ַ', '192.168.8.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1137, 10121, 'HMI��ַ', '192.168.8.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1138, 10121, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1139, 10121, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1140, 10121, '4Gģ���ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1141, 10122, '���ƹ���', 'KJR2005018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1142, 10122, '���ƹ��ͺ�', 'JRC380-150-G1J3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1143, 10122, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1144, 10122, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1145, 10122, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1146, 10122, 'PLC��ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1147, 10122, 'HMI��ַ', '192.168.8.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1148, 10122, 'IOģ���ַ', '192.168.8.18', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1149, 10122, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1150, 10122, '4Gģ���ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1151, 10123, '���ƹ���', 'KJR2005015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1152, 10123, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1153, 10123, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1154, 10123, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1155, 10123, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1156, 10123, 'PLC��ַ', '192.168.8.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1157, 10123, 'HMI��ַ', '192.168.8.15', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1158, 10123, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1159, 10123, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1160, 10123, '4Gģ���ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1161, 10124, '���ƹ���', 'KJR203001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1162, 10124, '���ƹ��ͺ�', 'JRC380-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1163, 10124, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1164, 10124, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1165, 10124, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1166, 10124, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1167, 10124, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1168, 10124, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1169, 10124, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1170, 10124, '4Gģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1171, 10125, '���ƹ���', 'KJR2005011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1172, 10125, '���ƹ��ͺ�', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1173, 10125, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1174, 10125, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1175, 10125, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1176, 10125, 'PLC��ַ', '192.168.8.22', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1177, 10125, 'HMI��ַ', '192.168.8.23', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1178, 10125, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1179, 10125, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1180, 10125, '4Gģ���ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1181, 10126, '���ƹ���', 'KJR2005012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1182, 10126, '���ƹ��ͺ�', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1183, 10126, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1184, 10126, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1185, 10126, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1186, 10126, 'PLC��ַ', '192.168.8.19', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1187, 10126, 'HMI��ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1188, 10126, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1189, 10126, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1190, 10126, '4Gģ���ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1208, 10181, '���ƹ���', 'KJR2103005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1209, 10181, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1210, 10181, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1211, 10181, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1212, 10181, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1213, 10181, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1214, 10181, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1215, 10181, 'IOģ���ַ', '192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1216, 10181, '�ź����ַ', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1217, 10181, '4Gģ���ַ', '192.168.6.20/192.168.6.21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1198, 10182, '���ƹ���', 'KJR2103005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1199, 10182, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1200, 10182, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1201, 10182, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1202, 10182, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1203, 10182, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1204, 10182, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1205, 10182, 'IOģ���ַ', '192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1206, 10182, '�ź����ַ', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1207, 10182, '4Gģ���ַ', '192.168.6.20/192.168.6.21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1232, 10224, '���ƹ���', 'KJR2005017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1233, 10224, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1234, 10224, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1235, 10224, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1236, 10224, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1237, 10224, 'PLC��ַ', '192.168.6.100', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1238, 10224, 'HMI��ַ', '192.168.6.102', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1239, 10224, 'IOģ���ַ', '192.168.6.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1240, 10224, '�ź����ַ', '192.168.6.106', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1241, 10224, '4Gģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1242, 10225, '���ƹ���', 'KJR2005013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1243, 10225, '���ƹ��ͺ�', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1244, 10225, 'IOģ���ͺ�', 'HJ3209D/HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1245, 10225, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1246, 10225, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1247, 10225, 'PLC��ַ', '192.168.6.103', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1248, 10225, 'HMI��ַ', '192.168.6.110', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1249, 10225, 'IOģ���ַ', '192.168.6.104/192.168.6.105', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1250, 10225, '�ź����ַ', '192.168.6.107', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1251, 10225, '4Gģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1252, 10226, '���ƹ���', 'KJR2006025', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1253, 10226, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1254, 10226, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1255, 10226, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1256, 10226, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1257, 10226, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1258, 10226, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1259, 10226, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1260, 10226, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1261, 10226, '4Gģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1396, 10227, '���ƹ���', 'KJR2005014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1397, 10227, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1398, 10227, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1399, 10227, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1400, 10227, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1401, 10227, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1402, 10227, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1403, 10227, 'IOģ���ַ', '192.168.6.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1404, 10227, '�ź����ַ', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1405, 10227, '4Gģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1296, 10228, '���ƹ���', 'KJR2005008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1297, 10228, '���ƹ��ͺ�', 'JRC380-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1298, 10228, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1299, 10228, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1300, 10228, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1301, 10228, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1302, 10228, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1303, 10228, 'IOģ���ַ', '192.168.6.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1304, 10228, '�ź����ַ', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1305, 10228, '4Gģ���ַ', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1286, 10229, '���ƹ���', 'KJR203002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1287, 10229, '���ƹ��ͺ�', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1288, 10229, 'IOģ���ͺ�', 'HJ3209D/HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1289, 10229, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1290, 10229, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1291, 10229, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1292, 10229, 'HMI��ַ', '192.168.8.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1293, 10229, 'IOģ���ַ', '192.168.8.91/192.168.8.93', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1294, 10229, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1295, 10229, '4Gģ���ַ', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1266, 10230, '���ƹ���', 'KJR203003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1267, 10230, '���ƹ��ͺ�', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1268, 10230, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1269, 10230, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1270, 10230, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1271, 10230, 'PLC��ַ', '192.168.8.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1272, 10230, 'HMI��ַ', '192.168.8.15', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1273, 10230, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1274, 10230, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1275, 10230, '4Gģ���ַ', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1276, 10231, '���ƹ���', 'KJR203004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1277, 10231, '���ƹ��ͺ�', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1278, 10231, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1279, 10231, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1280, 10231, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1281, 10231, 'PLC��ַ', '192.168.8.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1282, 10231, 'HMI��ַ', '192.168.8.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1283, 10231, 'IOģ���ַ', '192.168.8.92', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1284, 10231, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1285, 10231, '4Gģ���ַ', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1264, 10232, '���ƹ���', '201908001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1265, 10232, '���ƹ��ͺ�', 'CHBP-75kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1262, 10233, '���ƹ���', '201908002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1263, 10233, '���ƹ��ͺ�', 'CHBP-55kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1386, 10234, '���ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1387, 10234, '���ƹ��ͺ�', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1388, 10234, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1389, 10234, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1390, 10234, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1391, 10234, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1392, 10234, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1393, 10234, 'IOģ���ַ', '192.168.6.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1394, 10234, '�ź����ַ', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1395, 10234, '4Gģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1757, 10241, '���ƹ���', 'KCS2106026', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1758, 10241, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1759, 10241, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1760, 10241, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1761, 10241, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1762, 10241, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1763, 10241, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1984, 10281, '���ƹ���', 'KCS2206052', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1985, 10281, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1986, 10281, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1987, 10281, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1988, 10281, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1989, 10281, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1990, 10281, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3149, 10301, '���ƹ���', 'KCS2206055', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3150, 10301, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3151, 10301, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3152, 10301, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3153, 10301, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3154, 10301, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3155, 10301, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3156, 10301, '����', '��ƽ99-102', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1778, 10302, '���ƹ���', 'KCS2206051', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1779, 10302, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1780, 10302, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1781, 10302, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1782, 10302, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1783, 10302, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1784, 10302, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3691, 10303, '���ƹ���', 'KCS2206053', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3692, 10303, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3693, 10303, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3694, 10303, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3695, 10303, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3696, 10303, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3697, 10303, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1968, 10304, '���ƹ���', 'KCS2206054', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1969, 10304, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1970, 10304, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1971, 10304, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1972, 10304, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1973, 10304, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1974, 10304, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2965, 10305, '���ƹ���', 'KCS2206056', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2966, 10305, '���ƹ��ͺ�', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2967, 10305, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2968, 10305, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2969, 10305, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2970, 10305, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2971, 10305, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2373, 10306, '���ƹ���', 'KCS2206057', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2374, 10306, '���ƹ��ͺ�', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2375, 10306, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2376, 10306, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2377, 10306, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2378, 10306, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2379, 10306, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2183, 10307, '���ƹ���', 'KCS2206058', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2184, 10307, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2185, 10307, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2186, 10307, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2187, 10307, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2188, 10307, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2189, 10307, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1764, 10308, '���ƹ���', 'KCS2206059', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1765, 10308, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1766, 10308, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1767, 10308, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1768, 10308, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1769, 10308, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1770, 10308, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1998, 10309, '���ƹ���', 'KCS2206060', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1999, 10309, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2000, 10309, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2001, 10309, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2002, 10309, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2003, 10309, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2004, 10309, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1785, 10310, '���ƹ���', 'KCS2206061', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1786, 10310, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1787, 10310, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1788, 10310, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1789, 10310, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1790, 10310, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1791, 10310, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1792, 10311, '���ƹ���', 'KCS2206062', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1306, 10312, '���ƹ���', 'KCS2206063', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1307, 10313, '���ƹ���', 'KCS2206064', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1308, 10314, '���ƹ���', 'KCS2206065', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1807, 10321, '���ƹ���', 'KJR2104011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1808, 10321, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1809, 10321, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1810, 10321, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1811, 10321, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1812, 10321, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2285, 10322, '���ƹ���', 'KJR2108035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2286, 10322, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2287, 10322, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2288, 10322, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2289, 10322, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2290, 10322, 'HMI��ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2291, 10322, '4Gģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1671, 10323, '���ƹ���', 'KJR2108042', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1672, 10323, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1673, 10323, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1674, 10323, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1675, 10323, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1676, 10323, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1677, 10323, '4Gģ���ַ', '192.168.2.120', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1218, 10324, '���ƹ���', 'KJR2108034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1219, 10324, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1220, 10324, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1221, 10324, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1222, 10324, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1223, 10324, 'HMI��ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1224, 10324, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1225, 10325, '���ƹ���', 'KJR2108038', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1226, 10325, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1227, 10325, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1228, 10325, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1229, 10325, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1230, 10325, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1231, 10325, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2632, 10326, '���ƹ���', 'KJR2108037', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2633, 10326, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2634, 10326, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2635, 10326, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2636, 10326, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2637, 10326, 'HMI��ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2638, 10326, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1716, 10343, '���ƹ���', 'KCS2009009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1717, 10343, '���ƹ��ͺ�', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1718, 10343, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1719, 10343, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1720, 10343, 'PLC��ַ', '11.5.10.54', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1721, 10343, 'HMI��ַ', '11.5.10.55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1722, 10343, '4Gģ���ַ', '11.5.10.51', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1494, 10344, '���ƹ���', 'KCS2107033', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1495, 10344, '���ƹ��ͺ�', 'CSC380-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1496, 10344, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1497, 10344, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1498, 10344, 'PLC��ַ', '11.5.26.91', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1499, 10344, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1500, 10344, '4Gģ���ַ', '11.5.26.90', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4297, 10345, '���ƹ���', 'KCS2005003(KCS2005001)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2012, 10346, '���ƹ���', '��KCS2105017��KCS2209068', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2013, 10346, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2014, 10346, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2015, 10346, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2016, 10346, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2017, 10346, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2018, 10346, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1508, 10347, '���ƹ���', 'KCS2105018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1509, 10347, '���ƹ��ͺ�', 'CSC380-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1510, 10347, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1511, 10347, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1512, 10347, 'PLC��ַ', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1513, 10347, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1514, 10347, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1501, 10348, '���ƹ���', 'KCS2009012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1502, 10348, '���ƹ��ͺ�', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1503, 10348, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1504, 10348, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1505, 10348, 'PLC��ַ', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1506, 10348, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1507, 10348, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1487, 10349, '���ƹ���', 'KCS2009008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1488, 10349, '���ƹ��ͺ�', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1489, 10349, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1490, 10349, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1491, 10349, 'PLC��ַ', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1492, 10349, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1493, 10349, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1459, 10350, '���ƹ���', 'KCS2009007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1460, 10350, '���ƹ��ͺ�', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1461, 10350, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1462, 10350, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1463, 10350, 'PLC��ַ', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1464, 10350, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1465, 10350, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1516, 10351, '���ƹ���', 'KCS2009013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1517, 10351, '���ƹ��ͺ�', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1518, 10351, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1519, 10351, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1520, 10351, 'PLC��ַ', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1521, 10351, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1522, 10351, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1646, 10352, '���ƹ���', 'KCS2108041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1647, 10352, '���ƹ��ͺ�', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1648, 10352, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1649, 10352, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1650, 10352, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1651, 10352, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1652, 10352, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1416, 10353, '���ƹ��ͺ�', 'CSC380-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1417, 10353, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1418, 10353, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1419, 10353, 'PLC��ַ', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1420, 10353, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1421, 10353, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1422, 10354, '���ƹ���', 'KCS2009011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1423, 10354, '���ƹ��ͺ�', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1424, 10354, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1425, 10354, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1426, 10354, 'PLC��ַ', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1427, 10354, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1428, 10354, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1723, 10355, '���ƹ���', 'KCS2009015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1724, 10355, '���ƹ��ͺ�', 'CSC380-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1725, 10355, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1726, 10355, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1727, 10355, 'PLC��ַ', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1728, 10355, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1729, 10355, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1959, 10356, '���ƹ���', 'KCS2009014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1960, 10356, '���ƹ��ͺ�', 'CSC380-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1961, 10356, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1962, 10356, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1963, 10356, 'PLC��ַ', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1964, 10356, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1965, 10356, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1967, 10357, '���ƹ���', 'KCS2005002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1800, 10358, '���ƹ���', 'KCS2108043', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1801, 10358, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1802, 10358, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1803, 10358, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1804, 10358, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1805, 10358, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1806, 10358, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2647, 10359, '���ƹ���', 'KCS2108039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2648, 10359, '���ƹ��ͺ�', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2649, 10359, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2650, 10359, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2651, 10359, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2652, 10359, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2653, 10359, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2484, 10360, '���ƹ���', 'KCS2108045', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2485, 10360, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2486, 10360, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2487, 10360, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2488, 10360, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2489, 10360, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2490, 10360, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2491, 10360, '���»���', '45kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1332, 10361, '���ƹ���', 'KCS2108040', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1333, 10361, '���ƹ��ͺ�', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1334, 10361, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1335, 10361, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1336, 10361, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1337, 10361, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1338, 10361, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1702, 10362, '���ƹ���', 'KCS2106028', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1703, 10362, '���ƹ��ͺ�', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1704, 10362, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1705, 10362, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1706, 10362, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1707, 10362, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1708, 10362, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1473, 10363, '���ƹ���', 'KCS2106019', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1474, 10363, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1475, 10363, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1476, 10363, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1477, 10363, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1478, 10363, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1479, 10363, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1466, 10364, '���ƹ���', 'KCS2106020', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1467, 10364, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1468, 10364, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1469, 10364, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1470, 10364, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1471, 10364, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1472, 10364, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2504, 10365, '���ƹ���', 'KCS2106021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2505, 10365, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2506, 10365, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2507, 10365, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2508, 10365, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2509, 10365, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2510, 10365, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1480, 10366, '���ƹ���', 'KCS2108044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1481, 10366, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1482, 10366, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1483, 10366, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1484, 10366, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1485, 10366, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1486, 10366, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1523, 10367, '���ƹ���', 'KCS2106022', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1524, 10367, '���ƹ��ͺ�', 'CSC660-45', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1525, 10367, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1526, 10367, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1527, 10367, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1528, 10367, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1529, 10367, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1323, 10368, '���ƹ���', 'KCS2106023', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1324, 10368, '���ƹ��ͺ�', 'CSC660-45', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1325, 10368, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1326, 10368, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1327, 10368, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1328, 10368, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1329, 10368, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1309, 10369, '���ƹ���', 'KCS2108042', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1310, 10369, '���ƹ��ͺ�', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1311, 10369, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1312, 10369, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1313, 10369, 'PLC��ַ', '11.10.13.108', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1314, 10369, 'HMI��ַ', '11.10.13.107', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1315, 10369, '4Gģ���ַ', '11.10.13.115', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1316, 10370, '���ƹ���', 'KCS2108047', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1317, 10370, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1318, 10370, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1319, 10370, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1320, 10370, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1321, 10370, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1322, 10370, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2532, 10371, '���ƹ���', 'KCS2106025', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2533, 10371, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2534, 10371, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2535, 10371, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2536, 10371, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2537, 10371, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2538, 10371, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1515, 10372, '���ƹ���', 'KCS2106029', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3806, 10373, '���ƹ���', 'KCS2009004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3807, 10373, '���ƹ��ͺ�', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3808, 10373, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3809, 10373, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3810, 10373, 'PLC��ַ', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3811, 10373, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3812, 10373, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2525, 10374, '���ƹ���', 'KCS2009010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2526, 10374, '���ƹ��ͺ�', 'CSC380-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2527, 10374, '��Ƶ���ͺ�', 'ABB_ACS510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2528, 10374, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2529, 10374, 'PLC��ַ', '192.168.8.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2530, 10374, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2531, 10374, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1639, 10375, '���ƹ���', 'KCS2108046', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1640, 10375, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1641, 10375, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1642, 10375, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1643, 10375, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1644, 10375, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1645, 10375, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1330, 10376, '���ƹ���', 'KCS2106024', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1331, 10376, '���ƹ��ͺ�', 'CSC660-45', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1977, 10377, '���ƹ���', 'KCS2106030', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1978, 10377, '���ƹ��ͺ�', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1979, 10377, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1980, 10377, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1981, 10377, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1982, 10377, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1983, 10377, '4Gģ���ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1191, 10378, '���ƹ���', 'KJR2112084', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1192, 10378, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1193, 10378, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1194, 10378, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1195, 10378, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1196, 10378, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1197, 10378, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2518, 10379, '���ƹ���', 'KCS2108050', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2519, 10379, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2520, 10379, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2521, 10379, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2522, 10379, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2523, 10379, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2524, 10379, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1991, 10380, '���ƹ���', 'KCS2108048', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1992, 10380, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1993, 10380, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1994, 10380, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1995, 10380, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1996, 10380, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1997, 10380, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1771, 10381, '���ƹ���', 'KCS2108049', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1772, 10381, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1773, 10381, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1774, 10381, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1775, 10381, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1776, 10381, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1777, 10381, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2397, 10382, '���ƹ���', 'KCS2107031', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2398, 10382, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2399, 10382, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2400, 10382, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2401, 10382, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2402, 10382, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2403, 10382, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1748, 10383, '���ƹ���', 'KCS2106027', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1749, 10383, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1750, 10383, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1751, 10383, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1752, 10383, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1753, 10383, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1754, 10383, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3755, 10384, '���ƹ���', 'KJR2110067', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3756, 10384, 'IOģ���ͺ�', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3757, 10384, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3758, 10384, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3759, 10384, 'PLC��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3760, 10384, 'HMI��ַ', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3761, 10384, 'IOģ���ַ', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3762, 10384, '�ź����ַ', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3763, 10384, '4Gģ���ַ', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3764, 10384, 'ͨѶ��ʽ', '����70��8300����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3418, 10385, '���ƹ���', 'KJR2110068', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3419, 10385, 'IOģ���ͺ�', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3420, 10385, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3421, 10385, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3422, 10385, 'PLC��ַ', '192.168.6.41', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3423, 10385, 'HMI��ַ', '192.168.6.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3424, 10385, 'IOģ���ַ', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3425, 10385, '�ź����ַ', '192.168.6.43', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3426, 10385, '4Gģ���ַ', '192.168.6.40', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3427, 10385, 'ͨѶ��ʽ', '����70��8300����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1339, 10386, '���ƹ���', 'KJR2110069', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1340, 10386, 'IOģ���ͺ�', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1341, 10386, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1342, 10386, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1343, 10386, 'PLC��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1344, 10386, 'HMI��ַ', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1345, 10386, 'IOģ���ַ', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1346, 10386, '�ź����ַ', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1347, 10386, '4Gģ���ַ', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1348, 10387, '���ƹ���', 'KJR2109051', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1349, 10387, 'IOģ���ͺ�', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1350, 10387, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1351, 10387, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1352, 10387, 'PLC��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1353, 10387, 'HMI��ַ', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1354, 10387, 'IOģ���ַ', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1355, 10387, '�ź����ַ', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1356, 10387, '4Gģ���ַ', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1357, 10388, '���ƹ���', 'KJR2110066', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1358, 10388, 'IOģ���ͺ�', 'VM3209A/VM3209P16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1359, 10388, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1360, 10388, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1361, 10388, 'PLC��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1362, 10388, 'HMI��ַ', '192.168.6.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1363, 10388, 'IOģ���ַ', '192.168.6.14/192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1364, 10388, '�ź����ַ', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1365, 10388, '4Gģ���ַ', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3369, 10389, '���ƹ���', 'KJR2005016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3370, 10389, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3371, 10389, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3372, 10389, '�������ͺ�', 'TH', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3373, 10389, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3374, 10389, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3375, 10389, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3376, 10389, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3377, 10389, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3378, 10389, '4Gģ���ַ', '192.168.2.15', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3389, 10390, '���ƹ���', 'KJR2005018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3390, 10390, '���ƹ��ͺ�', 'JRC380-150-G1J3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3391, 10390, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3392, 10390, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3393, 10390, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3394, 10390, 'PLC��ַ', '192.168.2.6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3395, 10390, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3396, 10390, 'IOģ���ַ', '192.168.2.24', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3397, 10390, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3398, 10390, '4Gģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3379, 10391, '���ƹ���', 'KJR2005015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3380, 10391, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3381, 10391, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3382, 10391, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3383, 10391, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3384, 10391, 'PLC��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3385, 10391, 'HMI��ַ', '192.168.2.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3386, 10391, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3387, 10391, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3388, 10391, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1813, 10392, '���ƹ���', 'KJR203001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1814, 10392, '���ƹ��ͺ�', 'JRC380-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1815, 10392, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1816, 10392, '�������ͺ�', 'SR', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1817, 10392, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1818, 10392, 'PLC��ַ', '192.168.6.83', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1819, 10392, 'HMI��ַ', '192.168.6.87', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1820, 10392, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1821, 10392, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1822, 10392, '4Gģ���ַ', '192.168.6.85', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1366, 10393, '���ƹ���', 'KJR2005011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1367, 10393, '���ƹ��ͺ�', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1368, 10393, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1369, 10393, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1370, 10393, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1371, 10393, 'PLC��ַ', '192.168.8.22', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1372, 10393, 'HMI��ַ', '192.168.8.23', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1373, 10393, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1374, 10393, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1375, 10393, '4Gģ���ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1376, 10394, '���ƹ���', 'KJR2005012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1377, 10394, '���ƹ��ͺ�', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1378, 10394, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1379, 10394, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1380, 10394, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1381, 10394, 'PLC��ַ', '192.168.8.19', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1382, 10394, 'HMI��ַ', '192.168.8.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1383, 10394, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1384, 10394, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1385, 10394, '4Gģ���ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2166, 10395, '���ƹ���', 'KJR2103005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2167, 10395, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2168, 10395, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2169, 10395, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2170, 10395, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2171, 10395, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2172, 10395, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2173, 10395, 'IOģ���ַ', '192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2174, 10395, '�ź����ַ', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2175, 10395, '4Gģ���ַ', '192.168.6.20/192.168.6.21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2190, 10396, '���ƹ���', 'KJR2103006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2191, 10396, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2192, 10396, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2193, 10396, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2194, 10396, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2195, 10396, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2196, 10396, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2197, 10396, 'IOģ���ַ', '192.168.6.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2198, 10396, '�ź����ַ', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2199, 10396, '4Gģ���ַ', '192.168.6.20/192.168.6.21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2200, 10397, '���ƹ���', 'KJR2005017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2201, 10397, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2202, 10397, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2203, 10397, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2204, 10397, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2205, 10397, 'PLC��ַ', '192.168.6.100', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2206, 10397, 'HMI��ַ', '192.168.6.102', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2207, 10397, 'IOģ���ַ', '192.168.6.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2208, 10397, '�ź����ַ', '192.168.6.106', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2209, 10397, '4Gģ���ַ', '192.168.6.108', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2132, 10398, '���ƹ���', 'KJR2005013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2133, 10398, '���ƹ��ͺ�', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2134, 10398, 'IOģ���ͺ�', 'HJ3209D/HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2135, 10398, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2136, 10398, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2137, 10398, 'PLC��ַ', '192.168.6.103', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2138, 10398, 'HMI��ַ', '192.168.6.110', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2139, 10398, 'IOģ���ַ', '192.168.6.104/192.168.6.105', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2140, 10398, '�ź����ַ', '192.168.6.107', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2141, 10398, '4Gģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2142, 10398, '�ɿ�ʵ���д���ȶ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2210, 10399, '���ƹ���', 'KJR2006025', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2211, 10399, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2212, 10399, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2213, 10399, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2214, 10399, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2215, 10399, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2216, 10399, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2217, 10399, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2218, 10399, '�ź����ַ', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2219, 10399, '4Gģ���ַ', '192.168.6.21/192.168.6.22', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1823, 10400, '���ƹ���', 'KJR2005014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1824, 10400, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1825, 10400, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1826, 10400, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1827, 10400, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1828, 10400, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1829, 10400, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1830, 10400, 'IOģ���ַ', '192.168.6.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1831, 10400, '�ź����ַ', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1832, 10400, '4Gģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2905, 10401, '���ƹ���', '201908002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2906, 10401, '���ƹ��ͺ�', 'CHBP-55kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2907, 10402, '���ƹ���', '201908001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2908, 10402, '���ƹ��ͺ�', 'CHBP-75kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3673, 10403, '���ƹ���', 'KJR203003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3674, 10403, '���ƹ��ͺ�', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3675, 10403, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3676, 10403, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3677, 10403, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3678, 10403, 'PLC��ַ', '192.168.8.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3679, 10403, 'HMI��ַ', '192.168.8.15', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3680, 10403, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3681, 10403, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3682, 10403, '4Gģ���ַ', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2711, 10404, '���ƹ���', 'KJR203004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2712, 10404, '���ƹ��ͺ�', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2713, 10404, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2714, 10404, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2715, 10404, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2716, 10404, 'PLC��ַ', '192.168.8.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2717, 10404, 'HMI��ַ', '192.168.8.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2718, 10404, 'IOģ���ַ', '192.168.8.92', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2719, 10404, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2720, 10404, '4Gģ���ַ', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2721, 10404, '�ɿ�ʵ���д���ȶ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1738, 10405, '���ƹ���', 'KJR203002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1739, 10405, '���ƹ��ͺ�', 'JRC380-G1J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1740, 10405, 'IOģ���ͺ�', 'HJ3209D/HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1741, 10405, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1742, 10405, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1743, 10405, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1744, 10405, 'HMI��ַ', '192.168.8.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1745, 10405, 'IOģ���ַ', '192.168.8.91/192.168.8.93', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1746, 10405, '�ź����ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1747, 10405, '4Gģ���ַ', '192.168.8.42', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1833, 10406, '���ƹ���', 'KJR2005008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1834, 10406, '���ƹ��ͺ�', 'JRC380-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1835, 10406, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1836, 10406, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1837, 10406, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1838, 10406, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1839, 10406, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1840, 10406, 'IOģ���ַ', '192.168.6.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1841, 10406, '�ź����ַ', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1842, 10406, '4Gģ���ַ', '192.168.6.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1406, 10407, '���ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1407, 10407, '���ƹ��ͺ�', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1408, 10407, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1409, 10407, '�������ͺ�', 'SRϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1410, 10407, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1411, 10407, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1412, 10407, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1413, 10407, 'IOģ���ַ', '192.168.6.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1414, 10407, '�ź����ַ', '192.168.6.11', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1415, 10407, '4Gģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1429, 10408, '���ƹ���', 'KJR2108041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1430, 10408, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1431, 10408, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1432, 10408, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1433, 10408, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1434, 10408, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1435, 10408, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2625, 10409, '���ƹ���', 'KJR2108044', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2626, 10409, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2627, 10409, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2628, 10409, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2629, 10409, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2630, 10409, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2631, 10409, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1436, 10410, '���ƹ���', 'KJR2111070', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1437, 10410, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1438, 10410, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1439, 10410, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1440, 10410, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1441, 10410, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1442, 10410, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1443, 10410, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1444, 10410, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1445, 10411, '���ƹ���', 'KJR2112081', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1446, 10411, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1447, 10411, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1448, 10411, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1449, 10411, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1450, 10411, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1451, 10411, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1452, 10412, '���ƹ���', 'KJR2112086', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1453, 10412, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1454, 10412, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1455, 10412, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1456, 10412, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1457, 10412, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1458, 10412, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1862, 10413, '���ƹ���', 'KJR2112085', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1863, 10413, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1864, 10413, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1865, 10413, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1866, 10413, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1867, 10413, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1868, 10413, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3165, 10414, '���ƹ���', 'KJR2108040', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3166, 10414, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3167, 10414, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3168, 10414, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3169, 10414, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3170, 10414, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3171, 10414, '4Gģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2157, 10421, '���ƹ���', 'KJR2207019', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2158, 10421, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2159, 10421, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2160, 10421, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2161, 10421, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2162, 10421, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2163, 10421, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2164, 10421, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2165, 10421, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3893, 10422, '���ƹ���', 'KJR2207021', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3894, 10422, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3895, 10422, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3896, 10422, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3897, 10422, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3898, 10422, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3899, 10422, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3900, 10422, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3901, 10422, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3902, 10422, '��������', '�ܶ�������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2764, 10423, '���ƹ���', 'KJR2207020', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2765, 10423, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2766, 10423, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2767, 10423, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2768, 10423, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2769, 10423, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2770, 10423, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2771, 10423, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2772, 10423, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2005, 10441, '���ƹ���', 'KCS2207066', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2006, 10441, '���ƹ��ͺ�', 'CSC380-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2007, 10441, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2008, 10441, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2009, 10441, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2010, 10441, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2011, 10441, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2404, 10442, '���ƹ���', 'KCS2207067', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2405, 10442, '���ƹ��ͺ�', 'CSC380-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2406, 10442, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2407, 10442, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2408, 10442, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2409, 10442, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2410, 10442, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1530, 10461, '���ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1531, 10461, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1532, 10461, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1533, 10461, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1534, 10461, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1535, 10461, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1536, 10461, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1537, 10461, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1538, 10461, '4Gģ���ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1853, 10462, '���ƹ���', 'KGR2105015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1854, 10462, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1855, 10462, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1856, 10462, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1857, 10462, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1858, 10462, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1859, 10462, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1860, 10462, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1861, 10462, '4Gģ���ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2654, 10463, '���ƹ���', 'KGR2105012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2655, 10463, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2656, 10463, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2657, 10463, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2658, 10463, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2659, 10463, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2660, 10463, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2661, 10463, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2662, 10463, '4Gģ���ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1539, 10464, '���ƹ���', 'KGR2105012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1540, 10464, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1541, 10464, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1542, 10464, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1543, 10464, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1544, 10464, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1545, 10464, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1546, 10464, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1547, 10464, '4Gģ���ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1611, 10465, '���ƹ���', 'KGR2105012', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1612, 10465, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1613, 10465, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1614, 10465, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1615, 10465, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1616, 10465, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1617, 10465, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1618, 10465, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1619, 10465, '4Gģ���ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1548, 10466, '���ƹ���', 'KGR2105015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1549, 10466, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1550, 10466, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1551, 10466, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1552, 10466, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1553, 10466, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1554, 10466, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1555, 10466, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1556, 10466, '4Gģ���ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2896, 10467, '���ƹ���', 'KGR2105013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2897, 10467, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2898, 10467, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2899, 10467, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2900, 10467, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2901, 10467, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2902, 10467, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2903, 10467, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2904, 10467, '4Gģ���ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1557, 10468, '���ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1558, 10468, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1559, 10468, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1560, 10468, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1561, 10468, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1562, 10468, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1563, 10468, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1564, 10468, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1565, 10468, '4Gģ���ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1566, 10469, '���ƹ���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1567, 10469, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1568, 10469, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1569, 10469, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1570, 10469, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1571, 10469, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1572, 10469, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1573, 10469, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1574, 10469, '4Gģ���ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1620, 10470, '���ƹ���', 'KGR2105014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1621, 10470, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1622, 10470, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1623, 10470, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1624, 10470, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1625, 10470, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1626, 10470, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1627, 10470, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1628, 10470, '4Gģ���ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1575, 10471, '���ƹ���', 'KGR2105014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1576, 10471, '���ƹ��ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1577, 10471, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1578, 10471, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1579, 10471, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1580, 10471, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1581, 10471, 'HMI��ַ', '192.168.2.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1582, 10471, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1583, 10471, '4Gģ���ַ', '192.168.2.20', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3854, 10472, '���ƹ���', 'KJR2108043', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3855, 10472, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3856, 10472, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3857, 10472, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3858, 10472, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3859, 10472, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3860, 10472, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3861, 10472, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3862, 10472, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3873, 10473, '���ƹ���', 'KJR2108039', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3874, 10473, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3875, 10473, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3876, 10473, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3877, 10473, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3878, 10473, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3879, 10473, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3880, 10473, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3881, 10473, '4Gģ���ַ', '192.168.2.123', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3882, 10473, 'ԭ����', '��34-26', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1653, 10474, '���ƹ���', 'KJR2112080', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1654, 10474, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1655, 10474, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1656, 10474, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1657, 10474, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1658, 10474, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1659, 10474, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1660, 10474, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1661, 10474, '4Gģ���ַ', '192.168.2.87', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3863, 10475, '���ƹ���', 'KJR2112087', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3864, 10475, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3865, 10475, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3866, 10475, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3867, 10475, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3868, 10475, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3869, 10475, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3870, 10475, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3871, 10475, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3872, 10475, 'ԭ����', '��19-21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1629, 10476, '���ƹ���', 'KJR2111072', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1630, 10476, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1631, 10476, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1632, 10476, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1633, 10476, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1634, 10476, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1635, 10476, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1636, 10476, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1637, 10476, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1584, 10477, '���ƹ���', 'KJR2111073', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1585, 10477, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1586, 10477, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1587, 10477, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1588, 10477, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1589, 10477, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1590, 10477, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1591, 10477, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1592, 10477, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1662, 10478, '���ƹ���', 'KJR2112079', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1663, 10478, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1664, 10478, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1665, 10478, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1666, 10478, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1667, 10478, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1668, 10478, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1669, 10478, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1670, 10478, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3651, 10479, '���ƹ���', 'KJR2112083', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3652, 10479, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3653, 10479, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3654, 10479, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3655, 10479, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3656, 10479, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3657, 10479, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1602, 10480, '���ƹ���', 'KJR2111071', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1603, 10480, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1604, 10480, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1605, 10480, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1606, 10480, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1607, 10480, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1608, 10480, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1609, 10480, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1610, 10480, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3484, 10481, '���ƹ���', 'KJR2112082', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3485, 10481, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3486, 10481, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3487, 10481, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3488, 10481, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3489, 10481, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3490, 10481, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3491, 10481, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3492, 10481, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1593, 10482, '���ƹ���', 'KJR2112088', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1594, 10482, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1595, 10482, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1596, 10482, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1597, 10482, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1598, 10482, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1599, 10482, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1600, 10482, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1601, 10482, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1638, 10483, '���ƹ���', 'KBP2105011', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1730, 10484, '���ƹ���', '201907005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2131, 10485, '���ƹ���', '210907008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2130, 10487, '���ƹ���', '210907008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1878, 10489, '���ƹ���', 'KJR2111074', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1879, 10489, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1880, 10489, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1881, 10489, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1882, 10489, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1883, 10489, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1884, 10489, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1885, 10489, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1886, 10489, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3713, 10490, '���ƹ���', 'KJR2108036', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3714, 10490, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3715, 10490, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3716, 10490, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3717, 10490, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3718, 10490, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3719, 10490, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3720, 10490, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3721, 10490, '4Gģ���ַ', '192.168.2.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1887, 10491, '���ƹ���', 'KJR2111075', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1888, 10491, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1889, 10491, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1890, 10491, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1891, 10491, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1892, 10491, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1893, 10491, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1894, 10491, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1895, 10491, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1896, 10492, '���ƹ���', 'KJR2111076', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1897, 10492, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1898, 10492, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1899, 10492, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1900, 10492, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1901, 10492, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1902, 10492, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1903, 10492, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1904, 10492, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3465, 10493, '���ƹ���', 'KJR2111077', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3466, 10493, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3467, 10493, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3468, 10493, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3469, 10493, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3470, 10493, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3471, 10493, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3472, 10493, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3473, 10493, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1869, 10494, '���ƹ���', 'KJR2202011��07��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1870, 10494, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1871, 10494, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1872, 10494, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1873, 10494, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1874, 10494, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1875, 10494, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1876, 10494, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1877, 10494, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1914, 10495, '���ƹ���', 'KJR2202008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1915, 10495, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1916, 10495, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1917, 10495, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1918, 10495, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1919, 10495, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1920, 10495, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1921, 10495, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1922, 10495, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1923, 10496, '���ƹ���', 'KJR2204013', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1924, 10496, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1925, 10496, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1926, 10496, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1927, 10496, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1928, 10496, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1929, 10496, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1930, 10496, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1931, 10496, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3731, 10497, '���ƹ���', 'KJR2204014', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3732, 10497, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3733, 10497, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3734, 10497, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3735, 10497, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3736, 10497, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3737, 10497, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3738, 10497, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3739, 10497, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3437, 10498, '���ƹ���', 'KJR2204015', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3438, 10498, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3439, 10498, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3440, 10498, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3441, 10498, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3442, 10498, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3443, 10498, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3444, 10498, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3445, 10498, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3428, 10499, '���ƹ���', 'KJR2204016', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3429, 10499, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3430, 10499, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3431, 10499, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3432, 10499, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3433, 10499, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3434, 10499, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3435, 10499, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3436, 10499, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1932, 10500, '���ƹ���', 'KJR2204017', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1933, 10500, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1934, 10500, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1935, 10500, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1936, 10500, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1937, 10500, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1938, 10500, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1939, 10500, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1940, 10500, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1941, 10501, '���ƹ���', 'KJR2204018', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1942, 10501, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1943, 10501, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1944, 10501, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1945, 10501, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1946, 10501, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1947, 10501, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1948, 10501, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1949, 10501, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4301, 10502, '���ƹ���', 'KJR2206089', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4302, 10502, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4303, 10502, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4304, 10502, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4305, 10502, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4306, 10502, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4307, 10502, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4308, 10502, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4309, 10502, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2555, 10521, '���ƹ���', 'KJR2110045', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2556, 10521, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2557, 10521, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2558, 10521, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2559, 10521, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2560, 10521, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2561, 10521, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2562, 10521, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2813, 10522, '���ƹ���', 'KJR2110046', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2814, 10522, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2815, 10522, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2816, 10522, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2817, 10522, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2818, 10522, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2819, 10522, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2820, 10522, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2046, 10523, '���ƹ���', 'KJR2110047', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2047, 10523, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2048, 10523, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2049, 10523, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2050, 10523, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2051, 10523, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2052, 10523, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2053, 10523, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2054, 10524, '���ƹ���', 'KJR2110048', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2055, 10524, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2056, 10524, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2057, 10524, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2058, 10524, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2059, 10524, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2060, 10524, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2061, 10524, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2062, 10525, '���ƹ���', 'KJR2110049', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2063, 10525, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2064, 10525, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2065, 10525, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2066, 10525, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2067, 10525, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2068, 10525, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2069, 10525, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2781, 10526, '���ƹ���', 'KJR2110050', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2782, 10526, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2783, 10526, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2784, 10526, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2785, 10526, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2786, 10526, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2787, 10526, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2788, 10526, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2917, 10527, '���ƹ���', 'KJR2110051', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2918, 10527, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2919, 10527, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2920, 10527, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2921, 10527, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2922, 10527, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2923, 10527, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2924, 10527, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2740, 10528, '���ƹ���', 'KJR2110052', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2741, 10528, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2742, 10528, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2743, 10528, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2744, 10528, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2745, 10528, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2746, 10528, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2747, 10528, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2773, 10529, '���ƹ���', 'KJR2110053', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2774, 10529, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2775, 10529, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2776, 10529, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2777, 10529, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2778, 10529, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2779, 10529, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2780, 10529, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2909, 10530, '���ƹ���', 'KJR2110054', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2910, 10530, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2911, 10530, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2912, 10530, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2913, 10530, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2914, 10530, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2915, 10530, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2916, 10530, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2070, 10531, '���ƹ���', 'KJR1912006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2071, 10531, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2072, 10531, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2073, 10531, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2074, 10532, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2075, 10532, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2076, 10532, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1694, 10533, '���ƹ���', 'KJR1912002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1695, 10533, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1696, 10533, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1697, 10533, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1698, 10533, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1699, 10533, 'PLC��ַ', '11.8.11.115', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1700, 10533, 'HMI��ַ', '11.8.11.', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1701, 10533, '4Gģ���ַ', '11.8.11.130', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2077, 10534, '���ƹ���', 'KJR1912003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2078, 10534, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2079, 10534, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2080, 10534, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2262, 10535, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2263, 10535, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2264, 10535, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2239, 10536, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2240, 10536, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2241, 10536, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2259, 10537, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2260, 10537, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2261, 10537, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2271, 10538, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2272, 10538, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2273, 10538, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2268, 10539, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2269, 10539, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2270, 10539, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2256, 10540, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2257, 10540, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2258, 10540, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2081, 10541, '���ƹ���', 'KJR2001001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2082, 10541, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2083, 10541, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2084, 10541, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2501, 10542, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2502, 10542, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2503, 10542, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2265, 10543, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2266, 10543, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2267, 10543, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2030, 10544, '���ƹ���', 'KJR2012034', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2031, 10544, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2032, 10544, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2033, 10544, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2250, 10545, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2251, 10545, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2252, 10545, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2034, 10546, '���ƹ���', 'KJR2011031', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2035, 10546, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2036, 10546, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2037, 10546, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2038, 10547, '���ƹ���', 'KJR2012035', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2039, 10547, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2040, 10547, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2041, 10547, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2042, 10548, '���ƹ���', 'KJR2012036', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2043, 10548, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2044, 10548, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2045, 10548, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2253, 10549, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2254, 10549, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2255, 10549, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2232, 10550, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2233, 10550, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2234, 10550, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2026, 10551, '���ƹ���', 'KJR2012037', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2027, 10551, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2028, 10551, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2029, 10551, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2229, 10552, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2230, 10552, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2231, 10552, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1686, 10553, '���ƹ���', 'KJR2012041', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1687, 10553, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1688, 10553, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1689, 10553, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1690, 10553, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1691, 10553, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1692, 10553, 'HMI��ַ', '192.168.2.', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1693, 10553, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2220, 10554, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2221, 10554, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2222, 10554, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2226, 10555, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2227, 10555, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2228, 10555, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2223, 10556, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2224, 10556, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2225, 10556, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2085, 10557, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2086, 10557, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2087, 10557, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2246, 10558, '���ƹ���', 'KJR2103003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2247, 10558, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2248, 10558, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2249, 10558, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2235, 10559, '���ƹ���', 'KJR2103002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2236, 10559, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2237, 10559, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2238, 10559, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2242, 10560, '���ƹ���', 'KJR2103004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2243, 10560, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2244, 10560, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2245, 10560, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2274, 10561, '���ƹ���', 'KJR2103001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2275, 10561, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2276, 10561, '����', '������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2277, 10561, '�������ͺ�', '��ʩHM', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1678, 10562, '���ƹ���', 'JR20190002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1679, 10562, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1680, 10562, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1681, 10562, '�������ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1682, 10562, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1683, 10562, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1684, 10562, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1685, 10562, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2088, 10563, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2089, 10563, '����', '��һ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2090, 10563, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3190, 10564, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3191, 10564, '����', '��һ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3192, 10564, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2091, 10565, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2092, 10565, '����', '��һ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2093, 10565, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2094, 10566, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2095, 10566, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2096, 10566, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2097, 10567, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2098, 10567, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2099, 10567, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2100, 10568, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2101, 10568, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2102, 10568, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2103, 10569, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2104, 10569, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2105, 10569, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2106, 10570, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2107, 10570, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2108, 10570, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2109, 10571, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2110, 10571, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2111, 10571, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2112, 10572, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2113, 10572, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2114, 10572, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2115, 10573, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2116, 10573, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2117, 10573, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2118, 10574, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2119, 10574, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2120, 10574, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2121, 10575, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2122, 10575, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2123, 10575, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2124, 10576, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2125, 10576, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2126, 10576, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2127, 10577, 'PLC��ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2128, 10577, '����', '�ڶ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2129, 10577, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2364, 10581, '���ƹ���', 'KJR2202004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2365, 10581, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2366, 10581, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2367, 10581, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2368, 10581, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2369, 10581, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2370, 10581, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2371, 10581, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2372, 10581, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1793, 10601, '���ƹ���', 'KCS2209062', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1794, 10601, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1795, 10601, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1796, 10601, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1797, 10601, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1798, 10601, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1799, 10601, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2618, 10602, '���ƹ���', 'KCS2209063', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2619, 10602, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2620, 10602, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2621, 10602, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2622, 10602, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2623, 10602, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2624, 10602, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1709, 10603, '���ƹ���', 'KCS2209064', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1710, 10603, '���ƹ��ͺ�', 'CSC380-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1711, 10603, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1712, 10603, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1713, 10603, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1714, 10603, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1715, 10603, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1731, 10604, '���ƹ���', 'KCS2209065', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1732, 10604, '���ƹ��ͺ�', 'CSC380-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1733, 10604, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1734, 10604, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1735, 10604, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1736, 10604, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1737, 10604, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2469, 10605, '���ƹ���', 'KCS2209068', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2470, 10605, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2471, 10605, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2472, 10605, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2473, 10605, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2474, 10605, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2475, 10605, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2511, 10606, '���ƹ���', 'KCS2209069', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2512, 10606, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2513, 10606, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2514, 10606, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2515, 10606, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2516, 10606, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2517, 10606, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3813, 10607, '���ƹ���', 'KCS2209070', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3814, 10607, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3815, 10607, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3816, 10607, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3817, 10607, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3818, 10607, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3819, 10607, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2950, 10608, '���ƹ���', 'KCS2209071', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2951, 10608, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2952, 10608, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2953, 10608, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2954, 10608, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2955, 10608, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2956, 10608, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2278, 10609, '���ƹ���', 'KCS2209072', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2279, 10609, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2280, 10609, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2281, 10609, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2282, 10609, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2283, 10609, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2284, 10609, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2337, 10621, '���ƹ���', 'KJR2202002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2338, 10621, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2339, 10621, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2340, 10621, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2341, 10621, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2342, 10621, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2343, 10621, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2344, 10621, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2345, 10621, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3456, 10622, '���ƹ���', 'KJR2202001', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3457, 10622, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3458, 10622, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3459, 10622, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3460, 10622, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3461, 10622, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3462, 10622, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3463, 10622, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3464, 10622, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2328, 10623, '���ƹ���', 'KJR2202003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2329, 10623, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2330, 10623, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2331, 10623, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2332, 10623, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2333, 10623, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2334, 10623, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2335, 10623, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2336, 10623, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3349, 10624, '���ƹ���', 'KJR2202005', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3350, 10624, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3351, 10624, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3352, 10624, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3353, 10624, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3354, 10624, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3355, 10624, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3356, 10624, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3357, 10624, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3358, 10624, '����ʱ��', '��һ�����������塢����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2355, 10625, '���ƹ���', 'KJR2110006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2356, 10625, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2357, 10625, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2358, 10625, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2359, 10625, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2360, 10625, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2361, 10625, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2362, 10625, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2363, 10625, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2301, 10626, '���ƹ���', 'KJR2110007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2302, 10626, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2303, 10626, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2304, 10626, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2305, 10626, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2306, 10626, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2307, 10626, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2308, 10626, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2309, 10626, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2346, 10627, '���ƹ���', 'KJR2110008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2347, 10627, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2348, 10627, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2349, 10627, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2350, 10627, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2351, 10627, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2352, 10627, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2353, 10627, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2354, 10627, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2319, 10628, '���ƹ���', 'KJR2110009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2320, 10628, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2321, 10628, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2322, 10628, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2323, 10628, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2324, 10628, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2325, 10628, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2326, 10628, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2327, 10628, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2310, 10629, '���ƹ���', 'KJR2110010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2311, 10629, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2312, 10629, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2313, 10629, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2314, 10629, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2315, 10629, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2316, 10629, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2317, 10629, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2318, 10629, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3706, 10661, '���ƹ���', 'KCS2106028', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3707, 10661, '���ƹ��ͺ�', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3708, 10661, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3709, 10661, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3710, 10661, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3711, 10661, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3712, 10661, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3061, 10681, '���ƹ���', 'KCS2008008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2821, 10701, '���ƹ���', 'KJR2210070', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2822, 10701, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2823, 10701, 'IOģ���ͺ�', '������ģ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2824, 10701, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2825, 10701, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2826, 10701, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2827, 10701, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2828, 10701, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4453, 10702, '���ƹ���', 'KJR230111', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4454, 10702, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4455, 10702, 'IOģ���ͺ�', 'HJ5209P16;HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4456, 10702, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4457, 10702, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4458, 10702, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4459, 10702, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4460, 10702, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4461, 10702, 'IOģ���ַ', '192.168.2.13;192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4462, 10702, '���ƹ�CPU', '��Ƭ����������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3256, 10703, '���ƹ���', 'KJR2210072', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3257, 10703, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3258, 10703, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3259, 10703, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3260, 10703, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3261, 10703, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3262, 10703, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3263, 10703, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3446, 10704, '���ƹ���', 'KJR2210093', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3447, 10704, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3448, 10704, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3449, 10704, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3450, 10704, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3451, 10704, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3452, 10704, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3453, 10704, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3454, 10704, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3455, 10704, '����ʱ��', '�ܶ������ġ�����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3740, 10705, '���ƹ���', 'KJR2210091', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3741, 10705, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3742, 10705, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3743, 10705, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3744, 10705, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3745, 10705, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3746, 10705, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3747, 10705, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3748, 10705, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3749, 10705, '����ʱ��', '��һ�����������塢����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3339, 10706, '���ƹ���', 'KJR2210092', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3340, 10706, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3341, 10706, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3342, 10706, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3343, 10706, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3344, 10706, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3345, 10706, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3346, 10706, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3347, 10706, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3348, 10706, '����ʱ��', '�ܶ������ġ�����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3279, 10707, '���ƹ���', 'KJR2210090', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3280, 10707, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3281, 10707, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3282, 10707, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3283, 10707, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3284, 10707, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3285, 10707, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3286, 10707, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3287, 10707, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3288, 10707, '����ʱ��', '��һ�����������塢����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3289, 10708, '���ƹ���', 'KJR2210094', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3290, 10708, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3291, 10708, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3292, 10708, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3293, 10708, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3294, 10708, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3295, 10708, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3296, 10708, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3297, 10708, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3298, 10708, '����ʱ��', '�ܶ������塢����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3319, 10709, '���ƹ���', 'KJR2210095', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3320, 10709, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3321, 10709, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3322, 10709, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3323, 10709, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3324, 10709, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3325, 10709, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3326, 10709, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3327, 10709, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3328, 10709, '����ʱ��', '�ܶ������ġ�����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3309, 10721, '���ƹ���', 'KJR2210096', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3310, 10721, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3311, 10721, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3312, 10721, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3313, 10721, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3314, 10721, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3315, 10721, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3316, 10721, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3317, 10721, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3318, 10721, '����ʱ��', '����������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3883, 10722, '���ƹ���', 'KJR2210097', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3884, 10722, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3885, 10722, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3886, 10722, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3887, 10722, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3888, 10722, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3889, 10722, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3890, 10722, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3891, 10722, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3892, 10722, '����ʱ��', '�ܶ������ġ�����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3299, 10723, '���ƹ���', 'KJR2210098', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3300, 10723, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3301, 10723, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3302, 10723, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3303, 10723, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3304, 10723, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3305, 10723, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3306, 10723, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3307, 10723, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3308, 10723, '����ʱ��', '��һ������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3329, 10724, '���ƹ���', 'KJR2210099', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3330, 10724, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3331, 10724, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3332, 10724, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3333, 10724, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3334, 10724, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3335, 10724, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3336, 10724, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3337, 10724, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3338, 10724, '����ʱ��', '��һ�����������塢����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3359, 10725, '���ƹ���', 'KJR2210100', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3360, 10725, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3361, 10725, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3362, 10725, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3363, 10725, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3364, 10725, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3365, 10725, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3366, 10725, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3367, 10725, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3368, 10725, '����ʱ��', '�ܶ������ġ�����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2869, 10726, '���ƹ���', 'KJR2210101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2870, 10726, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2871, 10726, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2872, 10726, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2873, 10726, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2874, 10726, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2875, 10726, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2876, 10726, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2877, 10726, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2878, 10726, '����ʱ��', '��һ������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2859, 10727, '���ƹ���', 'KJR2210102', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2860, 10727, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2861, 10727, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2862, 10727, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2863, 10727, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2864, 10727, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2865, 10727, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2866, 10727, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2867, 10727, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2868, 10727, '����ʱ��', '��һ������������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3269, 10728, '���ƹ���', 'KJR2210103', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3270, 10728, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3271, 10728, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3272, 10728, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3273, 10728, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3274, 10728, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3275, 10728, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3276, 10728, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3277, 10728, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3278, 10728, '����ʱ��', '��һ�����������塢����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2839, 10729, '���ƹ���', 'KJR2210104', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2840, 10729, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2841, 10729, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2842, 10729, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2843, 10729, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2844, 10729, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2845, 10729, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2846, 10729, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2847, 10729, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2848, 10729, '��������', '����.��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3180, 10730, '���ƹ���', 'KJR2210105', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3181, 10730, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3182, 10730, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3183, 10730, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3184, 10730, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3185, 10730, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3186, 10730, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3187, 10730, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3188, 10730, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3189, 10730, '����ʱ��', '�ܶ������ġ�����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3722, 10731, '���ƹ���', 'KJR2210106', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3723, 10731, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3724, 10731, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3725, 10731, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3726, 10731, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3727, 10731, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3728, 10731, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3729, 10731, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3730, 10731, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2849, 10732, '���ƹ���', 'KJR2210107', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2850, 10732, '���ƹ��ͺ�', 'JCR660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2851, 10732, '�������ͺ�', 'SHϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2852, 10732, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2853, 10732, 'IOģ���ͺ�', 'VM5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2854, 10732, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2855, 10732, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2856, 10732, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2857, 10732, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2858, 10732, '��������', '����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1843, 10741, '���ƹ���', 'KJR2001009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1844, 10741, '���ƹ��ͺ�', 'JRC380-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1845, 10741, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1846, 10741, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1847, 10741, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1848, 10741, 'PLC��ַ', '192.168.6.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1849, 10741, 'HMI��ַ', '192.168.6.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1850, 10741, 'IOģ���ַ', '192.168.6.4', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1851, 10741, '�ź����ַ', '192.168.6.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1852, 10741, '4Gģ���ַ', '192.168.6.20/192.168.6.101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2570, 10765, '���ƹ���', 'KCS2210073', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2571, 10765, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2572, 10765, '��Ƶ���ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2573, 10765, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2574, 10765, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2575, 10765, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2576, 10765, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2019, 10766, '���ƹ���', 'KCS2210074', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2020, 10766, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2021, 10766, '��Ƶ���ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2022, 10766, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2023, 10766, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2024, 10766, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2025, 10766, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2611, 10767, '���ƹ���', 'KCS2210075', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2612, 10767, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2613, 10767, '��Ƶ���ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2614, 10767, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2615, 10767, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2616, 10767, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2617, 10767, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2563, 10768, '���ƹ���', 'KCS2210076', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2564, 10768, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2565, 10768, '��Ƶ���ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2566, 10768, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2567, 10768, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2568, 10768, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2569, 10768, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2143, 10769, '���ƹ���', 'KCS2210077', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2144, 10769, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2145, 10769, '��Ƶ���ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2146, 10769, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2147, 10769, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2148, 10769, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2149, 10769, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2150, 10770, '���ƹ���', 'KCS2210078', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2151, 10770, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2152, 10770, '��Ƶ���ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2153, 10770, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2154, 10770, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2155, 10770, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2156, 10770, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2176, 10771, '���ƹ���', 'KCS2210079', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2177, 10771, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2178, 10771, '��Ƶ���ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2179, 10771, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2180, 10771, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2181, 10771, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2182, 10771, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3644, 10772, '���ƹ���', 'KCS2210080', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3645, 10772, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3646, 10772, '��Ƶ���ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3647, 10772, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3648, 10772, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3649, 10772, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3650, 10772, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1966, 10781, '���ƹ���', 'KCS2005003(KCS2005001)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1755, 10782, '���ƹ���', 'KCS2005003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1756, 10783, '���ƹ���', 'G2019112801', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1975, 10841, '���ƹ���', 'KCS2106024', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (1976, 10841, '���ƹ��ͺ�', 'CSC660-45', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4341, 10861, '���ƹ���', 'KJR2211113', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4342, 10861, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4343, 10861, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4344, 10861, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4345, 10861, 'IOģ���ͺ�', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4346, 10861, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4347, 10861, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4348, 10861, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4349, 10861, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4350, 10861, '��������', 'ȫ����/HJ����', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3820, 10862, '���ƹ���', 'KJR2211114', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3821, 10862, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3822, 10862, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3823, 10862, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3824, 10862, 'IOģ���ͺ�', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3825, 10862, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3826, 10862, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3827, 10862, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3828, 10862, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4351, 10863, '���ƹ���', 'KJR2211115', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4352, 10863, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4353, 10863, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4354, 10863, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4355, 10863, 'IOģ���ͺ�', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4356, 10863, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4357, 10863, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4358, 10863, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4359, 10863, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4360, 10863, '��������', '��Ӧ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2933, 10864, '���ƹ���', 'KJR2211116', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2934, 10864, '���ƹ��ͺ�', 'JRC400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2935, 10864, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2936, 10864, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2937, 10864, 'IOģ���ͺ�', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2938, 10864, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2939, 10864, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2940, 10864, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2941, 10864, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3078, 10881, '���ƹ���', 'KJR2301118', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3079, 10881, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3080, 10881, 'IOģ���ͺ�', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3081, 10881, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3082, 10881, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3083, 10881, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3084, 10881, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3085, 10881, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3086, 10881, 'IOģ���ַ', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2887, 10882, '���ƹ���', 'KJR2301119', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2888, 10882, '���ƹ��ͺ�', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2889, 10882, 'IOģ���ͺ�', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2890, 10882, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2891, 10882, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2892, 10882, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2893, 10882, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2894, 10882, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2895, 10882, 'IOģ���ַ', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2731, 10883, '���ƹ���', 'KJR2301120', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2732, 10883, '���ƹ��ͺ�', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2733, 10883, 'IOģ���ͺ�', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2734, 10883, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2735, 10883, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2736, 10883, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2737, 10883, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2738, 10883, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2739, 10883, 'IOģ���ַ', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2292, 10901, '���ƹ���', 'KJR2211117', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2293, 10901, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2294, 10901, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2295, 10901, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2296, 10901, 'IOģ���ͺ�', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2297, 10901, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2298, 10901, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2299, 10901, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2300, 10901, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3096, 10902, '���ƹ���', 'KJR2303121', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3097, 10902, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3098, 10902, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3099, 10902, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3100, 10902, 'IOģ���ͺ�', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3101, 10902, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3102, 10902, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3103, 10902, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3104, 10902, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3105, 10903, '���ƹ���', 'KJR2303122', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3106, 10903, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3107, 10903, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3108, 10903, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3109, 10903, 'IOģ���ͺ�', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3110, 10903, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3111, 10903, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3112, 10903, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3113, 10903, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3114, 10904, '���ƹ���', 'KJR2303123', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3115, 10904, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3116, 10904, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3117, 10904, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3118, 10904, 'IOģ���ͺ�', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3119, 10904, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3120, 10904, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3121, 10904, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3122, 10904, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3123, 10905, '���ƹ���', 'KJR2303124', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3124, 10905, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3125, 10905, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3126, 10905, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3127, 10905, 'IOģ���ͺ�', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3128, 10905, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3129, 10905, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3130, 10905, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3131, 10905, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3132, 10906, '���ƹ���', 'KJR2303125', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3133, 10906, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3134, 10906, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3135, 10906, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3136, 10906, 'IOģ���ͺ�', 'HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3137, 10906, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3138, 10906, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3139, 10906, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3140, 10906, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2722, 10921, '���ƹ���', 'KJR2302126', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2723, 10921, '���ƹ��ͺ�', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2724, 10921, 'IOģ���ͺ�', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2725, 10921, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2726, 10921, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2727, 10921, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2728, 10921, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2729, 10921, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2730, 10921, 'IOģ���ַ', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3629, 10961, '���ƹ���', 'KJR2303128', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3630, 10961, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3631, 10961, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3632, 10961, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3633, 10961, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2996, 10962, '���ƹ���', 'KJR2303129', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2997, 10962, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2998, 10962, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2999, 10962, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3000, 10962, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3001, 10963, '���ƹ���', 'KJR2303130', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3002, 10963, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3003, 10963, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3004, 10963, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3005, 10963, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3006, 10964, '���ƹ���', 'KJR2303131', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3007, 10964, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3008, 10964, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3009, 10964, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3010, 10964, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3011, 10965, '���ƹ���', 'KJR2303132', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3012, 10965, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3013, 10965, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3014, 10965, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3015, 10965, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3016, 10966, '���ƹ���', 'KJR2303133', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3017, 10966, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3018, 10966, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3019, 10966, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3020, 10966, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3639, 10967, '���ƹ���', 'KJR2303134', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3640, 10967, '���ƹ��ͺ�', 'JRC380-STM-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3641, 10967, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3642, 10967, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3643, 10967, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3634, 10968, '���ƹ���', 'KJR2303135', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3635, 10968, '���ƹ��ͺ�', 'JRC380-STM-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3636, 10968, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3637, 10968, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3638, 10968, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3750, 10969, '���ƹ���', 'KJR2303136', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3751, 10969, '���ƹ��ͺ�', 'JRC380-STM-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3752, 10969, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3753, 10969, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3754, 10969, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3021, 10970, '���ƹ���', 'KJR2303137', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3022, 10970, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3023, 10970, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3024, 10970, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3025, 10970, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3264, 10971, '���ƹ���', 'KJR2303138', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3265, 10971, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3266, 10971, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3267, 10971, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3268, 10971, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3026, 10972, '���ƹ���', 'KJR2303139', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3027, 10972, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3028, 10972, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3029, 10972, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3030, 10972, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3031, 10973, '���ƹ���', 'KJR2303140', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3032, 10973, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3033, 10973, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3034, 10973, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3035, 10973, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3036, 10974, '���ƹ���', 'KJR2303141', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3037, 10974, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3038, 10974, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3039, 10974, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3040, 10974, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3041, 10975, '���ƹ���', 'KJR2303142', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3042, 10975, '���ƹ��ͺ�', 'JRC400-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3043, 10975, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3044, 10975, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3045, 10975, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2797, 10981, '���ƹ���', 'KCS2303081', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2798, 10981, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2799, 10981, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2800, 10981, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2801, 10981, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2802, 10981, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2803, 10981, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2804, 10981, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2805, 10982, '���ƹ���', 'KCS2303082', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2806, 10982, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2807, 10982, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2808, 10982, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2809, 10982, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2810, 10982, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2811, 10982, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2812, 10982, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2476, 10983, '���ƹ���', 'KCS2303083', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2477, 10983, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2478, 10983, '��Ƶ���ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2479, 10983, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2480, 10983, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2481, 10983, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2482, 10983, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2483, 10983, '���»���', '22kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2547, 10984, '���ƹ���', 'KCS2303084', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2548, 10984, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2549, 10984, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2550, 10984, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2551, 10984, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2552, 10984, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2553, 10984, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2554, 10984, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4361, 10985, '���ƹ���', 'KCS2303085', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4362, 10985, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4363, 10985, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4364, 10985, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4365, 10985, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4366, 10985, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4367, 10985, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4368, 10985, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2663, 10986, '���ƹ���', 'KCS2303086', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2664, 10986, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2665, 10986, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2666, 10986, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2667, 10986, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2668, 10986, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2669, 10986, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2670, 10986, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2942, 10987, '���ƹ���', 'KCS2303087', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2943, 10987, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2944, 10987, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2945, 10987, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2946, 10987, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2947, 10987, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2948, 10987, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2949, 10987, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2595, 10988, '���ƹ���', 'KCS2303088', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2596, 10988, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2597, 10988, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2598, 10988, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2599, 10988, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2600, 10988, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2601, 10988, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2602, 10988, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2411, 10989, '���ƹ���', 'KCS2303089', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2412, 10989, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2413, 10989, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2414, 10989, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2415, 10989, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2416, 10989, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2417, 10989, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2418, 10989, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2603, 10990, '���ƹ���', 'KCS2303090', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2604, 10990, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2605, 10990, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2606, 10990, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2607, 10990, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2608, 10990, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2609, 10990, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2610, 10990, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2389, 11001, '���ƹ���', 'KCS2303091', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2390, 11001, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2391, 11001, '�����ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2392, 11001, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2393, 11001, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2394, 11001, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2395, 11001, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2396, 11001, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2577, 11002, '���ƹ���', 'KCS2303092', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2578, 11002, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2579, 11002, '�����ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2580, 11002, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2581, 11002, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2582, 11002, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2583, 11002, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2584, 11002, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2925, 11003, '���ƹ���', 'KCS2303093', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2926, 11003, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2927, 11003, '�����ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2928, 11003, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2929, 11003, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2930, 11003, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2931, 11003, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2932, 11003, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2639, 11004, '���ƹ���', 'KCS2303094', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2640, 11004, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2641, 11004, '�����ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2642, 11004, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2643, 11004, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2644, 11004, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2645, 11004, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2646, 11004, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2539, 11005, '���ƹ���', 'KCS2303095', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2540, 11005, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2541, 11005, '�����ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2542, 11005, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2543, 11005, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2544, 11005, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2545, 11005, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2546, 11005, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3221, 11006, '���ƹ���', 'KJR2303143', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3222, 11006, '���ƹ��ͺ�', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3223, 11006, 'IOģ���ͺ�', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3224, 11006, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3225, 11006, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3226, 11006, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3227, 11006, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3228, 11006, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3229, 11006, 'IOģ���ַ', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3247, 11007, '���ƹ���', 'KJR2303144', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3248, 11007, '���ƹ��ͺ�', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3249, 11007, 'IOģ���ͺ�', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3250, 11007, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3251, 11007, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3252, 11007, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3253, 11007, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3254, 11007, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3255, 11007, 'IOģ���ַ', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3087, 11008, '���ƹ���', 'KJR2303145', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3088, 11008, '���ƹ��ͺ�', 'JRC380-150-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3089, 11008, 'IOģ���ͺ�', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3090, 11008, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3091, 11008, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3092, 11008, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3093, 11008, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3094, 11008, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3095, 11008, 'IOģ���ַ', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3238, 11009, '���ƹ���', 'KJR2303146', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3239, 11009, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3240, 11009, 'IOģ���ͺ�', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3241, 11009, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3242, 11009, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3243, 11009, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3244, 11009, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3245, 11009, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3246, 11009, 'IOģ���ַ', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3829, 11010, '���ƹ���', 'KJR2303147', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3830, 11010, '���ƹ��ͺ�', 'JRC380-65-G1X2W3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3831, 11010, 'IOģ���ͺ�', 'HJ5209P16/HJ5209A', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3832, 11010, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3833, 11010, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3834, 11010, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3835, 11010, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3836, 11010, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3837, 11010, 'IOģ���ַ', '192.168.2.13/192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2419, 11021, '���ƹ���', 'KJR2110006', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2420, 11021, '���ƹ��ͺ�', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2421, 11021, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2422, 11021, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2423, 11021, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2424, 11021, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2425, 11021, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2426, 11021, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2427, 11021, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2428, 11021, '����ʱ��', '����������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2429, 11022, '���ƹ���', 'KJR2110008', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2430, 11022, '���ƹ��ͺ�', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2431, 11022, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2432, 11022, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2433, 11022, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2434, 11022, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2435, 11022, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2436, 11022, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2437, 11022, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2438, 11022, '����ʱ��', '�ܶ�������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2439, 11023, '���ƹ���', 'KJR2110009', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2440, 11023, '���ƹ��ͺ�', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2441, 11023, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2442, 11023, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2443, 11023, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2444, 11023, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2445, 11023, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2446, 11023, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2447, 11023, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2448, 11023, '����ʱ��', '��һ������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2449, 11024, '���ƹ���', 'KJR2110007', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2450, 11024, '���ƹ��ͺ�', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2451, 11024, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2452, 11024, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2453, 11024, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2454, 11024, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2455, 11024, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2456, 11024, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2457, 11024, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2458, 11024, '����ʱ��', '�ܶ�������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2459, 11025, '���ƹ���', 'KJR2110010', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2460, 11025, '���ƹ��ͺ�', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2461, 11025, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2462, 11025, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2463, 11025, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2464, 11025, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2465, 11025, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2466, 11025, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2467, 11025, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2468, 11025, '����ʱ��', '����������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2829, 11026, '���ƹ���', 'KJR2202003', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2830, 11026, '���ƹ��ͺ�', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2831, 11026, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2832, 11026, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2833, 11026, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2834, 11026, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2835, 11026, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2836, 11026, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2837, 11026, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2838, 11026, '����ʱ��', '��һ������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3193, 11027, '���ƹ���', 'KJR2202002', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3194, 11027, '���ƹ��ͺ�', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3195, 11027, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3196, 11027, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3197, 11027, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3198, 11027, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3199, 11027, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3200, 11027, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3201, 11027, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3202, 11027, '��������', '��һ��������', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3203, 11028, '���ƹ���', 'KJR2202004', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3204, 11028, '���ƹ��ͺ�', 'JRC660-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3205, 11028, 'IOģ���ͺ�', 'VM3209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3206, 11028, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3207, 11028, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3208, 11028, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3209, 11028, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3210, 11028, 'IOģ���ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3211, 11028, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3212, 11028, '��������', '�ܶ����ġ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2585, 11042, '���ƹ���', 'KJR2306157', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2586, 11042, '���ƹ��ͺ�', 'JRC380-200-G1J1(����ֹ���)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2587, 11042, 'IOģ���ͺ�', 'HJ5209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2588, 11042, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2589, 11042, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2590, 11042, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2591, 11042, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2592, 11042, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2593, 11042, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2594, 11042, '�ܳ�', '450M', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3408, 11061, '���ƹ���', 'KJR2304153', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3409, 11061, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3410, 11061, 'IOģ���ͺ�', 'HJ5209A/HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3411, 11061, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3412, 11061, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3413, 11061, 'PLC��ַ', '192.168.2.8', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3414, 11061, 'HMI��ַ', '192.168.2.14', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3415, 11061, 'IOģ���ַ', '192.168.2.22/192.168.2.23', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3416, 11061, '4Gģ���ַ', '192.168.2.19', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3417, 11061, '�ź���', '���ߴ���', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2492, 11062, '���ƹ���', 'KJR2304154', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2493, 11062, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2494, 11062, 'IOģ���ͺ�', 'HJ5209A/HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2495, 11062, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2496, 11062, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2497, 11062, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2498, 11062, 'HMI��ַ', '192.168.2.12', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2499, 11062, 'IOģ���ַ', '192.168.2.15/192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2500, 11062, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3399, 11063, '���ƹ���', 'KJR2304155', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3400, 11063, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3401, 11063, 'IOģ���ͺ�', 'HJ5209A/HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3402, 11063, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3403, 11063, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3404, 11063, 'PLC��ַ', '192.168.2.7', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3405, 11063, 'HMI��ַ', '192.168.2.13', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3406, 11063, 'IOģ���ַ', '192.168.2.20/192.168.2.21', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3407, 11063, '4Gģ���ַ', '192.168.2.18', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3046, 11081, '���ƹ���', 'KCS2309099', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3047, 11081, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3048, 11081, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3049, 11081, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3050, 11081, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3051, 11081, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3052, 11081, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3053, 11081, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4369, 11084, '���ƹ���', 'KCS2209071', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4370, 11084, '���ƹ��ͺ�', 'CSC660-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4371, 11084, '��Ƶ���ͺ�', 'Ӣ����GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4372, 11084, '4Gģ���ͺ�', 'PLCNET510', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4373, 11084, 'PLC��ַ', '192.168.8.2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4374, 11084, 'HMI��ַ', '192.168.8.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4375, 11084, '4Gģ���ַ', '192.168.8.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3846, 11085, '���ƹ���', 'KCS2303092', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3847, 11085, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3848, 11085, '�����ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3849, 11085, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3850, 11085, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3851, 11085, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3852, 11085, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3853, 11085, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2695, 11101, '���ƹ���', 'KCS2309102', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2696, 11101, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2697, 11101, '�����ͺ�', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2698, 11101, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2699, 11101, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2700, 11101, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2701, 11101, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2702, 11101, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4423, 11102, '���ƹ���', 'KCS2309097', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4424, 11102, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4425, 11102, '�����ͺ�', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4426, 11102, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4427, 11102, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4428, 11102, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4429, 11102, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4430, 11102, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4431, 11102, '��ע', '��103����Ϊ097', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2756, 11103, '���ƹ���', 'KCS2309104', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2757, 11103, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2758, 11103, '�����ͺ�', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2759, 11103, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2760, 11103, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2761, 11103, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2762, 11103, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2763, 11103, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3789, 11104, '���ƹ���', 'KCS2309105', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3790, 11104, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3791, 11104, '�����ͺ�', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3792, 11104, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3793, 11104, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3794, 11104, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3795, 11104, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3796, 11104, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2703, 11105, '���ƹ���', 'KCS2309106', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2704, 11105, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2705, 11105, '�����ͺ�', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2706, 11105, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2707, 11105, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2708, 11105, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2709, 11105, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2710, 11105, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2879, 11106, '���ƹ���', 'KCS2309107', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2880, 11106, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2881, 11106, '�����ͺ�', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2882, 11106, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2883, 11106, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2884, 11106, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2885, 11106, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2886, 11106, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3773, 11107, '���ƹ���', 'KCS2309108', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3774, 11107, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3775, 11107, '�����ͺ�', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3776, 11107, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3777, 11107, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3778, 11107, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3779, 11107, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3780, 11107, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3765, 11108, '���ƹ���', 'KCS2309109', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3766, 11108, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3767, 11108, '�����ͺ�', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3768, 11108, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3769, 11108, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3770, 11108, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3771, 11108, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3772, 11108, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2679, 11121, '���ƹ���', 'KCS2309095', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2680, 11121, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2681, 11121, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2682, 11121, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2683, 11121, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2684, 11121, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2685, 11121, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2686, 11121, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2789, 11122, '���ƹ���', 'KCS2309096', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2790, 11122, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2791, 11122, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2792, 11122, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2793, 11122, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2794, 11122, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2795, 11122, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2796, 11122, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4432, 11123, '���ƹ���', 'KCS2309097', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4433, 11123, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4434, 11123, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4435, 11123, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4436, 11123, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4437, 11123, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4438, 11123, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4439, 11123, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3157, 11124, '���ƹ���', 'KCS2309098', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3158, 11124, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3159, 11124, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3160, 11124, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3161, 11124, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3162, 11124, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3163, 11124, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3164, 11124, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2957, 11125, '���ƹ���', 'KCS2309099', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2958, 11125, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2959, 11125, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2960, 11125, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2961, 11125, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2962, 11125, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2963, 11125, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2964, 11125, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2748, 11126, '���ƹ���', 'KCS2309100', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2749, 11126, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2750, 11126, '�����ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2751, 11126, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2752, 11126, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2753, 11126, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2754, 11126, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2755, 11126, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2671, 11127, '���ƹ���', 'KCS2309101', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2672, 11127, '���ƹ��ͺ�', 'CSC660-SS-37', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2673, 11127, '�����ͺ�', 'NJR5-44/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2674, 11127, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2675, 11127, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2676, 11127, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2677, 11127, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2678, 11127, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3781, 11161, '���ƹ���', 'KCS2311110', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3782, 11161, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3783, 11161, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3784, 11161, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3785, 11161, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3786, 11161, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3787, 11161, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3788, 11161, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3062, 11162, '���ƹ���', 'KCS2311111', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3063, 11162, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3064, 11162, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3065, 11162, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3066, 11162, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3067, 11162, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3068, 11162, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3069, 11162, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2979, 11163, '���ƹ���', 'KCS2311112', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2980, 11163, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2981, 11163, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2982, 11163, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2983, 11163, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2984, 11163, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2985, 11163, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2986, 11163, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3141, 11164, '���ƹ���', 'KCS2311113', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3142, 11164, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3143, 11164, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3144, 11164, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3145, 11164, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3146, 11164, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3147, 11164, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3148, 11164, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3070, 11165, '���ƹ���', 'KCS2311114', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3071, 11165, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3072, 11165, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3073, 11165, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3074, 11165, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3075, 11165, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3076, 11165, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3077, 11165, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2972, 11181, '���ƹ���', 'KCS2311115', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2973, 11181, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2974, 11181, '��Ƶ���ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2975, 11181, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2976, 11181, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2977, 11181, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (2978, 11181, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3213, 11182, '���ƹ���', 'KCS2311116', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3214, 11182, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3215, 11182, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3216, 11182, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3217, 11182, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3218, 11182, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3219, 11182, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3220, 11182, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3172, 11183, '���ƹ���', 'KCS2311117', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3173, 11183, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3174, 11183, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3175, 11183, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3176, 11183, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3177, 11183, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3178, 11183, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3179, 11183, '���»���', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3838, 11201, '���ƹ���', 'KCS2312123', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3839, 11201, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3840, 11201, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3841, 11201, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3842, 11201, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3843, 11201, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3844, 11201, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3845, 11201, '���»���', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3493, 11202, '���ƹ���', 'KCS2312124', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3494, 11202, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3495, 11202, '�����ͺ�', 'NJR5-74/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3496, 11202, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3497, 11202, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3498, 11202, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3499, 11202, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3500, 11202, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3230, 11203, '���ƹ���', 'KCS2312125', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3231, 11203, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3232, 11203, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3233, 11203, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3234, 11203, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3235, 11203, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3236, 11203, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3237, 11203, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3565, 11221, '���ƹ���', 'KCS2312118', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3566, 11221, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3567, 11221, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3568, 11221, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3569, 11221, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3570, 11221, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3571, 11221, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3572, 11221, '���»���', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3573, 11222, '���ƹ���', 'KCS2312119', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3574, 11222, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3575, 11222, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3576, 11222, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3577, 11222, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3578, 11222, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3579, 11222, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3580, 11222, '���»���', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3581, 11223, '���ƹ���', 'KCS2312120', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3582, 11223, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3583, 11223, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3584, 11223, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3585, 11223, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3586, 11223, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3587, 11223, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3588, 11223, '���»���', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3798, 11224, '���ƹ���', 'KCS2312121', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3799, 11224, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3800, 11224, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3801, 11224, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3802, 11224, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3803, 11224, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3804, 11224, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3805, 11224, '���»���', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3589, 11225, '���ƹ���', 'KCS2312122', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3590, 11225, '���ƹ��ͺ�', 'CSC660-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3591, 11225, '�����ͺ�', 'NJR5-60/ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3592, 11225, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3593, 11225, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3594, 11225, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3595, 11225, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3596, 11225, '���»���', '63kW', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4403, 11261, '���ƹ���', 'KJR2312163', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4404, 11261, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4405, 11261, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4406, 11261, '�������ͺ�', 'THϵ��(��)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4407, 11261, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4408, 11261, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4409, 11261, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4410, 11261, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4411, 11261, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4412, 11261, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4413, 11262, '���ƹ���', 'KJR2312164', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4414, 11262, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4415, 11262, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4416, 11262, '�������ͺ�', 'THϵ��(��)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4417, 11262, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4418, 11262, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4419, 11262, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4420, 11262, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4421, 11262, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4422, 11262, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3525, 11263, '���ƹ���', 'KJR2312165', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3526, 11263, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3527, 11263, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3528, 11263, '�������ͺ�', 'THϵ��(��)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3529, 11263, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3530, 11263, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3531, 11263, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3532, 11263, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3533, 11263, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3534, 11263, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3683, 11281, '���ƹ���', 'KCS2401126', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3684, 11281, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3685, 11281, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3686, 11281, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3687, 11281, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3688, 11281, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3689, 11281, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3690, 11281, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3597, 11282, '���ƹ���', 'KCS2401127', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3598, 11282, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3599, 11282, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3600, 11282, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3601, 11282, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3602, 11282, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3603, 11282, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3604, 11282, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3605, 11283, '���ƹ���', 'KCS2401128', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3606, 11283, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3607, 11283, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3608, 11283, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3609, 11283, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3610, 11283, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3611, 11283, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3612, 11283, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3613, 11284, '���ƹ���', 'KCS2401129', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3614, 11284, '���ƹ��ͺ�', 'CSC660-SS-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3615, 11284, '�����ͺ�', 'NJR5-60ZX6', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3616, 11284, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3617, 11284, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3618, 11284, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3619, 11284, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3620, 11284, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3545, 11301, '���ƹ���', 'KJR2312167', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3546, 11301, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3547, 11301, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3548, 11301, '�������ͺ�', 'THϵ��(��)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3549, 11301, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3550, 11301, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3551, 11301, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3552, 11301, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3553, 11301, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3554, 11301, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4393, 11302, '���ƹ���', 'KJR2312168', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4394, 11302, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4395, 11302, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4396, 11302, '�������ͺ�', 'THϵ��(��)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4397, 11302, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4398, 11302, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4399, 11302, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4400, 11302, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4401, 11302, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4402, 11302, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3501, 11321, '���ƹ���', 'KJR2401166', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3502, 11321, '���ƹ��ͺ�', 'JRC660-STM-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3503, 11321, '�������ͺ�', 'SH300ϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3504, 11321, 'PLC��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3505, 11321, 'HMI��ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3506, 11361, '���ƹ���', 'KJR2112082', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3507, 11361, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3508, 11361, 'IOģ���ͺ�', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3509, 11361, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3510, 11361, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3511, 11361, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3512, 11361, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3513, 11361, 'IOģ���ַ', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3514, 11361, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3658, 11362, '���ƹ���', 'KJR2112083', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3659, 11362, '���ƹ��ͺ�', 'JCR400-G1T2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3660, 11362, '�������ͺ�', 'THϵ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3661, 11362, '4Gģ���ͺ�', 'PLCNET210', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3662, 11362, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3663, 11362, 'HMI��ַ', '192.168.2.5', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3664, 11362, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3961, 11363, '���ƹ���', 'KJR2402169', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3962, 11363, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3963, 11363, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3964, 11363, '�������ͺ�', 'THϵ��(��)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3965, 11363, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3966, 11363, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3967, 11363, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3968, 11363, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3969, 11363, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3970, 11363, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3971, 11364, '���ƹ���', 'KJR2402170', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3972, 11364, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3973, 11364, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3974, 11364, '�������ͺ�', 'THϵ��(��)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3975, 11364, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3976, 11364, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3977, 11364, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3978, 11364, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3979, 11364, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3980, 11364, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3981, 11365, '���ƹ���', 'KJR2402171', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3982, 11365, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3983, 11365, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3984, 11365, '�������ͺ�', 'THϵ��(��)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3985, 11365, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3986, 11365, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3987, 11365, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3988, 11365, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3989, 11365, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3990, 11365, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3991, 11366, '���ƹ���', 'KJR2402172', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3992, 11366, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3993, 11366, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3994, 11366, '�������ͺ�', 'THϵ��(��)', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3995, 11366, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3996, 11366, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3997, 11366, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3998, 11366, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3999, 11366, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4000, 11366, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4376, 11381, '���ƹ���', 'KCS2404130', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4377, 11381, '���ƹ��ͺ�', 'CSC660-75', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4378, 11381, '��Ƶ���ͺ�', 'GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4379, 11381, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4380, 11381, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4381, 11381, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4382, 11381, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4383, 11381, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3665, 11382, '���ƹ���', 'KCS2404131', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3666, 11382, '���ƹ��ͺ�', 'CSC660-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3667, 11382, '��Ƶ���ͺ�', 'GD350', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3668, 11382, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3669, 11382, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3670, 11382, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3671, 11382, '4Gģ���ַ', '192.168.2.16', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (3672, 11382, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4188, 11421, '���ƹ���', 'KCS2404132', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4189, 11421, '���ƹ��ͺ�', 'CSC380-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4190, 11421, '�����ͺ�', 'NJR5-110/ZX3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4191, 11421, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4192, 11421, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4193, 11421, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4194, 11421, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4195, 11421, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4196, 11421, '����', 'ST30', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4197, 11461, '���ƹ���', 'KCS2404133', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4198, 11461, '���ƹ��ͺ�', 'CSC380-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4199, 11461, '�����ͺ�', 'NJR5-110/ZX3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4200, 11461, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4201, 11461, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4202, 11461, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4203, 11461, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4204, 11461, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4205, 11461, '����', 'ST30', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4206, 11462, '���ƹ���', 'KCS2404134', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4207, 11462, '���ƹ��ͺ�', 'CSC380-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4208, 11462, '�����ͺ�', 'NJR5-110/ZX3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4209, 11462, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4210, 11462, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4211, 11462, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4212, 11462, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4213, 11462, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4214, 11462, '����', 'ST30', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4215, 11463, '���ƹ���', 'KCS2404135', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4216, 11463, '���ƹ��ͺ�', 'CSC380-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4217, 11463, '�����ͺ�', 'NJR5-110/ZX3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4218, 11463, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4219, 11463, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4220, 11463, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4221, 11463, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4222, 11463, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4223, 11463, '����', 'ST30', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4384, 11464, '���ƹ���', 'KCS2404136', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4385, 11464, '���ƹ��ͺ�', 'CSC380-SS-55', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4386, 11464, '�����ͺ�', 'NJR5-110/ZX3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4387, 11464, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4388, 11464, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4389, 11464, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4390, 11464, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4391, 11464, '���»���', null, null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4392, 11464, '����', 'ST30', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4166, 11501, '���ƹ���', 'KJR2407275', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4167, 11501, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4168, 11501, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4169, 11501, '�������ͺ�', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4170, 11501, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4171, 11501, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4172, 11501, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4173, 11501, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4174, 11501, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4175, 11501, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4176, 11501, '����', 'ST30/G3C�ز�ģ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4155, 11502, '���ƹ���', 'KJR2407276', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4156, 11502, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4157, 11502, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4158, 11502, '�������ͺ�', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4159, 11502, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4160, 11502, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4161, 11502, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4162, 11502, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4163, 11502, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4164, 11502, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4165, 11502, '����', 'ST30/G3C�ز�ģ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4144, 11503, '���ƹ���', 'KJR2407277', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4145, 11503, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4146, 11503, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4147, 11503, '�������ͺ�', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4148, 11503, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4149, 11503, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4150, 11503, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4151, 11503, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4152, 11503, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4153, 11503, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4154, 11503, '����', 'ST30/G3C�ز�ģ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4133, 11504, '���ƹ���', 'KJR2407278', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4134, 11504, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4135, 11504, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4136, 11504, '�������ͺ�', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4137, 11504, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4138, 11504, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4139, 11504, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4140, 11504, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4141, 11504, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4142, 11504, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4143, 11504, '����', 'ST30/G3C�ز�ģ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4122, 11505, '���ƹ���', 'KJR2407279', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4123, 11505, '���ƹ��ͺ�', 'JRC380-150-G1J1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4124, 11505, 'IOģ���ͺ�', 'HJ3209D', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4125, 11505, '�������ͺ�', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4126, 11505, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4127, 11505, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4128, 11505, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4129, 11505, 'IOģ���ַ', '192.168.2.17', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4130, 11505, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4131, 11505, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4132, 11505, '����', 'ST30/G3C�ز�ģ��', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4285, 11506, '���ƹ���', 'KJR2407280', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4286, 11506, '���ƹ��ͺ�', 'JRC660-150-G2J2', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4287, 11506, 'IOģ���ͺ�', 'HJ3209D/HJ5209P', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4288, 11506, '�������ͺ�', 'SH300T', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4289, 11506, '4Gģ���ͺ�', 'HJ8300', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4290, 11506, 'PLC��ַ', '192.168.2.3', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4291, 11506, 'HMI��ַ', '192.168.2.10', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4292, 11506, 'IOģ���ַ', '192.168.2.17/192.168.2.18', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4293, 11506, '�ź����ַ', '����ͨѶ', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4294, 11506, '4Gģ���ַ', '192.168.2.1', null);

insert into tbl_deviceaddinfo (ID, DEVICEID, ITEMNAME, ITEMVALUE, ITEMUNIT)
values (4295, 11506, '����', 'S71200/G3C�ز�ģ��', null);

commit;

ALTER TRIGGER trg_b_org_i_u ENABLE;
ALTER TRIGGER trg_b_device_i ENABLE;
ALTER TRIGGER trg_b_auxiliary2master_i ENABLE;
ALTER TRIGGER trg_b_DEVICEADDINFO_i ENABLE;

exit;