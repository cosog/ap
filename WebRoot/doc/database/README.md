**敏捷生产 V7.6**

**数据库手册**

**北京科斯奇石油科技有限公司 制作**

**目 录**

[一、表	1](#_Toc79482499)

>   [1.1 概览	1](#_Toc79482500)

>   [1.2 逻辑结构	3](#_Toc79482501)

>   [1.3 详述	4](#_Toc79482502)

>   [1.3.1 tbl_org	4](#_Toc79482503)

>   [1.3.2 tbl_user	5](#_Toc79482504)

>   [1.3.3 tbl_role	6](#_Toc79482505)

>   [1.3.4 tbl_module	6](#_Toc79482506)

>   [1.3.5 tbl_module2role	7](#_Toc79482507)

>   [1.3.6 tbl_dist_name	7](#_Toc79482508)

>   [1.3.7 tbl_dist_item	8](#_Toc79482509)

>   [1.3.8 tbl_code	8](#_Toc79482510)

>   [1.3.9 tbl_acq_unit_conf	9](#_Toc79482511)

>   [1.3.10 tbl_acq_group_conf	9](#_Toc79482512)

>   [1.3.11 tbl_acq_item2group_conf	10](#_Toc79482513)

>   [1.3.12 tbl_acq_group2unit_conf	10](#_Toc79482514)

>   [1.3.13 tbl_wellinformation	11](#_Toc79482515)

>   [1.3.14 tbl_wellboretrajectory	12](#_Toc79482516)

>   [1.3.15 tbl_rpc_productiondata_latest	12](#_Toc79482517)

>   [1.3.16 tbl_rpc_productiondata_hist	14](#_Toc79482518)

>   [1.3.17 tbl_rpc_discrete_latest	14](#_Toc79482519)

>   [1.3.18 tbl_rpc_discrete_hist	18](#_Toc79482520)

>   [1.3.19 tbl_rpc_diagram_latest	18](#_Toc79482521)

>   [1.3.20 tbl_rpc_diagram_hist	22](#_Toc79482522)

>   [1.3.21 tbl_rpc_diagram_total	22](#_Toc79482523)

>   [1.3.22 tbl_rpc_worktype	23](#_Toc79482524)

>   [1.3.23 tbl_rpc_alarmtype_conf	25](#_Toc79482525)

>   [1.3.24 tbl_rpc_total_day	25](#_Toc79482526)

>   [1.3.25 tbl_rpc_statistics_conf	36](#_Toc79482527)

>   [1.3.26 tbl_rpcinformation	37](#_Toc79482528)

>   [1.3.27 tbl_rpc_motor	38](#_Toc79482529)

>   [1.3.28 tbl_rpc_inver_opt	38](#_Toc79482530)

>   [1.3.29 tbl_pcp_productiondata_latest	39](#_Toc79482531)

>   [1.3.30 tbl_pcp_productiondata_hist	40](#_Toc79482532)

>   [1.3.31 tbl_pcp_discrete_latest	40](#_Toc79482533)

>   [1.3.32 tbl_pcp_discrete_hist	44](#_Toc79482534)

>   [1.3.33 tbl_pcp_rpm_latest	45](#_Toc79482535)

>   [1.3.34 tbl_pcp_rpm_hist	46](#_Toc79482536)

>   [1.3.35 tbl_pcp_total_day	47](#_Toc79482537)

>   [1.3.36 tbl_a9rawdata_latest	53](#_Toc79482538)

>   [1.3.37 tbl_a9rawdata_hist	53](#_Toc79482539)

>   [1.3.38 tbl_a9rawwatercutdata_latest	54](#_Toc79482540)

>   [1.3.39 tbl_a9rawwatercutdata_hist	54](#_Toc79482541)

>   [1.3.40 tbl_resourcemonitoring	54](#_Toc79482542)

[二、视图	55](#_Toc79482543)

>   [2.1 概览	55](#_Toc79482544)

>   [2.2 详述	56](#_Toc79482545)

>   [2.2.1 viw_commstatus	56](#_Toc79482546)

>   [2.2.2 viw_wellinformation	56](#_Toc79482547)

>   [2.2.3 viw_wellboretrajectory	57](#_Toc79482548)

>   [2.2.4 viw_rpc_productiondata_latest	57](#_Toc79482549)

>   [2.2.5 viw_rpc_productiondata_hist	59](#_Toc79482550)

>   [2.2.6 viw_rpc_diagram_latest	60](#_Toc79482551)

>   [2.2.7 viw_rpc_diagram_hist	64](#_Toc79482552)

>   [2.2.8 viw_rpc_discrete_latest	64](#_Toc79482553)

>   [2.2.9 viw_rpc_discrete_hist	68](#_Toc79482554)

>   [2.2.10 viw_rpc_comprehensive_latest	68](#_Toc79482555)

>   [2.2.11 viw_rpc_comprehensive_hist	76](#_Toc79482556)

>   [2.2.12 viw_rpc_diagramquery_latest	76](#_Toc79482557)

>   [2.2.13 viw_rpc_diagramquery_hist	78](#_Toc79482558)

>   [2.2.14 viw_rpc_total_day	79](#_Toc79482559)

>   [2.1.15 viw_rpc_calculatemain	84](#_Toc79482560)

>   [2.1.16 viw_rpc_calculatemain_elec	85](#_Toc79482561)

>   [2.1.17 viw_pcp_productiondata_latest	86](#_Toc79482562)

>   [2.1.18 viw_pcp_productiondata_hist	88](#_Toc79482563)

>   [2.1.19 viw_pcp_rpm_latest	89](#_Toc79482564)

>   [2.1.20 viw_pcp_rpm_hist	91](#_Toc79482565)

>   [2.1.21 viw_pcp_discrete_latest	91](#_Toc79482566)

>   [2.1.22 viw_pcp_discrete_hist	95](#_Toc79482567)

>   [2.1.23 viw_pcp_comprehensive_latest	95](#_Toc79482568)

>   [2.1.24 viw_pcp_comprehensive_hist	100](#_Toc79482569)

>   [2.1.25 viw_pcp_total_day	101](#_Toc79482570)

[三、存储过程	105](#_Toc79482571)

[四、触发器	106](#_Toc79482572)

# 一、表

## 1.1 概览

表1-1 表概览

| **序号** | **名称**                      | **描述**                 |
|----------|-------------------------------|--------------------------|
| 1        | tbl_org                       | 组织数据表               |
| 2        | tbl_user                      | 用户数据表               |
| 3        | tbl_role                      | 角色数据表               |
| 4        | tbl_module                    | 模块数据表               |
| 5        | tbl_module2role               | 模块角色关系表           |
| 6        | tbl_dist_name                 | 字典名称表               |
| 7        | tbl_dist_item                 | 字典数据项表             |
| 8        | tbl_code                      | 代码表                   |
| 9        | tbl_acq\_unit\_conf           | 采控单元表               |
| 10       | tbl_acq_group_conf            | 采控组表                 |
| 11       | tbl_acq_item_conf             | 采控项表                 |
| 12       | tbl_acq_item2group_conf       | 采控组和采控项关系表     |
| 13       | tbl_acq_group2unit_conf       | 采控单元和采控组关系表   |
| 14       | tbl_wellinformation           | 井名基本信息表           |
| 15       | tbl_wellboretrajectory        | 井身轨迹表               |
| 16       | tbl_rpc_productiondata_latest | 抽油机生产数据实时表     |
| 17       | tbl_rpc_productiondata_hist   | 抽油机生产数据历史表     |
| 18       | tbl_rpc_discrete_latest       | 抽油机离散数据实时表     |
| 19       | tbl_rpc_discrete_hist         | 抽油机离散数据历史表     |
| 20       | tbl_rpc_diagram_latest        | 抽油机曲线数据实时表     |
| 21       | tbl_rpc_diagram_hist          | 抽油机曲线数据历史表     |
| 22       | tbl_rpc_worktype              | 抽油机工况类型表         |
| 23       | tbl_rpc_alarmtype_conf        | 抽油机报警类型表         |
| 24       | tbl_rpc_total_day             | 抽油机日累计数据表       |
| 25       | tbl_rpc_statistics_conf       | 抽油机统计配置表         |
| 26       | tbl_rpcinformation            | 抽油机设备表             |
| 27       | tbl_rpc_motor                 | 抽油机电机数据表         |
| 28       | tbl_rpc_inver_opt             | 抽油机电参反演参数优化表 |
| 29       | tbl_pcp_productiondata_latest | 螺杆泵生产数据实时表     |
| 30       | tbl_pcp_productiondata_hist   | 螺杆泵生产数据历史表     |
| 31       | tbl_pcp_discrete_latest       | 螺杆泵离散数据实时表     |
| 32       | tbl_pcp_discrete_hist         | 螺杆泵离散数据历史表     |
| 33       | tbl_pcp_rpm_latest            | 螺杆泵曲线数据实时表     |
| 34       | tbl_pcp_rpm_hist              | 螺杆泵曲线数据历史表     |
| 35       | tbl_pcp_total_day             | 螺杆泵日累计数据表       |
| 36       | tbl_a9rawdata_latest          | a9设备原始数据实时表     |
| 37       | tbl_a9rawdata_hist            | a9设备原始数据历史表     |
| 38       | tbl_resourcemonitoring        | 资源监测数据表           |

## 1.2 逻辑结构

图1-1 逻辑结构

## 1.3 详述

### 1.3.1 tbl\_org

表1-2 组织数据表

| **序号** | **代码**   | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|------------|--------------|----------------|----------|----------|--------|----------|
| 1        | ORG_ID     | 单位序号     | NUMBER(10)     |          | N        | 主键   |          |
| 2        | ORG_CODE   | 单位编码     | VARCHAR2(20)   |          | Y        |        |          |
| 3        | ORG_NAME   | 单位名称     | VARCHAR2(100)  |          | N        |        |          |
| 4        | ORG_MEMO   | 单位说明     | VARCHAR2(4000) |          | Y        |        |          |
| 5        | ORG_PARENT | 父级单位编号 | NUMBER(10)     |          | N        |        |          |
| 6        | ORG_SEQ    | 单位排序     | NUMBER(10)     |          | Y        |        |          |
| 7        | ORG_FLAG   | 单位标志     | CHAR(1)        |          | Y        |        |          |
| 8        | ORG_REALID | 单位当前编号 | NUMBER(10)     |          | Y        |        |          |
| 9        | ORG_LEVEL  | 单位级别     | NUMBER(1)      |          | Y        |        |          |
| 10       | ORG_TYPE   | 单位类型     | NUMBER(1)      |          | Y        |        |          |
| 11       | ORG_COORDX | 纬度         | NUMBER(10,6)   |          | Y        |        |          |
| 12       | ORG_COORDY | 经度         | NUMBER(10,6)   |          | Y        |        |          |
| 13       | SHOW_LEVEL | 地图显示级别 | NUMBER(2)      |          | Y        |        |          |

### 1.3.2 tbl\_user

表1-3 用户数据表

| **序号** | **代码**        | **名称**     | **类型**      | **单位** | **为空** | **键** | **备注**                    |
|----------|-----------------|--------------|---------------|----------|----------|--------|-----------------------------|
| 1        | USER_NO         | 用户序号     | NUMBER(10)    |          | N        | 主键   |                             |
| 2        | USER_ID         | 用户账号     | VARCHAR2(20)  |          | N        |        |                             |
| 3        | USER_PWD        | 用户密码     | VARCHAR2(20)  |          | Y        |        |                             |
| 4        | USER_NAME       | 用户姓名     | VARCHAR2(40)  |          | N        |        |                             |
| 5        | USER_IN_EMAIL   | 内部邮箱     | VARCHAR2(40)  |          | Y        |        |                             |
| 6        | USER_OUT_EMAIL  | 外部邮箱     | VARCHAR2(100) |          | Y        |        |                             |
| 7        | USER_PHONE      | 用户电话     | VARCHAR2(40)  |          | Y        |        |                             |
| 8        | USER_MOBILE     | 手机号       | VARCHAR2(40)  |          | Y        |        |                             |
| 9        | USER_ADDRESS    | 地址         | VARCHAR2(200) |          | Y        |        |                             |
| 10       | USER_POSTCODE   | 邮编         | CHAR(6)       |          | Y        |        |                             |
| 11       | USER_TITLE      | 用户职称     | VARCHAR2(100) |          | Y        |        |                             |
| 12       | USER_TYPE       | 用户类型     | NUMBER(10)    |          | Y        |        | 对应tbl_role表中role_id字段 |
| 13       | USER_ORGID      | 用户所属组织 | NUMBER(10)    |          | N        |        | 对应tbl_org表中org_id字段   |
| 14       | USER_ISLEADER   | 是否领导     | CHAR(1)       |          | Y        |        | 0-不是，1-是                |
| 15       | USER_REGTIME    | 用户注册时间 | DATE          |          | Y        |        |                             |
| 16       | USER_STYLE      | 显示风格     | VARCHAR2(20)  |          | Y        |        |                             |
| 17       | USER_QUICKLOGIN | 是否快捷登录 | NUMBER(1)     |          | Y        |        | 是否快捷登录 0-不是 1-是    |

### 1.3.3 tbl\_role

表1-4 角色数据表

| **序号** | **代码**  | **名称** | **类型**       | **单位** | **为空** | **键** | **备注**   |
|----------|-----------|----------|----------------|----------|----------|--------|------------|
| 1        | ROLE_ID   | 角色序号 | NUMBER(10)     |          | N        | 主键   |            |
| 2        | ROLE_CODE | 角色编码 | VARCHAR2(50)   |          | N        |        |            |
| 3        | ROLE_NAME | 角色名称 | VARCHAR2(40)   |          | N        |        |            |
| 4        | ROLE_FLAG | 控制权限 | NUMBER(10)     |          | Y        |        | 0-无，1-是 |
| 5        | REMARK    | 角色描述 | VARCHAR2(2000) |          | Y        |        |            |

### 1.3.4 tbl\_module

表1-5 模块数据表

| **序号** | **代码**    | **名称**     | **类型**      | **单位** | **为空** | **键** | **备注**               |
|----------|-------------|--------------|---------------|----------|----------|--------|------------------------|
| 1        | MD_ID       | 模块序号     | NUMBER(10)    |          | N        | 主键   |                        |
| 2        | MD_PARENTID | 父级模块序号 | NUMBER(10)    |          | N        |        |                        |
| 3        | MD_NAME     | 模块名称     | VARCHAR2(100) |          | N        |        |                        |
| 4        | MD_SHOWNAME | 模块简介     | VARCHAR2(100) |          | Y        |        |                        |
| 5        | MD_URL      | 模块URL      | VARCHAR2(200) |          | Y        |        |                        |
| 6        | MD_CODE     | 模块编码     | VARCHAR2(200) |          | Y        |        |                        |
| 7        | MD_SEQ      | 模块排序     | NUMBER(20)    |          | Y        |        |                        |
| 8        | MD_LEVEL    | 模块级别     | NUMBER(10)    |          | Y        |        |                        |
| 9        | MD_FLAG     | 模块标志     | NUMBER(10)    |          | Y        |        |                        |
| 10       | MD_ICON     | 模块图标     | VARCHAR2(100) |          | Y        |        |                        |
| 11       | MD_TYPE     | 模块类型     | NUMBER(1)     |          | Y        |        | 0-启用模块，2-备用模块 |
| 12       | MD_CONTROL  | 模块控制器   | VARCHAR2(100) |          | Y        |        |                        |

### 1.3.5 tbl\_module2role

表1-6 模块角色关系表

| **序号** | **代码**    | **名称** | **类型**    | **单位** | **为空** | **键** | **备注**                    |
|----------|-------------|----------|-------------|----------|----------|--------|-----------------------------|
| 1        | RM_ID       | 序号     | NUMBER(10)  |          | N        | 主键   |                             |
| 2        | RM_ROLEID   | 角色编号 | NUMBER(10)  |          | N        |        | 对应tbl_role表中role_id字段 |
| 3        | RM_MODULEID | 模块序号 | NUMBER(10)  |          | N        |        | 对应module表中md_id字段     |
| 4        | RM_MATRIX   | 权限矩阵 | VARCHAR2(8) |          | N        |        |                             |

### 1.3.6 tbl_dist_name

表1-7 字典名称表

| **序号** | **代码**   | **名称** | **类型**     | **单位** | **为空** | **键** | **备注**       |
|----------|------------|----------|--------------|----------|----------|--------|----------------|
| 1        | SYSDATAID  | 字典编码 | VARCHAR2(32) |          | N        | 主键   |                |
| 2        | TENANTID   | 组织编号 | VARCHAR2(50) |          | Y        |        |                |
| 3        | CNAME      | 中文名称 | VARCHAR2(50) |          | Y        |        |                |
| 4        | ENAME      | 英文名称 | VARCHAR2(50) |          | Y        |        |                |
| 5        | SORTS      | 排序     | NUMBER       |          | Y        |        |                |
| 6        | STATUS     | 显示状态 | NUMBER       |          | Y        |        | 0-显示，1-隐藏 |
| 7        | CREATOR    | 创建人   | VARCHAR2(50) |          | Y        |        |                |
| 8        | UPDATEUSER | 修改人   | VARCHAR2(50) |          | Y        |        |                |
| 9        | UPDATETIME | 创建时间 | DATE         |          | Y        |        | SYSDATE        |
| 10       | CREATEDATE | 修改时间 | DATE         |          | N        |        | SYSDATE        |

### 1.3.7 tbl_dist_item

表1-8 字典数据项表

| **序号** | **代码**   | **名称**   | **类型**      | **单位** | **为空** | **键** | **备注**       |
|----------|------------|------------|---------------|----------|----------|--------|----------------|
| 1        | DATAITEMID | 数据项编码 | VARCHAR2(32)  |          | N        | 主键   |                |
| 2        | TENANTID   | 组织编号   | VARCHAR2(50)  |          | Y        |        |                |
| 3        | SYSDATAID  | 字典编码   | VARCHAR2(50)  |          | Y        |        |                |
| 4        | CNAME      | 中文名称   | VARCHAR2(50)  |          | Y        |        |                |
| 5        | ENAME      | 英文名称   | VARCHAR2(200) |          | Y        |        |                |
| 6        | DATAVALUE  | 数据项值   | VARCHAR2(200) |          | Y        |        |                |
| 7        | SORTS      | 排序       | NUMBER        |          | Y        |        |                |
| 8        | STATUS     | 显示状态   | NUMBER        |          | Y        |        | 0-显示，1-隐藏 |
| 9        | CREATOR    | 创建人     | VARCHAR2(50)  |          | Y        |        |                |
| 10       | UPDATEUSER | 修改人     | VARCHAR2(50)  |          | Y        |        |                |
| 11       | UPDATETIME | 创建时间   | DATE          |          | Y        |        | SYSDATE        |
| 12       | CREATEDATE | 修改时间   | DATE          |          | Y        |        | SYSDATE        |

### 1.3.8 tbl_code

表1-9 代码表

| **序号** | **代码**  | **名称**   | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|-----------|------------|---------------|----------|----------|--------|----------|
| 1        | ID        | 记录编号   | NUMBER(10)    |          | N        | 主键   |          |
| 2        | TABLECODE | 数据表代码 | VARCHAR2(200) |          | Y        |        |          |
| 3        | ITEMCODE  | 数据项代码 | VARCHAR2(200) |          | Y        |        |          |
| 4        | ITEMVALUE | 代码       | VARCHAR2(20)  |          | Y        |        |          |
| 5        | ITEMNAME  | 名称       | VARCHAR2(200) |          | Y        |        |          |
| 6        | STATE     | 状态       | NUMBER(10)    |          | Y        |        |          |
| 7        | REMARK    | 备注       | VARCHAR2(200) |          | Y        |        |          |

### 1.3.9 tbl_acq_unit_conf

表1-10 采控组名称表

| **序号** | **代码**  | **名称** | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|-----------|----------|----------------|----------|----------|--------|----------|
| 1        | ID        | 记录编号 | NUMBER(10)     |          | N        | 主键   |          |
| 2        | UNIT_CODE | 单元代码 | VARCHAR2(50)   |          | N        |        |          |
| 3        | UNIT_NAME | 单元名称 | VARCHAR2(50)   |          | Y        |        |          |
| 4        | PROTOCOL  | 协议     | VARCHAR2(50)   |          | Y        |        |          |
| 5        | REMARK    | 单元描述 | VARCHAR2(2000) |          | Y        |        |          |

### 1.3.10 tbl_acq_group_conf

表1-11 采控组名称表

| **序号** | **代码**   | **名称** | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|------------|----------|----------------|----------|----------|--------|----------|
| 1        | id         | 记录编号 | NUMBER(10)     |          | N        | 主键   |          |
| 2        | GROUP_CODE | 组代码   | VARCHAR2(50)   |          | N        |        |          |
| 3        | GROUP_NAME | 组名称   | VARCHAR2(50)   |          | Y        |        |          |
| 4        | ACQ_CYCLE  | 采集周期 | NUMBER(10)     | 秒       | Y        |        |          |
| 5        | SAVE_CYCLE | 保存周期 | NUMBER(10)     | 秒       | Y        |        |          |
| 6        | PROTOCOL   | 协议     | VARCHAR2(50)   |          | Y        |        |          |
| 7        | REMARK     | 组描述   | VARCHAR2(2000) |          | Y        |        |          |

### 1.3.11 tbl_acq_item2group_conf

表1-12 采控组和采集项关系表

| **序号** | **代码** | **名称**   | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|----------|------------|---------------|----------|----------|--------|----------|
| 1        | ID       | 记录编号   | NUMBER(10)    |          | N        | 主键   |          |
| 2        | GROUPID  | 采控组编号 | NUMBER(10)    |          | N        |        |          |
| 3        | ITEMID   | 采控项编号 | NUMBER(10)    |          | Y        |        |          |
| 4        | ITEMCODE | 采控项代码 | VARCHAR2(100) |          | Y        |        |          |
| 5        | ITEMNAME | 采控项名称 | VARCHAR2(100) |          | Y        |        |          |
| 6        | MATRIX   | 阵列       | VARCHAR2(8)   |          | Y        |        |          |

### 1.3.12 tbl_acq_group2unit_conf

表1-13 采控单元和采集采控组关系表

| **序号** | **代码** | **名称**     | **类型**    | **单位** | **为空** | **键** | **备注**                          |
|----------|----------|--------------|-------------|----------|----------|--------|-----------------------------------|
| 1        | ID       | 记录编号     | NUMBER(10)  |          | N        | 主键   |                                   |
| 2        | UNITID   | 采控单元编号 | NUMBER(10)  |          | N        |        | 对应tbl_acq\_unit\_conf表中id字段 |
| 3        | GROUPID  | 采控组编号   | NUMBER(10)  |          | N        |        | 对应tbl_acq_group_conf表中id字段  |
| 4        | MATRIX   | 阵列         | VARCHAR2(8) |          | N        |        |                                   |

### 1.3.13 tbl_wellinformation

表1-14 井名基本信息表

| **序号** | **代码**          | **名称**             | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|-------------------|----------------------|---------------|----------|----------|--------|----------|
| 1        | ID                | 记录编号             | NUMBER(10)    |          | N        | 主键   |          |
| 2        | ORGID             | 单位编号             | NUMBER(10)    |          | Y        |        |          |
| 3        | RESNAME           | 油气藏名称           | VARCHAR2(200) |          | Y        |        |          |
| 4        | WELLNAME          | 井名                 | VARCHAR2(200) |          | N        |        |          |
| 5        | LIFTINGTYPE       | 举升类型             | NUMBER(10)    |          | Y        |        |          |
| 6        | SIGNINID          | 注册包ID             | VARCHAR2(200) |          | Y        |        |          |
| 7        | SLAVE             | 设备从地址           | VARCHAR2(200) |          | Y        |        |          |
| 8        | PROTOCOLCODE      | 协议编码             | VARCHAR2(50)  |          | Y        |        |          |
| 9        | UNITCODE          | 采集单元编码         | VARCHAR2(50)  |          | Y        |        |          |
| 10       | VIDEOURL          | 视频url              | VARCHAR2(400) |          | Y        |        |          |
| 11       | SORTNUM           | 排序编号             | NUMBER(10)    |          | Y        |        |          |
| 12       | LEVELCORRECTVALUE | 功图反演动液面校正值 | NUMBER(10,3)  |          | Y        |        |          |

### 1.3.14 tbl_wellboretrajectory

表1-15 井身轨迹表

| **序号** | **代码**       | **名称**  | **类型**   | **单位** | **为空** | **键** | **备注** |
|----------|----------------|-----------|------------|----------|----------|--------|----------|
| 1        | ID             | 记录编号  | NUMBER(10) |          | N        | 主键   |          |
| 2        | WELLID         | 井编号    | NUMBER(10) |          | N        |        |          |
| 3        | MEASURINGDEPTH | 测量深度  | CLOB       | m        | Y        |        |          |
| 4        | VERTICALDEPTH  | 垂直深度  | CLOB       | m        | Y        |        |          |
| 5        | DEVIATIONANGLE | 井斜角    | CLOB       | 度       | Y        |        |          |
| 6        | AZIMUTHANGLE   | 方位角    | CLOB       | 度       | Y        |        |          |
| 7        | X              | 直角坐标X | CLOB       | m        | Y        |        |          |
| 8        | Y              | 直角坐标Y | CLOB       | m        | Y        |        |          |
| 9        | Z              | 直角坐标Z | CLOB       | m        | Y        |        |          |
| 10       | SAVETIME       | 入库时间  | DATE       |          | Y        |        |          |
| 11       | RESULTSTATUS   | 计算标志  | NUMBER(4)  |          | Y        |        |          |

### 1.3.15 tbl_rpc_productiondata_latest

表1-16 抽油机生产数据实时表

| **序号** | **代码**                   | **名称**       | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|----------------------------|----------------|---------------|----------|----------|--------|----------|
| 1        | ID                         | 记录编号       | NUMBER(10)    |          | N        | 主键   |          |
| 2        | WELLID                     | 井编号         | NUMBER(10)    |          | N        |        |          |
| 3        | ACQTIME                    | 采集时间       | DATE          |          | Y        |        |          |
| 4        | LIFTINGTYPE                | 举升类型       | NUMBER(10)    |          | Y        |        |          |
| 5        | DISPLACEMENTTYPE           | 驱替类型       | NUMBER(1)     |          | Y        |        |          |
| 6        | RUNTIME                    | 生产时间       | NUMBER(8,2)   | h        | Y        |        |          |
| 7        | CRUDEOILDENSITY            | 原油密度       | NUMBER(16,2)  | g/cm\^3  | Y        |        |          |
| 8        | WATERDENSITY               | 水密度         | NUMBER(16,2)  | g/cm\^3  | Y        |        |          |
| 9        | NATURALGASRELATIVEDENSITY  | 天然气相对密度 | NUMBER(16,2)  |          | Y        |        |          |
| 10       | SATURATIONPRESSURE         | 饱和压力       | NUMBER(16,2)  | MPa      | Y        |        |          |
| 11       | RESERVOIRDEPTH             | 油层中部深度   | NUMBER(16,2)  | m        | Y        |        |          |
| 12       | RESERVOIRTEMPERATURE       | 油层中部温度   | NUMBER(16,2)  | ℃        | Y        |        |          |
| 13       | VOLUMEWATERCUT             | 体积含水率     | NUMBER(8,2)   | %        | Y        |        |          |
| 14       | WEIGHTWATERCUT             | 重量含水率     | NUMBER(8,2)   | %        | Y        |        |          |
| 15       | TUBINGPRESSURE             | 油压           | NUMBER(8,2)   | MPa      | Y        |        |          |
| 16       | CASINGPRESSURE             | 套压           | NUMBER(8,2)   | MPa      | Y        |        |          |
| 17       | BACKPRESSURE               | 回压           | NUMBER(8,2)   | MPa      | Y        |        |          |
| 18       | WELLHEADFLUIDTEMPERATURE   | 井口流温       | NUMBER(8,2)   | ℃        | Y        |        |          |
| 19       | PRODUCINGFLUIDLEVEL        | 动液面         | NUMBER(8,2)   | m        | Y        |        |          |
| 20       | PUMPSETTINGDEPTH           | 泵挂           | NUMBER(8,2)   | m        | Y        |        |          |
| 21       | PRODUCTIONGASOILRATIO      | 生产气油比     | NUMBER(8,2)   | m\^3/t   | Y        |        |          |
| 22       | TUBINGSTRINGINSIDEDIAMETER | 油管内径       | NUMBER(8,2)   | mm       | Y        |        |          |
| 23       | CASINGSTRINGINSIDEDIAMETER | 油层套管内径   | NUMBER(8,2)   | mm       | Y        |        |          |
| 24       | RODSTRING                  | 抽油杆参数     | VARCHAR2(200) |          | Y        |        |          |
| 25       | PUMPGRADE                  | 泵级别         | NUMBER(1)     |          | Y        |        |          |
| 26       | PUMPBOREDIAMETER           | 泵径           | NUMBER(8,2)   | mm       | Y        |        |          |
| 27       | PLUNGERLENGTH              | 柱塞长         | NUMBER(8,2)   | m        | Y        |        |          |
| 28       | PUMPTYPE                   | 泵类型         | VARCHAR2(20)  |          | Y        |        |          |
| 29       | BARRELTYPE                 | 泵筒类型       | VARCHAR2(20)  |          | Y        |        |          |
| 30       | BARRELLENGTH               | 泵筒长         | NUMBER(8,2)   | m        | Y        |        |          |
| 31       | BARRELSERIES               | 泵级数         | NUMBER(8,2)   |          | Y        |        |          |
| 32       | ROTORDIAMETER              | 转子截面直径   | NUMBER(8,2)   | mm       | Y        |        |          |
| 33       | QPR                        | 公称排量       | NUMBER(8,2)   | m\^3/r   | Y        |        |          |
| 34       | MANUALINTERVENTION         | 人工干预       | NUMBER(4)     |          | Y        |        |          |
| 35       | NETGROSSRATIO              | 净毛比         | NUMBER(8,2)   |          | Y        |        |          |
| 36       | ANCHORINGSTATE             | 锚定状态       | NUMBER(1)     |          | Y        |        |          |
| 37       | RUNTIMEEFFICIENCYSOURCE    | 时率来源       | NUMBER(2)     |          | Y        |        |          |
| 38       | REMARK                     | 备注           | VARCHAR2(200) |          | Y        |        |          |

### 1.3.16 tbl_rpc_productiondata_hist

同tbl_rpc_productiondata_latest

### 1.3.17 tbl_rpc_discrete_latest

表1-17 抽油机离散数据实时表

| **序号** | **代码**                  | **名称**                     | **类型** | **单位** | **为空** | **键** | **备注**                |
|----------|---------------------------|------------------------------|----------|----------|----------|--------|-------------------------|
| 1        | ID                        | 记录编号                     | NUMBER   |          | N        | 主键   |                         |
| 2        | WELLID                    | 井编号                       | NUMBER   |          | N        |        |                         |
| 3        | ACQTIME                   | 采集时间                     | DATE     |          | Y        |        |                         |
| 4        | COMMSTATUS                | 通信状态                     | NUMBER   |          | Y        |        | 0-离线 1-在线           |
| 5        | COMMTIME                  | 在线时间                     | NUMBER   |          | Y        |        |                         |
| 6        | COMMTIMEEFFICIENCY        | 在线时率                     | NUMBER   |          | Y        |        |                         |
| 7        | COMMRANGE                 | 在线区间                     | CLOB     |          | Y        |        |                         |
| 8        | RUNSTATUS                 | 运行状态                     | NUMBER   |          | Y        |        | 0-停抽 1-运行           |
| 9        | RUNTIME                   | 运行时率                     | NUMBER   |          | Y        |        |                         |
| 10       | RUNTIMEEFFICIENCY         | 运行时间                     | NUMBER   |          | Y        |        |                         |
| 11       | RUNRANGE                  | 运行区间                     | CLOB     |          | Y        |        |                         |
| 12       | IA                        | A相电流                      | NUMBER   |          | Y        |        |                         |
| 13       | IB                        | B相电流                      | NUMBER   |          | Y        |        |                         |
| 14       | IC                        | C相电流                      | NUMBER   |          | Y        |        |                         |
| 15       | VA                        | A相电压                      | NUMBER   |          | Y        |        |                         |
| 16       | VB                        | B相电压                      | NUMBER   |          | Y        |        |                         |
| 17       | VC                        | C相电压                      | NUMBER   |          | Y        |        |                         |
| 18       | TOTALKWATTH               | 累计有功功耗                 | NUMBER   |          | Y        |        |                         |
| 19       | TOTALKVARH                | 累计无功功耗                 | NUMBER   |          | Y        |        |                         |
| 20       | WATTA                     | A相有功功率                  | NUMBER   |          | Y        |        |                         |
| 21       | WATTB                     | B相有功功率                  | NUMBER   |          | Y        |        |                         |
| 22       | WATTC                     | C相有功功率                  | NUMBER   |          | Y        |        |                         |
| 23       | WATT3                     | 三相总有功功率               | NUMBER   |          | Y        |        |                         |
| 24       | VARA                      | A相无功功率                  | NUMBER   |          | Y        |        |                         |
| 25       | VARB                      | B相无功功率                  | NUMBER   |          | Y        |        |                         |
| 26       | VARC                      | C相无功功率                  | NUMBER   |          | Y        |        |                         |
| 27       | VAR3                      | 三相总无功功率               | NUMBER   |          | Y        |        |                         |
| 28       | VAA                       | A相视在功率                  | NUMBER   |          | Y        |        |                         |
| 29       | VAB                       | B相视在功率                  | NUMBER   |          | Y        |        |                         |
| 30       | VAC                       | C相视在功率                  | NUMBER   |          | Y        |        |                         |
| 31       | VA3                       | 视在功率                     | NUMBER   |          | Y        |        |                         |
| 32       | REVERSEPOWER              | 反向功率                     | NUMBER   |          | Y        |        |                         |
| 33       | PFA                       | A相功率因数                  | NUMBER   |          | Y        |        |                         |
| 34       | PFB                       | B相功率因数                  | NUMBER   |          | Y        |        |                         |
| 35       | PFC                       | C相功率因数                  | NUMBER   |          | Y        |        |                         |
| 36       | PF3                       | 三相综合功率因数             | NUMBER   |          | Y        |        |                         |
| 37       | ACQCYCLE_DIAGRAM          | 曲线采集周期                 | NUMBER   |          | Y        |        |                         |
| 38       | SETFREQUENCY              | 变频设置频率                 | NUMBER   |          | Y        |        |                         |
| 39       | RUNFREQUENCY              | 变频运行频率                 | NUMBER   |          | Y        |        |                         |
| 40       | TUBINGPRESSURE            | 油压                         | NUMBER   |          | Y        |        |                         |
| 41       | CASINGPRESSURE            | 套压                         | NUMBER   |          | Y        |        |                         |
| 42       | BACKPRESSURE              | 回压                         | NUMBER   |          | Y        |        |                         |
| 43       | WELLHEADFLUIDTEMPERATURE  | 井口油温                     | NUMBER   |          | Y        |        |                         |
| 44       | TODAYKWATTH               | 日有功功耗                   | NUMBER   |          | Y        |        |                         |
| 45       | RESULTCODE                | 电参工况代码                 | NUMBER   |          | Y        |        |                         |
| 46       | RESULTSTRING              | 电参工况字符串               | CLOB     |          | Y        |        |                         |
| 47       | IAALARM                   | A相电流报警项                | VARCHAR2 |          | Y        |        |                         |
| 48       | IBALARM                   | B相电流报警项                | VARCHAR2 |          | Y        |        |                         |
| 49       | ICALARM                   | C相电流报警项                | VARCHAR2 |          | Y        |        |                         |
| 50       | VAALARM                   | A相电压报警项                | VARCHAR2 |          | Y        |        |                         |
| 51       | VBALARM                   | B相电压报警项                | VARCHAR2 |          | Y        |        |                         |
| 52       | VCALARM                   | C相电压报警项                | VARCHAR2 |          | Y        |        |                         |
| 53       | IAUPLIMIT                 | A相电流上限                  | NUMBER   |          | Y        |        |                         |
| 54       | IADOWNLIMIT               | A相电流下限                  | NUMBER   |          | Y        |        |                         |
| 55       | IBUPLIMIT                 | A相电流零值                  | NUMBER   |          | Y        |        |                         |
| 56       | IBDOWNLIMIT               | B相电流上限                  | NUMBER   |          | Y        |        |                         |
| 57       | ICUPLIMIT                 | B相电流下限                  | NUMBER   |          | Y        |        |                         |
| 58       | ICDOWNLIMIT               | B相电流零值                  | NUMBER   |          | Y        |        |                         |
| 59       | VAUPLIMIT                 | C相电流上限                  | NUMBER   |          | Y        |        |                         |
| 60       | VADOWNLIMIT               | C相电流下限                  | NUMBER   |          | Y        |        |                         |
| 61       | VBUPLIMIT                 | C相电流零值                  | NUMBER   |          | Y        |        |                         |
| 62       | VBDOWNLIMIT               | A相电压上限                  | NUMBER   |          | Y        |        |                         |
| 63       | VCUPLIMIT                 | A相电压下限                  | NUMBER   |          | Y        |        |                         |
| 64       | VCDOWNLIMIT               | A相电压零值                  | NUMBER   |          | Y        |        |                         |
| 65       | IAZERO                    | B相电压上限                  | NUMBER   |          | Y        |        |                         |
| 66       | IBZERO                    | B相电压下限                  | NUMBER   |          | Y        |        |                         |
| 67       | ICZERO                    | B相电压零值                  | NUMBER   |          | Y        |        |                         |
| 68       | VAZERO                    | C相电压上限                  | NUMBER   |          | Y        |        |                         |
| 69       | VBZERO                    | C相电压下限                  | NUMBER   |          | Y        |        |                         |
| 70       | VCZERO                    | C相电压零值                  | NUMBER   |          | Y        |        |                         |
| 71       | TOTALPKWATTH              | 累计正向有功功耗             | NUMBER   |          | Y        |        |                         |
| 72       | TOTALNKWATTH              | 累计反向有功功耗             | NUMBER   |          | Y        |        |                         |
| 73       | TOTALPKVARH               | 累计正向无功功耗             | NUMBER   |          | Y        |        |                         |
| 74       | TOTALNKVARH               | 累计反向无功功耗             | NUMBER   |          | Y        |        |                         |
| 75       | TOTALKVAH                 | 累计视在功耗                 | NUMBER   |          | Y        |        |                         |
| 76       | TODAYPKWATTH              | 日正向有功功耗               | NUMBER   |          | Y        |        |                         |
| 77       | TODAYNKWATTH              | 日反向有功功耗               | NUMBER   |          | Y        |        |                         |
| 78       | TODAYKVARH                | 日无功功耗                   | NUMBER   |          | Y        |        |                         |
| 79       | TODAYPKVARH               | 日正向无功功耗               | NUMBER   |          | Y        |        |                         |
| 80       | TODAYNKVARH               | 日反向无功功耗               | NUMBER   |          | Y        |        |                         |
| 81       | TODAYKVAH                 | 日视在功耗                   | NUMBER   |          | Y        |        |                         |
| 82       | SIGNAL                    | 信号强度                     | NUMBER   |          | Y        |        |                         |
| 83       | INTERVAL                  | 传输间隔                     | NUMBER   |          | Y        |        |                         |
| 84       | DEVICEVER                 | 设备版本信息                 | VARCHAR2 |          | Y        |        |                         |
| 85       | VAVG                      | 三相电压平均值               | NUMBER   |          | Y        |        |                         |
| 86       | IAVG                      | 三相电流平均值               | NUMBER   |          | Y        |        |                         |
| 87       | BALANCECONTROLMODE        | 平衡远程调节远程触发控制     | NUMBER   |          | Y        |        | 0-无操作执行 1-正在调节 |
| 88       | BALANCECALCULATEMODE      | 平衡计算方式                 | NUMBER   |          | Y        |        | 1-下/上 2-上/下         |
| 89       | BALANCEAWAYTIME           | 重心远离支点调节时间         | NUMBER   |          | Y        |        |                         |
| 90       | BALANCECLOSETIME          | 重心接近支点调节时间         | NUMBER   |          | Y        |        |                         |
| 91       | BALANCESTROKECOUNT        | 参与平衡度计算的冲程测量次数 | NUMBER   |          | Y        |        |                         |
| 92       | BALANCEOPERATIONUPLIMIT   | 平衡调节上限                 | NUMBER   |          | Y        |        |                         |
| 93       | BALANCEOPERATIONDOWNLIMIT | 平衡调节下限                 | NUMBER   |          | Y        |        |                         |
| 94       | BALANCEAUTOCONTROL        | 平衡远程自动调节             | NUMBER   |          | Y        |        | 0-允许 1-禁止           |
| 95       | BALANCEFRONTLIMIT         | 平衡前限位                   | NUMBER   |          | Y        |        | 0-限位 1-未限位         |
| 96       | BALANCEAFTERLIMIT         | 平衡后限位                   | NUMBER   |          | Y        |        | 0-限位 1-未限位         |
| 97       | SPMAUTOCONTROL            | 冲次远程自动调节             | NUMBER   |          | Y        |        | 0-允许 1-禁止           |
| 98       | BALANCEAWAYTIMEPERBEAT    | 重心远离支点每拍调节时间     | NUMBER   |          | Y        |        |                         |
| 99       | BALANCECLOSETIMEPERBEAT   | 重心接近支点每拍调节时间     | NUMBER   |          | Y        |        |                         |
| 100      | ACQCYCLE_DISCRETE         | 离散数据采集间隔             | NUMBER   |          | Y        |        |                         |
| 101      | WATTUPLIMIT               | 有功功率上限                 | NUMBER   |          | Y        |        |                         |
| 102      | WATTDOWNLIMIT             | 有功功率下限                 | NUMBER   |          | Y        |        |                         |
| 103      | IAMAX                     | a相电流最大值                | NUMBER   |          | Y        |        |                         |
| 104      | IBMAX                     | b相电流最大值                | NUMBER   |          | Y        |        |                         |
| 105      | IAMIN                     | a相电流最小值                | NUMBER   |          | Y        |        |                         |
| 106      | IBMIN                     | b相电流最小值                | NUMBER   |          | Y        |        |                         |
| 107      | ICMAX                     | c相电流最大值                | NUMBER   |          | Y        |        |                         |
| 108      | ICMIN                     | c相电流最小值                | NUMBER   |          | Y        |        |                         |
| 109      | SAVETIME                  | 保存时间                     | DATE     |          |          |        |                         |

### 1.3.18 tbl_rpc_discrete_hist

同tbl_rpc_discrete_latest

### 1.3.19 tbl_rpc_diagram_latest

表1-18 抽油机曲线数据实时表

| **序号** | **代码**                       | **名称**           | **类型**      | **单位**    | **为空** | **键** | **备注**                                                                                       |
|----------|--------------------------------|--------------------|---------------|-------------|----------|--------|------------------------------------------------------------------------------------------------|
| 1        | ID                             | 记录编号           | NUMBER(10)    |             | N        | 主键   |                                                                                                |
| 2        | WELLID                         | 井编号             | NUMBER(10)    |             | N        |        |                                                                                                |
| 3        | ACQTIME                        | 采集时间           | DATE          |             | Y        |        |                                                                                                |
| 4        | STROKE                         | 冲程               | NUMBER(8,2)   | m           | Y        |        |                                                                                                |
| 5        | SPM                            | 冲次               | NUMBER(8,2)   | 次/min      | Y        |        |                                                                                                |
| 6        | FMAX                           | 最大载荷           | NUMBER(8,2)   | kN          | Y        |        |                                                                                                |
| 7        | FMIN                           | 最小载荷           | NUMBER(8,2)   | kN          | Y        |        |                                                                                                |
| 8        | POSITION_CURVE                 | 位移曲线           | CLOB          | m           | Y        |        | 位移1,位移2…                                                                                   |
| 9        | ANGLE_CURVE                    | 角度曲线           | CLOB          | °           | Y        |        | 角度1,角度2…                                                                                   |
| 10       | LOAD_CURVE                     | 载荷曲线           | CLOB          | kN          | Y        |        | 载荷1,载荷2…                                                                                   |
| 11       | POWER_CURVE                    | 功率曲线           | CLOB          | kW          | Y        |        | 功率1,功率2…                                                                                   |
| 12       | CURRENT_CURVE                  | 电流曲线           | CLOB          | A           | Y        |        | 电流1,电流2…                                                                                   |
| 13       | RPM_CURVE                      | 电机转速曲线       | CLOB          | r/min       | Y        |        | 转速1,转速2…                                                                                   |
| 14       | RAWPOWER_CURVE                 | 功率原始曲线       | CLOB          | kW          | Y        |        | 功率1,功率2…                                                                                   |
| 15       | RAWCURRENT_CURVE               | 电流原始曲线       | CLOB          | A           | Y        |        | 电流1,电流2…                                                                                   |
| 16       | RAWRPM_CURVE                   | 电机转速原始曲线   | CLOB          | r/min       | Y        |        | 转速1,转速2…                                                                                   |
| 17       | UPSTROKEIMAX                   | 上冲程最大电流     | NUMBER(8,2)   | A           | Y        |        |                                                                                                |
| 18       | DOWNSTROKEIMAX                 | 下冲程最大电流     | NUMBER(8,2)   | A           | Y        |        |                                                                                                |
| 19       | UPSTROKEWATTMAX                | 上冲程最大功率     | NUMBER(8,2)   | kW          | Y        |        |                                                                                                |
| 20       | DOWNSTROKEWATTMAX              | 下冲程最大功率     | NUMBER(8,2)   | kW          | Y        |        |                                                                                                |
| 21       | IDEGREEBALANCE                 | 电流平衡度         | NUMBER(8,2)   | %           | Y        |        |                                                                                                |
| 22       | WATTDEGREEBALANCE              | 功率平衡度         | NUMBER(8,2)   | %           | Y        |        |                                                                                                |
| 23       | DATASOURCE                     | 功图来源           | NUMBER(1)     |             | Y        |        | 0-采集 1-电参反演 2-人工上传                                                                   |
| 24       | RESULTCODE                     | 功图工况代码       | NUMBER(4)     |             | Y        |        |                                                                                                |
| 25       | FULLNESSCOEFFICIENT            | 功图充满系数       | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 26       | UPPERLOADLINE                  | 理论上载荷         | NUMBER(8,2)   | kN          | Y        |        |                                                                                                |
| 27       | UPPERLOADLINEOFEXACT           | 真实理论上载荷     | NUMBER(8,2)   | kN          | Y        |        |                                                                                                |
| 28       | LOWERLOADLINE                  | 理论下载荷         | NUMBER(8,2)   | kN          | Y        |        |                                                                                                |
| 29       | PUMPFSDIAGRAM                  | 泵功图             | CLOB          |             | Y        |        |                                                                                                |
| 30       | THEORETICALPRODUCTION          | 理论排量           | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 31       | LIQUIDVOLUMETRICPRODUCTION     | 产液量方           | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 32       | OILVOLUMETRICPRODUCTION        | 产油量方           | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 33       | WATERVOLUMETRICPRODUCTION      | 产水量方           | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 34       | AVAILABLEPLUNGERSTROKEPROD_V   | 柱塞有效冲程产量方 | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 35       | PUMPCLEARANCELEAKPROD_V        | 泵间隙漏失量方     | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 36       | TVLEAKVOLUMETRICPRODUCTION     | 游动凡尔漏失量方   | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 37       | SVLEAKVOLUMETRICPRODUCTION     | 固定凡尔漏失量方   | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 38       | GASINFLUENCEPROD_V             | 气影响方           | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 39       | LIQUIDWEIGHTPRODUCTION         | 产液量吨           | NUMBER(8,2)   | t/d         | Y        |        |                                                                                                |
| 40       | OILWEIGHTPRODUCTION            | 产油量吨           | NUMBER(8,2)   | t/d         | Y        |        |                                                                                                |
| 41       | WATERWEIGHTPRODUCTION          | 产水量吨           | NUMBER(8,2)   | t/d         | Y        |        |                                                                                                |
| 42       | AVAILABLEPLUNGERSTROKEPROD_W   | 柱塞有效冲程产量吨 | NUMBER(8,2)   | t/d         | Y        |        |                                                                                                |
| 43       | PUMPCLEARANCELEAKPROD_W        | 泵间隙漏失量吨     | NUMBER(8,2)   | t/d         | Y        |        |                                                                                                |
| 44       | TVLEAKWEIGHTPRODUCTION         | 游动凡尔漏失量吨   | NUMBER(8,2)   | t/d         | Y        |        |                                                                                                |
| 45       | SVLEAKWEIGHTPRODUCTION         | 固定凡尔漏失量吨   | NUMBER(8,2)   | t/d         | Y        |        |                                                                                                |
| 46       | GASINFLUENCEPROD_W             | 气影响吨           | NUMBER(8,2)   | t/d         | Y        |        |                                                                                                |
| 47       | AVERAGEWATT                    | 有功功率           | NUMBER(8,2)   | kW          | Y        |        |                                                                                                |
| 48       | POLISHRODPOWER                 | 光杆功率           | NUMBER(8,2)   | kW          | Y        |        |                                                                                                |
| 49       | WATERPOWER                     | 水功率             | NUMBER(8,2)   | kW          | Y        |        |                                                                                                |
| 50       | SURFACESYSTEMEFFICIENCY        | 地面效率           | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 51       | WELLDOWNSYSTEMEFFICIENCY       | 井下效率           | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 52       | SYSTEMEFFICIENCY               | 系统效率           | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 53       | ENERGYPER100MLIFT              | 吨液百米耗电量     | NUMBER(8,2)   | kW·h/100m·t | Y        |        |                                                                                                |
| 54       | AREA                           | 功图面积           | NUMBER(8,2)   |             | Y        |        |                                                                                                |
| 55       | RODFLEXLENGTH                  | 抽油杆伸长量       | NUMBER(8,2)   | m           | Y        |        |                                                                                                |
| 56       | TUBINGFLEXLENGTH               | 油管伸缩量         | NUMBER(8,2)   | m           | Y        |        |                                                                                                |
| 57       | INERTIALENGTH                  | 惯性增量           | NUMBER(8,2)   | m           | Y        |        |                                                                                                |
| 58       | PUMPEFF1                       | 冲程损失系数       | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 59       | PUMPEFF2                       | 充满系数           | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 60       | PUMPEFF3                       | 间隙漏失系数       | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 61       | PUMPEFF4                       | 液体收缩系数       | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 62       | PUMPEFF                        | 总泵效             | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 63       | PUMPINTAKEP                    | 泵入口压力         | NUMBER(8,2)   | MPa         | Y        |        |                                                                                                |
| 64       | PUMPINTAKET                    | 泵入口温度         | NUMBER(8,2)   | ℃           | Y        |        |                                                                                                |
| 65       | PUMPINTAKEGOL                  | 泵入口就地气液比   | NUMBER(8,2)   | m\^3/m\^3   | Y        |        |                                                                                                |
| 66       | PUMPINTAKEVISL                 | 泵入口液体粘度     | NUMBER(8,2)   | mPa·s       | Y        |        |                                                                                                |
| 67       | PUMPINTAKEBO                   | 泵入口原油体积系数 | NUMBER(8,2)   | 小数        | Y        |        |                                                                                                |
| 68       | PUMPOUTLETP                    | 泵出口压力         | NUMBER(8,2)   | MPa         | Y        |        |                                                                                                |
| 69       | PUMPOUTLETT                    | 泵出口温度         | NUMBER(8,2)   | ℃           | Y        |        |                                                                                                |
| 70       | PUMPOUTLETGOL                  | 泵出口就地气液比   | NUMBER(8,2)   | m\^3/m\^3   | Y        |        |                                                                                                |
| 71       | PUMPOUTLETVISL                 | 泵出口液体粘度     | NUMBER(8,2)   | mPa·s       | Y        |        |                                                                                                |
| 72       | PUMPOUTLETBO                   | 泵出口原油体积系数 | NUMBER(8,2)   | 小数        | Y        |        |                                                                                                |
| 73       | RODSTRING                      | 抽油杆柱分析数据   | VARCHAR2(200) |             | Y        |        | 格式：杆数,总杆长,总杆重,总浮力;一级杆最大应力,一级杆最小应力,一级杆许用应力,一级杆应力范围比… |
| 74       | SAVETIME                       | 入库时间           | DATE          |             | Y        |        | SYSDATE                                                                                        |
| 75       | PRODUCTIONDATAID               | 生产数据编号       | NUMBER(10)    |             | Y        |        | 关联生产数据历史表                                                                             |
| 76       | RESULTSTATUS                   | 计算标志           | NUMBER(2)     |             | Y        |        |                                                                                                |
| 77       | INVERRESULTSTATUS              | 功图反演状态       | NUMBER(2)     |             | Y        |        |                                                                                                |
| 78       | REMARK                         | 备注               | VARCHAR2(200) |             | Y        |        |                                                                                                |
| 79       | POSITION360_CURVE              | 360度均分位移曲线  | CLOB          | m           | Y        |        | 360度均分位移曲线                                                                              |
| 80       | ANGLE360_CURVE                 | 360度均分角度曲线  | CLOB          | °           | Y        |        | 360度均分角度曲线                                                                              |
| 81       | LOAD360_CURVE                  | 360度均分载荷曲线  | CLOB          | kN          | Y        |        | 360度均分载荷曲线                                                                              |
| 82       | SIGNAL                         | 信号强度           | NUMBER(8,2)   |             | Y        |        |                                                                                                |
| 83       | INTERVAL                       | 传输间隔           | NUMBER(10)    | min         | Y        |        |                                                                                                |
| 84       | DEVICEVER                      | 设备版本信息       | VARCHAR2(50)  |             | Y        |        |                                                                                                |
| 85       | DISCRETEDATAID                 | 离散数据编号       | NUMBER(10)    |             | Y        |        |                                                                                                |
| 86       | PLUNGERSTROKE                  | 柱塞冲程           | NUMBER(8,2)   | m           | Y        |        |                                                                                                |
| 87       | AVAILABLEPLUNGERSTROKE         | 柱塞有效冲程       | NUMBER(8,2)   | m           | Y        |        |                                                                                                |
| 88       | IA_CURVE                       | a相电流曲线        | CLOB          |             | Y        |        |                                                                                                |
| 89       | IB_CURVE                       | b相电流曲线        | CLOB          |             | Y        |        |                                                                                                |
| 90       | IC_CURVE                       | c相电流曲线        | CLOB          |             | Y        |        |                                                                                                |
| 91       | DELTARADIUS                    | 曲柄平衡移动距离   | NUMBER(8,2)   | cm          | Y        |        |                                                                                                |
| 92       | CRANKANGLE                     | 曲柄转角曲线       | CLOB          |             | Y        |        |                                                                                                |
| 93       | POLISHRODV                     | 速度曲线           | CLOB          |             | Y        |        |                                                                                                |
| 94       | POLISHRODA                     | 加速度曲线         | CLOB          |             | Y        |        |                                                                                                |
| 95       | PR                             | 位置因数曲线       | CLOB          |             | Y        |        |                                                                                                |
| 96       | TF                             | 扭矩因数曲线       | CLOB          |             | Y        |        |                                                                                                |
| 97       | LOADTORQUE                     | 载荷扭矩曲线       | CLOB          |             | Y        |        |                                                                                                |
| 98       | CRANKTORQUE                    | 曲柄扭矩曲线       | CLOB          |             | Y        |        |                                                                                                |
| 99       | CURRENTBALANCETORQUE           | 目前平衡块扭矩曲线 | CLOB          |             | Y        |        |                                                                                                |
| 100      | CURRENTNETTORQUE               | 目前净扭矩曲线     | CLOB          |             | Y        |        |                                                                                                |
| 101      | EXPECTEDBALANCETORQUE          | 预期平衡块扭矩曲线 | CLOB          |             | Y        |        |                                                                                                |
| 102      | EXPECTEDNETTORQUE              | 预期净扭矩曲线     | CLOB          |             | Y        |        |                                                                                                |
| 103      | WELLBORESLICE                  | 井身切片曲线       | CLOB          |             | Y        |        |                                                                                                |
| 104      | LEVELCORRECTVALUE              | 液面反演校正值     | NUMBER(8,2)   | MPa         | Y        |        |                                                                                                |
| 105      | INVERPRODUCINGFLUIDLEVEL       | 液面反演结果值     | NUMBER(8,2)   | m           | Y        |        |                                                                                                |
| 106      | NOLIQUIDFULLNESSCOEFFICIENT    | 抽空充满系数       | NUMBER(10,4)  | 小数        | Y        |        |                                                                                                |
| 107      | NOLIQUIDAVAILABLEPLUNGERSTROKE | 抽空柱塞有效冲程   | NUMBER(10,4)  | m           | Y        |        |                                                                                                |
| 108      | SMAXINDEX                      | 位移最大值索引     | NUMBER(10)    |             | Y        |        |                                                                                                |
| 109      | SMININDEX                      | 位移最小值索引     | NUMBER(10)    |             | Y        |        |                                                                                                |

### 1.3.20 tbl_rpc_diagram_hist

同tbl_rpc_diagram_latest

### 1.3.21 tbl_rpc_diagram_total

表1-19 抽油机曲线累计数据表

| **序号** | **代码**                   | **名称**           | **类型**     | **单位**    | **为空** | **键** | **备注** |
|----------|----------------------------|--------------------|--------------|-------------|----------|--------|----------|
| 1        | ID                         | 记录编号           | NUMBER(10)   |             | N        | 主键   |          |
| 2        | WELLID                     | 井编号             | NUMBER(10)   |             | N        |        |          |
| 3        | COMMSTATUS                 | 通信状态           | NUMBER(2)    |             | Y        |        |          |
| 4        | COMMTIME                   | 在线时间           | NUMBER(8,2)  |             | Y        |        |          |
| 5        | COMMTIMEEFFICIENCY         | 在线时率           | NUMBER(12,3) |             | Y        |        |          |
| 6        | COMMRANGE                  | 在线区间           | CLOB         |             | Y        |        |          |
| 7        | RUNSTATUS                  | 运行状态           | NUMBER(2)    |             | Y        |        |          |
| 8        | RUNTIME                    | 运行时间           | NUMBER(8,2)  |             | Y        |        |          |
| 9        | RUNTIMEEFFICIENCY          | 运行时率           | NUMBER(12,3) |             | Y        |        |          |
| 10       | RUNRANGE                   | 运行区间           | CLOB         |             | Y        |        |          |
| 11       | ACQTIME                    | 功图采集时间       | DATE         |             | Y        |        |          |
| 12       | RESULTCODE                 | 工况代码           | NUMBER(4)    |             | Y        |        |          |
| 13       | STROKE                     | 冲程               | NUMBER(8,2)  |             | Y        |        |          |
| 14       | SPM                        | 冲次               | NUMBER(8,2)  |             | Y        |        |          |
| 15       | FMAX                       | 最大载荷           | NUMBER(8,2)  |             | Y        |        |          |
| 16       | FMIN                       | 最小载荷           | NUMBER(8,2)  |             | Y        |        |          |
| 17       | FULLNESSCOEFFICIENT        | 充满系数           | NUMBER(10,4) | 小数        | Y        |        |          |
| 18       | LIQUIDVOLUMETRICPRODUCTION | 日累计产液量方     | NUMBER(8,2)  |             | Y        |        |          |
| 19       | OILVOLUMETRICPRODUCTION    | 日累计产油量方     | NUMBER(8,2)  |             | Y        |        |          |
| 20       | WATERVOLUMETRICPRODUCTION  | 日累计产水量方     | NUMBER(8,2)  |             | Y        |        |          |
| 21       | VOLUMEWATERCUT             | 体积含水率         | NUMBER(10,4) | 小数        | Y        |        |          |
| 22       | LIQUIDWEIGHTPRODUCTION     | 日累计产液量吨     | NUMBER(8,2)  |             | Y        |        |          |
| 23       | OILWEIGHTPRODUCTION        | 日累计产油量吨     | NUMBER(8,2)  |             | Y        |        |          |
| 24       | WATERWEIGHTPRODUCTION      | 日累计产水量吨     | NUMBER(8,2)  |             | Y        |        |          |
| 25       | WEIGHTWATERCUT             | 重量含水率         | NUMBER(10,4) | 小数        | Y        |        |          |
| 26       | WATTDEGREEBALANCE          | 功率平衡度         | NUMBER(8,2)  |             | Y        |        |          |
| 27       | IDEGREEBALANCE             | 电流平衡度         | NUMBER(8,2)  |             | Y        |        |          |
| 28       | DELTARADIUS                | 曲柄平衡块移动距离 | NUMBER(8,2)  |             | Y        |        |          |
| 29       | SYSTEMEFFICIENCY           | 系统效率           | NUMBER(10,4) | 小数        | Y        |        |          |
| 30       | SURFACESYSTEMEFFICIENCY    | 地面效率           | NUMBER(10,4) | 小数        | Y        |        |          |
| 31       | WELLDOWNSYSTEMEFFICIENCY   | 井下效率           | NUMBER(10,4) | 小数        | Y        |        |          |
| 32       | ENERGYPER100MLIFT          | 吨液百米耗电量     | NUMBER(8,2)  | kW·h/100m·t | Y        |        |          |
| 33       | PUMPEFF                    | 总泵效             | NUMBER(10,4) |             | Y        |        |          |

### 1.3.22 tbl_rpc_worktype

表1-20 抽油机工况类型表

| **序号** | **代码**               | **名称** | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|------------------------|----------|---------------|----------|----------|--------|----------|
| 1        | ID                     | 记录编号 | NUMBER(10)    |          | N        | 主键   |          |
| 2        | RESULTCODE             | 工况类型 | NUMBER(4)     |          | N        |        |          |
| 3        | RESULTNAME             | 工况名称 | VARCHAR2(200) |          | N        |        |          |
| 4        | RESULTDESCRIPTION      | 工况说明 | VARCHAR2(200) |          | Y        |        |          |
| 5        | RESULTTEMPLATE         | 工况模板 | BLOB          |          | Y        |        |          |
| 6        | OPTIMIZATIONSUGGESTION | 优化建议 | VARCHAR2(200) |          | Y        |        |          |
| 7        | REMARK                 | 备注     | VARCHAR2(200) |          | Y        |        |          |

### 1.3.23 tbl_rpc_alarmtype_conf

表1-21 抽油机报警类型表

| **序号** | **代码**   | **名称** | **类型**      | **单位** | **为空** | **键** | **备注**                                                                                                                                                                     |
|----------|------------|----------|---------------|----------|----------|--------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1        | ID         | 记录编号 | NUMBER(10)    |          | N        | 主键   |                                                                                                                                                                              |
| 2        | RESULTCODE | 工况类型 | NUMBER(10)    |          | N        |        |                                                                                                                                                                              |
| 3        | ALARMTYPE  | 报警类型 | NUMBER(3)     |          | N        |        | 100-通信报警，200-测试报警，300-视频和RFID报警，301-视频，302-RFID报警，400-工况报警，500-平衡报警，600-设备报警，601-载荷传感器报警，602-压力传感器报警，603-温度传感器报警 |
| 4        | ALARMLEVEL | 报警级别 | NUMBER(3)     |          | N        |        | 100-一级报警，200-二级报警，300-三级报警，400-四级报警                                                                                                                       |
| 5        | ALARMSIGN  | 报警标志 | NUMBER(1)     |          | Y        |        | 0-正常，1-报警                                                                                                                                                               |
| 6        | REMARK     | 备注     | VARCHAR2(200) |          | Y        |        |                                                                                                                                                                              |

### 1.3.24 tbl_rpc_total_day

表1-22 抽油机日累计数据表

| **序号** | **代码**                       | **名称**                         | **类型**     | **单位**    | **为空** | **键** | **备注**      |
|----------|--------------------------------|----------------------------------|--------------|-------------|----------|--------|---------------|
| 1        | ID                             | 记录编号                         | NUMBER(10)   |             | N        | 主键   |               |
| 2        | WELLID                         | 井编号                           | NUMBER(10)   |             | Y        |        |               |
| 3        | CALCULATEDATE                  | 计算时间                         | DATE         |             | Y        |        |               |
| 4        | COMMSTATUS                     | 通信状态                         | NUMBER(2)    |             | Y        |        | 0-离线 1-在线 |
| 5        | RUNSTATUS                      | 运行状态                         | NUMBER(2)    |             | Y        |        | 0-停止 1-运行 |
| 6        | COMMTIME                       | 在线时间                         | NUMBER(8,2)  | h           | Y        |        |               |
| 7        | COMMTIMEEFFICIENCY             | 在线时率                         | NUMBER(12,3) |             | Y        |        |               |
| 8        | COMMRANGE                      | 在线区间                         | CLOB         |             | Y        |        |               |
| 9        | RUNTIME                        | 运行时间                         | NUMBER(8,2)  | h           | Y        |        |               |
| 10       | RUNTIMEEFFICIENCY              | 生产时率                         | NUMBER(12,3) | 小数        | Y        |        |               |
| 11       | RUNRANGE                       | 运行区间                         | CLOB         |             | Y        |        |               |
| 12       | RESULTCODE                     | 功图工况代码                     | NUMBER(4)    |             | Y        |        |               |
| 13       | RESULTSTRING                   | 功图工况字符串                   | CLOB         |             | Y        |        |               |
| 14       | RESULTCODE_E                   | 电参工况类型                     | NUMBER(4)    |             | Y        |        |               |
| 15       | RESULTSTRING_E                 | 电参工况字符串                   | CLOB         |             | Y        |        |               |
| 16       | FULLNESSCOEFFICIENT            | 功图充满系数                     | NUMBER(10,4) | 小数        | Y        |        |               |
| 17       | FULLNESSCOEFFICIENTMAX         | 功图充满系数最大值               | NUMBER(10,4) | 小数        | Y        |        |               |
| 18       | FULLNESSCOEFFICIENTMIN         | 功图充满系数最小值               | NUMBER(10,4) | 小数        | Y        |        |               |
| 19       | STROKE                         | 冲程                             | NUMBER(8,2)  | m           | Y        |        |               |
| 20       | STROKEMAX                      | 冲程最大值                       | NUMBER(8,2)  | m           | Y        |        |               |
| 21       | STROKEMIN                      | 冲程最小值                       | NUMBER(8,2)  | m           | Y        |        |               |
| 22       | SPM                            | 冲次                             | NUMBER(8,2)  | 次/min      | Y        |        |               |
| 23       | SPMMAX                         | 冲次最大值                       | NUMBER(8,2)  | 次/min      | Y        |        |               |
| 24       | SPMMIN                         | 冲次最小值                       | NUMBER(8,2)  | 次/min      | Y        |        |               |
| 25       | LIQUIDVOLUMETRICPRODUCTION     | 产液量方                         | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 26       | OILVOLUMETRICPRODUCTION        | 产油量方                         | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 27       | WATERVOLUMETRICPRODUCTION      | 产水量方                         | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 28       | LIQUIDWEIGHTPRODUCTION         | 产液量吨                         | NUMBER(8,2)  | t/d         | Y        |        |               |
| 29       | OILWEIGHTPRODUCTION            | 产油量吨                         | NUMBER(8,2)  | t/d         | Y        |        |               |
| 30       | WATERWEIGHTPRODUCTION          | 产水量吨                         | NUMBER(8,2)  | t/d         | Y        |        |               |
| 31       | LIQUIDVOLUMETRICPRODUCTIONMAX  | 产液量最大值方                   | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 32       | LIQUIDVOLUMETRICPRODUCTIONMIN  | 产液量最小值方                   | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 33       | OILVOLUMETRICPRODUCTIONMAX     | 产油量最大值方                   | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 34       | OILVOLUMETRICPRODUCTIONMIN     | 产油量最小值方                   | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 35       | WATERVOLUMETRICPRODUCTIONMAX   | 产水量最大值方                   | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 36       | WATERVOLUMETRICPRODUCTIONMIN   | 产水量最小值方                   | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 37       | LIQUIDWEIGHTPRODUCTIONMAX      | 产液量最大值吨                   | NUMBER(8,2)  | t/d         | Y        |        |               |
| 38       | LIQUIDWEIGHTPRODUCTIONMIN      | 产液量最小值吨                   | NUMBER(8,2)  | t/d         | Y        |        |               |
| 39       | OILWEIGHTPRODUCTIONMAX         | 产油量最大值吨                   | NUMBER(8,2)  | t/d         | Y        |        |               |
| 40       | OILWEIGHTPRODUCTIONMIN         | 产油量最小值吨                   | NUMBER(8,2)  | t/d         | Y        |        |               |
| 41       | WATERWEIGHTPRODUCTIONMAX       | 产水量最大值吨                   | NUMBER(8,2)  | t/d         | Y        |        |               |
| 42       | WATERWEIGHTPRODUCTIONMIN       | 产水量最小值吨                   | NUMBER(8,2)  | t/d         | Y        |        |               |
| 43       | WATTDEGREEBALANCE              | 功率平衡度                       | NUMBER(8,2)  | %           | Y        |        |               |
| 44       | IDEGREEBALANCE                 | 电流平衡度                       | NUMBER(8,2)  | %           | Y        |        |               |
| 45       | WATTDEGREEBALANCEMAX           | 功率平衡度最大值                 | NUMBER(8,2)  | %           | Y        |        |               |
| 46       | WATTDEGREEBALANCEMIN           | 功率平衡度最小值                 | NUMBER(8,2)  | %           | Y        |        |               |
| 47       | IDEGREEBALANCEMAX              | 电流平衡度最大值                 | NUMBER(8,2)  | %           | Y        |        |               |
| 48       | IDEGREEBALANCEMIN              | 电流平衡度最小值                 | NUMBER(8,2)  | %           | Y        |        |               |
| 49       | DELTARADIUS                    | 曲柄平衡移动距离                 | NUMBER(8,2)  | m           | Y        |        |               |
| 50       | DELTARADIUSMAX                 | 移动距离最大值                   | NUMBER(8,2)  | m           | Y        |        |               |
| 51       | DELTARADIUSMIN                 | 移动距离最小值                   | NUMBER(8,2)  | m           | Y        |        |               |
| 52       | VOLUMEWATERCUT                 | 体积含水率                       | NUMBER(10,4) | %           | Y        |        |               |
| 53       | WEIGHTWATERCUT                 | 重量含水率                       | NUMBER(10,4) | %           | Y        |        |               |
| 54       | VOLUMEWATERCUTMAX              | 体积含水率最大值                 | NUMBER(10,4) | %           | Y        |        |               |
| 55       | VOLUMEWATERCUTMIN              | 体积含水率最小值                 | NUMBER(10,4) | %           | Y        |        |               |
| 56       | WEIGHTWATERCUTMAX              | 重量含水率最大值                 | NUMBER(10,4) | %           | Y        |        |               |
| 57       | WEIGHTWATERCUTMIN              | 重量含水率最小值                 | NUMBER(10,4) | %           | Y        |        |               |
| 58       | TUBINGPRESSURE                 | 油压                             | NUMBER(8,2)  | MPa         | Y        |        |               |
| 59       | TUBINGPRESSUREMAX              | 油压最大值                       | NUMBER(8,2)  | MPa         | Y        |        |               |
| 60       | TUBINGPRESSUREMIN              | 油压最小值                       | NUMBER(8,2)  | MPa         | Y        |        |               |
| 61       | CASINGPRESSURE                 | 套压                             | NUMBER(8,2)  | MPa         | Y        |        |               |
| 62       | CASINGPRESSUREMAX              | 套压最大值                       | NUMBER(8,2)  | MPa         | Y        |        |               |
| 63       | CASINGPRESSUREMIN              | 套压最小值                       | NUMBER(8,2)  | MPa         | Y        |        |               |
| 64       | WELLHEADFLUIDTEMPERATURE       | 井口油温                         | NUMBER(8,2)  | ℃           | Y        |        |               |
| 65       | WELLHEADFLUIDTEMPERATUREMAX    | 井口油温最大值                   | NUMBER(8,2)  | ℃           | Y        |        |               |
| 66       | WELLHEADFLUIDTEMPERATUREMIN    | 井口油温最小值                   | NUMBER(8,2)  | ℃           | Y        |        |               |
| 67       | PRODUCTIONGASOILRATIO          | 生产气油比                       | NUMBER(8,2)  | m\^3/t      | Y        |        |               |
| 68       | PRODUCTIONGASOILRATIOMAX       | 生产气油比最大值                 | NUMBER(8,2)  | m\^3/t      | Y        |        |               |
| 69       | PRODUCTIONGASOILRATIOMIN       | 生产气油比最小值                 | NUMBER(8,2)  | m\^3/t      | Y        |        |               |
| 70       | PRODUCINGFLUIDLEVEL            | 动液面                           | NUMBER(8,2)  | m           | Y        |        |               |
| 71       | PRODUCINGFLUIDLEVELMAX         | 动液面最大值                     | NUMBER(8,2)  | m           | Y        |        |               |
| 72       | PRODUCINGFLUIDLEVELMIN         | 动液面最小值                     | NUMBER(8,2)  | m           | Y        |        |               |
| 73       | PUMPSETTINGDEPTH               | 泵挂                             | NUMBER(8,2)  | m           | Y        |        |               |
| 74       | PUMPSETTINGDEPTHMAX            | 泵挂最大值                       | NUMBER(8,2)  | m           | Y        |        |               |
| 75       | PUMPSETTINGDEPTHMIN            | 泵挂最小值                       | NUMBER(8,2)  | m           | Y        |        |               |
| 76       | SUBMERGENCE                    | 沉没度                           | NUMBER(8,2)  | m           | Y        |        |               |
| 77       | SUBMERGENCEMAX                 | 沉没度最大值                     | NUMBER(8,2)  | m           | Y        |        |               |
| 78       | SUBMERGENCEMIN                 | 沉没度最小值                     | NUMBER(8,2)  | m           | Y        |        |               |
| 79       | PUMPBOREDIAMETER               | 泵径                             | NUMBER(8,2)  | mm          | Y        |        |               |
| 80       | PUMPBOREDIAMETERMAX            | 泵径最大值                       | NUMBER(8,2)  | mm          | Y        |        |               |
| 81       | PUMPBOREDIAMETERMIN            | 泵径最小值                       | NUMBER(8,2)  | mm          | Y        |        |               |
| 82       | SYSTEMEFFICIENCY               | 系统效率                         | NUMBER(10,4) | 小数        | Y        |        |               |
| 83       | SYSTEMEFFICIENCYMAX            | 系统效率最大值                   | NUMBER(10,4) | 小数        | Y        |        |               |
| 84       | SYSTEMEFFICIENCYMIN            | 系统效率最小值                   | NUMBER(10,4) | 小数        | Y        |        |               |
| 85       | SURFACESYSTEMEFFICIENCY        | 地面效率                         | NUMBER(10,4) | 小数        | Y        |        |               |
| 86       | SURFACESYSTEMEFFICIENCYMAX     | 地面效率最大值                   | NUMBER(10,4) | 小数        | Y        |        |               |
| 87       | SURFACESYSTEMEFFICIENCYMIN     | 地面效率最小值                   | NUMBER(10,4) | 小数        | Y        |        |               |
| 88       | WELLDOWNSYSTEMEFFICIENCY       | 井下效率                         | NUMBER(10,4) | 小数        | Y        |        |               |
| 89       | WELLDOWNSYSTEMEFFICIENCYMAX    | 井下效率最大值                   | NUMBER(10,4) | 小数        | Y        |        |               |
| 90       | WELLDOWNSYSTEMEFFICIENCYMIN    | 井下效率最小值                   | NUMBER(10,4) | 小数        | Y        |        |               |
| 91       | ENERGYPER100MLIFT              | 吨液百米耗电量                   | NUMBER(8,2)  | kW·h/100m·t | Y        |        |               |
| 92       | ENERGYPER100MLIFTMAX           | 吨液百米耗电量最大值             | NUMBER(8,2)  | kW·h/100m·t | Y        |        |               |
| 93       | ENERGYPER100MLIFTMIN           | 吨液百米耗电量最小值             | NUMBER(8,2)  | kW·h/100m·t | Y        |        |               |
| 94       | PUMPEFF                        | 总泵效                           | NUMBER(10,4) | 小数        | Y        |        |               |
| 95       | PUMPEFFMAX                     | 总泵效最大值                     | NUMBER(10,4) | 小数        | Y        |        |               |
| 96       | PUMPEFFMIN                     | 总泵效最小值                     | NUMBER(10,4) | 小数        | Y        |        |               |
| 97       | IA                             | A相电流                          | NUMBER(8,2)  | A           | Y        |        |               |
| 98       | IAMAX                          | A相电流最大值                    | NUMBER(8,2)  | A           | Y        |        |               |
| 99       | IAMIN                          | A相电流最小值                    | NUMBER(8,2)  | A           | Y        |        |               |
| 100      | IB                             | B相电流                          | NUMBER(8,2)  | A           | Y        |        |               |
| 101      | IBMAX                          | B相电流最大值                    | NUMBER(8,2)  | A           | Y        |        |               |
| 102      | IBMIN                          | B相电流最小值                    | NUMBER(8,2)  | A           | Y        |        |               |
| 103      | IC                             | C相电流                          | NUMBER(8,2)  | A           | Y        |        |               |
| 104      | ICMAX                          | C相电流最大值                    | NUMBER(8,2)  | A           | Y        |        |               |
| 105      | ICMIN                          | C相电流最小值                    | NUMBER(8,2)  | A           | Y        |        |               |
| 106      | VA                             | A相电压                          | NUMBER(8,2)  | V           | Y        |        |               |
| 107      | VAMAX                          | A相电压最大值                    | NUMBER(8,2)  | V           | Y        |        |               |
| 108      | VAMIN                          | A相电压最小值                    | NUMBER(8,2)  | V           | Y        |        |               |
| 109      | VB                             | B相电压                          | NUMBER(8,2)  | V           | Y        |        |               |
| 110      | VBMAX                          | B相电压最大值                    | NUMBER(8,2)  | V           | Y        |        |               |
| 111      | VBMIN                          | B相电压最小值                    | NUMBER(8,2)  | V           | Y        |        |               |
| 112      | VC                             | C相电压                          | NUMBER(8,2)  | V           | Y        |        |               |
| 113      | VCMAX                          | C相电压最大值                    | NUMBER(8,2)  | V           | Y        |        |               |
| 114      | VCMIN                          | C相电压最小值                    | NUMBER(8,2)  | V           | Y        |        |               |
| 115      | WATT3                          | 有功功率                         | NUMBER(8,2)  | kW          | Y        |        |               |
| 116      | WATT3MAX                       | 有功功率最大值                   | NUMBER(8,2)  | kW          | Y        |        |               |
| 117      | WATT3MIN                       | 有功功率最小值                   | NUMBER(8,2)  | kW          | Y        |        |               |
| 118      | VAR3                           | 无功功率                         | NUMBER(8,2)  | kVar        | Y        |        |               |
| 119      | VAR3MAX                        | 无功功率最大值                   | NUMBER(8,2)  | kVar        | Y        |        |               |
| 120      | VAR3MIN                        | 无功功率最小值                   | NUMBER(8,2)  | kVar        | Y        |        |               |
| 121      | PF3                            | 功率因数                         | NUMBER(8,2)  |             | Y        |        |               |
| 122      | PF3MAX                         | 功率因数最大值                   | NUMBER(8,2)  |             | Y        |        |               |
| 123      | PF3MIN                         | 功率因数最小值                   | NUMBER(8,2)  |             | Y        |        |               |
| 124      | FREQUENCY                      | 运行频率                         | NUMBER(8,2)  | HZ          | Y        |        |               |
| 125      | FREQUENCYMAX                   | 运行频率最大值                   | NUMBER(8,2)  | HZ          | Y        |        |               |
| 126      | FREQUENCYMIN                   | 运行频率最小值                   | NUMBER(8,2)  | HZ          | Y        |        |               |
| 127      | TODAYKWATTH                    | 日有功功耗                       | NUMBER(10,2) | kW·h        | Y        |        |               |
| 128      | TODAYPKWATTH                   | 日正向有功功耗                   | NUMBER(10,2) | kW·h        | Y        |        |               |
| 129      | TODAYNKWATTH                   | 日反向有功功耗                   | NUMBER(10,2) | kW·h        | Y        |        |               |
| 130      | TODAYKVARH                     | 日无功功耗                       | NUMBER(10,2) | kVar·h      | Y        |        |               |
| 131      | TODAYPKVARH                    | 日正向无功功耗                   | NUMBER(10,2) | kVar·h      | Y        |        |               |
| 132      | TODAYNKVARH                    | 日反向无功功耗                   | NUMBER(10,2) | kVar·h      | Y        |        |               |
| 133      | TODAYKVAH                      | 日视在功耗                       | NUMBER(10,2) | kVA·h       | Y        |        |               |
| 134      | TOTALKWATTH                    | 累计有功功耗                     | NUMBER(10,2) | kW·h        | Y        |        |               |
| 135      | TOTALPKWATTH                   | 累计正向有功功耗                 | NUMBER(10,2) | kW·h        | Y        |        |               |
| 136      | TOTALNKWATTH                   | 累计反向有功功耗                 | NUMBER(10,2) | kW·h        | Y        |        |               |
| 137      | TOTALKVARH                     | 累计无功功耗                     | NUMBER(10,2) | kVar·h      | Y        |        |               |
| 138      | TOTALPKVARH                    | 累计正向无功功耗                 | NUMBER(10,2) | kVar·h      | Y        |        |               |
| 139      | TOTALNKVARH                    | 累计反向无功功耗                 | NUMBER(10,2) | kVar·h      | Y        |        |               |
| 140      | TOTALKVAH                      | 累计视在功耗                     | NUMBER(10,2) | kVA·h       | Y        |        |               |
| 141      | RPM                            | 螺杆泵转速                       | NUMBER(8,2)  | r/min       | Y        |        |               |
| 142      | RPMMAX                         | 螺杆泵转速最大值                 | NUMBER(8,2)  | r/min       | Y        |        |               |
| 143      | RPMMIN                         | 螺杆泵转速最小值                 | NUMBER(8,2)  | r/min       | Y        |        |               |
| 144      | EXTENDEDDAYS                   | 延用天数                         | NUMBER(5)    | d           | Y        |        |               |
| 145      | RESULTSTATUS                   | 计算标志                         | NUMBER(2)    |             | Y        |        |               |
| 146      | SIGNAL                         | 信号强度                         | NUMBER(8,2)  |             | Y        |        |               |
| 147      | SIGNALMAX                      | 信号强度最大值                   | NUMBER(8,2)  |             | Y        |        |               |
| 148      | SIGNALMIN                      | 信号强度最小值                   | NUMBER(8,2)  |             | Y        |        |               |
| 149      | F                              | 载荷                             | NUMBER(8,2)  | kN          | Y        |        |               |
| 150      | FMAX                           | 载荷最大值                       | NUMBER(8,2)  | kN          | Y        |        |               |
| 151      | FMIN                           | 载荷最小值                       | NUMBER(8,2)  | kN          | Y        |        |               |
| 152      | SAVETIME                       | 存储时间                         | DATE         |             | Y        |        | SYSDATE       |
| 153      | UPPERLOADLINE                  | 理论上载荷线值                   | NUMBER(8,2)  | kN          | Y        |        |               |
| 154      | UPPERLOADLINEMAX               | 理论上载荷线最大值               | NUMBER(8,2)  | kN          | Y        |        |               |
| 155      | UPPERLOADLINEMIN               | 理论上载荷线最小值               | NUMBER(8,2)  | kN          | Y        |        |               |
| 156      | LOWERLOADLINE                  | 理论下载荷线值                   | NUMBER(8,2)  | kN          | Y        |        |               |
| 157      | LOWERLOADLINEMAX               | 理论下载荷线最大值               | NUMBER(8,2)  | kN          | Y        |        |               |
| 158      | LOWERLOADLINEMIN               | 理论下载荷线最小值               | NUMBER(8,2)  | kN          | Y        |        |               |
| 159      | UPPERLOADLINEOFEXACT           | 考虑沉没压力的上载荷线值         | NUMBER(8,2)  | kN          | Y        |        |               |
| 160      | UPPERLOADLINEOFEXACTMAX        | 考虑沉没压力的上载荷线最大值     | NUMBER(8,2)  | kN          | Y        |        |               |
| 161      | UPPERLOADLINEOFEXACTMIN        | 考虑沉没压力的上载荷线最小值     | NUMBER(8,2)  | kN          | Y        |        |               |
| 162      | DELTALOADLINE                  | 理论液柱载荷值                   | NUMBER(8,2)  | kN          | Y        |        |               |
| 163      | DELTALOADLINEMAX               | 理论液柱载荷最大值               | NUMBER(8,2)  | kN          | Y        |        |               |
| 164      | DELTALOADLINEMIN               | 理论液柱载荷最小值               | NUMBER(8,2)  | kN          | Y        |        |               |
| 165      | DELTALOADLINEOFEXACT           | 考虑沉没压力的理论液柱载荷值     | NUMBER(8,2)  | kN          | Y        |        |               |
| 166      | DELTALOADLINEOFEXACTMAX        | 考虑沉没压力的理论液柱载荷最大值 | NUMBER(8,2)  | kN          | Y        |        |               |
| 167      | DELTALOADLINEOFEXACTMIN        | 考虑沉没压力的理论液柱载荷最小值 | NUMBER(8,2)  | kN          | Y        |        |               |
| 168      | FMAX_AVG                       | 最大载荷值                       | NUMBER(8,2)  | kN          | Y        |        |               |
| 169      | FMAX_MAX                       | 最大载荷最大值                   | NUMBER(8,2)  | kN          | Y        |        |               |
| 170      | FMAX_MIN                       | 最大载荷最小值                   | NUMBER(8,2)  | kN          | Y        |        |               |
| 171      | FMIN_AVG                       | 最小载荷值                       | NUMBER(8,2)  | kN          | Y        |        |               |
| 172      | FMIN_MAX                       | 最小载荷最大值                   | NUMBER(8,2)  | kN          | Y        |        |               |
| 173      | FMIN_MIN                       | 最小载荷最小值                   | NUMBER(8,2)  | kN          | Y        |        |               |
| 174      | DELTAF                         | 载荷差                           | NUMBER(8,2)  | kN          | Y        |        |               |
| 175      | DELTAFMAX                      | 载荷差最大值                     | NUMBER(8,2)  | kN          | Y        |        |               |
| 176      | DELTAFMIN                      | 载荷差最小值                     | NUMBER(8,2)  | kN          | Y        |        |               |
| 177      | AREA                           | 功图面积                         | NUMBER(8,2)  | kN·m        | Y        |        |               |
| 178      | AREAMAX                        | 功图面积最大值                   | NUMBER(8,2)  | kN·m        | Y        |        |               |
| 179      | AREAMIN                        | 功图面积最小值                   | NUMBER(8,2)  | kN·m        | Y        |        |               |
| 180      | PLUNGERSTROKE                  | 柱塞冲程                         | NUMBER(8,2)  | m           | Y        |        |               |
| 181      | PLUNGERSTROKEMAX               | 柱塞冲程最大值                   | NUMBER(8,2)  | m           | Y        |        |               |
| 182      | PLUNGERSTROKEMIN               | 柱塞冲程最小值                   | NUMBER(8,2)  | m           | Y        |        |               |
| 183      | AVAILABLEPLUNGERSTROKE         | 柱塞有效冲程                     | NUMBER(8,2)  | m           | Y        |        |               |
| 184      | AVAILABLEPLUNGERSTROKEMAX      | 柱塞有效冲程最大值               | NUMBER(8,2)  | m           | Y        |        |               |
| 185      | AVAILABLEPLUNGERSTROKEMIN      | 柱塞有效冲程最小值               | NUMBER(8,2)  | m           | Y        |        |               |
| 186      | THEORETICALPRODUCTION          | 理论排量                         | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 187      | THEORETICALPRODUCTIONMAX       | 理论排量最大值                   | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 188      | THEORETICALPRODUCTIONMIN       | 理论排量最小值                   | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 189      | AVAILABLESTROKEPROD_V          | 柱塞有效冲程计算产量方           | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 190      | AVAILABLESTROKEPROD_V_MAX      | 柱塞有效冲程计算产量方最大值     | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 191      | AVAILABLESTROKEPROD_V_MIN      | 柱塞有效冲程计算产量方最小值     | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 192      | PUMPCLEARANCELEAKPROD_V        | 泵间隙漏失量方                   | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 193      | PUMPCLEARANCELEAKPROD_V_MAX    | 泵间隙漏失量方最大值             | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 194      | PUMPCLEARANCELEAKPROD_V_MIN    | 泵间隙漏失量方最小值             | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 195      | TVLEAKVOLUMETRICPRODUCTION     | 游动凡尔漏失量方                 | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 196      | TVLEAKVOLUMETRICPRODUCTIONMAX  | 游动凡尔漏失量方最大值           | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 197      | TVLEAKVOLUMETRICPRODUCTIONMIN  | 游动凡尔漏失量方最小值           | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 198      | SVLEAKVOLUMETRICPRODUCTION     | 固定凡尔漏失量方                 | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 199      | SVLEAKVOLUMETRICPRODUCTIONMAX  | 固定凡尔漏失量方最大值           | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 200      | SVLEAKVOLUMETRICPRODUCTIONMIN  | 固定凡尔漏失量方最小值           | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 201      | GASINFLUENCEPROD_V             | 气影响方                         | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 202      | GASINFLUENCEPROD_V_MAX         | 气影响方最大值                   | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 203      | GASINFLUENCEPROD_V_MIN         | 气影响方最小值                   | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 204      | AVAILABLESTROKEPROD_W          | 柱塞有效冲程计算产量吨           | NUMBER(8,2)  | t/d         | Y        |        |               |
| 205      | AVAILABLESTROKEPROD_W_MAX      | 柱塞有效冲程计算产量吨最大值     | NUMBER(8,2)  | t/d         | Y        |        |               |
| 206      | AVAILABLESTROKEPROD_W_MIN      | 柱塞有效冲程计算产量吨最小值     | NUMBER(8,2)  | t/d         | Y        |        |               |
| 207      | PUMPCLEARANCELEAKPROD_W        | 泵间隙漏失量吨                   | NUMBER(8,2)  | t/d         | Y        |        |               |
| 208      | PUMPCLEARANCELEAKPROD_W_MAX    | 泵间隙漏失量吨最大值             | NUMBER(8,2)  | t/d         | Y        |        |               |
| 209      | PUMPCLEARANCELEAKPROD_W_MIN    | 泵间隙漏失量吨最小值             | NUMBER(8,2)  | t/d         | Y        |        |               |
| 210      | TVLEAKWEIGHTPRODUCTION         | 游动凡尔漏失量吨                 | NUMBER(8,2)  | t/d         | Y        |        |               |
| 211      | TVLEAKWEIGHTPRODUCTIONMAX      | 游动凡尔漏失量吨最大值           | NUMBER(8,2)  | t/d         | Y        |        |               |
| 212      | TVLEAKWEIGHTPRODUCTIONMIN      | 游动凡尔漏失量吨最小值           | NUMBER(8,2)  | t/d         | Y        |        |               |
| 213      | SVLEAKWEIGHTPRODUCTION         | 固定凡尔漏失量吨                 | NUMBER(8,2)  | t/d         | Y        |        |               |
| 214      | SVLEAKWEIGHTPRODUCTIONMAX      | 固定凡尔漏失量吨最大值           | NUMBER(8,2)  | t/d         | Y        |        |               |
| 215      | SVLEAKWEIGHTPRODUCTIONMIN      | 固定凡尔漏失量吨最小值           | NUMBER(8,2)  | t/d         | Y        |        |               |
| 216      | GASINFLUENCEPROD_W             | 气影响吨                         | NUMBER(8,2)  | t/d         | Y        |        |               |
| 217      | GASINFLUENCEPROD_W_MAX         | 气影响吨最大值                   | NUMBER(8,2)  | t/d         | Y        |        |               |
| 218      | GASINFLUENCEPROD_W_MIN         | 气影响吨最小值                   | NUMBER(8,2)  | t/d         | Y        |        |               |
| 219      | PUMPEFF1                       | 冲程损失系数                     | NUMBER(10,4) | 小数        | Y        |        |               |
| 220      | PUMPEFF1MAX                    | 冲程损失系数最大值               | NUMBER(10,4) | 小数        | Y        |        |               |
| 221      | PUMPEFF1MIN                    | 冲程损失系数最小值               | NUMBER(10,4) | 小数        | Y        |        |               |
| 222      | PUMPEFF2                       | 充满系数                         | NUMBER(10,4) | 小数        | Y        |        |               |
| 223      | PUMPEFF2MAX                    | 充满系数最大值                   | NUMBER(10,4) | 小数        | Y        |        |               |
| 224      | PUMPEFF2MIN                    | 充满系数最小值                   | NUMBER(10,4) | 小数        | Y        |        |               |
| 225      | PUMPEFF3                       | 间隙漏失系数                     | NUMBER(10,4) | 小数        | Y        |        |               |
| 226      | PUMPEFF3MAX                    | 间隙漏失系数最大值               | NUMBER(10,4) | 小数        | Y        |        |               |
| 227      | PUMPEFF3MIN                    | 间隙漏失系数最小值               | NUMBER(10,4) | 小数        | Y        |        |               |
| 228      | PUMPEFF4                       | 液体收缩系数                     | NUMBER(10,4) | 小数        | Y        |        |               |
| 229      | PUMPEFF4MAX                    | 液体收缩系数最大值               | NUMBER(10,4) | 小数        | Y        |        |               |
| 230      | PUMPEFF4MIN                    | 液体收缩系数最小值               | NUMBER(10,4) | 小数        | Y        |        |               |
| 231      | RODFLEXLENGTH                  | 抽油杆伸长量                     | NUMBER(8,2)  | m           | Y        |        |               |
| 232      | RODFLEXLENGTHMAX               | 抽油杆伸长量最大值               | NUMBER(8,2)  | m           | Y        |        |               |
| 233      | RODFLEXLENGTHMIN               | 抽油杆伸长量最小值               | NUMBER(8,2)  | m           | Y        |        |               |
| 234      | TUBINGFLEXLENGTH               | 计算油管伸缩值                   | NUMBER(8,2)  | m           | Y        |        |               |
| 235      | TUBINGFLEXLENGTHMAX            | 计算油管伸缩值最大值             | NUMBER(8,2)  | m           | Y        |        |               |
| 236      | TUBINGFLEXLENGTHMIN            | 计算油管伸缩值最小值             | NUMBER(8,2)  | m           | Y        |        |               |
| 237      | INERTIALENGTH                  | 惯性载荷增量                     | NUMBER(8,2)  | m           | Y        |        |               |
| 238      | INERTIALENGTHMAX               | 惯性载荷增量最大值               | NUMBER(8,2)  | m           | Y        |        |               |
| 239      | INERTIALENGTHMIN               | 惯性载荷增量最小值               | NUMBER(8,2)  | m           | Y        |        |               |
| 240      | PUMPINTAKEP                    | 泵入口压力                       | NUMBER(8,2)  | MPa         | Y        |        |               |
| 241      | PUMPINTAKEPMAX                 | 泵入口压力最大值                 | NUMBER(8,2)  | MPa         | Y        |        |               |
| 242      | PUMPINTAKEPMIN                 | 泵入口压力最小值                 | NUMBER(8,2)  | MPa         | Y        |        |               |
| 243      | PUMPINTAKET                    | 泵入口温度                       | NUMBER(8,2)  | ℃           | Y        |        |               |
| 244      | PUMPINTAKETMAX                 | 泵入口温度最大值                 | NUMBER(8,2)  | ℃           | Y        |        |               |
| 245      | PUMPINTAKETMIN                 | 泵入口温度最小值                 | NUMBER(8,2)  | ℃           | Y        |        |               |
| 246      | PUMPINTAKEGOL                  | 泵入口就地气液比                 | NUMBER(8,2)  | m\^3/m\^3   | Y        |        |               |
| 247      | PUMPINTAKEGOLMAX               | 泵入口就地气液比最大值           | NUMBER(8,2)  | m\^3/m\^3   | Y        |        |               |
| 248      | PUMPINTAKEGOLMIN               | 泵入口就地气液比最小值           | NUMBER(8,2)  | m\^3/m\^3   | Y        |        |               |
| 249      | PUMPINTAKEVISL                 | 泵入口粘度                       | NUMBER(8,2)  | mPa·s       | Y        |        |               |
| 250      | PUMPINTAKEVISLMAX              | 泵入口粘度最大值                 | NUMBER(8,2)  | mPa·s       | Y        |        |               |
| 251      | PUMPINTAKEVISLMIN              | 泵入口粘度最小值                 | NUMBER(8,2)  | mPa·s       | Y        |        |               |
| 252      | PUMPINTAKEBO                   | 泵入口原油体积系数               | NUMBER(8,2)  |             | Y        |        |               |
| 253      | PUMPINTAKEBOMAX                | 泵入口原油体积系数最大值         | NUMBER(8,2)  |             | Y        |        |               |
| 254      | PUMPINTAKEBOMIN                | 泵入口原油体积系数最小值         | NUMBER(8,2)  |             | Y        |        |               |
| 255      | PUMPOUTLETP                    | 泵出口压力                       | NUMBER(8,2)  | MPa         | Y        |        |               |
| 256      | PUMPOUTLETPMAX                 | 泵出口压力最大值                 | NUMBER(8,2)  | MPa         | Y        |        |               |
| 257      | PUMPOUTLETPMIN                 | 泵出口压力最小值                 | NUMBER(8,2)  | MPa         | Y        |        |               |
| 258      | PUMPOUTLETT                    | 泵出口温度                       | NUMBER(8,2)  | ℃           | Y        |        |               |
| 259      | PUMPOUTLETTMAX                 | 泵出口温度最大值                 | NUMBER(8,2)  | ℃           | Y        |        |               |
| 260      | PUMPOUTLETTMIN                 | 泵出口温度最小值                 | NUMBER(8,2)  | ℃           | Y        |        |               |
| 261      | PUMPOUTLETGOL                  | 泵出口就地气液比                 | NUMBER(8,2)  | m\^3/m\^3   | Y        |        |               |
| 262      | PUMPOUTLETGOLMAX               | 泵出口就地气液比最大值           | NUMBER(8,2)  | m\^3/m\^3   | Y        |        |               |
| 263      | PUMPOUTLETGOLMIN               | 泵出口就地气液比最小值           | NUMBER(8,2)  | m\^3/m\^3   | Y        |        |               |
| 264      | PUMPOUTLETVISL                 | 泵出口粘度                       | NUMBER(8,2)  | mPa·s       | Y        |        |               |
| 265      | PUMPOUTLETVISLMAX              | 泵出口粘度最大值                 | NUMBER(8,2)  | mPa·s       | Y        |        |               |
| 266      | PUMPOUTLETVISLMIN              | 泵出口粘度最小值                 | NUMBER(8,2)  | mPa·s       | Y        |        |               |
| 267      | PUMPOUTLETBO                   | 泵出口原油体积系数               | NUMBER(8,2)  |             | Y        |        |               |
| 268      | PUMPOUTLETBOMAX                | 泵出口原油体积系数最大值         | NUMBER(8,2)  |             | Y        |        |               |
| 269      | PUMPOUTLETBOMIN                | 泵出口原油体积系数最小值         | NUMBER(8,2)  |             | Y        |        |               |
| 270      | UPSTROKEWATTMAX                | 上冲程功率最大值                 | NUMBER(8,2)  | kW          | Y        |        |               |
| 271      | UPSTROKEWATTMAX_MAX            | 上冲程功率最大值的最大值         | NUMBER(8,2)  | kW          | Y        |        |               |
| 272      | UPSTROKEWATTMAX_MIN            | 上冲程功率最大值的最小值         | NUMBER(8,2)  | kW          | Y        |        |               |
| 273      | UPSTROKEIMAX                   | 上冲程电流最大值                 | NUMBER(8,2)  | A           | Y        |        |               |
| 274      | UPSTROKEIMAX_MAX               | 上冲程电流最大值的最大值         | NUMBER(8,2)  | A           | Y        |        |               |
| 275      | UPSTROKEIMAX_MIN               | 上冲程电流最大值的最小值         | NUMBER(8,2)  | A           | Y        |        |               |
| 276      | AVGWATT                        | 平均有功功率                     | NUMBER(8,2)  | kW          | Y        |        |               |
| 277      | AVGWATTMAX                     | 平均有功功率最大值               | NUMBER(8,2)  | kW          | Y        |        |               |
| 278      | AVGWATTMIN                     | 平均有功功率最小值               | NUMBER(8,2)  | kW          | Y        |        |               |
| 279      | POLISHRODPOWER                 | 光杆功率                         | NUMBER(8,2)  | kW          | Y        |        |               |
| 280      | POLISHRODPOWERMAX              | 光杆功率最大值                   | NUMBER(8,2)  | kW          | Y        |        |               |
| 281      | POLISHRODPOWERMIN              | 光杆功率最小值                   | NUMBER(8,2)  | kW          | Y        |        |               |
| 282      | WATERPOWER                     | 水功率                           | NUMBER(8,2)  | kW          | Y        |        |               |
| 283      | WATERPOWERMAX                  | 水功率最大值                     | NUMBER(8,2)  | kW          | Y        |        |               |
| 284      | WATERPOWERMIN                  | 水功率最小值                     | NUMBER(8,2)  | kW          | Y        |        |               |
| 285      | VA3                            | 视在功率                         | NUMBER(8,2)  | kVA         | Y        |        |               |
| 286      | VA3MAX                         | 视在功率最大值                   | NUMBER(8,2)  | kVA         | Y        |        |               |
| 287      | VA3MIN                         | 视在功率最小值                   | NUMBER(8,2)  | kVA         | Y        |        |               |
| 288      | DOWNSTROKEWATTMAX              | 下冲程功率最大值                 | NUMBER(8,2)  | kW          | Y        |        |               |
| 289      | DOWNSTROKEWATTMAX_MAX          | 下冲程功率最大值的最大值         | NUMBER(8,2)  | kW          | Y        |        |               |
| 290      | DOWNSTROKEWATTMAX_MIN          | 下冲程功率最大值的最小值         | NUMBER(8,2)  | kW          | Y        |        |               |
| 291      | DOWNSTROKEIMAX                 | 下冲程电流最大值                 | NUMBER(8,2)  | A           | Y        |        |               |
| 292      | DOWNSTROKEIMAX_MAX             | 下冲程电流最大值的最大值         | NUMBER(8,2)  | A           | Y        |        |               |
| 293      | DOWNSTROKEIMAX_MIN             | 下冲程电流最大值的最小值         | NUMBER(8,2)  | A           | Y        |        |               |
| 294      | LEVELCORRECTVALUE              | 液面反演校正值                   | NUMBER(8,2)  | MPa         | Y        |        |               |
| 295      | LEVELCORRECTVALUEMAX           | 液面反演校正最大值               | NUMBER(8,2)  | MPa         | Y        |        |               |
| 296      | LEVELCORRECTVALUEMIN           | 液面反演校正最小值               | NUMBER(8,2)  | MPa         | Y        |        |               |
| 297      | NOLIQUIDFULLNESSCOEFFICIENT    | 抽空充满系数                     | NUMBER(10,4) | 小数        | Y        |        |               |
| 298      | NOLIQUIDFULLNESSCOEFFICIENTMAX | 抽空充满系数最大值               | NUMBER(10,4) | 小数        | Y        |        |               |
| 299      | NOLIQUIDFULLNESSCOEFFICIENTMIN | 抽空充满系数最小值               | NUMBER(10,4) | 小数        | Y        |        |               |
| 300      | NOLIQUIDAVAILABLESTROKE        | 抽空柱塞有效冲程                 | NUMBER(10,4) | m           | Y        |        |               |
| 301      | NOLIQUIDAVAILABLESTROKEMAX     | 抽空柱塞有效冲程最大值           | NUMBER(10,4) | m           | Y        |        |               |
| 302      | NOLIQUIDAVAILABLESTROKEMIN     | 抽空柱塞有效冲程最小值           | NUMBER(10,4) | m           | Y        |        |               |

### 1.3.25 tbl_rpc_statistics_conf

表1-23 抽油机统计配置表

| **序号** | **代码** | **名称**   | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|----------|------------|--------------|----------|----------|--------|----------|
| 1        | ID       | 记录编号   | NUMBER(10)   |          | N        | 主键   |          |
| 2        | S_LEVEL  | 级别       | VARCHAR2(50) |          | Y        |        |          |
| 3        | S_CODE   | 代码       | NUMBER(4)    |          | Y        |        |          |
| 4        | S_MIN    | 范围最小值 | NUMBER(11,3) |          | Y        |        |          |
| 5        | S_MAX    | 范围最大值 | NUMBER(11,3) |          | Y        |        |          |
| 6        | S_TYPE   | 统计类型   | VARCHAR2(20) |          | Y        |        |          |

### 1.3.26 tbl_rpcinformation

表1-24 抽油机设备表

| **序号** | **代码**                      | **名称**         | **类型**      | **单位** | **为空** | **键** | **备注**                             |
|----------|-------------------------------|------------------|---------------|----------|----------|--------|--------------------------------------|
| 1        | ID                            | 记录编号         | NUMBER(10)    |          | N        | 主键   |                                      |
| 2        | WELLID                        | 井编号           | NUMBER(10)    |          | Y        |        |                                      |
| 3        | MANUFACTURER                  | 抽油机厂家       | VARCHAR2(200) |          | Y        |        |                                      |
| 4        | MODEL                         | 抽油机型号       | VARCHAR2(200) |          | Y        |        |                                      |
| 5        | STROKE                        | 冲程             | NUMBER(8,2)   | m        | Y        |        |                                      |
| 6        | CRANKROTATIONDIRECTION        | 旋转方向         | VARCHAR2(200) |          | Y        |        | Clockwise-顺时针Anticlockwise-逆时针 |
| 7        | OFFSETANGLEOFCRANK            | 曲柄偏置角       | NUMBER(8,2)   | 度       | Y        |        |                                      |
| 8        | CRANKGRAVITYRADIUS            | 曲柄重心半径     | NUMBER(10,4)  | m        | Y        |        |                                      |
| 9        | SINGLECRANKWEIGHT             | 单块曲柄重量     | NUMBER(8,2)   | kN       | Y        |        |                                      |
| 10       | SINGLECRANKPINWEIGHT          | 单块曲柄销重量   | NUMBER(8,2)   | kN       | Y        |        |                                      |
| 11       | STRUCTURALUNBALANCE           | 结构不平衡重     | NUMBER(8,2)   | kN       | Y        |        |                                      |
| 12       | GEARREDUCERRATIO              | 减速箱传动比     | NUMBER(10,4)  | %        | Y        |        |                                      |
| 13       | GEARREDUCERBELTPULLEYDIAMETER | 减速箱皮带轮直径 | NUMBER(10,4)  | m        | Y        |        |                                      |
| 14       | BALANCEPOSITION               | 平衡块位置       | VARCHAR2(200) | m        | Y        |        |                                      |
| 15       | BALANCEWEIGHT                 | 平衡块重量       | VARCHAR2(200) | kN       | Y        |        |                                      |
| 16       | PRTF                          | 位置扭矩因数     | CLOB          |          | Y        |        |                                      |

### 1.3.27 tbl_rpc_motor

表1-25 抽油机电机数据表

| **序号** | **代码**           | **名称**   | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|--------------------|------------|---------------|----------|----------|--------|----------|
| 1        | ID                 | 记录编号   | NUMBER(10)    |          | N        | 主键   |          |
| 2        | WELLID             | 井编号     | NUMBER(10)    |          | N        |        |          |
| 3        | MANUFACTURER       | 电机厂家   | VARCHAR2(200) |          | N        |        |          |
| 4        | MODEL              | 电机型号   | VARCHAR2(200) |          | N        |        |          |
| 5        | SYNCHROSPEED       | 同步转速   | NUMBER(8,2)   | r/min    | Y        |        |          |
| 6        | BELTPULLEYDIAMETER | 皮带轮直径 | NUMBER(10,4)  | m        | Y        |        |          |
| 7        | PERFORMANCECURVER  | 特性曲线   | CLOB          |          | Y        |        |          |

### 1.3.28 tbl_rpc_inver_opt

表1-26 抽油机电参反演参数优化表

| **序号** | **代码**                | **名称**             | **类型**    | **单位** | **为空** | **键** | **备注** |
|----------|-------------------------|----------------------|-------------|----------|----------|--------|----------|
| 1        | ID                      | 记录编号             | NUMBER(10)  |          | N        | 主键   |          |
| 2        | WELLID                  | 井编号               | NUMBER(10)  |          | Y        |        |          |
| 3        | OFFSETANGLEOFCRANKPS    | 曲柄位置开关偏置角   | NUMBER(8,2) | 度       | Y        |        |          |
| 4        | SURFACESYSTEMEFFICIENCY | 地面效率             | NUMBER(8,2) | 小数     | Y        |        |          |
| 5        | FS_LEFTPERCENT          | 功图左侧截取百分比   | NUMBER(8,2) | %        | Y        |        |          |
| 6        | FS_RIGHTPERCENT         | 功图右侧截取百分比   | NUMBER(8,2) | %        | Y        |        |          |
| 7        | FILTERTIME_WATT         | 功率曲线滤波次数     | NUMBER(3)   |          | Y        |        |          |
| 8        | FILTERTIME_I            | 电流曲线滤波次数     | NUMBER(3)   |          | Y        |        |          |
| 9        | FILTERTIME_FSDIAGRAM    | 地面功图滤波次数     | NUMBER(3)   |          | Y        |        |          |
| 10       | FILTERTIME_RPM          | 转速曲线滤波次数     | NUMBER(3)   |          | Y        |        |          |
| 11       | FILTERTIME_FSDIAGRAM_L  | 地面功图左侧滤波次数 | NUMBER(3)   |          | Y        |        |          |
| 12       | FILTERTIME_FSDIAGRAM_R  | 地面功图右侧滤波次数 | NUMBER(3)   |          | Y        |        |          |
| 13       | WATTANGLE               | 功率滤波角度         | NUMBER(8,2) | 度       | Y        |        |          |

### 1.3.29 tbl_pcp_productiondata_latest

表1-27 螺杆泵生产数据实时表

| **序号** | **代码**                   | **名称**       | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|----------------------------|----------------|---------------|----------|----------|--------|----------|
| 1        | ID                         | 记录编号       | NUMBER(10)    |          | N        | 主键   |          |
| 2        | WELLID                     | 井编号         | NUMBER(10)    |          | N        |        |          |
| 3        | ACQTIME                    | 采集时间       | DATE          |          | Y        |        |          |
| 4        | LIFTINGTYPE                | 举升类型       | NUMBER(10)    |          | Y        |        |          |
| 5        | DISPLACEMENTTYPE           | 驱替类型       | NUMBER(1)     |          | Y        |        |          |
| 6        | RUNTIME                    | 生产时间       | NUMBER(8,2)   | h        | Y        |        |          |
| 7        | CRUDEOILDENSITY            | 原油密度       | NUMBER(16,2)  | g/cm\^3  | Y        |        |          |
| 8        | WATERDENSITY               | 水密度         | NUMBER(16,2)  | g/cm\^3  | Y        |        |          |
| 9        | NATURALGASRELATIVEDENSITY  | 天然气相对密度 | NUMBER(16,2)  |          | Y        |        |          |
| 10       | SATURATIONPRESSURE         | 饱和压力       | NUMBER(16,2)  | MPa      | Y        |        |          |
| 11       | RESERVOIRDEPTH             | 油层中部深度   | NUMBER(16,2)  | m        | Y        |        |          |
| 12       | RESERVOIRTEMPERATURE       | 油层中部温度   | NUMBER(16,2)  | ℃        | Y        |        |          |
| 13       | VOLUMEWATERCUT             | 体积含水率     | NUMBER(8,2)   | %        | Y        |        |          |
| 14       | WEIGHTWATERCUT             | 重量含水率     | NUMBER(8,2)   | %        | Y        |        |          |
| 15       | TUBINGPRESSURE             | 油压           | NUMBER(8,2)   | MPa      | Y        |        |          |
| 16       | CASINGPRESSURE             | 套压           | NUMBER(8,2)   | MPa      | Y        |        |          |
| 17       | BACKPRESSURE               | 回压           | NUMBER(8,2)   | MPa      | Y        |        |          |
| 18       | WELLHEADFLUIDTEMPERATURE   | 井口流温       | NUMBER(8,2)   | ℃        | Y        |        |          |
| 19       | PRODUCINGFLUIDLEVEL        | 动液面         | NUMBER(8,2)   | m        | Y        |        |          |
| 20       | PUMPSETTINGDEPTH           | 泵挂           | NUMBER(8,2)   | m        | Y        |        |          |
| 21       | PRODUCTIONGASOILRATIO      | 生产气油比     | NUMBER(8,2)   | m\^3/t   | Y        |        |          |
| 22       | TUBINGSTRINGINSIDEDIAMETER | 油管内径       | NUMBER(8,2)   | mm       | Y        |        |          |
| 23       | CASINGSTRINGINSIDEDIAMETER | 油层套管内径   | NUMBER(8,2)   | mm       | Y        |        |          |
| 24       | RODSTRING                  | 抽油杆参数     | VARCHAR2(200) |          | Y        |        |          |
| 25       | PUMPTYPE                   | 泵类型         | VARCHAR2(20)  |          | Y        |        |          |
| 26       | BARRELTYPE                 | 泵筒类型       | VARCHAR2(20)  |          | Y        |        |          |
| 27       | BARRELLENGTH               | 泵筒长         | NUMBER(8,2)   | m        | Y        |        |          |
| 28       | BARRELSERIES               | 泵级数         | NUMBER(8,2)   |          | Y        |        |          |
| 29       | ROTORDIAMETER              | 转子截面直径   | NUMBER(8,2)   | mm       | Y        |        |          |
| 30       | QPR                        | 公称排量       | NUMBER(8,2)   | m\^3/r   | Y        |        |          |
| 31       | MANUALINTERVENTION         | 人工干预       | NUMBER(4)     |          | Y        |        |          |
| 32       | NETGROSSRATIO              | 净毛比         | NUMBER(8,2)   |          | Y        |        |          |
| 33       | ANCHORINGSTATE             | 锚定状态       | NUMBER(1)     |          | Y        |        |          |
| 34       | PUMPGRADE                  | 泵级别         | NUMBER(1)     |          | Y        |        |          |
| 35       | PUMPBOREDIAMETER           | 泵径           | NUMBER(8,2)   |          | Y        |        |          |
| 36       | PLUNGERLENGTH              | 柱塞长         | NUMBER(8,2)   |          | Y        |        |          |
| 37       | RUNTIMEEFFICIENCYSOURCE    | 时率来源       | NUMBER(2)     |          | Y        |        |          |
| 38       | REMARK                     | 备注           | VARCHAR2(200) |          | Y        |        |          |

### 1.3.30 tbl_pcp_productiondata_hist

同tbl_pcp_productiondata_latest

### 1.3.31 tbl_pcp_discrete_latest

表1-28 螺杆泵离散数据实时表

| **序号** | **代码**                 | **名称**       | **类型**     | **单位** | **为空** | **键** | **备注**                |
|----------|--------------------------|----------------|--------------|----------|----------|--------|-------------------------|
| 1        | ID                       | 记录编号       | NUMBER(10)   |          | N        | 主键   |                         |
| 2        | WELLID                   | 井编号         | NUMBER(10)   |          | N        |        |                         |
| 3        | ACQTIME                  | 采集时间       | DATE         |          | Y        |        |                         |
| 4        | COMMSTATUS               | 通信状态       | NUMBER(2)    |          | Y        |        | 0-离线 1-在线           |
| 5        | COMMTIME                 | 在线时间       | NUMBER(8,2)  | h        | Y        |        |                         |
| 6        | COMMTIMEEFFICIENCY       | 在线时率       | NUMBER(10,4) |          | Y        |        |                         |
| 7        | COMMRANGE                | 在线区间       | CLOB         |          | Y        |        |                         |
| 8        | RUNSTATUS                | 运行状态       | NUMBER(2)    |          | Y        |        | 0-停抽 1-运行           |
| 9        | RUNTIME                  | 运行时间       | NUMBER(8,2)  | h        | Y        |        |                         |
| 10       | RUNTIMEEFFICIENCY        | 运行时率       | NUMBER(10,4) |          | Y        |        |                         |
| 11       | RUNRANGE                 | 运行区间       | CLOB         |          | Y        |        |                         |
| 12       | IA                       | A相电流        | NUMBER(8,2)  | A        | Y        |        |                         |
| 13       | IB                       | B相电流        | NUMBER(8,2)  | A        | Y        |        |                         |
| 14       | IC                       | C相电流        | NUMBER(8,2)  | A        | Y        |        |                         |
| 15       | IAVG                     | 三相电流平均值 | NUMBER(8,2)  | A        | Y        |        |                         |
| 16       | VA                       | A相电压        | NUMBER(8,2)  | V        | Y        |        |                         |
| 17       | VB                       | B相电压        | NUMBER(8,2)  | V        | Y        |        |                         |
| 18       | VC                       | C相电压        | NUMBER(8,2)  | V        | Y        |        |                         |
| 19       | VAVG                     | 三相电压平均值 | NUMBER(8,2)  | V        | Y        |        |                         |
| 20       | TOTALKWATTH              | 有功功耗       | NUMBER(8,2)  | kW·h     | Y        |        |                         |
| 21       | TOTALPKWATTH             | 正向有功功耗   | NUMBER(8,2)  | kW·h     | Y        |        |                         |
| 22       | TOTALNKWATTH             | 反向有功功耗   | NUMBER(8,2)  | kW·h     | Y        |        |                         |
| 23       | TOTALKVARH               | 无功功耗       | NUMBER(8,2)  | kVar·h   | Y        |        |                         |
| 24       | TOTALPKVARH              | 正向无功功耗   | NUMBER(8,2)  | kVar·h   | Y        |        |                         |
| 25       | TOTALNKVARH              | 反向无功功耗   | NUMBER(8,2)  | kVar·h   | Y        |        |                         |
| 26       | TOTALKVAH                | 视在功耗       | NUMBER(8,2)  | kVA·h    | Y        |        |                         |
| 27       | TODAYKWATTH              | 日有功功耗     | NUMBER(8,2)  | kW·h     | Y        |        |                         |
| 28       | TODAYPKWATTH             | 日正向有功功耗 | NUMBER(8,2)  | kW·h     | Y        |        |                         |
| 29       | TODAYNKWATTH             | 日反向有功功耗 | NUMBER(8,2)  | kW·h     | Y        |        |                         |
| 30       | TODAYKVARH               | 日无功功耗     | NUMBER(8,2)  | kVar·h   | Y        |        | 日用电量                |
| 31       | TODAYPKVARH              | 日正向无功功耗 | NUMBER(8,2)  | kVar·h   | Y        |        |                         |
| 32       | TODAYNKVARH              | 日反向无功功耗 | NUMBER(8,2)  | kVar·h   | Y        |        | 高报/低报/零值/均衡报警 |
| 33       | TODAYKVAH                | 日视在功耗     | NUMBER(8,2)  | kVA·h    | Y        |        | 高报/低报/零值/均衡报警 |
| 34       | WATTA                    | A相有功功率    | NUMBER(8,2)  | kW       | Y        |        | 高报/低报/零值/均衡报警 |
| 35       | WATTB                    | B相有功功率    | NUMBER(8,2)  | kW       | Y        |        | 高报/低报/零值/均衡报警 |
| 36       | WATTC                    | C相有功功率    | NUMBER(8,2)  | kW       | Y        |        | 高报/低报/零值/均衡报警 |
| 37       | WATT3                    | 有功功率       | NUMBER(8,2)  | kW       | Y        |        | 高报/低报/零值/均衡报警 |
| 38       | VARA                     | A相无功功率    | NUMBER(8,2)  | kVar     | Y        |        |                         |
| 39       | VARB                     | B相无功功率    | NUMBER(8,2)  | kVar     | Y        |        |                         |
| 40       | VARC                     | C相无功功率    | NUMBER(8,2)  | kVar     | Y        |        |                         |
| 41       | VAR3                     | 无功功率       | NUMBER(8,2)  | kVar     | Y        |        |                         |
| 42       | VAA                      | A相视在功率    | NUMBER(8,2)  | kVA      | Y        |        |                         |
| 43       | VAB                      | B相视在功率    | NUMBER(8,2)  | kVA      | Y        |        |                         |
| 44       | VAC                      | C相视在功率    | NUMBER(8,2)  | kVA      | Y        |        |                         |
| 45       | VA3                      | 视在功率       | NUMBER(8,2)  | kVA      | Y        |        |                         |
| 46       | REVERSEPOWER             | 反向功率       | NUMBER(8,2)  |          | Y        |        |                         |
| 47       | PFA                      | A相功率因数    | NUMBER(8,2)  |          | Y        |        |                         |
| 48       | PFB                      | B相功率因数    | NUMBER(8,2)  |          | Y        |        |                         |
| 49       | PFC                      | C相功率因数    | NUMBER(8,2)  |          | Y        |        |                         |
| 50       | PF3                      | 功率因数       | NUMBER(8,2)  |          | Y        |        |                         |
| 51       | SETFREQUENCY             | 变频设置频率   | NUMBER(8,2)  | HZ       | Y        |        |                         |
| 52       | RUNFREQUENCY             | 变频运行频率   | NUMBER(8,2)  | HZ       | Y        |        |                         |
| 53       | TUBINGPRESSURE           | 油压           | NUMBER(8,2)  | MPa      | Y        |        |                         |
| 54       | CASINGPRESSURE           | 套压           | NUMBER(8,2)  | MPa      | Y        |        |                         |
| 55       | BACKPRESSURE             | 回压           | NUMBER(8,2)  | MPa      | Y        |        |                         |
| 56       | WELLHEADFLUIDTEMPERATURE | 井口油温       | NUMBER(8,2)  | ℃        | Y        |        |                         |
| 57       | RESULTCODE               | 电参工况代码   | NUMBER(4)    |          | Y        |        |                         |
| 58       | RESULTSTRING             | 电参工况字符串 | CLOB         |          | Y        |        |                         |
| 59       | IAALARM                  | A相电流报警项  | VARCHAR2(20) |          | Y        |        |                         |
| 60       | IBALARM                  | B相电流报警项  | VARCHAR2(20) |          | Y        |        |                         |
| 61       | ICALARM                  | C相电流报警项  | VARCHAR2(20) |          | Y        |        |                         |
| 62       | VAALARM                  | A相电压报警项  | VARCHAR2(20) |          | Y        |        |                         |
| 63       | VBALARM                  | B相电压报警项  | VARCHAR2(20) |          | Y        |        |                         |
| 64       | VCALARM                  | C相电压报警项  | VARCHAR2(20) |          | Y        |        |                         |
| 65       | IAUPLIMIT                | A相电流上限    | NUMBER(8,2)  | A        | Y        |        |                         |
| 66       | IADOWNLIMIT              | A相电流下限    | NUMBER(8,2)  | A        | Y        |        |                         |
| 67       | IBUPLIMIT                | B相电流上限    | NUMBER(8,2)  | A        | Y        |        |                         |
| 68       | IBDOWNLIMIT              | B相电流下限    | NUMBER(8,2)  | A        | Y        |        |                         |
| 69       | ICUPLIMIT                | C相电流上限    | NUMBER(8,2)  | A        | Y        |        |                         |
| 70       | ICDOWNLIMIT              | C相电流下限    | NUMBER(8,2)  | A        | Y        |        |                         |
| 71       | VAUPLIMIT                | A相电压上限    | NUMBER(8,2)  | V        | Y        |        |                         |
| 72       | VADOWNLIMIT              | A相电压下限    | NUMBER(8,2)  | V        | Y        |        |                         |
| 73       | VBUPLIMIT                | B相电压上限    | NUMBER(8,2)  | V        | Y        |        |                         |
| 74       | VBDOWNLIMIT              | B相电压下限    | NUMBER(8,2)  | V        | Y        |        |                         |
| 75       | VCUPLIMIT                | C相电压上限    | NUMBER(8,2)  | V        | Y        |        |                         |
| 76       | VCDOWNLIMIT              | C相电压下限    | NUMBER(8,2)  | V        | Y        |        |                         |
| 77       | IAZERO                   | A相电流零值    | NUMBER(8,2)  | A        | Y        |        |                         |
| 78       | IBZERO                   | B相电流零值    | NUMBER(8,2)  | A        | Y        |        |                         |
| 79       | ICZERO                   | C相电流零值    | NUMBER(8,2)  | A        | Y        |        |                         |
| 80       | VAZERO                   | A相电压零值    | NUMBER(8,2)  | V        | Y        |        |                         |
| 81       | VBZERO                   | B相电压零值    | NUMBER(8,2)  | V        | Y        |        |                         |
| 82       | VCZERO                   | C相电压零值    | NUMBER(8,2)  | V        | Y        |        |                         |
| 83       | SIGNAL                   | 信号强度       | NUMBER(8,2)  |          | Y        |        |                         |
| 84       | INTERVAL                 | 传输间隔       | NUMBER(10)   | min      | Y        |        |                         |
| 85       | DEVICEVER                | 设备版本信息   | VARCHAR2(50) |          | Y        |        |                         |

### 1.3.32 tbl_pcp_discrete_hist

同tbl_pcp_discrete_latest

### 1.3.33 tbl_pcp_rpm_latest

表1-29 螺杆泵转速数据实时表

| **序号** | **代码**                   | **名称**           | **类型**      | **单位**    | **为空** | **键** | **备注**                                                                                       |
|----------|----------------------------|--------------------|---------------|-------------|----------|--------|------------------------------------------------------------------------------------------------|
| 1        | ID                         | 记录编号           | NUMBER(10)    |             | N        | 主键   |                                                                                                |
| 2        | WELLID                     | 井编号             | NUMBER(10)    |             | N        |        |                                                                                                |
| 3        | ACQTIME                    | 采集时间           | DATE          |             | Y        |        |                                                                                                |
| 4        | RPM                        | 转速               | NUMBER(8,2)   | r/min       | Y        |        |                                                                                                |
| 5        | TORQUE                     | 扭矩               | NUMBER(8,2)   | kN·m        | Y        |        |                                                                                                |
| 6        | RESULTCODE                 | 工况代码           | NUMBER(4)     |             | Y        |        |                                                                                                |
| 7        | THEORETICALPRODUCTION      | 理论排量           | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 8        | LIQUIDVOLUMETRICPRODUCTION | 产液量方           | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 9        | OILVOLUMETRICPRODUCTION    | 产油量方           | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 10       | WATERVOLUMETRICPRODUCTION  | 产水量方           | NUMBER(8,2)   | m\^3/d      | Y        |        |                                                                                                |
| 11       | LIQUIDWEIGHTPRODUCTION     | 产液量吨           | NUMBER(8,2)   | t/d         | Y        |        |                                                                                                |
| 12       | OILWEIGHTPRODUCTION        | 产油量吨           | NUMBER(8,2)   | t/d         | Y        |        |                                                                                                |
| 13       | WATERWEIGHTPRODUCTION      | 产水量吨           | NUMBER(8,2)   | t/d         | Y        |        |                                                                                                |
| 14       | AVERAGEWATT                | 电机输入有功功率   | NUMBER(8,2)   | kW          | Y        |        |                                                                                                |
| 15       | WATERPOWER                 | 水功率             | NUMBER(8,2)   | kW          | Y        |        |                                                                                                |
| 16       | SYSTEMEFFICIENCY           | 系统效率           | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 17       | ENERGYPER100MLIFT          | 吨液百米耗电量     | NUMBER(8,2)   | kW·h/100m·t | Y        |        |                                                                                                |
| 18       | PUMPEFF1                   | 容积效率           | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 19       | PUMPEFF2                   | 液体收缩系数       | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 20       | PUMPEFF                    | 泵效               | NUMBER(12,3)  | 小数        | Y        |        |                                                                                                |
| 21       | PUMPINTAKEP                | 泵入口压力         | NUMBER(8,2)   | MPa         | Y        |        |                                                                                                |
| 22       | PUMPINTAKET                | 泵入口温度         | NUMBER(8,2)   | ℃           | Y        |        |                                                                                                |
| 23       | PUMPINTAKEGOL              | 泵入口就地气液比   | NUMBER(8,2)   | m\^3/m\^3   | Y        |        |                                                                                                |
| 24       | PUMPINTAKEVISL             | 泵入口粘度         | NUMBER(8,2)   | mPa·s       | Y        |        |                                                                                                |
| 25       | PUMPINTAKEBO               | 泵入口原油体积系数 | NUMBER(8,2)   | 小数        | Y        |        |                                                                                                |
| 26       | PUMPOUTLETP                | 泵出口压力         | NUMBER(8,2)   | MPa         | Y        |        |                                                                                                |
| 27       | PUMPOUTLETT                | 泵出口温度         | NUMBER(8,2)   | ℃           | Y        |        |                                                                                                |
| 28       | PUMPOUTLETGOL              | 泵出口就地气液比   | NUMBER(8,2)   | m\^3/m\^3   | Y        |        |                                                                                                |
| 29       | PUMPOUTLETVISL             | 泵出口粘度         | NUMBER(8,2)   | mPa·s       | Y        |        |                                                                                                |
| 30       | PUMPOUTLETBO               | 泵出口原油体积系数 | NUMBER(8,2)   | 小数        | Y        |        |                                                                                                |
| 31       | RODSTRING                  | 抽油杆柱分析数据   | VARCHAR2(200) |             | Y        |        | 格式：杆数,总杆长,总杆重,总浮力;一级杆最大应力,一级杆最小应力,一级杆许用应力,一级杆应力范围比… |
| 32       | SAVETIME                   | 入库时间           | DATE          |             | Y        |        |                                                                                                |
| 33       | PRODUCTIONDATAID           | 生产数据编号       | NUMBER(10)    |             | Y        |        | 关联生产数据历史表                                                                             |
| 34       | RESULTSTATUS               | 计算标志           | NUMBER(2)     |             | Y        |        |                                                                                                |
| 35       | DISCRETEDATAID             | 离散数据编号       | NUMBER(10)    |             | Y        |        |                                                                                                |
| 36       | REMARK                     | 备注               | VARCHAR2(200) |             | Y        |        |                                                                                                |

### 1.3.34 tbl_pcp_rpm_hist

同tbl_pcp_rpm_latest

### 1.3.35 tbl_pcp_total_day

表1-30 螺杆泵日累计数据表

| **序号** | **代码**                      | **名称**             | **类型**     | **单位**    | **为空** | **键** | **备注**      |
|----------|-------------------------------|----------------------|--------------|-------------|----------|--------|---------------|
| 1        | ID                            | 记录编号             | NUMBER(10)   |             | N        | 主键   |               |
| 2        | WELLID                        | 井编号               | NUMBER(10)   |             | Y        |        |               |
| 3        | CALCULATEDATE                 | 计算时间             | DATE         |             | Y        |        |               |
| 4        | COMMSTATUS                    | 通信状态             | NUMBER(2)    |             | Y        |        | 0-离线 1-在线 |
| 5        | RUNSTATUS                     | 运行状态             | NUMBER(2)    |             | Y        |        | 0-停止 1-运行 |
| 6        | COMMTIME                      | 在线时间             | NUMBER(8,2)  | h           | Y        |        |               |
| 7        | COMMTIMEEFFICIENCY            | 在线时率             | NUMBER(12,3) |             | Y        |        |               |
| 8        | COMMRANGE                     | 在线区间             | CLOB         |             | Y        |        |               |
| 9        | RUNTIME                       | 运行时间             | NUMBER(8,2)  | h           | Y        |        |               |
| 10       | RUNTIMEEFFICIENCY             | 运行时率             | NUMBER(12,3) | 小数        | Y        |        |               |
| 11       | RUNRANGE                      | 运行区间             | CLOB         |             | Y        |        |               |
| 12       | RESULTCODE                    | 工况代码             | NUMBER(4)    |             | Y        |        |               |
| 13       | RESULTSTRING                  | 工况字符串           | CLOB         |             | Y        |        |               |
| 14       | RPM                           | 转速                 | NUMBER(4)    | r/min       | Y        |        |               |
| 15       | RPMMAX                        | 转速最大值           | NUMBER(10,4) | r/min       | Y        |        |               |
| 16       | RPMMIN                        | 转速最小值           | NUMBER(10,4) | r/min       | Y        |        |               |
| 17       | TORQUE                        | 扭矩                 | NUMBER(10,4) | kN·m        | Y        |        |               |
| 18       | TORQUEMAX                     | 扭矩最大值           | NUMBER(10,4) | kN·m        | Y        |        |               |
| 19       | TORQUEMIN                     | 扭矩最小值           | NUMBER(8,2)  | kN·m        | Y        |        |               |
| 20       | LIQUIDVOLUMETRICPRODUCTION    | 产液量方             | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 21       | OILVOLUMETRICPRODUCTION       | 产油量方             | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 22       | WATERVOLUMETRICPRODUCTION     | 产水量方             | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 23       | LIQUIDWEIGHTPRODUCTION        | 产液量吨             | NUMBER(8,2)  | t/d         | Y        |        |               |
| 24       | OILWEIGHTPRODUCTION           | 产油量吨             | NUMBER(8,2)  | t/d         | Y        |        |               |
| 25       | WATERWEIGHTPRODUCTION         | 产水量吨             | NUMBER(8,2)  | t/d         | Y        |        |               |
| 26       | LIQUIDVOLUMETRICPRODUCTIONMAX | 产液量最大值方       | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 27       | LIQUIDVOLUMETRICPRODUCTIONMIN | 产液量最小值方       | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 28       | OILVOLUMETRICPRODUCTIONMAX    | 产油量最大值方       | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 29       | OILVOLUMETRICPRODUCTIONMIN    | 产油量最小值方       | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 30       | WATERVOLUMETRICPRODUCTIONMAX  | 产水量最大值方       | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 31       | WATERVOLUMETRICPRODUCTIONMIN  | 产水量最小值方       | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 32       | LIQUIDWEIGHTPRODUCTIONMAX     | 产液量最大值吨       | NUMBER(8,2)  | t/d         | Y        |        |               |
| 33       | LIQUIDWEIGHTPRODUCTIONMIN     | 产液量最小值吨       | NUMBER(8,2)  | t/d         | Y        |        |               |
| 34       | OILWEIGHTPRODUCTIONMAX        | 产油量最大值吨       | NUMBER(8,2)  | t/d         | Y        |        |               |
| 35       | OILWEIGHTPRODUCTIONMIN        | 产油量最小值吨       | NUMBER(8,2)  | t/d         | Y        |        |               |
| 36       | WATERWEIGHTPRODUCTIONMAX      | 产水量最大值吨       | NUMBER(8,2)  | t/d         | Y        |        |               |
| 37       | WATERWEIGHTPRODUCTIONMIN      | 产水量最小值吨       | NUMBER(8,2)  | t/d         | Y        |        |               |
| 38       | VOLUMEWATERCUT                | 体积含水率           | NUMBER(8,2)  | %           | Y        |        |               |
| 39       | WEIGHTWATERCUT                | 重量含水率           | NUMBER(8,2)  | %           | Y        |        |               |
| 40       | VOLUMEWATERCUTMAX             | 体积含水率最大值     | NUMBER(8,2)  | %           | Y        |        |               |
| 41       | VOLUMEWATERCUTMIN             | 体积含水率最小值     | NUMBER(8,2)  | %           | Y        |        |               |
| 42       | WEIGHTWATERCUTMAX             | 重量含水率最大值     | NUMBER(8,2)  | %           | Y        |        |               |
| 43       | WEIGHTWATERCUTMIN             | 重量含水率最小值     | NUMBER(8,2)  | %           | Y        |        |               |
| 44       | TUBINGPRESSURE                | 油压                 | NUMBER(8,2)  | MPa         | Y        |        |               |
| 45       | TUBINGPRESSUREMAX             | 油压最大值           | NUMBER(8,2)  | MPa         | Y        |        |               |
| 46       | TUBINGPRESSUREMIN             | 油压最小值           | NUMBER(8,2)  | MPa         | Y        |        |               |
| 47       | CASINGPRESSURE                | 套压                 | NUMBER(8,2)  | MPa         | Y        |        |               |
| 48       | CASINGPRESSUREMAX             | 套压最大值           | NUMBER(8,2)  | MPa         | Y        |        |               |
| 49       | CASINGPRESSUREMIN             | 套压最小值           | NUMBER(8,2)  | MPa         | Y        |        |               |
| 50       | WELLHEADFLUIDTEMPERATURE      | 井口油温             | NUMBER(8,2)  | ℃           | Y        |        |               |
| 51       | WELLHEADFLUIDTEMPERATUREMAX   | 井口油温最大值       | NUMBER(8,2)  | ℃           | Y        |        |               |
| 52       | WELLHEADFLUIDTEMPERATUREMIN   | 井口油温最小值       | NUMBER(10,4) | ℃           | Y        |        |               |
| 53       | PRODUCTIONGASOILRATIO         | 生产气油比           | NUMBER(10,4) | m\^3/t      | Y        |        |               |
| 54       | PRODUCTIONGASOILRATIOMAX      | 生产气油比最大值     | NUMBER(10,4) | m\^3/t      | Y        |        |               |
| 55       | PRODUCTIONGASOILRATIOMIN      | 生产气油比最小值     | NUMBER(10,4) | m\^3/t      | Y        |        |               |
| 56       | PRODUCINGFLUIDLEVEL           | 动液面               | NUMBER(10,4) | m           | Y        |        |               |
| 57       | PRODUCINGFLUIDLEVELMAX        | 动液面最大值         | NUMBER(10,4) | m           | Y        |        |               |
| 58       | PRODUCINGFLUIDLEVELMIN        | 动液面最小值         | NUMBER(8,2)  | m           | Y        |        |               |
| 59       | PUMPSETTINGDEPTH              | 泵挂                 | NUMBER(8,2)  | m           | Y        |        |               |
| 60       | PUMPSETTINGDEPTHMAX           | 泵挂最大值           | NUMBER(8,2)  | m           | Y        |        |               |
| 61       | PUMPSETTINGDEPTHMIN           | 泵挂最小值           | NUMBER(8,2)  | m           | Y        |        |               |
| 62       | SUBMERGENCE                   | 沉没度               | NUMBER(8,2)  | m           | Y        |        |               |
| 63       | SUBMERGENCEMAX                | 沉没度最大值         | NUMBER(8,2)  | m           | Y        |        |               |
| 64       | SUBMERGENCEMIN                | 沉没度最小值         | NUMBER(8,2)  | m           | Y        |        |               |
| 65       | PUMPBOREDIAMETER              | 泵径                 | NUMBER(8,2)  | mm          | Y        |        |               |
| 66       | PUMPBOREDIAMETERMAX           | 泵径最大值           | NUMBER(8,2)  | mm          | Y        |        |               |
| 67       | PUMPBOREDIAMETERMIN           | 泵径最小值           | NUMBER(8,2)  | mm          | Y        |        |               |
| 68       | SYSTEMEFFICIENCY              | 系统效率             | NUMBER(8,2)  | 小数        | Y        |        |               |
| 69       | SYSTEMEFFICIENCYMAX           | 系统效率最大值       | NUMBER(8,2)  | 小数        | Y        |        |               |
| 70       | SYSTEMEFFICIENCYMIN           | 系统效率最小值       | NUMBER(8,2)  | 小数        | Y        |        |               |
| 71       | SURFACESYSTEMEFFICIENCY       | 地面效率             | NUMBER(8,2)  | 小数        | Y        |        |               |
| 72       | SURFACESYSTEMEFFICIENCYMAX    | 地面效率最大值       | NUMBER(8,2)  | 小数        | Y        |        |               |
| 73       | SURFACESYSTEMEFFICIENCYMIN    | 地面效率最小值       | NUMBER(8,2)  | 小数        | Y        |        |               |
| 74       | WELLDOWNSYSTEMEFFICIENCY      | 井下效率             | NUMBER(8,2)  | 小数        | Y        |        |               |
| 75       | WELLDOWNSYSTEMEFFICIENCYMAX   | 井下效率最大值       | NUMBER(8,2)  | 小数        | Y        |        |               |
| 76       | WELLDOWNSYSTEMEFFICIENCYMIN   | 井下效率最小值       | NUMBER(8,2)  | 小数        | Y        |        |               |
| 77       | ENERGYPER100MLIFT             | 吨液百米耗电量       | NUMBER(8,2)  | kW·h/100m·t | Y        |        |               |
| 78       | ENERGYPER100MLIFTMAX          | 吨液百米耗电量最大值 | NUMBER(8,2)  | kW·h/100m·t | Y        |        |               |
| 79       | ENERGYPER100MLIFTMIN          | 吨液百米耗电量最小值 | NUMBER(8,2)  | kW·h/100m·t | Y        |        |               |
| 80       | PUMPEFF                       | 总泵效               | NUMBER(8,2)  | 小数        | Y        |        |               |
| 81       | PUMPEFFMAX                    | 总泵效最大值         | NUMBER(8,2)  | 小数        | Y        |        |               |
| 82       | PUMPEFFMIN                    | 总泵效最小值         | NUMBER(10,4) | 小数        | Y        |        |               |
| 83       | IA                            | A相电流              | NUMBER(10,4) | A           | Y        |        |               |
| 84       | IAMAX                         | A相电流最大值        | NUMBER(10,4) | A           | Y        |        |               |
| 85       | IAMIN                         | A相电流最小值        | NUMBER(10,4) | A           | Y        |        |               |
| 86       | IB                            | B相电流              | NUMBER(10,4) | A           | Y        |        |               |
| 87       | IBMAX                         | B相电流最大值        | NUMBER(10,4) | A           | Y        |        |               |
| 88       | IBMIN                         | B相电流最小值        | NUMBER(10,4) | A           | Y        |        |               |
| 89       | IC                            | C相电流              | NUMBER(10,4) | A           | Y        |        |               |
| 90       | ICMAX                         | C相电流最大值        | NUMBER(10,4) | A           | Y        |        |               |
| 91       | ICMIN                         | C相电流最小值        | NUMBER(8,2)  | A           | Y        |        |               |
| 92       | VA                            | A相电压              | NUMBER(8,2)  | V           | Y        |        |               |
| 93       | VAMAX                         | A相电压最大值        | NUMBER(8,2)  | V           | Y        |        |               |
| 94       | VAMIN                         | A相电压最小值        | NUMBER(10,4) | V           | Y        |        |               |
| 95       | VB                            | B相电压              | NUMBER(10,4) | V           | Y        |        |               |
| 96       | VBMAX                         | B相电压最大值        | NUMBER(10,4) | V           | Y        |        |               |
| 97       | VBMIN                         | B相电压最小值        | NUMBER(8,2)  | V           | Y        |        |               |
| 98       | VC                            | C相电压              | NUMBER(8,2)  | V           | Y        |        |               |
| 99       | VCMAX                         | C相电压最大值        | NUMBER(8,2)  | V           | Y        |        |               |
| 100      | VCMIN                         | C相电压最小值        | NUMBER(8,2)  | V           | Y        |        |               |
| 101      | WATT3                         | 有功功率             | NUMBER(8,2)  | kW          | Y        |        |               |
| 102      | WATT3MAX                      | 有功功率最大值       | NUMBER(8,2)  | kW          | Y        |        |               |
| 103      | WATT3MIN                      | 有功功率最小值       | NUMBER(8,2)  | kW          | Y        |        |               |
| 104      | VAR3                          | 无功功率             | NUMBER(8,2)  | kVar        | Y        |        |               |
| 105      | VAR3MAX                       | 无功功率最大值       | NUMBER(8,2)  | kVar        | Y        |        |               |
| 106      | VAR3MIN                       | 无功功率最小值       | NUMBER(8,2)  | kVar        | Y        |        |               |
| 107      | PF3                           | 功率因数             | NUMBER(8,2)  |             | Y        |        |               |
| 108      | PF3MAX                        | 功率因数最大值       | NUMBER(8,2)  |             | Y        |        |               |
| 109      | PF3MIN                        | 功率因数最小值       | NUMBER(8,2)  |             | Y        |        |               |
| 110      | FREQUENCY                     | 运行频率             | NUMBER(8,2)  | HZ          | Y        |        |               |
| 111      | FREQUENCYMAX                  | 运行频率最大值       | NUMBER(8,2)  | HZ          | Y        |        |               |
| 112      | FREQUENCYMIN                  | 运行频率最小值       | NUMBER(8,2)  | HZ          | Y        |        |               |
| 113      | TODAYKWATTH                   | 日有功功耗           | NUMBER(8,2)  | kW·h        | Y        |        |               |
| 114      | TODAYPKWATTH                  | 日正向有功功耗       | NUMBER(8,2)  | kW·h        | Y        |        |               |
| 115      | TODAYNKWATTH                  | 日反向有功功耗       | NUMBER(8,2)  | kW·h        | Y        |        |               |
| 116      | TODAYKVARH                    | 日无功功耗           | NUMBER(8,2)  | kVar·h      | Y        |        |               |
| 117      | TODAYPKVARH                   | 日正向无功功耗       | NUMBER(8,2)  | kVar·h      | Y        |        |               |
| 118      | TODAYNKVARH                   | 日反向无功功耗       | NUMBER(8,2)  | kVar·h      | Y        |        |               |
| 119      | TODAYKVAH                     | 日视在功耗           | NUMBER(8,2)  | kVA·h       | Y        |        |               |
| 120      | TOTALKWATTH                   | 累计有功功耗         | NUMBER(8,2)  | kW·h        | Y        |        |               |
| 121      | TOTALPKWATTH                  | 累计正向有功功耗     | NUMBER(8,2)  | kW·h        | Y        |        |               |
| 122      | TOTALNKWATTH                  | 累计反向有功功耗     | NUMBER(8,2)  | kW·h        | Y        |        |               |
| 123      | TOTALKVARH                    | 累计无功功耗         | NUMBER(8,2)  | kVar·h      | Y        |        |               |
| 124      | TOTALPKVARH                   | 累计正向无功功耗     | NUMBER(8,2)  | kVar·h      | Y        |        |               |
| 125      | TOTALNKVARH                   | 累计反向无功功耗     | NUMBER(8,2)  | kVar·h      | Y        |        |               |
| 126      | TOTALKVAH                     | 累计视在功耗         | NUMBER(8,2)  | kVA·h       | Y        |        |               |
| 127      | EXTENDEDDAYS                  | 延用天数             | NUMBER(8,2)  |             | Y        |        |               |
| 128      | RESULTSTATUS                  | 计算标志             | NUMBER(8,2)  |             | Y        |        |               |
| 129      | SIGNAL                        | 信号强度             | NUMBER(8,2)  |             | Y        |        |               |
| 130      | SIGNALMAX                     | 信号强度最大值       | NUMBER(8,2)  |             | Y        |        |               |
| 131      | SIGNALMIN                     | 信号强度最小值       | NUMBER(8,2)  |             | Y        |        |               |
| 132      | SAVETIME                      | 存储时间             | NUMBER(8,2)  |             | Y        |        |               |
| 133      | THEORETICALPRODUCTION         | 理论排量             | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 134      | THEORETICALPRODUCTIONMAX      | 理论排量最大值       | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 135      | THEORETICALPRODUCTIONMIN      | 理论排量最小值       | NUMBER(8,2)  | m\^3/d      | Y        |        |               |
| 136      | AVGWATT                       | 平均有功功率         | NUMBER(8,2)  | kW          | Y        |        |               |
| 137      | AVGWATTMAX                    | 平均有功功率最大值   | NUMBER(8,2)  | kW          | Y        |        |               |
| 138      | AVGWATTMIN                    | 平均有功功率最小值   | NUMBER(8,2)  | kW          | Y        |        |               |
| 139      | WATERPOWER                    | 水功率               | NUMBER(8,2)  | kW          | Y        |        |               |
| 140      | WATERPOWERMAX                 | 水功率最大值         | NUMBER(8,2)  | kW          | Y        |        |               |
| 141      | WATERPOWERMIN                 | 水功率最小值         | NUMBER(8,2)  | kW          | Y        |        |               |
| 142      | PUMPEFF1                      | 容积效率             | NUMBER(10,4) | 小数        | Y        |        |               |
| 143      | PUMPEFF1MAX                   | 容积效率最大值       | NUMBER(10,4) | 小数        | Y        |        |               |
| 144      | PUMPEFF1MIN                   | 容积效率最小值       | NUMBER(10,4) | 小数        | Y        |        |               |
| 145      | PUMPEFF2                      | 液体收缩系数         | NUMBER(10,4) | 小数        | Y        |        |               |
| 146      | PUMPEFF2MAX                   | 液体收缩系数最大值   | NUMBER(10,4) | 小数        | Y        |        |               |
| 147      | PUMPEFF2MIN                   | 液体收缩系数最小值   | NUMBER(10,4) | 小数        | Y        |        |               |
| 148      | VA3                           | 视在功率             | NUMBER(8,2)  | kVA         | Y        |        |               |
| 149      | VA3MAX                        | 视在功率最大值       | NUMBER(8,2)  | kVA         | Y        |        |               |
| 150      | VA3MIN                        | 视在功率最小值       | NUMBER(8,2)  | kVA         | Y        |        |               |

### 1.3.36 tbl_a9rawdata_latest

表1-31 a9设备原始数据实时表

| **序号** | **代码**         | **名称**         | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|------------------|------------------|---------------|----------|----------|--------|----------|
| 1        | ID               | 记录编号         | NUMBER(10)    |          | N        | 主键   |          |
| 2        | DEVICEID         | 设备编号         | VARCHAR2(200) |          | Y        |        |          |
| 3        | ACQTIME          | 采集时间         | DATE          |          | Y        |        |          |
| 4        | SIGNAL           | 信号强度         | NUMBER(8,2)   |          | Y        |        |          |
| 5        | DEVICEVER        | 设备版本信息     | VARCHAR2(50)  |          | Y        |        |          |
| 6        | INTERVAL         | 传输间隔曲线数据 | CLOB          |          | Y        |        |          |
| 7        | A                | 角度曲线数据     | CLOB          |          | Y        |        |          |
| 8        | F                | 载荷曲线数据     | CLOB          |          | Y        |        |          |
| 9        | WATT             | 有功功率曲线数据 | CLOB          |          | Y        |        |          |
| 10       | I                | 电流曲线数据     | CLOB          |          | Y        |        |          |
| 11       | TRANSFERINTERVEL | 数据上传间隔     | NUMBER(10)    | min      | Y        |        |          |

### 1.3.37 tbl_a9rawdata_hist

同tbl_a9rawdata_latest

### 1.3.38 tbl_a9rawwatercutdata_latest

表1-32 含水率原始数据实时表

| **序号** | **代码**         | **名称**     | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|------------------|--------------|---------------|----------|----------|--------|----------|
| 1        | ID               | 记录编号     | NUMBER(10)    |          | N        | 主键   |          |
| 2        | DEVICEID         | 设备注册包ID | VARCHAR2(200) |          | Y        |        |          |
| 3        | ACQTIME          | 采集时间     | DATE          |          | Y        |        |          |
| 4        | SIGNAL           | 信号强度     | NUMBER(8,2)   |          | Y        |        |          |
| 5        | DEVICEVER        | 设备版本信息 | VARCHAR2(50)  |          | Y        |        |          |
| 6        | TRANSFERINTERVEL | 数据上传间隔 | NUMBER(10)    |          | Y        |        |          |
| 7        | INTERVAL         | 传输间隔     | CLOB          |          | Y        |        |          |
|          | WATERCUT         | 含水率       | CLOB          |          | Y        |        |          |

### 1.3.39 tbl_a9rawwatercutdata_hist

同tbl_a9rawwatercutdata_latest

### 1.3.40 tbl_resourcemonitoring

表1-33 资源监测数据表

| **序号** | **代码**       | **名称**         | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|----------------|------------------|--------------|----------|----------|--------|----------|
| 1        | ID             | 记录编号         | NUMBER(10)   |          | N        | 主键   |          |
| 2        | ACQTIME        | 采集时间         | DATE         |          | Y        |        |          |
| 3        | APPRUNSTATUS   | SDK运行状态      | NUMBER(2)    |          | Y        |        |          |
| 4        | APPVERSION     | SDK版本信息      | VARCHAR2(50) |          | Y        |        |          |
| 5        | CPUUSEDPERCENT | CPU使用率        | VARCHAR2(50) | %        | Y        |        |          |
| 6        | MEMUSEDPERCENT | 内存使用率       | NUMBER(8,2)  | %        | Y        |        |          |
| 7        | TABLESPACESIZE | 数据库表空间大小 | NUMBER(10,2) | Mb       | Y        |        |          |

# 二、视图

## 2.1 概览

表2-1 视图概览

| **序号** | **名称**                      | **描述**                 |
|----------|-------------------------------|--------------------------|
| 1        | viw_wellinformation           | 井名信息视图             |
| 2        | viw_wellboretrajectory        | 井身轨迹视图             |
| 3        | viw_rpc_productiondata_latest | 抽油机生产数据实时视图   |
| 4        | viw_rpc_productiondata_hist   | 抽油机生产数据历史视图   |
| 5        | viw_commstatus                | 通信状态视图             |
| 6        | viw_rpc_diagram_latest        | 抽油机曲线数据实时视图   |
| 7        | viw_rpc_diagram_hist          | 抽油机曲线数据历史视图   |
| 8        | viw_rpc_discrete_latest       | 抽油机离散数据实时视图   |
| 9        | viw_rpc_discrete_hist         | 抽油机离散数据历史视图   |
| 10       | viw_rpc_comprehensive_latest  | 抽油机综合数据实时视图   |
| 11       | viw_rpc_comprehensive_hist    | 抽油机综合数据历史视图   |
| 12       | viw_rpc_diagramquery_latest   | 抽油机图形查询实时视图   |
| 13       | viw_rpc_diagramquery_hist     | 抽油机图形查询历史视图   |
| 14       | viw_rpc_total_day             | 抽油机日累计数据视图     |
| 15       | viw_rpc_calculatemain         | 抽油机计算结果管理视图   |
| 16       | viw_rpc_calculatemain\_elec   | 电参反演计算结果管理视图 |
| 17       | viw_pcp_productiondata_latest | 螺杆泵生产数据实时视图   |
| 18       | viw_pcp_productiondata_hist   | 螺杆泵生产数据历史视图   |
| 19       | viw_pcp_rpm_latest            | 螺杆泵曲线数据实时视图   |
| 20       | viw_pcp_rpm_hist              | 螺杆泵曲线数据历史视图   |
| 21       | viw_pcp_discrete_latest       | 螺杆泵离散数据实时视图   |
| 22       | viw_pcp_discrete_hist         | 螺杆泵离散数据历史视图   |
| 23       | viw_pcp_comprehensive_latest  | 螺杆泵综合数据实时视图   |
| 24       | viw_pcp_comprehensive_hist    | 螺杆泵综合数据历史视图   |
| 25       | viw_pcp_total_day             | 螺杆泵日累计数据视图     |

## 2.2 详述

### 2.2.1 viw_commstatus

表2-1 发布订阅模式通信状态视图

| **序号** | **代码**     | **名称** | **类型** | **单位** |
|----------|--------------|----------|----------|----------|
| 1        | ID           | 记录编号 | NUMBER   |          |
| 2        | WELLNAME     | 井名     | VARCHAR2 |          |
| 3        | PROTOCOLCODE | 协议     | VARCHAR2 |          |
| 4        | SIGNINID     | 注册包ID | VARCHAR2 |          |
| 5        | COMMSTATUS   | 通信状态 | NUMBER   |          |

### 2.2.2 viw_wellinformation

表2-2 井名基本信息视图

| **序号** | **代码**        | **名称**     | **类型** | **单位** |
|----------|-----------------|--------------|----------|----------|
| 1        | ID              | 记录编号     | NUMBER   |          |
| 2        | ORGNAME         | 组织名称     | VARCHAR2 |          |
| 3        | ORGID           | 组织编号     | NUMBER   |          |
| 4        | RESNAME         | 油气藏名称   | VARCHAR2 |          |
| 5        | WELLNAME        | 井名         | VARCHAR2 |          |
| 6        | LIFTINGTYPE     | 举升类型     | NUMBER   |          |
| 7        | SIGNINID        | 注册包ID     | VARCHAR2 |          |
| 8        | SLAVE           | 设备从地址   | VARCHAR2 |          |
| 9        | VIDEOURL        | 视频路径     | VARCHAR2 |          |
| 10       | LIFTINGTYPENAME | 举升类型名称 | VARCHAR2 |          |
| 11       | PROTOCOLCODE    | 协议编码     | VARCHAR2 |          |
| 12       | ACQUISITIONUNIT | 采集单元     | VARCHAR2 |          |
| 13       | SORTNUM         | 排序编号     | NUMBER   |          |

### 2.2.3 viw_wellboretrajectory

表2-3 井身轨迹视图

| **序号** | **代码**       | **名称**  | **类型**      | **单位** |
|----------|----------------|-----------|---------------|----------|
| 1        | ID             | 记录编号  | NUMBER(10)    |          |
| 2        | ORGNAME        | 单位名称  | VARCHAR2(100) |          |
| 3        | ORGID          | 单位序号  | NUMBER(10)    |          |
| 4        | WELLNAME       | 井名      | VARCHAR2(200) |          |
| 5        | MEASURINGDEPTH | 测量深度  | CLOB          | m        |
| 6        | VERTICALDEPTH  | 垂直深度  | CLOB          | m        |
| 7        | DEVIATIONANGLE | 井斜角    | CLOB          | 度       |
| 8        | AZIMUTHANGLE   | 方位角    | CLOB          | 度       |
| 9        | X              | 直角坐标x | CLOB          |          |
| 10       | Y              | 直角坐标y | CLOB          |          |
| 11       | Z              | 直角坐标z | CLOB          |          |
| 12       | SAVETIME       | 入库时间  | DATE          |          |
| 13       | RESULTSTATUS   | 计算标志  | NUMBER(4)     |          |
| 14       | SORTNUM        | 排序编号  | NUMBER(10)    |          |

### 2.2.4 viw_rpc_productiondata_latest

表2-4 抽油机生产数据实时视图

| **序号** | **代码**                    | **名称**       | **类型**      | **单位** |
|----------|-----------------------------|----------------|---------------|----------|
| 1        | ID                          | 记录编号       | NUMBER(10)    |          |
| 2        | WELLNAME                    | 井名           | VARCHAR2(200) |          |
| 3        | WELLID                      | 井编号         | NUMBER(10)    |          |
| 4        | LIFTINGTYPE                 | 举升类型       | NUMBER(10)    |          |
| 5        | ACQTIME                     | 采集时间       | DATE          |          |
| 6        | RUNTIME                     | 运行时间       | NUMBER(8,2)   | h        |
| 7        | CRUDEOILDENSITY             | 原油密度       | NUMBER(16,2)  | g/cm\^3  |
| 8        | WATERDENSITY                | 水密度         | NUMBER(16,2)  | g/cm\^3  |
| 9        | NATURALGASRELATIVEDENSITY   | 天然气相对密度 | NUMBER(16,2)  |          |
| 10       | SATURATIONPRESSURE          | 饱和压力       | NUMBER(16,2)  | MPa      |
| 11       | RESERVOIRDEPTH              | 油层中部深度   | NUMBER(16,2)  | m        |
| 12       | RESERVOIRTEMPERATURE        | 油层中部温度   | NUMBER(16,2)  | ℃        |
| 13       | WEIGHTWATERCUT              | 重量含水率     | NUMBER(8,2)   | %        |
| 14       | VOLUMEWATERCUT              | 体积含水率     | NUMBER(8,2)   | %        |
| 15       | TUBINGPRESSURE              | 油压           | NUMBER(8,2)   | MPa      |
| 16       | CASINGPRESSURE              | 套压           | NUMBER(8,2)   | MPa      |
| 17       | BACKPRESSURE                | 回压           | NUMBER(8,2)   | MPa      |
| 18       | WELLHEADFLUIDTEMPERATURE    | 井口油温       | NUMBER(8,2)   | ℃        |
| 19       | PRODUCINGFLUIDLEVEL         | 动液面         | NUMBER(8,2)   | m        |
| 20       | PUMPSETTINGDEPTH            | 泵挂           | NUMBER(8,2)   | m        |
| 21       | PRODUCTIONGASOILRATIO       | 生产气油比     | NUMBER(8,2)   | m\^3/t   |
| 22       | PUMPBOREDIAMETER            | 泵径           | NUMBER(8,2)   | mm       |
| 23       | PUMPTYPE                    | 泵类型         | VARCHAR2(20)  |          |
| 24       | PUMPTYPENAME                | 泵类型名称     | VARCHAR2(200) |          |
| 25       | PUMPGRADE                   | 泵级别         | NUMBER(1)     |          |
| 26       | PLUNGERLENGTH               | 柱塞长         | NUMBER(8,2)   | m        |
| 27       | BARRELTYPE                  | 泵筒类型       | VARCHAR2(20)  |          |
| 28       | BARRELTYPENAME              | 泵筒类型名称   | VARCHAR2(200) |          |
| 29       | BARRELLENGTH                | 泵筒长         | NUMBER(8,2)   | m        |
| 30       | BARRELSERIES                | 泵级数         | NUMBER(8,2)   |          |
| 31       | ROTORDIAMETER               | 转子截面直径   | NUMBER(8,2)   | mm       |
| 32       | QPR                         | 转速           | NUMBER(8,2)   | r/min    |
| 33       | TUBINGSTRINGINSIDEDIAMETER  | 油管内径       | NUMBER(8,2)   | mm       |
| 34       | CASINGSTRINGINSIDEDIAMETER  | 套管内径       | NUMBER(8,2)   | mm       |
| 35       | RODSTRING                   | 杆数据         | VARCHAR2(200) |          |
| 36       | ANCHORINGSTATE              | 锚定状态       | NUMBER(1)     |          |
| 37       | ANCHORINGSTATENAME          | 锚定状态名称   | VARCHAR2(200) |          |
| 38       | NETGROSSRATIO               | 净毛比         | NUMBER(8,2)   |          |
| 39       | MANUALINTERVENTION          | 人工干预代码   | NUMBER(4)     |          |
| 40       | RUNTIMEEFFICIENCYSOURCE     | 时率来源       | NUMBER(1)     |          |
| 41       | RUNTIMEEFFICIENCYSOURCENAME | 时率来源名称   | VARCHAR2(200) |          |
| 42       | SORTNUM                     | 井排序编号     | NUMBER(10)    |          |
| 43       | ORG_ID                      | 组织编号       | NUMBER(10)    |          |

### 2.2.5 viw_rpc_productiondata_hist

同viw_rpc_productiondata_latest  
2.2.5 viw_commstatus

表2-5 通信状态视图

| **序号** | **代码**   | **名称** | **类型**      | **单位** |
|----------|------------|----------|---------------|----------|
| 1        | ID         | 编号     | NUMBER(10)    |          |
| 2        | WELLNAME   | 井名     | VARCHAR2(200) |          |
| 3        | COMMSTATUS | 通信状态 | NUMBER(1)     |          |

### 2.2.6 viw_rpc_diagram_latest

表2-6 抽油机曲线数据实时视图

| **序号** | **代码**                       | **名称**           | **类型**      | **单位**    |
|----------|--------------------------------|--------------------|---------------|-------------|
| 1        | ID                             | 记录编号           | NUMBER(10)    |             |
| 2        | WELLNAME                       | 井名               | VARCHAR2(200) |             |
| 3        | WELLID                         | 井编号             | NUMBER(10)    |             |
| 4        | LIFTINGTYPE                    | 举升类型           | NUMBER(10)    |             |
| 5        | SIGNINID                       | 设备地址           | VARCHAR2(200) |             |
| 6        | ACQTIME                        | 采集时间           | DATE          |             |
| 7        | COMMSTATUS                     | 通信状态           | NUMBER(1)     |             |
| 8        | COMMSTATUSNAME                 | 通信状态名称       | VARCHAR2      |             |
| 9        | COMMALARMLEVEL                 | 通信状态报警级别   | NUMBER(3)     |             |
| 10       | RESULTCODE                     | 功图工况代码       | NUMBER(4)     |             |
| 11       | RESULTNAME                     | 功图工况名称       | VARCHAR2(200) |             |
| 12       | OPTIMIZATIONSUGGESTION         | 优化建议           | VARCHAR2(200) |             |
| 13       | RESULTRUNALARMLEVEL            | 功图工况报警级别   | NUMBER(3)     |             |
| 14       | THEORETICALPRODUCTION          | 理论排量           | NUMBER(8,2)   | m\^3/d      |
| 15       | LIQUIDWEIGHTPRODUCTION         | 产液量吨           | NUMBER(8,2)   | t/d         |
| 16       | OILWEIGHTPRODUCTION            | 产油量吨           | NUMBER(8,2)   | t/d         |
| 17       | WATERWEIGHTPRODUCTION          | 产水量吨           | NUMBER(8,2)   | t/d         |
| 18       | WEIGHTWATERCUT                 | 重量含水率         | NUMBER(8,2)   | %           |
| 19       | LIQUIDWEIGHTPRODUCTIONLEVEL    | 产液级别吨         | VARCHAR2(50)  |             |
| 20       | LIQUIDVOLUMETRICPRODUCTION     | 产液量方           | NUMBER(8,2)   | m\^3/d      |
| 21       | OILVOLUMETRICPRODUCTION        | 产油量方           | NUMBER(8,2)   | m\^3/d      |
| 22       | WATERVOLUMETRICPRODUCTION      | 产水量方           | NUMBER(8,2)   | m\^3/d      |
| 23       | VOLUMEWATERCUT                 | 体积含水率         | NUMBER(8,2)   | %           |
| 24       | LIQUIDVOLUMEPRODUCTIONLEVEL    | 产液级别方         | VARCHAR2(50)  |             |
| 25       | LIQUIDWEIGHTPRODUCTION_L       | 实时累计产液量吨   | NUMBER(8,2)   | t           |
| 26       | OILWEIGHTPRODUCTION_L          | 实时累计产油量吨   | NUMBER(8,2)   | t           |
| 27       | WATERWEIGHTPRODUCTION_L        | 实时累计产水量吨   | NUMBER(8,2)   | t           |
| 28       | LIQUIDVOLUMETRICPRODUCTION_L   | 实时累计产液量方   | NUMBER(8,2)   | m\^3        |
| 29       | OILVOLUMETRICPRODUCTION_L      | 实时累计产油量方   | NUMBER(8,2)   | m\^3        |
| 30       | WATERVOLUMETRICPRODUCTION_L    | 实时累计产水量方   | NUMBER(8,2)   | m\^3        |
| 31       | PRODUCTIONGASOILRATIO          | 生产气油比         | NUMBER(8,2)   | m\^3/t      |
| 32       | TUBINGPRESSURE                 | 油压               | NUMBER(8,2)   | MPa         |
| 33       | CASINGPRESSURE                 | 套压               | NUMBER(8,2)   | MPa         |
| 34       | WELLHEADFLUIDTEMPERATURE       | 井口油温           | NUMBER(8,2)   | ℃           |
| 35       | PRODUCINGFLUIDLEVEL            | 动液面             | NUMBER(8,2)   | m           |
| 36       | LEVELCORRECTVALUE              | 液面反演校正值     | NUMBER(8,2)   | MPa         |
| 37       | PUMPSETTINGDEPTH               | 泵挂               | NUMBER(8,2)   | m           |
| 38       | SUBMERGENCE                    | 沉没度             | NUMBER(8,2)   | m           |
| 39       | PUMPBOREDIAMETER               | 泵径               | NUMBER(8,2)   | mm          |
| 40       | CRUDEOILDENSITY                | 原油密度           | NUMBER(16,2)  | g/cm\^3     |
| 41       | NETGROSSRATIO                  | 净毛比             | NUMBER(8,2)   |             |
| 42       | AVAILABLEPLUNGERSTROKEPROD_W   | 柱塞有效冲程产量吨 | NUMBER(8,2)   | t/d         |
| 43       | PUMPCLEARANCELEAKPROD_W        | 泵间隙漏失量吨     | NUMBER(8,2)   | t/d         |
| 44       | TVLEAKWEIGHTPRODUCTION         | 游动凡尔漏失量吨   | NUMBER(8,2)   | t/d         |
| 45       | SVLEAKWEIGHTPRODUCTION         | 固定凡尔漏失量吨   | NUMBER(8,2)   | t/d         |
| 46       | GASINFLUENCEPROD_W             | 气影响吨           | NUMBER(8,2)   | t/d         |
| 47       | AVAILABLEPLUNGERSTROKEPROD_V   | 柱塞有效冲程产量方 | NUMBER(8,2)   | m\^3/d      |
| 48       | PUMPCLEARANCELEAKPROD_V        | 泵间隙漏失量方     | NUMBER(8,2)   | m\^3/d      |
| 49       | TVLEAKVOLUMETRICPRODUCTION     | 游动凡尔漏失量方   | NUMBER(8,2)   | m\^3/d      |
| 50       | SVLEAKVOLUMETRICPRODUCTION     | 固定凡尔漏失量方   | NUMBER(8,2)   | m\^3/d      |
| 51       | GASINFLUENCEPROD_V             | 气影响方           | NUMBER(8,2)   | m\^3/d      |
| 52       | RODSTRING                      | 抽油杆数据         | VARCHAR2(200) |             |
| 53       | STROKE                         | 冲程               | NUMBER(8,2)   | m           |
| 54       | SPM                            | 冲次               | NUMBER(8,2)   | 次/min      |
| 55       | UPPERLOADLINE                  | 理论上载荷         | NUMBER(8,2)   | kN          |
| 56       | UPPERLOADLINEOFEXACT           | 真实理论上载荷     | NUMBER(8,2)   | kN          |
| 57       | LOWERLOADLINE                  | 理论下载荷         | NUMBER(8,2)   | kN          |
| 58       | DELTALOADLINE                  | 理论液柱载荷       | NUMBER(8,2)   | kN          |
| 59       | FMAX                           | 最大载荷           | NUMBER(8,2)   | kN          |
| 60       | FMIN                           | 最小载荷           | NUMBER(8,2)   | kN          |
| 61       | DELTAF                         | 载荷差             | NUMBER(8,2)   | kN          |
| 62       | FULLNESSCOEFFICIENT            | 充满系数           | NUMBER(8,2)   | 小数        |
| 63       | NOLIQUIDFULLNESSCOEFFICIENT    | 抽空充满系数       | NUMBER(10,4)  | 小数        |
| 64       | PLUNGERSTROKE                  | 柱塞冲程           | NUMBER(8,2)   | m           |
| 65       | AVAILABLEPLUNGERSTROKE         | 柱塞有效冲程       | NUMBER(8,2)   | m           |
| 66       | NOLIQUIDAVAILABLEPLUNGERSTROKE | 抽空柱塞有效冲程   | NUMBER(10,4)  | m           |
| 67       | AVERAGEWATT                    | 电机输入有功功率   | NUMBER(8,2)   | kW          |
| 68       | POLISHRODPOWER                 | 光杆功率           | NUMBER(8,2)   | kW          |
| 69       | WATERPOWER                     | 水功率             | NUMBER(8,2)   | kW          |
| 70       | SYSTEMEFFICIENCY               | 系统效率           | NUMBER(12,3)  | 小数        |
| 71       | SYSTEMEFFICIENCYLEVEL          | 系统效率级别       | VARCHAR2(50)  |             |
| 72       | SURFACESYSTEMEFFICIENCY        | 地面效率           | NUMBER(12,3)  | 小数        |
| 73       | SURFACESYSTEMEFFICIENCYLEVEL   | 地面效率级别       | VARCHAR2(50)  |             |
| 74       | WELLDOWNSYSTEMEFFICIENCY       | 井下效率           | NUMBER(12,3)  | 小数        |
| 75       | WELLDOWNSYSTEMEFFICIENCYLEVEL  | 井下效率级别       | VARCHAR2(50)  |             |
| 76       | AREA                           | 功图面积           | NUMBER(8,2)   |             |
| 77       | ENERGYPER100MLIFT              | 吨液百米耗电量     | NUMBER(8,2)   | kW·h/100m·t |
| 78       | IDEGREEBALANCE                 | 电流平衡度         | NUMBER(8,2)   | %           |
| 79       | IDEGREEBALANCENAME             | 电流平衡状态       | VARCHAR2(200) |             |
| 80       | UPSTROKEIMAX                   | 上冲程最大电流     | NUMBER(8,2)   | A           |
| 81       | DOWNSTROKEIMAX                 | 下冲程最大电流     | NUMBER(8,2)   | A           |
| 82       | IRATIO                         | 电流比             | NUMBER(8,2)   |             |
| 83       | IDEGREEBALANCEALARMLEVEL       | 电流平衡报警级别   | NUMBER(3)     |             |
| 84       | WATTDEGREEBALANCE              | 功率平衡度         | NUMBER(8,2)   | %           |
| 85       | WATTDEGREEBALANCENAME          | 功率平衡状态       | VARCHAR2(200) |             |
| 86       | UPSTROKEWATTMAX                | 上冲程最大功率     | NUMBER(8,2)   | kW          |
| 87       | DOWNSTROKEWATTMAX              | 下冲程最大功率     | NUMBER(8,2)   | kW          |
| 88       | WATTRATIO                      | 功率比             | NUMBER(8,2)   |             |
| 89       | WATTDEGREEBALANCEALARMLEVEL    | 功率平衡报警级别   | NUMBER(3)     |             |
| 90       | DELTARADIUS                    | 曲柄平衡移动距离   | NUMBER(8,2)   | cm          |
| 91       | PUMPEFF1                       | 冲程损失系数       | NUMBER(12,3)  | 小数        |
| 92       | PUMPEFF2                       | 充满系数           | NUMBER(12,3)  | 小数        |
| 93       | PUMPEFF3                       | 间隙漏失系数       | NUMBER(12,3)  | 小数        |
| 94       | PUMPEFF4                       | 液体收缩系数       | NUMBER(12,3)  | 小数        |
| 95       | PUMPEFF                        | 总泵效             | NUMBER(12,3)  | 小数        |
| 96       | RODFLEXLENGTH                  | 抽油杆伸长量       | NUMBER(8,2)   | m           |
| 97       | TUBINGFLEXLENGTH               | 油管伸缩值         | NUMBER(8,2)   | m           |
| 98       | INERTIALENGTH                  | 惯性载荷增量       | NUMBER(8,2)   | m           |
| 99       | PUMPINTAKEP                    | 泵入口压力         | NUMBER(8,2)   | MPa         |
| 100      | PUMPINTAKET                    | 泵入口温度         | NUMBER(8,2)   | ℃           |
| 101      | PUMPINTAKEGOL                  | 泵入口就地气液比   | NUMBER(8,2)   | m\^3/m\^3   |
| 102      | PUMPINTAKEVISL                 | 泵入口粘度         | NUMBER(8,2)   | mPa·s       |
| 103      | PUMPINTAKEBO                   | 泵入口原油体积系数 | NUMBER(8,2)   | 小数        |
| 104      | PUMPOUTLETP                    | 泵出口压力         | NUMBER(8,2)   | MPa         |
| 105      | PUMPOUTLETT                    | 泵出口温度         | NUMBER(8,2)   | ℃           |
| 106      | PUMPOUTLETGOL                  | 泵出口就地气液比   | NUMBER(8,2)   | m\^3/m\^3   |
| 107      | PUMPOUTLETVISL                 | 泵出口粘度         | NUMBER(8,2)   | mPa·s       |
| 108      | PUMPOUTLETBO                   | 泵出口原油体积系数 | NUMBER(8,2)   | 小数        |
| 109      | VIDEOURL                       | 视频路径           | VARCHAR2(400) |             |
| 110      | ORG_ID                         | 组织标号           | NUMBER(10)    |             |
| 111      | ORG_CODE                       | 组织代码           | VARCHAR2(20)  |             |
| 112      | SORTNUM                        | 排序编号           | NUMBER(10)    |             |

### 2.2.7 viw_rpc_diagram_hist

同viw_rpc_diagram_latest

### 2.2.8 viw_rpc_discrete_latest

表2-7 抽油机离散数据实时视图

| **序号** | **代码**                 | **名称**           | **类型**       | **单位** |
|----------|--------------------------|--------------------|----------------|----------|
| 1        | ID                       | 记录编号           | NUMBER(10)     |          |
| 2        | WELLNAME                 | 井名               | VARCHAR2(200)  |          |
| 3        | LIFTINGTYPE              | 举升类型           | NUMBER(10)     |          |
| 4        | LIFTINGTYPENAME          | 举升类型名称       | VARCHAR2(200)  |          |
| 5        | WELLID                   | 井编号             | NUMBER(10)     |          |
| 6        | SIGNINID                 | 设备地址           | VARCHAR2(200)  |          |
| 7        | COMMSTATUS               | 通信状态           | NUMBER(1)      |          |
| 8        | COMMSTATUSNAME           | 通信状态名称       | VARCHAR2       |          |
| 9        | COMMALARMLEVEL           | 通信状态报警级别   | NUMBER(4)      |          |
| 10       | RUNSTATUS                | 运行状态           | NUMBER(1)      |          |
| 11       | RUNSTATUSNAME            | 运行状态名称       | VARCHAR2       |          |
| 12       | RUNALARMLEVEL            | 运行状态报警级别   | NUMBER(3)      |          |
| 13       | COMMTIME                 | 在线时间           | NUMBER(8,2)    | h        |
| 14       | COMMRANGE                | 在线区间           | VARCHAR2(2000) |          |
| 15       | COMMTIMEEFFICIENCY       | 在线时率           | NUMBER(10,4)   |          |
| 16       | COMMTIMEEFFICIENCYLEVEL  | 在线时率级别       | VARCHAR2(50)   |          |
| 17       | RUNTIME                  | 运行时间           | NUMBER(8,2)    | h        |
| 18       | RUNRANGE                 | 运行区间           | VARCHAR2(2000) |          |
| 19       | RUNTIMEEFFICIENCY        | 运行时率           | NUMBER(10,4)   |          |
| 20       | RUNTIMEEFFICIENCYLEVEL   | 运行时率级别       | VARCHAR2(50)   |          |
| 21       | ACQTIME                  | 采集时间           | DATE           |          |
| 22       | ACQCYCLE_DIAGRAM         | 功图采集间隔       | NUMBER(6)      |          |
| 23       | ACQCYCLE_DISCRETE        | 离散数据采集间隔   | NUMBER(10)     |          |
| 24       | RESULTCODE               | 电参工况代码       | NUMBER(4)      |          |
| 25       | RESULTSTRING             | 电参工况累计字符串 | CLOB           |          |
| 26       | RESULTNAME               | 电参工况名称       | VARCHAR2(200)  |          |
| 27       | OPTIMIZATIONSUGGESTION   | 电参工况优化建议   | VARCHAR2(200)  |          |
| 28       | RESULTALARMLEVEL         | 电参工况报警等级   | NUMBER(3)      |          |
| 29       | TODAYKWATTH              | 日有功功耗         | NUMBER(10,2)   | kW·h     |
| 30       | TODAYKWATTHLEVEL         | 日有功功耗等级     | VARCHAR2(50)   |          |
| 31       | TODAYPKWATTH             | 日正向有功功耗     | NUMBER(10,2)   | kW·h     |
| 32       | TODAYNKWATTH             | 日反向有功功耗     | NUMBER(10,2)   | kW·h     |
| 33       | TODAYKVARH               | 日无功功耗         | NUMBER(10,2)   | kVar·h   |
| 34       | TODAYPKVARH              | 日正向无功功耗     | NUMBER(10,2)   | kVar·h   |
| 35       | TODAYNKVARH              | 日反向无功功耗     | NUMBER(10,2)   | kVar·h   |
| 36       | TODAYKVAH                | 日视在功耗         | NUMBER(10,2)   | kVA·h    |
| 37       | IA                       | A相电流            | NUMBER(8,2)    | A        |
| 38       | IB                       | B相电流            | NUMBER(8,2)    | A        |
| 39       | IC                       | C相电流            | NUMBER(8,2)    | A        |
| 40       | IAVG                     | 三项平均电流       | NUMBER(8,2)    | A        |
| 41       | ISTR                     | 电流字符串         | VARCHAR2       |          |
| 42       | IAUPLIMIT                | A相电流上限        | NUMBER(10,2)   | A        |
| 43       | IADOWNLIMIT              | A相电流下限        | NUMBER(10,2)   | A        |
| 44       | IAZERO                   | A相电流零值        | NUMBER(8,2)    | A        |
| 45       | IBUPLIMIT                | B相电流上限        | NUMBER(10,2)   | A        |
| 46       | IBDOWNLIMIT              | B相电流下限        | NUMBER(10,2)   | A        |
| 47       | IBZERO                   | B相电流零值        | NUMBER(8,2)    | A        |
| 48       | ICUPLIMIT                | C相电流上限        | NUMBER(10,2)   | A        |
| 49       | ICDOWNLIMIT              | C相电流下限        | NUMBER(10,2)   | A        |
| 50       | ICZERO                   | C相电流零值        | NUMBER(8,2)    | A        |
| 51       | WATTUPLIMIT              | 有功功率上限       | NUMBER(8,2)    | kW       |
| 52       | WATTDOWNLIMIT            | 有功功率下限       | NUMBER(8,2)    | kW       |
| 53       | IAMAX                    | A相电流最大值      | NUMBER(8,2)    | A        |
| 54       | IAMIN                    | A相电流最小值      | NUMBER(8,2)    | A        |
| 55       | IBMAX                    | B相电流最大值      | NUMBER(8,2)    | A        |
| 56       | IBMIN                    | B相电流最小值      | NUMBER(8,2)    | A        |
| 57       | ICMAX                    | C相电流最大值      | NUMBER(8,2)    | A        |
| 58       | ICMIN                    | C相电流最小值      | NUMBER(8,2)    | A        |
| 59       | VA                       | A相电压            | NUMBER(8,2)    | V        |
| 60       | VB                       | B相电压            | NUMBER(8,2)    | V        |
| 61       | VC                       | C相电压            | NUMBER(8,2)    | V        |
| 62       | VAVG                     | 三项平均电压       | NUMBER(8,2)    | V        |
| 63       | VSTR                     | 电压字符串         | VARCHAR2       |          |
| 64       | VAUPLIMIT                | A相电压上限        | NUMBER(10,2)   | V        |
| 65       | VADOWNLIMIT              | A相电压下限        | NUMBER(10,2)   | V        |
| 66       | VAZERO                   | A相电压零值        | NUMBER(8,2)    | V        |
| 67       | VBUPLIMIT                | B相电压上限        | NUMBER(10,2)   | V        |
| 68       | VBDOWNLIMIT              | B相电压下限        | NUMBER(10,2)   | V        |
| 69       | VBZERO                   | B相电压零值        | NUMBER(8,2)    | V        |
| 70       | VCUPLIMIT                | C相电压上限        | NUMBER(10,2)   | V        |
| 71       | VCDOWNLIMIT              | C相电压下限        | NUMBER(10,2)   | V        |
| 72       | VCZERO                   | C相电压零值        | NUMBER(8,2)    | V        |
| 73       | TOTALKWATTH              | 累计有功功耗       | NUMBER(10,2)   | kW·h     |
| 74       | TOTALPKWATTH             | 累计正向有功功耗   | NUMBER(10,2)   | kW·h     |
| 75       | TOTALNKWATTH             | 累计反向有功功耗   | NUMBER(10,2)   | kW·h     |
| 76       | TOTALKVARH               | 累计无功功耗       | NUMBER(10,2)   | kVar·h   |
| 77       | TOTALPKVARH              | 累计正向无功功耗   | NUMBER(10,2)   | kVar·h   |
| 78       | TOTALNKVARH              | 累计反向无功功耗   | NUMBER(10,2)   | kVar·h   |
| 79       | TOTALKVAH                | 累计视在功耗       | NUMBER(10,2)   | kVA·h    |
| 80       | WATTA                    | A相有功功率        | NUMBER(8,2)    | kW       |
| 81       | WATTB                    | B相有功功率        | NUMBER(8,2)    | kW       |
| 82       | WATTC                    | C相有功功率        | NUMBER(8,2)    | kW       |
| 83       | WATT3                    | 三相总有功功率     | NUMBER(8,2)    | kW       |
| 84       | WATTSTR                  | 有功功率字符串     | VARCHAR2       |          |
| 85       | VARA                     | A相无功功率        | NUMBER(8,2)    | kVar     |
| 86       | VARB                     | B相无功功率        | NUMBER(8,2)    | kVar     |
| 87       | VARC                     | C相无功功率        | NUMBER(8,2)    | kVar     |
| 88       | VAR3                     | 三相总无功功率     | NUMBER(8,2)    | kVar     |
| 89       | VARSTR                   | 无功功率字符串     | VARCHAR2       |          |
| 90       | REVERSEPOWER             | 反向功率           | NUMBER(8,2)    |          |
| 91       | VAA                      | A相视在功率        | NUMBER(8,2)    | kVA      |
| 92       | VAB                      | B相视在功率        | NUMBER(8,2)    | kVA      |
| 93       | VAC                      | C相视在功率        | NUMBER(8,2)    | kVA      |
| 94       | VA3                      | 三相总视在功率     | NUMBER(8,2)    | kVA      |
| 95       | VASTR                    | 视在功率字符串     | VARCHAR2       |          |
| 96       | PFA                      | A相功率因数        | NUMBER(8,2)    |          |
| 97       | PFB                      | B相功率因数        | NUMBER(8,2)    |          |
| 98       | PFC                      | C相功率因数        | NUMBER(8,2)    |          |
| 99       | PF3                      | 三相综合功率因数   | NUMBER(8,2)    |          |
| 100      | PFSTR                    | 功率因数字符串     | VARCHAR2       |          |
| 101      | SETFREQUENCY             | 设置频率           | NUMBER(8,2)    | HZ       |
| 102      | RUNFREQUENCY             | 运行频率           | NUMBER(8,2)    | HZ       |
| 103      | TUBINGPRESSURE           | 油压               | NUMBER(8,2)    | MPa      |
| 104      | CASINGPRESSURE           | 套压               | NUMBER(8,2)    | MPa      |
| 105      | BACKPRESSURE             | 回压               | NUMBER(8,2)    | MPa      |
| 106      | WELLHEADFLUIDTEMPERATURE | 井口油温           | NUMBER(8,2)    | ℃        |
| 107      | SIGNAL                   | 信号强度           | NUMBER(8,2)    |          |
| 108      | INTERVAL                 | 传输间隔           | NUMBER(10)     |          |
| 109      | DEVICEVER                | 设备版本           | VARCHAR2(50)   |          |
| 110      | VIDEOURL                 | 视频路径           | VARCHAR2(400)  |          |
| 111      | SORTNUM                  | 排序编号           | NUMBER(10)     |          |
| 112      | ORG_CODE                 | 组织代码           | VARCHAR2(20)   |          |
| 113      | ORG_ID                   | 组织编号           | NUMBER(10)     |          |

### 2.2.9 viw_rpc_discrete_hist

同 viw_rpc_discrete_latest

### 2.2.10 viw_rpc_comprehensive_latest

表2-8 抽油机综合数据实时视图

| **序号** | **代码**                       | **名称**                     | **类型**       | **单位**    |
|----------|--------------------------------|------------------------------|----------------|-------------|
| 1        | ID                             | 记录编号                     | NUMBER(10)     |             |
| 2        | WELLNAME                       | 井名                         | VARCHAR2(200)  |             |
| 3        | WELLID                         | 井编号                       | NUMBER(10)     |             |
| 4        | LIFTINGTYPE                    | 举升类型                     | NUMBER(10)     |             |
| 5        | ACQTIME                        | 采集时间                     | DATE           |             |
| 6        | ACQTIME_D                      | 离散数据采集时间             | DATE           |             |
| 7        | COMMSTATUS                     | 通信状态                     | NUMBER(2)      |             |
| 8        | COMMSTATUSNAME                 | 通信状态名称                 | VARCHAR2       |             |
| 9        | COMMALARMLEVEL                 | 通信状态报警级别             | NUMBER(3)      |             |
| 10       | RUNSTATUS                      | 运行状态                     | NUMBER(1)      |             |
| 11       | RUNSTATUSNAME                  | 运行状态名称                 | VARCHAR2       |             |
| 12       | RUNALARMLEVEL                  | 运行状态报警级别             | NUMBER(3)      |             |
| 13       | COMMTIME                       | 在线时间                     | NUMBER(8,2)    | h           |
| 14       | COMMRANGE                      | 在线区间                     | VARCHAR2(2000) |             |
| 15       | COMMTIMEEFFICIENCY             | 在线时率                     | NUMBER(10,4)   |             |
| 16       | COMMTIMEEFFICIENCYLEVEL        | 在线时率级别                 | VARCHAR2(50)   |             |
| 17       | RUNTIME                        | 运行时间                     | NUMBER(8,2)    | h           |
| 18       | RUNRANGE                       | 运行区间                     | VARCHAR2(2000) |             |
| 19       | RUNTIMEEFFICIENCY              | 运行时率                     | NUMBER(10,4)   |             |
| 20       | RUNTIMEEFFICIENCYLEVEL         | 运行时率级别                 | VARCHAR2(50)   |             |
| 21       | DATASOURCE                     | 功图数据来源                 | VARCHAR2(50)   |             |
| 22       | RESULTCODE                     | 功图工况代码                 | NUMBER(4)      |             |
| 23       | RESULTNAME                     | 功图工况名称                 | VARCHAR2(200)  |             |
| 24       | OPTIMIZATIONSUGGESTION         | 优化建议                     | VARCHAR2(200)  |             |
| 25       | RESULTALARMLEVEL               | 功图工况报警级别             | VARCHAR2       |             |
| 26       | RESULTCODE_E                   | 电参工况代码                 | NUMBER(4)      |             |
| 27       | RESULTNAME_E                   | 电参工况字符串               | CLOB           |             |
| 28       | RESULTALARMLEVEL_E             | 电参工况名称                 | VARCHAR2(200)  |             |
| 29       | RESULTSTRING_E                 | 电参工况报警级别             | NUMBER(3)      |             |
| 30       | THEORETICALPRODUCTION          | 理论排量                     | NUMBER(8,2)    | m\^3/d      |
| 31       | LIQUIDWEIGHTPRODUCTION         | 产液量吨                     | NUMBER(8,2)    | t/d         |
| 32       | OILWEIGHTPRODUCTION            | 产油量吨                     | NUMBER(8,2)    | t/d         |
| 33       | WATERWEIGHTPRODUCTION          | 产水量吨                     | NUMBER(8,2)    | t/d         |
| 34       | WEIGHTWATERCUT                 | 重量含水率                   | NUMBER(8,2)    | %           |
| 35       | LIQUIDWEIGHTPRODUCTIONLEVEL    | 产液级别吨                   | VARCHAR2(50)   |             |
| 36       | LIQUIDVOLUMETRICPRODUCTION     | 产液量方                     | NUMBER(8,2)    | m\^3/d      |
| 37       | OILVOLUMETRICPRODUCTION        | 产油量方                     | NUMBER(8,2)    | m\^3/d      |
| 38       | WATERVOLUMETRICPRODUCTION      | 产水量方                     | NUMBER(8,2)    | m\^3/d      |
| 39       | VOLUMEWATERCUT                 | 体积含水率                   | NUMBER(8,2)    | %           |
| 40       | LIQUIDVOLUMEPRODUCTIONLEVEL    | 产液级别方                   | VARCHAR2(50)   |             |
| 41       | LIQUIDWEIGHTPRODUCTION_L       | 实时累计产液量吨             | NUMBER(8,2)    | t/d         |
| 42       | OILWEIGHTPRODUCTION_L          | 实时累计产油量吨             | NUMBER(8,2)    | t/d         |
| 43       | WATERWEIGHTPRODUCTION_L        | 实时累计产水量吨             | NUMBER(8,2)    | t/d         |
| 44       | LIQUIDVOLUMETRICPRODUCTION_L   | 实时累计产液量方             | NUMBER(8,2)    | m\^3/d      |
| 45       | OILVOLUMETRICPRODUCTION_L      | 实时累计产油量方             | NUMBER(8,2)    | m\^3/d      |
| 46       | WATERVOLUMETRICPRODUCTION_L    | 实时累计产水量方             | NUMBER(8,2)    | m\^3/d      |
| 47       | PRODUCTIONGASOILRATIO          | 生产气油比                   | NUMBER(8,2)    | m\^3/t      |
| 48       | TUBINGPRESSURE                 | 油压                         | NUMBER(8,2)    | MPa         |
| 49       | CASINGPRESSURE                 | 套压                         | NUMBER(8,2)    | MPa         |
| 50       | WELLHEADFLUIDTEMPERATURE       | 井口油温                     | NUMBER(8,2)    | ℃           |
| 51       | PRODUCINGFLUIDLEVEL            | 动液面                       | NUMBER(8,2)    | m           |
| 52       | PRODUCINGFLUIDLEVELDATASOURCE  | 动液面来源                   | VARCHAR2(50)   |             |
| 53       | LEVELCORRECTVALUE              | 液面反演校正值               | NUMBER(8,2)    | m           |
| 54       | PUMPSETTINGDEPTH               | 泵挂                         | NUMBER(8,2)    | m           |
| 55       | SUBMERGENCE                    | 沉没度                       | NUMBER(8,2)    | m           |
| 56       | PUMPBOREDIAMETER               | 泵径                         | NUMBER(8,2)    | mm          |
| 57       | CRUDEOILDENSITY                | 原油密度                     | NUMBER(16,2)   | g/cm\^3     |
| 58       | NETGROSSRATIO                  | 净毛比                       | NUMBER(8,2)    |             |
| 59       | AVAILABLEPLUNGERSTROKEPROD_W   | 柱塞有效冲程产量吨           | NUMBER(8,2)    | t/d         |
| 60       | PUMPCLEARANCELEAKPROD_W        | 泵间隙漏失量吨               | NUMBER(8,2)    | t/d         |
| 61       | TVLEAKWEIGHTPRODUCTION         | 游动凡尔漏失量吨             | NUMBER(8,2)    | t/d         |
| 62       | SVLEAKWEIGHTPRODUCTION         | 固定凡尔漏失量吨             | NUMBER(8,2)    | t/d         |
| 63       | GASINFLUENCEPROD_W             | 气影响吨                     | NUMBER(8,2)    | t/d         |
| 64       | AVAILABLEPLUNGERSTROKEPROD_V   | 柱塞有效冲程产量方           | NUMBER(8,2)    | m\^3/d      |
| 65       | PUMPCLEARANCELEAKPROD_V        | 泵间隙漏失量方               | NUMBER(8,2)    | m\^3/d      |
| 66       | TVLEAKVOLUMETRICPRODUCTION     | 游动凡尔漏失量方             | NUMBER(8,2)    | m\^3/d      |
| 67       | SVLEAKVOLUMETRICPRODUCTION     | 固定凡尔漏失量方             | NUMBER(8,2)    | m\^3/d      |
| 68       | GASINFLUENCEPROD_V             | 气影响方                     | NUMBER(8,2)    | m\^3/d      |
| 69       | RODSTRING                      | 抽油杆数据                   | VARCHAR2(200)  |             |
| 70       | STROKE                         | 冲程                         | NUMBER(8,2)    | m           |
| 71       | SPM                            | 冲次                         | NUMBER(8,2)    | 次/min      |
| 72       | UPPERLOADLINE                  | 理论上载荷                   | NUMBER(8,2)    | kN          |
| 73       | UPPERLOADLINEOFEXACT           | 真实理论上载荷               | NUMBER(8,2)    | kN          |
| 74       | LOWERLOADLINE                  | 理论下载荷                   | NUMBER(8,2)    | kN          |
| 75       | DELTALOADLINE                  | 理论液柱载荷                 | NUMBER(8,2)    | kN          |
| 76       | FMAX                           | 最大载荷                     | NUMBER(8,2)    | kN          |
| 77       | FMIN                           | 最小载荷                     | NUMBER(8,2)    | kN          |
| 78       | DELTAF                         | 载荷差                       | NUMBER(8,2)    | kN          |
| 79       | FULLNESSCOEFFICIENT            | 充满系数                     | NUMBER(8,2)    | 小数        |
| 80       | NOLIQUIDFULLNESSCOEFFICIENT    | 抽空充满系数                 | NUMBER(10,4)   | 小数        |
| 81       | PLUNGERSTROKE                  | 柱塞冲程                     | NUMBER(8,2)    | m           |
| 82       | AVAILABLEPLUNGERSTROKE         | 柱塞有效冲程                 | NUMBER(8,2)    | m           |
| 83       | NOLIQUIDAVAILABLEPLUNGERSTROKE | 抽空柱塞有效冲程             | NUMBER(10,4)   | m           |
| 84       | AVERAGEWATT                    | 电机输入有功功率             | NUMBER(8,2)    | kW          |
| 85       | POLISHRODPOWER                 | 光杆功率                     | NUMBER(8,2)    | kW          |
| 86       | WATERPOWER                     | 水功率                       | NUMBER(8,2)    | kW          |
| 87       | SYSTEMEFFICIENCY               | 系统效率                     | NUMBER(12,3)   | 小数        |
| 88       | SYSTEMEFFICIENCYLEVEL          | 系统效率级别                 | VARCHAR2(50)   |             |
| 89       | SURFACESYSTEMEFFICIENCY        | 地面效率                     | NUMBER(12,3)   | 小数        |
| 90       | SURFACESYSTEMEFFICIENCYLEVEL   | 地面效率级别                 | VARCHAR2(50)   |             |
| 91       | WELLDOWNSYSTEMEFFICIENCY       | 井下效率                     | NUMBER(12,3)   | 小数        |
| 92       | WELLDOWNSYSTEMEFFICIENCYLEVEL  | 井下效率级别                 | VARCHAR2(50)   |             |
| 93       | AREA                           | 功图面积                     | NUMBER(8,2)    |             |
| 94       | ENERGYPER100MLIFT              | 吨液百米耗电量               | NUMBER(8,2)    | kW·h/100m·t |
| 95       | IDEGREEBALANCE                 | 电流平衡度                   | NUMBER(8,2)    | %           |
| 96       | IDEGREEBALANCENAME             | 电流平衡状态                 | VARCHAR2(200)  |             |
| 97       | UPSTROKEIMAX                   | 上冲程最大电流               | NUMBER(8,2)    | A           |
| 98       | DOWNSTROKEIMAX                 | 下冲程最大电流               | NUMBER(8,2)    | A           |
| 99       | IRATIO                         | 电流比                       | VARCHAR2       |             |
| 100      | IDEGREEBALANCEALARMLEVEL       | 电流平衡报警级别             | NUMBER(4)      |             |
| 101      | WATTDEGREEBALANCE              | 功率平衡度                   | NUMBER(8,2)    | %           |
| 102      | WATTDEGREEBALANCENAME          | 功率平衡状态                 | VARCHAR2(200)  |             |
| 103      | UPSTROKEWATTMAX                | 上冲程最大功率               | NUMBER(8,2)    | kW          |
| 104      | DOWNSTROKEWATTMAX              | 下冲程最大功率               | NUMBER(8,2)    | kW          |
| 105      | WATTRATIO                      | 功率比                       | VARCHAR2       |             |
| 106      | WATTDEGREEBALANCEALARMLEVEL    | 功率平衡报警级别             | NUMBER(4)      |             |
| 107      | DELTARADIUS                    | 曲柄平衡移动距离             | NUMBER(8,2)    | cm          |
| 108      | PUMPEFF1                       | 冲程损失系数                 | NUMBER(8,2)    | 小数        |
| 109      | PUMPEFF2                       | 充满系数                     | NUMBER(8,2)    | 小数        |
| 110      | PUMPEFF3                       | 间隙漏失系数                 | NUMBER(8,2)    | 小数        |
| 111      | PUMPEFF4                       | 液体收缩系数                 | NUMBER(8,2)    | 小数        |
| 112      | PUMPEFF                        | 总泵效                       | NUMBER(8,2)    | 小数        |
| 113      | RODFLEXLENGTH                  | 抽油杆伸长量                 | NUMBER(8,2)    | m           |
| 114      | TUBINGFLEXLENGTH               | 油管伸缩值                   | NUMBER(8,2)    | m           |
| 115      | INERTIALENGTH                  | 惯性载荷增量                 | NUMBER(8,2)    | m           |
| 116      | PUMPINTAKEP                    | 泵入口压力                   | NUMBER(8,2)    | MPa         |
| 117      | PUMPINTAKET                    | 泵入口温度                   | NUMBER(8,2)    | ℃           |
| 118      | PUMPINTAKEGOL                  | 泵入口就地气液比             | NUMBER(8,2)    | m\^3/m\^3   |
| 119      | PUMPINTAKEVISL                 | 泵入口粘度                   | NUMBER(8,2)    | mPa·s       |
| 120      | PUMPINTAKEBO                   | 泵入口原油体积系数           | NUMBER(8,2)    | 小数        |
| 121      | PUMPOUTLETP                    | 泵出口压力                   | NUMBER(8,2)    | MPa         |
| 122      | PUMPOUTLETT                    | 泵出口温度                   | NUMBER(8,2)    | ℃           |
| 123      | PUMPOUTLETGOL                  | 泵出口就地气液比             | NUMBER(8,2)    | m\^3/m\^3   |
| 124      | PUMPOUTLETVISL                 | 泵出口粘度                   | NUMBER(8,2)    | mPa·s       |
| 125      | PUMPOUTLETBO                   | 泵出口原油体积系数           | NUMBER(8,2)    | 小数        |
| 126      | ACQCYCLE_DIAGRAM               | 功图采集间隔                 | NUMBER(6)      | min         |
| 127      | ACQCYCLE_DISCRETE              | 离散数据采集间隔             | NUMBER(10)     | min         |
| 128      | TODAYKWATTH                    | 日有功功耗                   | NUMBER(10,2)   | kW·h        |
| 129      | TODAYKWATTHLEVEL               | 日有功功耗级别               | VARCHAR2(50)   |             |
| 130      | TODAYPKWATTH                   | 日正向有功功耗               | NUMBER(10,2)   | kW·h        |
| 131      | TODAYNKWATTH                   | 日反向有功功耗               | NUMBER(10,2)   | kW·h        |
| 132      | TODAYKVARH                     | 日无功功耗                   | NUMBER(10,2)   | kVar·h      |
| 133      | TODAYPKVARH                    | 日正向无功功耗               | NUMBER(10,2)   | kVar·h      |
| 134      | TODAYNKVARH                    | 日反向无功功耗               | NUMBER(10,2)   | kVar·h      |
| 135      | TODAYKVAH                      | 日视在功耗                   | NUMBER(10,2)   | kVA·h       |
| 136      | IA                             | A相电流                      | NUMBER(8,2)    | A           |
| 137      | IB                             | B相电流                      | NUMBER(8,2)    | A           |
| 138      | IC                             | C相电流                      | NUMBER(8,2)    | A           |
| 139      | IAVG                           | 三相平均电流                 | NUMBER(8,2)    | A           |
| 140      | ISTR                           | 电流字符串                   | VARCHAR2       |             |
| 141      | IAUPLIMIT                      | A相电流上限                  | NUMBER(10,2)   | A           |
| 142      | IADOWNLIMIT                    | A相电流下限                  | NUMBER(10,2)   | A           |
| 143      | IAZERO                         | A相电流零值                  | NUMBER(8,2)    | A           |
| 144      | IBUPLIMIT                      | B相电流上限                  | NUMBER(10,2)   | A           |
| 145      | IBDOWNLIMIT                    | B相电流下限                  | NUMBER(10,2)   | A           |
| 146      | IBZERO                         | B相电流零值                  | NUMBER(8,2)    | A           |
| 147      | ICUPLIMIT                      | C相电流上限                  | NUMBER(10,2)   | A           |
| 148      | ICDOWNLIMIT                    | C相电流下限                  | NUMBER(10,2)   | A           |
| 149      | ICZERO                         | C相电流零值                  | NUMBER(8,2)    | A           |
| 150      | WATTUPLIMIT                    | 有功功率上限                 | NUMBER(8,2)    | kW          |
| 151      | WATTDOWNLIMIT                  | 有功功率下限                 | NUMBER(8,2)    | kW          |
| 152      | IAMAX                          | A相电流最大值                | NUMBER(8,2)    | A           |
| 153      | IAMIN                          | A相电流最小值                | NUMBER(8,2)    | A           |
| 154      | IBMAX                          | B相电流最大值                | NUMBER(8,2)    | A           |
| 155      | IBMIN                          | B相电流最小值                | NUMBER(8,2)    | A           |
| 156      | ICMAX                          | C相电流最大值                | NUMBER(8,2)    | A           |
| 157      | ICMIN                          | C相电流最小值                | NUMBER(8,2)    | A           |
| 158      | VA                             | A相电压                      | NUMBER(8,2)    | V           |
| 159      | VB                             | B相电压                      | NUMBER(8,2)    | V           |
| 160      | VC                             | C相电压                      | NUMBER(8,2)    | V           |
| 161      | VAVG                           | 三相平均电压                 | NUMBER(8,2)    | V           |
| 162      | VSTR                           | 电压字符串                   | VARCHAR2       |             |
| 163      | VAUPLIMIT                      | A相电压上限                  | NUMBER(10,2)   | V           |
| 164      | VADOWNLIMIT                    | A相电压下限                  | NUMBER(10,2)   | V           |
| 165      | VAZERO                         | A相电压零值                  | NUMBER(8,2)    | V           |
| 166      | VBUPLIMIT                      | B相电压上限                  | NUMBER(10,2)   | V           |
| 167      | VBDOWNLIMIT                    | B相电压下限                  | NUMBER(10,2)   | V           |
| 168      | VBZERO                         | B相电压零值                  | NUMBER(8,2)    | V           |
| 169      | VCUPLIMIT                      | C相电压上限                  | NUMBER(10,2)   | V           |
| 170      | VCDOWNLIMIT                    | C相电压下限                  | NUMBER(10,2)   | V           |
| 171      | VCZERO                         | C相电压零值                  | NUMBER(8,2)    | V           |
| 172      | TOTALKWATTH                    | 累计有功功耗                 | NUMBER(10,2)   | kW·h        |
| 173      | TOTALPKWATTH                   | 累计正向有功功耗             | NUMBER(10,2)   | kW·h        |
| 174      | TOTALNKWATTH                   | 累计反向有功功耗             | NUMBER(10,2)   | kW·h        |
| 175      | TOTALKVARH                     | 累计无功功耗                 | NUMBER(10,2)   | kVar·h      |
| 176      | TOTALPKVARH                    | 累计正向无功功耗             | NUMBER(10,2)   | kVar·h      |
| 177      | TOTALNKVARH                    | 累计反向无功功耗             | NUMBER(10,2)   | kVar·h      |
| 178      | TOTALKVAH                      | 累计视在功耗                 | NUMBER(10,2)   | kVA·h       |
| 179      | WATTA                          | A相有功功率                  | NUMBER(8,2)    | kW          |
| 180      | WATTB                          | B相有功功率                  | NUMBER(8,2)    | kW          |
| 181      | WATTC                          | C相有功功率                  | NUMBER(8,2)    | kW          |
| 182      | WATT3                          | 三相总有功功率               | NUMBER(8,2)    | kW          |
| 183      | WATTSTR                        | 有功功率字符串               | VARCHAR2       |             |
| 184      | VARA                           | A相无功功率                  | NUMBER(8,2)    | kVar        |
| 185      | VARB                           | B相无功功率                  | NUMBER(8,2)    | kVar        |
| 186      | VARC                           | C相无功功率                  | NUMBER(8,2)    | kVar        |
| 187      | VAR3                           | 三相总无功功率               | NUMBER(8,2)    | kVar        |
| 188      | VARSTR                         | 无功功率字符串               | VARCHAR2       |             |
| 189      | REVERSEPOWER                   | 反向功率                     | NUMBER(8,2)    |             |
| 190      | VAA                            | A相视在功率                  | NUMBER(8,2)    | kVA         |
| 191      | VAB                            | B相视在功率                  | NUMBER(8,2)    | kVA         |
| 192      | VAC                            | C相视在功率                  | NUMBER(8,2)    | kVA         |
| 193      | VA3                            | 三相总视在功率               | NUMBER(8,2)    | kVA         |
| 194      | VASTR                          | 视在功率字符串               | VARCHAR2       |             |
| 195      | PFA                            | A相功率因数                  | NUMBER(8,2)    |             |
| 196      | PFB                            | B相功率因数                  | NUMBER(8,2)    |             |
| 197      | PFC                            | C相功率因数                  | NUMBER(8,2)    |             |
| 198      | PF3                            | 三相综合功率因数             | NUMBER(8,2)    |             |
| 199      | PFSTR                          | 功率因数字符串               | VARCHAR2       |             |
| 200      | SETFREQUENCY                   | 设置频率                     | NUMBER(8,2)    | HZ          |
| 201      | RUNFREQUENCY                   | 运行频率                     | NUMBER(8,2)    | HZ          |
| 202      | SIGNAL                         | 信号强度                     | NUMBER(8,2)    |             |
| 203      | INTERVAL                       | 传输间隔                     | NUMBER(10)     |             |
| 204      | DEVICEVER                      | 设备版本                     | VARCHAR2(50)   |             |
| 205      | BALANCECONTROLMODE             | 平衡远程调节远程触发控制     | NUMBER(10)     |             |
| 206      | BALANCECALCULATEMODE           | 平衡计算方式                 | NUMBER(10)     |             |
| 207      | BALANCEAWAYTIME                | 重心远离支点调节时间         | NUMBER(10)     | ms          |
| 208      | BALANCECLOSETIME               | 重心接近支点调节时间         | NUMBER(10)     | ms          |
| 209      | BALANCEAWAYTIMEPERBEAT         | 重心远离支点每拍调节时间     | NUMBER(10)     | ms          |
| 210      | BALANCECLOSETIMEPERBEAT        | 重心接近支点每拍调节时间     | NUMBER(10)     | ms          |
| 211      | BALANCESTROKECOUNT             | 参与平衡度计算的冲程测量次数 | NUMBER(10)     |             |
| 212      | BALANCEOPERATIONUPLIMIT        | 平衡调节上限                 | NUMBER(10)     | %           |
| 213      | BALANCEOPERATIONDOWNLIMIT      | 平衡调节下限                 | NUMBER(10)     | %           |
| 214      | BALANCEAUTOCONTROL             | 平衡远程自动调节             | NUMBER(1)      |             |
| 215      | SPMAUTOCONTROL                 | 冲次远程自动调节             | NUMBER(1)      |             |
| 216      | BALANCEFRONTLIMIT              | 平衡前限位                   | NUMBER(1)      |             |
| 217      | BALANCEAFTERLIMIT              | 平衡后限位                   | NUMBER(1)      |             |
| 218      | VIDEOURL                       | 视频路径                     | VARCHAR2(400)  |             |
| 219      | ORG_ID                         | 组织编号                     | NUMBER(10)     |             |
| 220      | ORG_CODE                       | 组织代码                     | VARCHAR2(20)   |             |
| 221      | SORTNUM                        | 排序编号                     | NUMBER(10)     |             |

### 2.2.11 viw_rpc_comprehensive_hist

同 viw_rpc_comprehensive_latest

### 2.2.12 viw_rpc_diagramquery_latest

表2-9 抽油机图形查询实时视图

| **序号** | **代码**                     | **名称**         | **类型**      | **单位** |
|----------|------------------------------|------------------|---------------|----------|
| 1        | ID                           | 记录编号         | NUMBER(10)    |          |
| 2        | WELLNAME                     | 井名             | VARCHAR2(200) |          |
| 3        | ACQTIME                      | 采集时间         | DATE          |          |
| 4        | STROKE                       | 冲程             | NUMBER(8,2)   | m        |
| 5        | SPM                          | 冲次             | NUMBER(8,2)   | 次/min   |
| 6        | FMAX                         | 最大载荷         | NUMBER(8,2)   | kN       |
| 7        | FMIN                         | 最小载荷         | NUMBER(8,2)   | kN       |
| 8        | POSITION_CURVE               | 位移曲线         | CLOB          |          |
| 9        | LOAD_CURVE                   | 载荷曲线         | CLOB          |          |
| 10       | POWER_CURVE                  | 功率曲线         | CLOB          |          |
| 11       | CURRENT_CURVE                | 电流曲线         | CLOB          |          |
| 12       | RPM_CURVE                    | 电机转速曲线     | CLOB          |          |
| 13       | RAWPOWER_CURVE               | 功率原始曲线     | CLOB          |          |
| 14       | RAWCURRENT_CURVE             | 电流原始曲线     | CLOB          |          |
| 15       | RAWRPM_CURVE                 | 电机转速原始曲线 | CLOB          |          |
| 16       | RESULTCODE                   | 功图工况代码     | NUMBER(4)     |          |
| 17       | RESULTNAME                   | 功图工况名称     | VARCHAR2(200) |          |
| 18       | RESULTALARMLEVEL             | 功图工况报警级别 |               |          |
| 19       | UPSTROKEIMAX                 | 上冲程最大电流   | VARCHAR2(200) | A        |
| 20       | DOWNSTROKEIMAX               | 下冲程最大电流   | NUMBER(8,2)   | A        |
| 21       | IDEGREEBALANCE               | 电流平衡度       | NUMBER(8,2)   | %        |
| 22       | IDEGREEBALANCELEVEL          | 电流平衡级别     | NUMBER(8,2)   |          |
| 23       | IDEGREEBALANCEALARMLEVEL     | 电流平衡报警级别 | VARCHAR2(200) |          |
| 24       | UPSTROKEWATTMAX              | 上冲程最大功率   | NUMBER(8,2)   | kW       |
| 25       | DOWNSTROKEWATTMAX            | 下冲程最大功率   | NUMBER(8,2)   | kW       |
| 26       | WATTDEGREEBALANCE            | 功率平衡度       | NUMBER(8,2)   | %        |
| 27       | WATTDEGREEBALANCELEVEL       | 功率平衡级别     | NUMBER(8,2)   |          |
| 28       | WATTDEGREEBALANCEALARMLEVEL  | 功率平衡报警级别 | VARCHAR2(200) |          |
| 29       | DATASOURCE                   | 数据来源         | NUMBER(1)     |          |
| 30       | UPPERLOADLINE                | 理论上载荷线     | NUMBER(8,2)   | kN       |
| 31       | LOWERLOADLINE                | 理论下载荷线     | NUMBER(8,2)   | kN       |
| 32       | LIQUIDWEIGHTPRODUCTION       | 产液量吨         | NUMBER(8,2)   | t/d      |
| 33       | LIQUIDVOLUMETRICPRODUCTION   | 产液量方         | NUMBER(8,2)   | m\^3/d   |
| 34       | LIQUIDWEIGHTPRODUCTION_L     | 实时累计产液量吨 |               |          |
| 35       | LIQUIDVOLUMETRICPRODUCTION_L | 实时累计产液量方 |               |          |
| 36       | SIGNAL                       | 信号强度         | NUMBER(8,2)   |          |
| 37       | DEVICEVER                    | 设备版本         | VARCHAR2(50)  |          |
| 38       | INTERVAL                     | 传输间隔         | NUMBER(10)    |          |
| 39       | ORGID                        | 组织编号         | NUMBER(10)    |          |
| 40       | SORTNUM                      | 排序编号         | NUMBER(10)    |          |

### 2.2.13 viw_rpc_diagramquery_hist

同 viw_rpc_diagramquery_latest

### 2.2.14 viw_rpc_total_day

表2-10 抽油机日累计数据视图

| **序号** | **代码**                      | **名称**         | **类型**       | **单位**    |
|----------|-------------------------------|------------------|----------------|-------------|
| 1        | ID                            | 记录编号         | NUMBER(10)     |             |
| 2        | WELLNAME                      | 井名             | VARCHAR2(200)  |             |
| 3        | LIFTINGTYPE                   | 举升类型         | NUMBER(10)     |             |
| 4        | LIFTINGTYPENAME               | 举升类型名称     | VARCHAR2(200)  |             |
| 5        | WELLID                        | 井编号           | NUMBER(10)     |             |
| 6        | SIGNINID                      | 设备地址         | VARCHAR2(200)  |             |
| 7        | CALCULATEDATE                 | 汇总日期         | DATE           |             |
| 8        | ACQUISITIONDATE               | 采集日期         | DATE           |             |
| 9        | COMMSTATUS                    | 通信状态         |                |             |
| 10       | COMMSTATUSNAME                | 通信状态名称     |                |             |
| 11       | COMMALARMLEVEL                | 通信状态报警级别 |                |             |
| 12       | RUNSTATUS                     | 运行状态         | NUMBER(2)      |             |
| 13       | RUNSTATUSNAME                 | 运行状态名称     |                |             |
| 14       | RUNALARMLEVEL                 | 运行状态报警级别 |                |             |
| 15       | COMMTIME                      | 在线时间         | NUMBER(8,2)    | h           |
| 16       | COMMRANGE                     | 在线区间         | VARCHAR2(4000) |             |
| 17       | COMMTIMEEFFICIENCY            | 在线时率         | NUMBER(12,3)   |             |
| 18       | COMMTIMEEFFICIENCYLEVEL       | 在线时率级别     | VARCHAR2(50)   |             |
| 19       | RUNTIME                       | 运行时间         | NUMBER(8,2)    | h           |
| 20       | RUNRANGE                      | 运行区间         | VARCHAR2(4000) |             |
| 21       | RUNTIMEEFFICIENCY             | 运行时率         | NUMBER(12,3)   |             |
| 22       | RUNTIMEEFFICIENCYLEVEL        | 运行时率级别     | VARCHAR2(50)   |             |
| 23       | RESULTCODE                    | 功图工况代码     | NUMBER(4)      |             |
| 24       | RESULTNAME                    | 功图工况名称     | VARCHAR2(200)  |             |
| 25       | RESULTSTRING                  | 功图工况字符串   | CLOB           |             |
| 26       | OPTIMIZATIONSUGGESTION        | 优化建议         | VARCHAR2(200)  |             |
| 27       | RESULTALARMLEVEL              | 功图工况报警级别 | NUMBER(3)      |             |
| 28       | RESULTCODE_E                  | 电参工况代码     | NUMBER(4)      |             |
| 29       | RESULTNAME_E                  | 电参工况名称     | VARCHAR2(200)  |             |
| 30       | RESULTSTRING_E                | 电参工况字符串   | CLOB           |             |
| 31       | RESULTALARMLEVEL_E            | 电参工况报警级别 | NUMBER(3)      |             |
| 32       | LIQUIDWEIGHTPRODUCTION        | 产液量吨         | NUMBER(8,2)    | t/d         |
| 33       | LIQUIDWEIGHTPRODUCTIONLEVEL   | 产油量吨         | NUMBER(8,2)    | t/d         |
| 34       | OILWEIGHTPRODUCTION           | 产水量吨         | NUMBER(8,2)    | t/d         |
| 35       | WATERWEIGHTPRODUCTION         | 重量含水率       | NUMBER(10,4)   | %           |
| 36       | WEIGHTWATERCUT                | 产液级别吨       | VARCHAR2(50)   |             |
| 37       | LIQUIDVOLUMETRICPRODUCTION    | 产液量方         | NUMBER(8,2)    | m\^3/d      |
| 38       | LIQUIDVOLUMEPRODUCTIONLEVEL   | 产油量方         | NUMBER(8,2)    | m\^3/d      |
| 39       | OILVOLUMETRICPRODUCTION       | 产水量方         | NUMBER(8,2)    | m\^3/d      |
| 40       | WATERVOLUMETRICPRODUCTION     | 体积含水率       | NUMBER(8,2)    | %           |
| 41       | VOLUMEWATERCUT                | 产液级别方       | VARCHAR2(50)   |             |
| 42       | EXTENDEDDAYS                  | 延用天数         | NUMBER(5)      |             |
| 43       | PRODUCTIONGASOILRATIO         | 生产气油比       | NUMBER(8,2)    | m\^3/t      |
| 44       | TUBINGPRESSURE                | 油压             | NUMBER(8,2)    | MPa         |
| 45       | CASINGPRESSURE                | 套压             | NUMBER(8,2)    | MPa         |
| 46       | WELLHEADFLUIDTEMPERATURE      | 井口油温         | NUMBER(8,2)    | ℃           |
| 47       | STROKE                        | 冲程             | NUMBER(8,2)    | m           |
| 48       | STROKEMAX                     | 冲程最大值       | NUMBER(8,2)    | m           |
| 49       | STROKEMIN                     | 冲程最小值       | NUMBER(8,2)    | m           |
| 50       | STROKESTR                     | 冲程字符串       | VARCHAR2       |             |
| 51       | SPM                           | 冲次             | NUMBER(8,2)    | 次/min      |
| 52       | SPMMAX                        | 冲次最大值       | NUMBER(8,2)    | 次/min      |
| 53       | SPMMIN                        | 冲次最小值       | NUMBER(8,2)    | 次/min      |
| 54       | SPMSTR                        | 冲次字符串       | VARCHAR2       |             |
| 55       | F                             | 载荷             | NUMBER(8,2)    | kN          |
| 56       | FMAX                          | 载荷最大值       | NUMBER(8,2)    | kN          |
| 57       | FMIN                          | 载荷最小值       | NUMBER(8,2)    | kN          |
| 58       | FSTR                          | 载荷字符串       | VARCHAR2       |             |
| 59       | PLUNGERSTROKE                 | 柱塞冲程         | NUMBER(8,2)    | m           |
| 60       | AVAILABLEPLUNGERSTROKE        | 柱塞有效冲程     | NUMBER(8,2)    | m           |
| 61       | NOLIQUIDAVAILABLESTROKE       | 抽空柱塞有效冲程 | NUMBER(10,4)   | m           |
| 62       | FULLNESSCOEFFICIENT           | 充满系数         | NUMBER(10,4)   | 小数        |
| 63       | NOLIQUIDFULLNESSCOEFFICIENT   | 抽空充满系数     | NUMBER(10,4)   | 小数        |
| 64       | PUMPEFF                       | 总泵效           | NUMBER(10,4)   | 小数        |
| 65       | PUMPBOREDIAMETER              | 泵径             | NUMBER(8,2)    | mm          |
| 66       | PUMPSETTINGDEPTH              | 泵挂             | NUMBER(8,2)    | m           |
| 67       | PRODUCINGFLUIDLEVEL           | 动液面           | NUMBER(8,2)    | m           |
| 68       | LEVELCORRECTVALUE             | 液面反演校正值   | NUMBER(8,2)    | MPa         |
| 69       | SUBMERGENCE                   | 沉没度           | NUMBER(8,2)    | m           |
| 70       | RPM                           | 转速             | NUMBER(8,2)    | r/min       |
| 71       | RPMMAX                        | 转速最大值       | NUMBER(8,2)    | r/min       |
| 72       | RPMMIN                        | 转速最小值       | NUMBER(8,2)    | r/min       |
| 73       | SYSTEMEFFICIENCY              | 系统效率         | NUMBER(10,4)   | 小数        |
| 74       | SYSTEMEFFICIENCYLEVEL         | 系统效率级别     | VARCHAR2(50)   |             |
| 75       | SURFACESYSTEMEFFICIENCY       | 地面效率         | NUMBER(10,4)   | 小数        |
| 76       | SURFACESYSTEMEFFICIENCYLEVEL  | 地面效率级别     | VARCHAR2(50)   |             |
| 77       | WELLDOWNSYSTEMEFFICIENCY      | 井下效率         | NUMBER(10,4)   | 小数        |
| 78       | WELLDOWNSYSTEMEFFICIENCYLEVEL | 井下效率级别     | VARCHAR2(50)   |             |
| 79       | ENERGYPER100MLIFT             | 吨液百米耗电量   | NUMBER(8,2)    | kW·h/100m·t |
| 80       | TODAYKWATTH                   | 日有功功耗       | NUMBER(10,2)   | kW·h        |
| 81       | TODAYKWATTHLEVEL              | 日有功功耗级别   | VARCHAR2(50)   |             |
| 82       | TODAYPKWATTH                  | 日正向有功功耗   | NUMBER(10,2)   | kW·h        |
| 83       | TODAYNKWATTH                  | 日反向有功功耗   | NUMBER(10,2)   | kW·h        |
| 84       | TODAYKVARH                    | 日无功功耗       | NUMBER(10,2)   | kVar·h      |
| 85       | TODAYPKVARH                   | 日正向无功功耗   | NUMBER(10,2)   | kVar·h      |
| 86       | TODAYNKVARH                   | 日反向无功功耗   | NUMBER(10,2)   | kVar·h      |
| 87       | TODAYKVAH                     | 日视在功耗       | NUMBER(10,2)   | kVA·h       |
| 88       | IDEGREEBALANCE                | 电流平衡度       | NUMBER(8,2)    | %           |
| 89       | IDEGREEBALANCEMAX             | 电流平衡度最大值 | NUMBER(8,2)    | %           |
| 90       | IDEGREEBALANCEMIN             | 电流平衡度最小值 | NUMBER(8,2)    | %           |
| 91       | IDEGREEBALANCESTR             | 电流平衡度字符串 | VARCHAR2       |             |
| 92       | IDEGREEBALANCELEVEL           | 电流平衡统计级别 | VARCHAR2(200)  |             |
| 93       | IDEGREEBALANCEALARMLEVEL      | 电流平衡报警级别 | NUMBER(3)      |             |
| 94       | WATTDEGREEBALANCE             | 功率平衡度       | NUMBER(8,2)    | %           |
| 95       | WATTDEGREEBALANCEMAX          | 功率平衡度最大值 | NUMBER(8,2)    | %           |
| 96       | WATTDEGREEBALANCEMIN          | 功率平衡度最小值 | NUMBER(8,2)    | %           |
| 97       | WATTDEGREEBALANCESTR          | 功率平衡度字符串 | VARCHAR2       |             |
| 98       | WATTDEGREEBALANCELEVEL        | 功率平衡统计级别 | VARCHAR2(200)  |             |
| 99       | WATTDEGREEBALANCEALARMLEVEL   | 功率平衡报警级别 | NUMBER(3)      |             |
| 100      | DELTARADIUS                   | 曲柄平衡移动距离 | NUMBER(8,2)    | m           |
| 101      | DELTARADIUSMAX                | 移动距离最大值   | NUMBER(8,2)    | m           |
| 102      | DELTARADIUSMIN                | 移动距离最小值   | NUMBER(8,2)    | m           |
| 103      | IA                            | A相电流          | NUMBER(8,2)    | A           |
| 104      | IAMAX                         | A相电流最大值    | NUMBER(8,2)    | A           |
| 105      | IAMIN                         | A相电流最小值    | NUMBER(8,2)    | A           |
| 106      | IASTR                         | A相电流字符串    | NUMBER(8,2)    |             |
| 107      | IB                            | B相电流          | NUMBER(8,2)    | A           |
| 108      | IBMAX                         | B相电流最大值    | NUMBER(8,2)    | A           |
| 109      | IBMIN                         | B相电流最小值    | NUMBER(8,2)    | A           |
| 110      | IBSTR                         | B相电流字符串    | VARCHAR2       |             |
| 111      | IC                            | C相电流          | NUMBER(8,2)    | A           |
| 112      | ICMAX                         | C相电流最大值    | NUMBER(8,2)    | A           |
| 113      | ICMIN                         | C相电流最小值    | NUMBER(8,2)    | A           |
| 114      | ICSTR                         | C相电流字符串    | VARCHAR2       |             |
| 115      | VA                            | A相电压          | NUMBER(8,2)    | V           |
| 116      | VAMAX                         | A相电压最大值    | NUMBER(8,2)    | V           |
| 117      | VAMIN                         | A相电压最小值    | NUMBER(8,2)    | V           |
| 118      | VASTR                         | A相电压字符串    | VARCHAR2       |             |
| 119      | VB                            | B相电压          | NUMBER(8,2)    | V           |
| 120      | VBMAX                         | B相电压最大值    | NUMBER(8,2)    | V           |
| 121      | VBMIN                         | B相电压最小值    | NUMBER(8,2)    | V           |
| 122      | VBSTR                         | B相电压字符串    | VARCHAR2       |             |
| 123      | VC                            | C相电压          | NUMBER(8,2)    | V           |
| 124      | VCMAX                         | C相电压最大值    | NUMBER(8,2)    | V           |
| 125      | VCMIN                         | C相电压最小值    | NUMBER(8,2)    | V           |
| 126      | VCSTR                         | C相电压字符串    | VARCHAR2       |             |
| 127      | SIGNAL                        | 信号强度         | NUMBER(8,2)    |             |
| 128      | SIGNALMAX                     | 信号强度最大值   | NUMBER(8,2)    |             |
| 129      | SIGNALMIN                     | 信号强度最小值   | NUMBER(8,2)    |             |
| 130      | SIGNALSTR                     | 信号强度字符串   | VARCHAR2       |             |
| 131      | VIDEOURL                      | 视频路径         | VARCHAR2(400)  |             |
| 132      | SORTNUM                       | 排序编号         | NUMBER(10)     |             |
| 133      | ORG_CODE                      | 组织代码         | VARCHAR2(20)   |             |
| 134      | ORG_ID                        | 组织编号         | NUMBER(10)     |             |
| 135      | REMARK                        | 备注             | VARCHAR2       |             |

### 2.1.15 viw_rpc_calculatemain

表2-11 抽油机计算结果管理视图

| **序号** | **代码**                   | **名称**       | **类型**      | **单位** |
|----------|----------------------------|----------------|---------------|----------|
| 1        | ID                         | 记录编号       | NUMBER(10)    |          |
| 2        | WELLNAME                   | 井名           | VARCHAR2(200) |          |
| 3        | LIFTINGTYPE                | 举升方式       | NUMBER(10)    |          |
| 4        | ACQTIME                    | 采集时间       | DATE          |          |
| 5        | RESULTNAME                 | 工况名称       | VARCHAR2(200) |          |
| 6        | LIQUIDWEIGHTPRODUCTION     | 产液量吨       | NUMBER(8,2)   | t/d      |
| 7        | OILWEIGHTPRODUCTION        | 产油量吨       | NUMBER(8,2)   | t/d      |
| 8        | LIQUIDVOLUMETRICPRODUCTION | 产液量方       | NUMBER(8,2)   | m\^3/d   |
| 9        | OILVOLUMETRICPRODUCTION    | 产油量方       | NUMBER(8,2)   | m\^3/d   |
| 10       | CRUDEOILDENSITY            | 原油密度       | NUMBER(16,2)  | g/cm\^3  |
| 11       | WATERDENSITY               | 水密度         | NUMBER(16,2)  | g/cm\^3  |
| 12       | NATURALGASRELATIVEDENSITY  | 天然气相对密度 | NUMBER(16,2)  |          |
| 13       | SATURATIONPRESSURE         | 饱和压力       | NUMBER(16,2)  | MPa      |
| 14       | RESERVOIRDEPTH             | 油层中部深度   | NUMBER(16,2)  | m        |
| 15       | RESERVOIRTEMPERATURE       | 油层中部温度   | NUMBER(16,2)  | ℃        |
| 16       | TUBINGPRESSURE             | 油压           | NUMBER(8,2)   | MPa      |
| 17       | CASINGPRESSURE             | 套压           | NUMBER(8,2)   | MPa      |
| 18       | WELLHEADFLUIDTEMPERATURE   | 井口温油       | NUMBER(8,2)   | ℃        |
| 19       | WEIGHTWATERCUT             | 体积含水率     | NUMBER(8,2)   | %        |
| 20       | PRODUCTIONGASOILRATIO      | 生产气油比     | NUMBER(8,2)   | m\^3/t   |
| 21       | PRODUCINGFLUIDLEVEL        | 动液面         | NUMBER(8,2)   | m        |
| 22       | PUMPSETTINGDEPTH           | 泵挂           | NUMBER(8,2)   | m        |
| 23       | PUMPTYPE                   | 泵类型         | VARCHAR2      |          |
| 24       | PUMPTYPENAME               | 泵类型名称     | VARCHAR2      |          |
| 25       | BARRELTYPE                 | 泵筒类型       | VARCHAR2      |          |
| 26       | BARRELTYPENAME             | 泵筒类型名称   | VARCHAR2      |          |
| 27       | PUMPGRADE                  | 泵级别         | NUMBER(1)     |          |
| 28       | PUMPBOREDIAMETER           | 泵径           | NUMBER(8,2)   | mm       |
| 29       | PLUNGERLENGTH              | 柱塞长         | NUMBER(8,2)   | m        |
| 30       | TUBINGSTRINGINSIDEDIAMETER | 油管内径       | NUMBER(8,2)   | mm       |
| 31       | CASINGSTRINGINSIDEDIAMETER | 套管内径       | NUMBER(8,2)   | mm       |
| 32       | RODSTRING                  | 杆数据         | VARCHAR2(200) |          |
| 33       | ANCHORINGSTATENAME         | 锚定状态       | VARCHAR2(200) |          |
| 34       | NETGROSSRATIO              | 净毛比         | NUMBER(8,2)   |          |
| 35       | RESULTSTATUS               | 计算标志       | NUMBER(2)     |          |
| 36       | ORGID                      | 组织编号       | NUMBER(10)    |          |

### 2.1.16 viw_rpc_calculatemain\_elec

表2-12 电参反演计算结果管理视图

| **序号** | **代码**                | **名称**              | **类型** | **单位** |
|----------|-------------------------|-----------------------|----------|----------|
| 1        | ID                      | 记录编号              | NUMBER   |          |
| 2        | WELLNAME                | 井名                  | VARCHAR2 |          |
| 3        | ACQTIME                 | 采集时间              | DATE     |          |
| 4        | RESULTSTATUS            | 计算状态              | NUMBER   |          |
| 5        | MANUFACTURER            | 抽油机厂家            | VARCHAR2 |          |
| 6        | MODEL                   | 抽油机型号            | VARCHAR2 |          |
| 7        | STROKE                  | 冲程                  | NUMBER   |          |
| 8        | CRANKROTATIONDIRECTION  | 曲柄旋转方向          | VARCHAR2 |          |
| 9        | OFFSETANGLEOFCRANK      | 曲柄偏置角            | NUMBER   |          |
| 10       | CRANKGRAVITYRADIUS      | 曲柄重心半径          | NUMBER   |          |
| 11       | SINGLECRANKWEIGHT       | 单块曲柄重量          | NUMBER   |          |
| 12       | SINGLECRANKPINWEIGHT    | 单块曲柄销重量        | NUMBER   |          |
| 13       | STRUCTURALUNBALANCE     | 结构不平衡重          | NUMBER   |          |
| 14       | BALANCEPOSITION         | 平衡块位置            | VARCHAR2 |          |
| 15       | BALANCEWEIGHT           | 平衡块重量            | VARCHAR2 |          |
| 16       | OFFSETANGLEOFCRANKPS    | 曲柄位置开关偏置角(°) | NUMBER   |          |
| 17       | SURFACESYSTEMEFFICIENCY | 地面效率              | NUMBER   |          |
| 18       | FS_LEFTPERCENT          | 功图左侧截取百分比    | NUMBER   |          |
| 19       | FS_RIGHTPERCENT         | 功图又侧截取百分比    | NUMBER   |          |
| 20       | WATTANGLE               | 功率滤波角度          | NUMBER   |          |
| 21       | FILTERTIME_WATT         | 功率曲线滤波次数      | NUMBER   |          |
| 22       | FILTERTIME_I            | 电流曲线滤波次数      | NUMBER   |          |
| 23       | FILTERTIME_RPM          | 转速曲线滤波次数      | NUMBER   |          |
| 24       | FILTERTIME_FSDIAGRAM    | 地面功图滤波次数      | NUMBER   |          |
| 25       | FILTERTIME_FSDIAGRAM_L  | 地面功图左侧滤波次数  | NUMBER   |          |
| 26       | FILTERTIME_FSDIAGRAM_R  | 地面功图左侧滤波次数  | NUMBER   |          |
| 27       | ORGID                   | 组织标号              | NUMBER   |          |

### 2.1.17 viw_pcp_productiondata_latest

表2-13 螺杆泵生产数据实时视图

| **序号** | **代码**                    | **名称**       | **类型**      | **单位** |
|----------|-----------------------------|----------------|---------------|----------|
| 1        | ID                          | 记录编号       | NUMBER(10)    |          |
| 2        | WELLNAME                    | 井名           | VARCHAR2(200) |          |
| 3        | WELLID                      | 井编号         | NUMBER(10)    |          |
| 4        | LIFTINGTYPE                 | 举升类型       | NUMBER(10)    |          |
| 5        | ACQTIME                     | 采集时间       | DATE          |          |
| 6        | RUNTIME                     | 运行时间       | NUMBER(8,2)   | h        |
| 7        | CRUDEOILDENSITY             | 原油密度       | NUMBER(16,2)  | g/cm\^3  |
| 8        | WATERDENSITY                | 水密度         | NUMBER(16,2)  | g/cm\^3  |
| 9        | NATURALGASRELATIVEDENSITY   | 天然气相对密度 | NUMBER(16,2)  |          |
| 10       | SATURATIONPRESSURE          | 饱和压力       | NUMBER(16,2)  | MPa      |
| 11       | RESERVOIRDEPTH              | 油层中部深度   | NUMBER(16,2)  | m        |
| 12       | RESERVOIRTEMPERATURE        | 油层中部温度   | NUMBER(16,2)  | ℃        |
| 13       | WEIGHTWATERCUT              | 重量含水率     | NUMBER(8,2)   | %        |
| 14       | VOLUMEWATERCUT              | 体积含水率     | NUMBER(8,2)   | %        |
| 15       | TUBINGPRESSURE              | 油压           | NUMBER(8,2)   | MPa      |
| 16       | CASINGPRESSURE              | 套压           | NUMBER(8,2)   | MPa      |
| 17       | BACKPRESSURE                | 回压           | NUMBER(8,2)   | MPa      |
| 18       | WELLHEADFLUIDTEMPERATURE    | 井口油温       | NUMBER(8,2)   | ℃        |
| 19       | PRODUCINGFLUIDLEVEL         | 动液面         | NUMBER(8,2)   | m        |
| 20       | PUMPSETTINGDEPTH            | 泵挂           | NUMBER(8,2)   | m        |
| 21       | PRODUCTIONGASOILRATIO       | 生产气油比     | NUMBER(8,2)   | m\^3/t   |
| 22       | PUMPBOREDIAMETER            | 泵径           | NUMBER(8,2)   | mm       |
| 23       | PUMPTYPE                    | 泵类型         | VARCHAR2(20)  |          |
| 24       | PUMPTYPENAME                | 泵类型名称     |               |          |
| 25       | PUMPGRADE                   | 泵级别         | NUMBER(1)     |          |
| 26       | PLUNGERLENGTH               | 柱塞长         | NUMBER(8,2)   | m        |
| 27       | BARRELTYPE                  | 泵筒类型       | VARCHAR2(20)  |          |
| 28       | BARRELTYPENAME              | 泵筒类型名称   |               |          |
| 29       | BARRELLENGTH                | 泵筒长         | NUMBER(8,2)   | m        |
| 30       | BARRELSERIES                | 泵级数         | NUMBER(8,2)   |          |
| 31       | ROTORDIAMETER               | 转子截面直径   | NUMBER(8,2)   | mm       |
| 32       | QPR                         | 转速           | NUMBER(8,2)   | r/min    |
| 33       | TUBINGSTRINGINSIDEDIAMETER  | 油管内径       | NUMBER(8,2)   | mm       |
| 34       | CASINGSTRINGINSIDEDIAMETER  | 套管内径       | NUMBER(8,2)   | mm       |
| 35       | RODSTRING                   | 杆数据         | VARCHAR2(200) |          |
| 36       | ANCHORINGSTATE              | 锚定状态       | NUMBER(1)     |          |
| 37       | ANCHORINGSTATENAME          | 锚定状态名称   | VARCHAR2(200) |          |
| 38       | NETGROSSRATIO               | 净毛比         | NUMBER(8,2)   |          |
| 39       | MANUALINTERVENTION          | 人工干预代码   | NUMBER(4)     |          |
| 40       | RUNTIMEEFFICIENCYSOURCE     | 时率来源       |               |          |
| 41       | RUNTIMEEFFICIENCYSOURCENAME | 时率来源名称   |               |          |
| 42       | SORTNUM                     | 排序编号       | NUMBER(10)    |          |
| 43       | ORG_ID                      | 组织编号       | NUMBER(10)    |          |

### 2.1.18 viw_pcp_productiondata_hist

同viw_pcp_productiondata_latest

### 2.1.19 viw_pcp_rpm_latest

表2-14 螺杆泵曲线数据实时视图

| **序号** | **代码**                    | **名称**           | **类型**      | **单位**    |
|----------|-----------------------------|--------------------|---------------|-------------|
| 1        | ID                          | 记录编号           | NUMBER(10)    |             |
| 2        | WELLNAME                    | 井名               | VARCHAR2(200) |             |
| 3        | WELLID                      | 井编号             | NUMBER(10)    |             |
| 4        | LIFTINGTYPE                 | 举升类型           | NUMBER(10)    |             |
| 5        | ACQTIME                     | 采集时间           | DATE          |             |
| 6        | RPM                         | 转速               | NUMBER(8,2)   | r/min       |
| 7        | TORQUE                      | 扭矩               | NUMBER(8,2)   | kN·m        |
| 8        | RESULTCODE                  | 工况代码           | NUMBER(4)     |             |
| 9        | RESULTNAME                  | 工况名称           | VARCHAR2(200) |             |
| 10       | OPTIMIZATIONSUGGESTION      | 优化建议           | VARCHAR2(200) |             |
| 11       | RESULTRUNALARMLEVEL         | 工况报警级别       | NUMBER(3)     |             |
| 12       | THEORETICALPRODUCTION       | 理论排量           | NUMBER(8,2)   | m\^3/d      |
| 13       | LIQUIDWEIGHTPRODUCTION      | 产液量吨           | NUMBER(8,2)   | t/d         |
| 14       | OILWEIGHTPRODUCTION         | 产油量吨           | NUMBER(8,2)   | t/d         |
| 15       | WATERWEIGHTPRODUCTION       | 产水量吨           | NUMBER(8,2)   | t/d         |
| 16       | WEIGHTWATERCUT              | 重量含水率         | NUMBER(8,2)   | %           |
| 17       | LIQUIDWEIGHTPRODUCTIONLEVEL | 产液级别吨         | VARCHAR2(50)  |             |
| 18       | LIQUIDVOLUMETRICPRODUCTION  | 产液量方           | NUMBER(8,2)   | m\^3/d      |
| 19       | OILVOLUMETRICPRODUCTION     | 产油量方           | NUMBER(8,2)   | m\^3/d      |
| 20       | WATERVOLUMETRICPRODUCTION   | 产水量方           | NUMBER(8,2)   | m\^3/d      |
| 21       | VOLUMEWATERCUT              | 体积含水率         | NUMBER(8,2)   | %           |
| 22       | LIQUIDVOLUMEPRODUCTIONLEVEL | 产液级别方         | VARCHAR2(50)  |             |
| 23       | PRODUCTIONGASOILRATIO       | 生产气油比         | NUMBER(8,2)   | m\^3/t      |
| 24       | TUBINGPRESSURE              | 油压               | NUMBER(8,2)   | MPa         |
| 25       | CASINGPRESSURE              | 套压               | NUMBER(8,2)   | MPa         |
| 26       | WELLHEADFLUIDTEMPERATURE    | 井口油温           | NUMBER(8,2)   | ℃           |
| 27       | QPR                         | 公称排量           | NUMBER(8,2)   | m\^3/r      |
| 28       | BARRELLENGTH                | 泵筒长             | NUMBER(8,2)   | m           |
| 29       | BARRELSERIES                | 泵级数             | NUMBER(8,2)   |             |
| 30       | ROTORDIAMETER               | 转子截面直径       | NUMBER(8,2)   | mm          |
| 31       | PRODUCINGFLUIDLEVEL         | 动液面             | NUMBER(8,2)   | m           |
| 32       | PUMPSETTINGDEPTH            | 泵挂               | NUMBER(8,2)   | m           |
| 33       | SUBMERGENCE                 | 沉没度             | NUMBER(8,2)   | m           |
| 34       | PUMPBOREDIAMETER            | 泵径               | NUMBER(8,2)   | mm          |
| 35       | CRUDEOILDENSITY             | 原油密度           | NUMBER(16,2)  | g/cm\^3     |
| 36       | NETGROSSRATIO               | 净毛比             | NUMBER(8,2)   |             |
| 37       | RODSTRING                   | 抽油杆柱分析数据   | VARCHAR2(200) |             |
| 38       | AVERAGEWATT                 | 电机输入有功功率   | NUMBER(8,2)   | kW          |
| 39       | WATERPOWER                  | 水功率             | NUMBER(8,2)   | kW          |
| 40       | SYSTEMEFFICIENCY            | 系统效率           | NUMBER(12,3)  | 小数        |
| 41       | SYSTEMEFFICIENCYLEVEL       | 系统效率级别       | VARCHAR2(50)  |             |
| 42       | ENERGYPER100MLIFT           | 吨液百米耗电量     | NUMBER(8,2)   | kW·h/100m·t |
| 43       | PUMPEFF1                    | 容积效率           | NUMBER(12,3)  | 小数        |
| 44       | PUMPEFF2                    | 液体收缩系数       | NUMBER(12,3)  | 小数        |
| 45       | PUMPEFF                     | 泵效               | NUMBER(12,3)  | 小数        |
| 46       | PUMPINTAKEP                 | 泵入口压力         | NUMBER(8,2)   | MPa         |
| 47       | PUMPINTAKET                 | 泵入口温度         | NUMBER(8,2)   | ℃           |
| 48       | PUMPINTAKEGOL               | 泵入口就地气液比   | NUMBER(8,2)   | m\^3/m\^3   |
| 49       | PUMPINTAKEVISL              | 泵入口粘度         | NUMBER(8,2)   | mPa·s       |
| 50       | PUMPINTAKEBO                | 泵入口原油体积系数 | NUMBER(8,2)   | 小数        |
| 51       | PUMPOUTLETP                 | 泵出口压力         | NUMBER(8,2)   | MPa         |
| 52       | PUMPOUTLETT                 | 泵出口温度         | NUMBER(8,2)   | ℃           |
| 53       | PUMPOUTLETGOL               | 泵出口就地气液比   | NUMBER(8,2)   | m\^3/m\^3   |
| 54       | PUMPOUTLETVISL              | 泵出口粘度         | NUMBER(8,2)   | mPa·s       |
| 55       | PUMPOUTLETBO                | 泵出口原油体积系数 | NUMBER(8,2)   | 小数        |
| 56       | VIDEOURL                    | 视频路径           | VARCHAR2(400) |             |
| 57       | ORG_ID                      | 组织标号           | NUMBER(10)    |             |
| 58       | ORG_CODE                    | 组织代码           | VARCHAR2(20)  |             |
| 59       | SORTNUM                     | 排序编号           | NUMBER(10)    |             |

### 2.1.20 viw_pcp_rpm_hist

同viw_pcp_rpm_latest

### 2.1.21 viw_pcp_discrete_latest

表2-15 螺杆泵离散数据实时视图

| **序号** | **代码**                    | **名称**         | **类型**       | **单位** |
|----------|-----------------------------|------------------|----------------|----------|
| 1        | ID                          | 记录编号         | NUMBER(10)     |          |
| 2        | WELLNAME                    | 井名             | VARCHAR2(200)  |          |
| 3        | LIFTINGTYPE                 | 举升类型         | NUMBER(10)     |          |
| 4        | LIFTINGTYPENAME             | 举升类型名称     | VARCHAR2(200)  |          |
| 5        | WELLID                      | 井编号           | NUMBER(10)     |          |
| 6        | COMMSTATUS                  | 通信状态         | NUMBER(1)      |          |
| 7        | COMMSTATUSNAME              | 通信状态名称     | VARCHAR2       |          |
| 8        | COMMALARMLEVEL              | 通信状态报警级别 | NUMBER(4)      |          |
| 9        | RUNSTATUS                   | 运行状态         | NUMBER(1)      |          |
| 10       | RUNSTATUSNAME               | 运行状态名称     | VARCHAR2       |          |
| 11       | RUNALARMLEVEL               | 运行状态报警级别 | NUMBER(3)      |          |
| 12       | COMMTIME                    | 在线时间         | NUMBER(8,2)    | h        |
| 13       | COMMRANGE                   | 在线区间         | VARCHAR2(2000) |          |
| 14       | COMMTIMEEFFICIENCY          | 在线时率         | NUMBER(10,4)   |          |
| 15       | COMMTIMEEFFICIENCYLEVEL     | 在线时率级别     | VARCHAR2(50)   |          |
| 16       | RUNTIME                     | 运行时间         | NUMBER(8,2)    | h        |
| 17       | RUNRANGE                    | 运行区间         | VARCHAR2(2000) |          |
| 18       | RUNTIMEEFFICIENCY           | 运行时率         | NUMBER(10,4)   |          |
| 19       | RUNTIMEEFFICIENCYLEVEL      | 运行时率等级     | VARCHAR2(50)   |          |
| 20       | ACQTIME                     | 采集时间         | DATE           |          |
| 21       | RESULTCODE_ELEC             | 工况代码         | NUMBER(4)      |          |
| 22       | RESULTSTRING_ELEC           | 工况累计字符串   | CLOB           |          |
| 23       | RESULTNAME_ELEC             | 工况名称         | VARCHAR2(200)  |          |
| 24       | OPTIMIZATIONSUGGESTION_ELEC | 优化建议         | VARCHAR2(200)  |          |
| 25       | RESULTALARMLEVEL            | 工况报警等级     | NUMBER(3)      |          |
| 26       | TODAYKWATTH                 | 日有功功耗       | NUMBER(8,2)    | kW·h     |
| 27       | TODAYKWATTHLEVEL            | 日有功功耗级别   | VARCHAR2(50)   |          |
| 28       | TODAYPKWATTH                | 日正向有功功耗   | NUMBER(8,2)    | kW·h     |
| 29       | TODAYNKWATTH                | 日反向有功功耗   | NUMBER(8,2)    | kW·h     |
| 30       | TODAYKVARH                  | 日无功功耗       | NUMBER(8,2)    | kVar·h   |
| 31       | TODAYPKVARH                 | 日正向无功功耗   | NUMBER(8,2)    | kVar·h   |
| 32       | TODAYNKVARH                 | 日反向无功功耗   | NUMBER(8,2)    | kVar·h   |
| 33       | TODAYKVAH                   | 日视在功耗       | NUMBER(8,2)    | kVA·h    |
| 34       | IA                          | A相电流          | NUMBER(8,2)    | A        |
| 35       | IB                          | B相电流          | NUMBER(8,2)    | A        |
| 36       | IC                          | C相电流          | NUMBER(8,2)    | A        |
| 37       | IAVG                        | 三项平均电流     | NUMBER(8,2)    | A        |
| 38       | ISTR                        | 电流字符串       | VARCHAR2       |          |
| 39       | IAUPLIMIT                   | A相电流上限      | NUMBER(8,2)    | A        |
| 40       | IADOWNLIMIT                 | A相电流下限      | NUMBER(8,2)    | A        |
| 41       | IAZERO                      | A相电流零值      | NUMBER(8,2)    | A        |
| 42       | IBUPLIMIT                   | B相电流上限      | NUMBER(8,2)    | A        |
| 43       | IBDOWNLIMIT                 | B相电流下限      | NUMBER(8,2)    | A        |
| 44       | IBZERO                      | B相电流零值      | NUMBER(8,2)    | A        |
| 45       | ICUPLIMIT                   | C相电流上限      | NUMBER(8,2)    | A        |
| 46       | ICDOWNLIMIT                 | C相电流下限      | NUMBER(8,2)    | A        |
| 47       | ICZERO                      | C相电流零值      | NUMBER(8,2)    | A        |
| 48       | VA                          | A相电压          | NUMBER(8,2)    | V        |
| 49       | VB                          | B相电压          | NUMBER(8,2)    | V        |
| 50       | VC                          | C相电压          | NUMBER(8,2)    | V        |
| 51       | VAVG                        | 三项平均电压     | NUMBER(8,2)    | V        |
| 52       | VSTR                        | 电压字符串       | VARCHAR2       |          |
| 53       | VAUPLIMIT                   | A相电压上限      | NUMBER(8,2)    | V        |
| 54       | VADOWNLIMIT                 | A相电压下限      | NUMBER(8,2)    | V        |
| 55       | VAZERO                      | A相电压零值      | NUMBER(8,2)    | V        |
| 56       | VBUPLIMIT                   | B相电压上限      | NUMBER(8,2)    | V        |
| 57       | VBDOWNLIMIT                 | B相电压下限      | NUMBER(8,2)    | V        |
| 58       | VBZERO                      | B相电压零值      | NUMBER(8,2)    | V        |
| 59       | VCUPLIMIT                   | C相电压上限      | NUMBER(8,2)    | V        |
| 60       | VCDOWNLIMIT                 | C相电压下限      | NUMBER(8,2)    | V        |
| 61       | VCZERO                      | C相电压零值      | NUMBER(8,2)    | V        |
| 62       | TOTALKWATTH                 | 累计有功功耗     | NUMBER(8,2)    | kW·h     |
| 63       | TOTALPKWATTH                | 累计正向有功功耗 | NUMBER(8,2)    | kW·h     |
| 64       | TOTALNKWATTH                | 累计反向有功功耗 | NUMBER(8,2)    | kW·h     |
| 65       | TOTALKVARH                  | 累计无功功耗     | NUMBER(8,2)    | kVar·h   |
| 66       | TOTALPKVARH                 | 累计正向无功功耗 | NUMBER(8,2)    | kVar·h   |
| 67       | TOTALNKVARH                 | 累计反向无功功耗 | NUMBER(8,2)    | kVar·h   |
| 68       | TOTALKVAH                   | 累计视在功耗     | NUMBER(8,2)    | kVA·h    |
| 69       | WATTA                       | A相有功功率      | NUMBER(8,2)    | kW       |
| 70       | WATTB                       | B相有功功率      | NUMBER(8,2)    | kW       |
| 71       | WATTC                       | C相有功功率      | NUMBER(8,2)    | kW       |
| 72       | WATT3                       | 三相总有功功率   | NUMBER(8,2)    | kW       |
| 73       | WATTSTR                     | 有功功率字符串   | VARCHAR2       |          |
| 74       | VARA                        | A相无功功率      | NUMBER(8,2)    | kVar     |
| 75       | VARB                        | B相无功功率      | NUMBER(8,2)    | kVar     |
| 76       | VARC                        | C相无功功率      | NUMBER(8,2)    | kVar     |
| 77       | VAR3                        | 三相总无功功率   | NUMBER(8,2)    | kVar     |
| 78       | VARSTR                      | 无功功率字符串   | VARCHAR2       |          |
| 79       | REVERSEPOWER                | 反向功率         | NUMBER(8,2)    |          |
| 80       | VAA                         | A相视在功率      | NUMBER(8,2)    | kVA      |
| 81       | VAB                         | B相视在功率      | NUMBER(8,2)    | kVA      |
| 82       | VAC                         | C相视在功率      | NUMBER(8,2)    | kVA      |
| 83       | VA3                         | 三相总视在功率   | NUMBER(8,2)    | kVA      |
| 84       | VASTR                       | 视在功率字符串   | VARCHAR2       |          |
| 85       | PFA                         | A相功率因数      | NUMBER(8,2)    |          |
| 86       | PFB                         | B相功率因数      | NUMBER(8,2)    |          |
| 87       | PFC                         | C相功率因数      | NUMBER(8,2)    |          |
| 88       | PF3                         | 三相综合功率因数 | NUMBER(8,2)    |          |
| 89       | PFSTR                       | 功率因数字符串   | VARCHAR2       |          |
| 90       | SETFREQUENCY                | 设置频率         | NUMBER(8,2)    | HZ       |
| 91       | RUNFREQUENCY                | 运行频率         | NUMBER(8,2)    | HZ       |
| 92       | TUBINGPRESSURE              | 油压             | NUMBER(8,2)    | MPa      |
| 93       | CASINGPRESSURE              | 套压             | NUMBER(8,2)    | MPa      |
| 94       | BACKPRESSURE                | 回压             | NUMBER(8,2)    | MPa      |
| 95       | WELLHEADFLUIDTEMPERATURE    | 井口油温         | NUMBER(8,2)    | ℃        |
| 96       | SIGNAL                      | 信号强度         | NUMBER(8,2)    |          |
| 97       | INTERVAL                    | 传输间隔         | NUMBER(10)     |          |
| 98       | DEVICEVER                   | 设备版本         | VARCHAR2(50)   |          |
| 99       | VIDEOURL                    | 视频路径         | VARCHAR2(400)  |          |
| 100      | SORTNUM                     | 排序编号         | NUMBER(10)     |          |
| 101      | ORG_CODE                    | 组织代码         | VARCHAR2(20)   |          |
| 102      | ORG_ID                      | 组织编号         | NUMBER(10)     |          |

### 2.1.22 viw_pcp_discrete_hist

同viw_pcp_discrete_latest

### 2.1.23 viw_pcp_comprehensive_latest

表2-16 螺杆泵综合数据实时视图

| **序号** | **名称**                    | **代码**           | **类型**      | **单位**    |
|----------|-----------------------------|--------------------|---------------|-------------|
| 1        | ID                          | 记录编号           | NUMBER(10)    |             |
| 2        | WELLNAME                    | 井名               | VARCHAR2(200) |             |
| 3        | WELLID                      | 井编号             | NUMBER(10)    |             |
| 4        | LIFTINGTYPE                 | 举升类型           | NUMBER(10)    |             |
| 5        | ACQTIME                     | 采集时间           | DATE          |             |
| 6        | ACQTIME_D                   | 离散数据采集时间   | DATE          |             |
| 7        | COMMSTATUS                  | 通信状态           | NUMBER(2)     |             |
| 8        | COMMSTATUSNAME              | 通信状态名称       | VARCHAR2      |             |
| 9        | COMMALARMLEVEL              | 通信状态报警级别   | NUMBER(3)     |             |
| 10       | RUNSTATUS                   | 运行状态           | NUMBER(1)     |             |
| 11       | RUNSTATUSNAME               | 运行状态名称       | VARCHAR2      |             |
| 12       | RUNALARMLEVEL               | 运行状态报警级别   | NUMBER(3)     |             |
| 13       | COMMTIME                    | 在线时间           | NUMBER(8,2)   | h           |
| 14       | COMMRANGE                   | 在线区间           | CLOB          |             |
| 15       | COMMTIMEEFFICIENCY          | 在线时率           | NUMBER(10,4)  |             |
| 16       | COMMTIMEEFFICIENCYLEVEL     | 在线时率级别       | VARCHAR2(50)  |             |
| 17       | RUNTIME                     | 运行时间           | NUMBER(8,2)   | h           |
| 18       | RUNRANGE                    | 运行区间           | CLOB          |             |
| 19       | RUNTIMEEFFICIENCY           | 运行时率           | NUMBER(10,4)  |             |
| 20       | RUNTIMEEFFICIENCYLEVEL      | 运行时率等级       | VARCHAR2(50)  |             |
| 21       | RESULTCODE                  | 工况代码           | NUMBER(4)     |             |
| 22       | RESULTNAME                  | 工况名称           | VARCHAR2(200) |             |
| 23       | OPTIMIZATIONSUGGESTION      | 优化建议           | VARCHAR2(200) |             |
| 24       | RESULTALARMLEVEL            | 工况报警级别       | VARCHAR2      |             |
| 25       | RESULTCODE_E                | 电参工况代码       | NUMBER(4)     |             |
| 26       | RESULTSTRING_E              | 电参工况字符串     | CLOB          |             |
| 27       | RESULTNAME_E                | 电参工况名称       | VARCHAR2(200) |             |
| 28       | OPTIMIZATIONSUGGESTION_E    | 电参工况优化建议   | VARCHAR2(200) |             |
| 29       | RESULTALARMLEVEL_E          | 电参工况报警级别   | NUMBER(3)     |             |
| 30       | RPM                         | 转速               | NUMBER(8,2)   | r/min       |
| 31       | TORQUE                      | 扭矩               | NUMBER(8,2)   | kN·m        |
| 32       | THEORETICALPRODUCTION       | 理论排量           | NUMBER(8,2)   | m\^3/d      |
| 33       | LIQUIDWEIGHTPRODUCTION      | 产液量吨           | NUMBER(8,2)   | t/d         |
| 34       | OILWEIGHTPRODUCTION         | 产油量吨           | NUMBER(8,2)   | t/d         |
| 35       | WATERWEIGHTPRODUCTION       | 产水量吨           | NUMBER(8,2)   | t/d         |
| 36       | WEIGHTWATERCUT              | 重量含水率         | NUMBER(8,2)   | %           |
| 37       | LIQUIDWEIGHTPRODUCTIONLEVEL | 产液级别吨         | VARCHAR2(50)  |             |
| 38       | LIQUIDVOLUMETRICPRODUCTION  | 产液量方           | NUMBER(8,2)   | m\^3/d      |
| 39       | OILVOLUMETRICPRODUCTION     | 产油量方           | NUMBER(8,2)   | m\^3/d      |
| 40       | WATERVOLUMETRICPRODUCTION   | 产水量方           | NUMBER(8,2)   | m\^3/d      |
| 41       | VOLUMEWATERCUT              | 体积含水率         | NUMBER(8,2)   | %           |
| 42       | LIQUIDVOLUMEPRODUCTIONLEVEL | 产液级别方         | VARCHAR2(50)  |             |
| 43       | PRODUCTIONGASOILRATIO       | 生产气油比         | NUMBER(8,2)   | m\^3/t      |
| 44       | TUBINGPRESSURE              | 油压               | NUMBER(8,2)   | MPa         |
| 45       | CASINGPRESSURE              | 套压               | NUMBER(8,2)   | MPa         |
| 46       | WELLHEADFLUIDTEMPERATURE    | 井口油温           | NUMBER(8,2)   | ℃           |
| 47       | QPR                         | 公称排量           | NUMBER(8,2)   | m\^3/r      |
| 48       | BARRELLENGTH                | 泵筒长             | NUMBER(8,2)   | m           |
| 49       | BARRELSERIES                | 泵级数             | NUMBER(8,2)   |             |
| 50       | ROTORDIAMETER               | 转子截面直径       | NUMBER(8,2)   | mm          |
| 51       | PRODUCINGFLUIDLEVEL         | 动液面             | NUMBER(8,2)   | m           |
| 52       | PUMPSETTINGDEPTH            | 泵挂               | NUMBER(8,2)   | m           |
| 53       | SUBMERGENCE                 | 沉没度             | NUMBER(8,2)   | m           |
| 54       | PUMPBOREDIAMETER            | 泵径               | NUMBER(8,2)   | mm          |
| 55       | CRUDEOILDENSITY             | 原油密度           | NUMBER(16,2)  | g/cm\^3     |
| 56       | NETGROSSRATIO               | 净毛比             | NUMBER(8,2)   |             |
| 57       | RODSTRING                   | 抽油杆数据         | VARCHAR2(200) |             |
| 58       | AVERAGEWATT                 | 电机输入有功功率   | NUMBER(8,2)   | kW          |
| 59       | WATERPOWER                  | 水功率             | NUMBER(8,2)   | kW          |
| 60       | SYSTEMEFFICIENCY            | 系统效率           | NUMBER(12,3)  | 小数        |
| 61       | SYSTEMEFFICIENCYLEVEL       | 系统效率级别       | VARCHAR2(50)  |             |
| 62       | ENERGYPER100MLIFT           | 吨液百米耗电量     | NUMBER(8,2)   | kW·h/100m·t |
| 63       | PUMPEFF1                    | 容积效率           | NUMBER(12,3)  | 小数        |
| 64       | PUMPEFF2                    | 液体收缩系数       | NUMBER(12,3)  | 小数        |
| 65       | PUMPEFF                     | 泵效               | NUMBER(12,3)  | 小数        |
| 66       | PUMPINTAKEP                 | 泵入口压力         | NUMBER(8,2)   | MPa         |
| 67       | PUMPINTAKET                 | 泵入口温度         | NUMBER(8,2)   | ℃           |
| 68       | PUMPINTAKEGOL               | 泵入口就地气液比   | NUMBER(8,2)   | m\^3/m\^3   |
| 69       | PUMPINTAKEVISL              | 泵入口粘度         | NUMBER(8,2)   | mPa·s       |
| 70       | PUMPINTAKEBO                | 泵入口原油体积系数 | NUMBER(8,2)   | 小数        |
| 71       | PUMPOUTLETP                 | 泵出口压力         | NUMBER(8,2)   | MPa         |
| 72       | PUMPOUTLETT                 | 泵出口温度         | NUMBER(8,2)   | ℃           |
| 73       | PUMPOUTLETGOL               | 泵出口就地气液比   | NUMBER(8,2)   | m\^3/m\^3   |
| 74       | PUMPOUTLETVISL              | 泵出口粘度         | NUMBER(8,2)   | mPa·s       |
| 75       | PUMPOUTLETBO                | 泵出口原油体积系数 | NUMBER(8,2)   | 小数        |
| 76       | TODAYKWATTH                 | 日有功功耗         | NUMBER(8,2)   | kW·h        |
| 77       | TODAYKWATTHLEVEL            | 日有功功耗级别     | VARCHAR2(50)  |             |
| 78       | TODAYPKWATTH                | 日正向有功功耗     | NUMBER(8,2)   | kW·h        |
| 79       | TODAYNKWATTH                | 日反向有功功耗     | NUMBER(8,2)   | kW·h        |
| 80       | TODAYKVARH                  | 日无功功耗         | NUMBER(8,2)   | kVar·h      |
| 81       | TODAYPKVARH                 | 日正向无功功耗     | NUMBER(8,2)   | kVar·h      |
| 82       | TODAYNKVARH                 | 日反向无功功耗     | NUMBER(8,2)   | kVar·h      |
| 83       | TODAYKVAH                   | 日视在功耗         | NUMBER(8,2)   | kVA·h       |
| 84       | IA                          | A相电流            | NUMBER(8,2)   | A           |
| 85       | IB                          | B相电流            | NUMBER(8,2)   | A           |
| 86       | IC                          | C相电流            | NUMBER(8,2)   | A           |
| 87       | IAVG                        | 三相平均电流       | NUMBER(8,2)   | A           |
| 88       | ISTR                        | 电流字符串         | VARCHAR2      |             |
| 89       | IAUPLIMIT                   | A相电流上限        | NUMBER(8,2)   | A           |
| 90       | IADOWNLIMIT                 | A相电流下限        | NUMBER(8,2)   | A           |
| 91       | IAZERO                      | A相电流零值        | NUMBER(8,2)   | A           |
| 92       | IBUPLIMIT                   | B相电流上限        | NUMBER(8,2)   | A           |
| 93       | IBDOWNLIMIT                 | B相电流下限        | NUMBER(8,2)   | A           |
| 94       | IBZERO                      | B相电流零值        | NUMBER(8,2)   | A           |
| 95       | ICUPLIMIT                   | C相电流上限        | NUMBER(8,2)   | A           |
| 96       | ICDOWNLIMIT                 | C相电流下限        | NUMBER(8,2)   | A           |
| 97       | ICZERO                      | C相电流零值        | NUMBER(8,2)   | A           |
| 98       | VA                          | A相电压            | NUMBER(8,2)   | V           |
| 99       | VB                          | B相电压            | NUMBER(8,2)   | V           |
| 100      | VC                          | C相电压            | NUMBER(8,2)   | V           |
| 101      | VAVG                        | 三相平均电压       | NUMBER(8,2)   | V           |
| 102      | VSTR                        | 电压字符串         | VARCHAR2      |             |
| 103      | VAUPLIMIT                   | A相电压上限        | NUMBER(8,2)   | V           |
| 104      | VADOWNLIMIT                 | A相电压下限        | NUMBER(8,2)   | V           |
| 105      | VAZERO                      | A相电压零值        | NUMBER(8,2)   | V           |
| 106      | VBUPLIMIT                   | B相电压上限        | NUMBER(8,2)   | V           |
| 107      | VBDOWNLIMIT                 | B相电压下限        | NUMBER(8,2)   | V           |
| 108      | VBZERO                      | B相电压零值        | NUMBER(8,2)   | V           |
| 109      | VCUPLIMIT                   | C相电压上限        | NUMBER(8,2)   | V           |
| 110      | VCDOWNLIMIT                 | C相电压下限        | NUMBER(8,2)   | V           |
| 111      | VCZERO                      | C相电压零值        | NUMBER(8,2)   | V           |
| 112      | TOTALKWATTH                 | 累计有功功耗       | NUMBER(8,2)   | kW·h        |
| 113      | TOTALPKWATTH                | 累计正向有功功耗   | NUMBER(8,2)   | kW·h        |
| 114      | TOTALNKWATTH                | 累计反向有功功耗   | NUMBER(8,2)   | kW·h        |
| 115      | TOTALKVARH                  | 累计无功功耗       | NUMBER(8,2)   | kVar·h      |
| 116      | TOTALPKVARH                 | 累计正向无功功耗   | NUMBER(8,2)   | kVar·h      |
| 117      | TOTALNKVARH                 | 累计反向无功功耗   | NUMBER(8,2)   | kVar·h      |
| 118      | TOTALKVAH                   | 累计视在功耗       | NUMBER(8,2)   | kVA·h       |
| 119      | WATTA                       | A相有功功率        | NUMBER(8,2)   | kW          |
| 120      | WATTB                       | B相有功功率        | NUMBER(8,2)   | kW          |
| 121      | WATTC                       | C相有功功率        | NUMBER(8,2)   | kW          |
| 122      | WATT3                       | 三相总有功功率     | NUMBER(8,2)   | kW          |
| 123      | WATTSTR                     | 有功功率字符串     | VARCHAR2      |             |
| 124      | VARA                        | A相无功功率        | NUMBER(8,2)   | kVar        |
| 125      | VARB                        | B相无功功率        | NUMBER(8,2)   | kVar        |
| 126      | VARC                        | C相无功功率        | NUMBER(8,2)   | kVar        |
| 127      | VAR3                        | 三相总无功功率     | NUMBER(8,2)   | kVar        |
| 128      | VARSTR                      | 无功功率字符串     | VARCHAR2      |             |
| 129      | REVERSEPOWER                | 反向功率           | NUMBER(8,2)   |             |
| 130      | VAA                         | A相视在功率        | NUMBER(8,2)   | kVA         |
| 131      | VAB                         | B相视在功率        | NUMBER(8,2)   | kVA         |
| 132      | VAC                         | C相视在功率        | NUMBER(8,2)   | kVA         |
| 133      | VA3                         | 三相总视在功率     | NUMBER(8,2)   | kVA         |
| 134      | VASTR                       | 视在功率字符串     | VARCHAR2      |             |
| 135      | PFA                         | A相功率因数        | NUMBER(8,2)   |             |
| 136      | PFB                         | B相功率因数        | NUMBER(8,2)   |             |
| 137      | PFC                         | C相功率因数        | NUMBER(8,2)   |             |
| 138      | PF3                         | 三相综合功率因数   | NUMBER(8,2)   |             |
| 139      | PFSTR                       | 功率因数字符串     | VARCHAR2      |             |
| 140      | SETFREQUENCY                | 设置频率           | NUMBER(8,2)   | HZ          |
| 141      | RUNFREQUENCY                | 运行频率           | NUMBER(8,2)   | HZ          |
| 142      | SIGNAL                      | 信号强度           | NUMBER(8,2)   |             |
| 143      | INTERVAL                    | 传输间隔           | NUMBER(10)    |             |
| 144      | DEVICEVER                   | 设备版本           | VARCHAR2(50)  |             |
| 145      | VIDEOURL                    | 视频路径           | VARCHAR2(400) |             |
| 146      | ORG_ID                      | 组织编号           | NUMBER(10)    |             |
| 147      | ORG_CODE                    | 组织代码           | VARCHAR2(20)  |             |
| 148      | SORTNUM                     | 排序编号           | NUMBER(10)    |             |

### 2.1.24 viw_pcp_comprehensive_hist

同viw_pcp_comprehensive_latest

### 2.1.25 viw_pcp_total_day

表2-17 螺杆泵日累计数据视图

| **序号** | **名称**                    | **代码**         | **类型**       | **单位**    |
|----------|-----------------------------|------------------|----------------|-------------|
| 1        | ID                          | 记录编号         | NUMBER(10)     |             |
| 2        | WELLNAME                    | 井名             | VARCHAR2(200)  |             |
| 3        | LIFTINGTYPE                 | 举升类型         | NUMBER(10)     |             |
| 4        | LIFTINGTYPENAME             | 举升类型名称     | VARCHAR2(200)  |             |
| 5        | WELLID                      | 井编号           | NUMBER(10)     |             |
| 6        | CALCULATEDATE               | 日期             | DATE           |             |
| 7        | COMMSTATUS                  | 通信状态         |                |             |
| 8        | COMMSTATUSNAME              | 通信名称         |                |             |
| 9        | COMMALARMLEVEL              | 通信状态报警级别 |                |             |
| 10       | RUNSTATUS                   | 运行状态         | NUMBER(2)      |             |
| 11       | RUNSTATUSNAME               | 运行状态名称     |                |             |
| 12       | RUNALARMLEVEL               | 运行状态报警级别 |                |             |
| 13       | COMMTIME                    | 在线时间         | NUMBER(8,2)    | h           |
| 14       | COMMRANGE                   | 在线区间         | VARCHAR2(4000) |             |
| 15       | COMMTIMEEFFICIENCY          | 在线时率         | NUMBER(12,3)   |             |
| 16       | COMMTIMEEFFICIENCYLEVEL     | 在线时率级别     | VARCHAR2(50)   |             |
| 17       | RUNTIME                     | 运行时间         | NUMBER(8,2)    | h           |
| 18       | RUNRANGE                    | 运行区间         | VARCHAR2(4000) |             |
| 19       | RUNTIMEEFFICIENCY           | 运行时率         | NUMBER(12,3)   |             |
| 20       | RUNTIMEEFFICIENCYLEVEL      | 运行时率级别     | VARCHAR2(50)   |             |
| 21       | RESULTCODE                  | 工况代码         | NUMBER(4)      |             |
| 22       | RESULTNAME                  | 工况名称         | VARCHAR2(200)  |             |
| 23       | RESULTSTRING                | 工况累计字符串   | VARCHAR2(4000) |             |
| 24       | OPTIMIZATIONSUGGESTION      | 优化建议         | VARCHAR2(200)  |             |
| 25       | RESULTALARMLEVEL            | 工况报警级别     | NUMBER(3)      |             |
| 26       | LIQUIDWEIGHTPRODUCTION      | 产液量吨         | NUMBER(8,2)    | t/d         |
| 27       | LIQUIDWEIGHTPRODUCTIONLEVEL | 产油量吨         | NUMBER(8,2)    | t/d         |
| 28       | OILWEIGHTPRODUCTION         | 产水量吨         | NUMBER(8,2)    | t/d         |
| 29       | WATERWEIGHTPRODUCTION       | 重量含水率       | NUMBER(8,2)    | %           |
| 30       | WEIGHTWATERCUT              | 产液级别吨       | VARCHAR2(50)   |             |
| 31       | LIQUIDVOLUMETRICPRODUCTION  | 产液量方         | NUMBER(8,2)    | m\^3/d      |
| 32       | LIQUIDVOLUMEPRODUCTIONLEVEL | 产油量方         | NUMBER(8,2)    | m\^3/d      |
| 33       | OILVOLUMETRICPRODUCTION     | 产水量方         | NUMBER(8,2)    | m\^3/d      |
| 34       | WATERVOLUMETRICPRODUCTION   | 体积含水率       | NUMBER(8,2)    | %           |
| 35       | VOLUMEWATERCUT              | 产液级别方       | VARCHAR2(50)   |             |
| 36       | PRODUCTIONGASOILRATIO       | 生产气油比       | NUMBER(8,2)    | m\^3/t      |
| 37       | TUBINGPRESSURE              | 油压             | NUMBER(8,2)    | MPa         |
| 38       | CASINGPRESSURE              | 套压             | NUMBER(8,2)    | MPa         |
| 39       | WELLHEADFLUIDTEMPERATURE    | 井口油温         | NUMBER(8,2)    | ℃           |
| 40       | PUMPEFF                     | 总泵效           | NUMBER(10,4)   | 小数        |
| 41       | PUMPBOREDIAMETER            | 泵径             | NUMBER(8,2)    | mm          |
| 42       | PUMPSETTINGDEPTH            | 泵挂             | NUMBER(8,2)    | m           |
| 43       | PRODUCINGFLUIDLEVEL         | 动液面           | NUMBER(8,2)    | m           |
| 44       | SUBMERGENCE                 | 沉没度           | NUMBER(8,2)    | m           |
| 45       | RPM                         | 转速             | NUMBER(8,2)    | r/min       |
| 46       | RPMMAX                      | 转速最大值       | NUMBER(8,2)    | r/min       |
| 47       | RPMMIN                      | 转速最小值       | NUMBER(8,2)    | r/min       |
| 48       | SYSTEMEFFICIENCY            | 系统效率         | NUMBER(10,4)   | 小数        |
| 49       | SYSTEMEFFICIENCYLEVEL       | 系统效率级别     | VARCHAR2(50)   |             |
| 50       | ENERGYPER100MLIFT           | 吨液百米耗电量   | NUMBER(8,2)    | kW·h/100m·t |
| 51       | TODAYKWATTH                 | 日有功功耗       | NUMBER(8,2)    | kW·h        |
| 52       | TODAYKWATTHLEVEL            | 日有功功耗级别   | VARCHAR2(50)   |             |
| 53       | TODAYPKWATTH                | 日正向有功功耗   | NUMBER(8,2)    | kW·h        |
| 54       | TODAYNKWATTH                | 日反向有功功耗   | NUMBER(8,2)    | kW·h        |
| 55       | TODAYKVARH                  | 日无功功耗       | NUMBER(8,2)    | kVar·h      |
| 56       | TODAYPKVARH                 | 日正向无功功耗   | NUMBER(8,2)    | kVar·h      |
| 57       | TODAYNKVARH                 | 日反向无功功耗   | NUMBER(8,2)    | kVar·h      |
| 58       | TODAYKVAH                   | 日视在功耗       | NUMBER(8,2)    | kVA·h       |
| 59       | IA                          | A相电流          | NUMBER(8,2)    | A           |
| 60       | IAMAX                       | A相电流最大值    | NUMBER(8,2)    | A           |
| 61       | IAMIN                       | A相电流最小值    | NUMBER(8,2)    | A           |
| 62       | IASTR                       | A相电流字符串    | NUMBER(8,2)    |             |
| 63       | IB                          | B相电流          | NUMBER(8,2)    | A           |
| 64       | IBMAX                       | B相电流最大值    | NUMBER(8,2)    | A           |
| 65       | IBMIN                       | B相电流最小值    | NUMBER(8,2)    | A           |
| 66       | IBSTR                       | B相电流字符串    | VARCHAR2       |             |
| 67       | IC                          | C相电流          | NUMBER(8,2)    | A           |
| 68       | ICMAX                       | C相电流最大值    | NUMBER(8,2)    | A           |
| 69       | ICMIN                       | C相电流最小值    | NUMBER(8,2)    | A           |
| 70       | ICSTR                       | C相电流字符串    | VARCHAR2       |             |
| 71       | VA                          | A相电压          | NUMBER(8,2)    | V           |
| 72       | VAMAX                       | A相电压最大值    | NUMBER(8,2)    | V           |
| 73       | VAMIN                       | A相电压最小值    | NUMBER(8,2)    | V           |
| 74       | VASTR                       | A相电压字符串    | VARCHAR2       |             |
| 75       | VB                          | B相电压          | NUMBER(8,2)    | V           |
| 76       | VBMAX                       | B相电压最大值    | NUMBER(8,2)    | V           |
| 77       | VBMIN                       | B相电压最小值    | NUMBER(8,2)    | V           |
| 78       | VBSTR                       | B相电压字符串    | VARCHAR2       |             |
| 79       | VC                          | C相电压          | NUMBER(8,2)    | V           |
| 80       | VCMAX                       | C相电压最大值    | NUMBER(8,2)    | V           |
| 81       | VCMIN                       | C相电压最小值    | NUMBER(8,2)    | V           |
| 82       | VCSTR                       | C相电压字符串    | VARCHAR2       |             |
| 83       | SIGNAL                      | 信号强度         | NUMBER(8,2)    |             |
| 84       | SIGNALMAX                   | 信号强度最大值   | NUMBER(8,2)    |             |
| 85       | SIGNALMIN                   | 信号强度最小值   | NUMBER(8,2)    |             |
| 86       | SIGNALSTR                   | 信号强度字符串   | VARCHAR2       |             |
| 87       | VIDEOURL                    | 视频路径         | VARCHAR2(400)  |             |
| 88       | SORTNUM                     | 排序编号         | NUMBER(10)     |             |
| 89       | ORG_CODE                    | 组织代码         | VARCHAR2(20)   |             |
| 90       | ORG_ID                      | 组织编号         | NUMBER(10)     |             |
| 91       | REMARK                      | 备注             | VARCHAR2       |             |

# 三、存储过程

表3-1 存储过程概览

| **序号** | **名称**                      | **描述**                           | **备注**             |
|----------|-------------------------------|------------------------------------|----------------------|
| 1        | PRD_CLEARRESOURCEPROBEDATA    | 清理资源监测数据                   |                      |
| 2        | PRD_CLEAR_DATA                | 清理数据并重置序列                 |                      |
| 3        | PRD_RESET_SEQUENCE            | 重置序列                           |                      |
| 4        | PRD_SAVE_WELLINFORMATION      | 保存井信息数据                     |                      |
| 5        | PRD_CHANGE_WELLNAME           | 修改井名                           |                      |
| 6        | PRD_SAVE_WELLBORETRAJECTORY   | 保存井身轨迹数据                   |                      |
| 7        | PRD_SAVE_RPC_PRODUCTIONDATA   | 保存生产数据                       |                      |
| 8        | PRD_SAVE_RPC_DIAGRAM          | 保存功图采集和计算数据             |                      |
| 9        | PRD_SAVE_RPC_UPLOADDIAGRAM    | 保存上传的功图数据                 |                      |
| 10       | PRD_SAVE_RPC_DIAGRAMRESULT    | 保存功图计算结果                   |                      |
| 11       | PRD_SAVE_RPC_RECALCULATEPARAM | 保存功图重新计算参数               |                      |
| 12       | PRD_SAVE_RPC_REINVERDIAGRAM   | 保存重新反演曲线数据               |                      |
| 13       | PRD_INIT_RPC_DAILY            | 初始化日汇总数据                   | 每天凌晨一点定时执行 |
| 14       | PRD_SAVE_RPC_DIAGRAMDAILY     | 保存功图日汇总数据                 |                      |
| 15       | PRD_SAVE_RPC_DISCRETEDAILY    | 保存离散数据日汇总结果             |                      |
| 16       | PRD_SAVE_RPC_INVER_DAILY      | 保存反演上传的日汇总数据           |                      |
| 17       | PRD_SAVE_RPC_MOTOR            | 保存反演电机数据                   |                      |
| 18       | PRD_SAVE_RPCINFORMATION       | 保存反演抽油机数据                 |                      |
| 19       | PRD_SAVE_RPCINFORMATIONNOPTF  | 保存反演抽油机数据\_无位置扭矩因数 |                      |
| 20       | PRD_SAVE_RPC_INVER_OPT        | 保存反演优化参数                   |                      |
| 21       | PRD_SAVE_A9RAWDATA            | 保存A9原始数据                     |                      |
| 22       | PRD_SAVE_ALARMCOLOR           | 保存报警级别颜色                   |                      |
| 23       | PRD_SAVE_PCP_PRODUCTIONDATA   | 保存生产数据\_螺杆泵               |                      |
| 24       | PRD_SAVE_PCP_DISCRETEDAILY    | 保存离散数据日汇总结果\_螺杆泵     |                      |
| 25       | PRD_SAVE_PCP_RPM              | 保存曲线采集和计算数据\_螺杆泵     |                      |
| 26       | PRD_SAVE_PCP_RPMDAILY         | 保存曲线日汇总数据\_螺杆泵         |                      |
| 27       | PRD_SAVE_RESOURCEMONITORING   | 保存资源监测数据                   |                      |

# 四、触发器

表4-1 触发器概览

| **序号** | **名称**                      | **描述**                                 |
|----------|-------------------------------|------------------------------------------|
| 1        | TRG_A_A9RAWDATA_HIST_I_U      | 功图原始数据历史表插入、更新数据后触发   |
| 2        | TRG_B_A9RAWDATA_HIST_I        | 功图原始数据历史表插入数据前触发         |
| 3        | TRG_B_A9RAWDATA_LATEST_I      | 功图原始数据实时表插入数据前触发         |
| 4        | TRG_A_A9RAWWATERCUT_HIST_I_U  | 含水率原始数据历史表插入、更新数据后触发 |
| 5        | TRG_B_A9RAWWATERCUT_HIST_I    | 含水率原始数据历史表插入数据前触发       |
| 6        | TRG_B_A9RAWWATERCUT_LATEST_I  | 含水率原始数据实时表插入数据前触发       |
| 7        | TRG_B_ACQ_GROUP2UNIT_CONF_I   | 采控单元、组关系表插入数据前触发         |
| 8        | TRG_B_ACQ_GROUP_CONF_I        | 采控组表插入数据前触发                   |
| 9        | TRG_B_ACQ_ITEM2GROUP_CONF_I   | 采控组、项关系表插入数据前触发           |
| 10       | TRG_B_ACQ_UNIT_CONF_I         | 采控单元表插入数据前触发                 |
| 11       | TRG_B_CODE_I                  | 代码表插入数据前触发                     |
| 12       | BEF_HIBERNATE_SEQUENCE_INSERT | 数据字典项数据表插入数据前触发           |
| 13       | TRG_B_MODULE_I                | 模块表插入数据前触发                     |
| 14       | TRG_B_ORG_I_U                 | 组织表插入、修改数据前触发               |
| 15       | TRG_B_PCP_DISCRETE_HIST_I     | 螺杆泵离散数据历史表插入数据前触发       |
| 16       | TRG_B_PCP_DISCRETE_LATEST_I   | 螺杆泵离散数据实时表插入数据前触发       |
| 17       | TRG_B_PCP_PRODDATA_HIST_I     | 螺杆泵生产数据历史表插入数据前触发抽     |
| 18       | TRG_B_PCP_PRODDATA_LATEST_I   | 螺杆泵生产数据实时表插入数据前触发       |
| 19       | TRG_A_PCP_RPM_HIST_I_U        | 螺杆泵曲线数据历史表插入、更新数据后触发 |
| 20       | TRG_B_PCP_RPM_HIST_I          | 螺杆泵曲线数据历史表插入数据前触发       |
| 21       | TRG_B_PCP_RPM_LATEST_I        | 螺杆泵曲线数据实时表插入数据前触发       |
| 22       | TRG_B_PCP_TOTAL_DAY_I         | 螺杆泵日累计数据表插入数据前触发         |
| 23       | TRG_B_RESOURCEMONITORING_I    | 资源监测数据表插入数据前触发             |
| 24       | TRG_B_ROLE_I                  | 角色表插入数据前触发                     |
| 25       | TRG_B_RPCINFORMATION_I        | 抽油机设备表插入数据前触发               |
| 26       | TRG_B_RPC_ALARMTYPE_CONF_I    | 抽油机工况报警配置表插入数据前触发       |
| 27       | TRG_A_RPC_DIAGRAM_HIST_I_U    | 抽油机曲线数据历史表插入、更新数据后触发 |
| 28       | TRG_B_RPC_DIAGRAM_HIST_I      | 抽油机曲线数据历史表插入数据前触发       |
| 29       | TRG_B_RPC_DIAGRAM_LATEST_I    | 抽油机曲线数据实时表插入数据前触发       |
| 30       | TRG_B_RPC_DIAGRAM_TOTAL_I     | 抽油机曲线累计数据表插入数据前触发       |
| 31       | TRG_B_RPC_DISCRETE_HIST_I     | 抽油机离散数据历史表插入数据前触发       |
| 32       | TRG_A_RPC_DISCRETE_LATEST_I_U | 抽油机离散数据实时表插入、更新数据后触发 |
| 33       | TRG_B_RPC_DISCRETE_LATEST_I   | 抽油机离散数据实时表插入数据前触发       |
| 34       | TRG_B_RPC_INVER_OPT_I         | 抽油机电参反演参数优化表插入数据前触发   |
| 35       | TRG_B_RPC_MOTOR_I             | 抽油机电机数据表插入数据前触发           |
| 36       | TRG_B_RPC_PRODDATA_HIST_I     | 油机生产数据历史表插入数据前触发         |
| 37       | TRG_A_RPC_PRODDATA_LATEST_I_U | 抽油机生产数据实时表插入、更新数据后触发 |
| 38       | TRG_B_RPC_PRODDATA_LATEST_I   | 抽油机生产数据实时表插入数据前触发       |
| 39       | TRG_B_RPC_STATISTICS_CONF_I   | 抽油机统计配置表插入数据前触发           |
| 40       | TRG_B_RPC_TOTAL_DAY_I         | 抽油机日累计数据表插入数据前触发         |
| 41       | TRG_B_RPC_WORKTYPE_I          | 抽油机工况类型表插入数据前触发           |
| 42       | TRG_B_USER_I                  | 用户表插入数据前触发                     |
| 43       | TRG_B_WELLBORETRAJECTORY_I    | 井身轨迹表插入数据前触发                 |
| 44       | TRG_A_WELLINFORMATION_I       | 井信息表插入数据后触发                   |
| 45       | TRG_B_WELLINFORMATION_I       | 井信息表插入数据前触发                   |
