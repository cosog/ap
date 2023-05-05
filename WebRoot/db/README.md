**油气生产智能监控系统 V8.5**

**数据库手册**

**北京科斯奇石油科技有限公司 制作**

**目 录**

[一、表	1](#_Toc134209124)

>   [1.1 概览	1](#_Toc134209125)

>   [1.2 详述	3](#_Toc134209126)

>   [1.2.1 tbl_org	3](#_Toc134209127)

>   [1.2.2 tbl_user	4](#_Toc134209128)

>   [1.2.3 tbl_role	5](#_Toc134209129)

>   [1.2.4 tbl_module	5](#_Toc134209130)

>   [1.2.5 tbl_module2role	6](#_Toc134209131)

>   [1.2.6 tbl_dist_name	6](#_Toc134209132)

>   [1.2.7 tbl_dist_item	7](#_Toc134209133)

>   [1.2.8 tbl_code	8](#_Toc134209134)

>   [1.2.9 tbl_protocol	8](#_Toc134209135)

>   [1.2.10 tbl_datamapping	8](#_Toc134209136)

>   [1.2.11 tbl_runstatusconfig	9](#_Toc134209137)

>   [1.2.12 tbl_acq_unit_conf	10](#_Toc134209138)

>   [1.2.13 tbl_acq_group_conf	10](#_Toc134209139)

>   [1.2.14 tbl_acq_item2group_conf	10](#_Toc134209140)

>   [1.2.15 tbl_acq_group2unit_conf	12](#_Toc134209141)

>   [1.2.16 tbl_alarm_unit_conf	12](#_Toc134209142)

>   [1.2.17 tbl_alarm_item2unit_conf	12](#_Toc134209143)

>   [1.2.18 tbl_display_unit_conf	13](#_Toc134209144)

>   [1.2.19 tbl_display_item2unit_conf	13](#_Toc134209145)

>   [1.2.20 tbl_report_unit_conf	14](#_Toc134209146)

>   [1.2.21 tbl_report_items2unit_conf	14](#_Toc134209147)

>   [1.2.22 tbl_protocolinstance	16](#_Toc134209148)

>   [1.2.23 tbl_protocolalarminstance	16](#_Toc134209149)

>   [1.2.24 tbl_protocoldisplayinstance	17](#_Toc134209150)

>   [1.2.25 tbl_protocolreportinstance	17](#_Toc134209151)

>   [1.2.26 tbl_protocolsmsinstance	17](#_Toc134209152)

>   [1.2.27 tbl_rpcdevice	18](#_Toc134209153)

>   [1.2.28 tbl_pcpdevice	19](#_Toc134209154)

>   [1.2.29 tbl_smsdevice	20](#_Toc134209155)

>   [1.2.30 tbl_pumpingmodel	20](#_Toc134209156)

>   [1.2.31 tbl_rpc_worktype	20](#_Toc134209157)

>   [1.2.32 tbl_rpcacqdata_latest	21](#_Toc134209158)

>   [1.2.33 tbl_rpcacqdata_hist	25](#_Toc134209159)

>   [1.2.34 tbl_rpcacqrawdata	26](#_Toc134209160)

>   [1.2.35 tbl_rpcalarminfo_latest	27](#_Toc134209161)

>   [1.2.36 tbl_rpcalarminfo_hist	27](#_Toc134209162)

>   [1.2.37 tbl_rpcdailycalculationdata	27](#_Toc134209163)

>   [1.2.38 tbl_rpcdevicegraphicset	29](#_Toc134209164)

>   [1.2.39 tbl_pcpacqdata_latest	30](#_Toc134209165)

>   [1.2.40 tbl_pcpacqdata_hist	32](#_Toc134209166)

>   [1.2.41 tbl_pcpacqrawdata	33](#_Toc134209167)

>   [1.2.42 tbl_pcpalarminfo_latest	34](#_Toc134209168)

>   [1.2.43 tbl_pcpalarminfo_hist	34](#_Toc134209169)

>   [1.2.44 tbl_pcpdailycalculationdata	34](#_Toc134209170)

>   [1.2.45 tbl_pcpdevicegraphicset	36](#_Toc134209171)

>   [1.2.46 tbl_deviceoperationlog	37](#_Toc134209172)

>   [1.2.47 tbl_systemlog	37](#_Toc134209173)

>   [1.2.48 tbl_resourcemonitoring	38](#_Toc134209174)

[二、视图	39](#_Toc134209175)

>   [2.1 概览	39](#_Toc134209176)

>   [2.2 详述	40](#_Toc134209177)

>   [2.2.1 viw_org	40](#_Toc134209178)

>   [2.2.2 viw_rpcdevice	41](#_Toc134209179)

>   [2.2.3 viw_pcpdevice	42](#_Toc134209180)

>   [2.2.4 viw_smsdevice	42](#_Toc134209181)

>   [2.2.5 viw_rpcacqrawdata	43](#_Toc134209182)

>   [2.2.6 viw_pcpacqrawdata	43](#_Toc134209183)

>   [2.2.7 viw_rpcalarminfo_latest	44](#_Toc134209184)

>   [2.2.8 viw_rpcalarminfo_hist	44](#_Toc134209185)

>   [2.2.9 viw_pcpalarminfo_latest	45](#_Toc134209186)

>   [2.2.10 viw_pcpalarminfo_hist	45](#_Toc134209187)

>   [2.2.11 viw_deviceoperationlog	46](#_Toc134209188)

>   [2.2.12 viw_systemlog	46](#_Toc134209189)

>   [2.2.13 viw_rpcdailycalculationdata	46](#_Toc134209190)

>   [2.2.14 viw_pcpdailycalculationdata	48](#_Toc134209191)

>   [2.2.15 viw_rpc_calculatemain	49](#_Toc134209192)

>   [2.2.16 viw_pcp_calculatemain	49](#_Toc134209193)

[三、存储过程	50](#_Toc134209194)

[四、触发器	51](#_Toc134209195)

# 一、表

## 1.1 概览

表1-1 表概览

| **序号** | **名称**                    | **描述**               |
|----------|-----------------------------|------------------------|
| 1        | tbl_org                     | 组织数据表             |
| 2        | tbl_user                    | 用户数据表             |
| 3        | tbl_role                    | 角色数据表             |
| 4        | tbl_module                  | 模块数据表             |
| 5        | tbl_module2role             | 模块角色关系表         |
| 6        | tbl_dist_name               | 字典名称表             |
| 7        | tbl_dist_item               | 字典数据项表           |
| 8        | tbl_code                    | 代码表                 |
| 9        | tbl_protocol                | 协议表                 |
| 10       | tbl_datamapping             | 字段映射表             |
| 11       | tbl_runstatusconfig         | 运行状态配置表         |
| 12       | tbl_acq\_unit\_conf         | 采控单元表             |
| 13       | tbl_acq_group_conf          | 采控组表               |
| 14       | tbl_acq_item2group_conf     | 采控组和采控项关系表   |
| 15       | tbl_acq_group2unit_conf     | 采控单元和采控组关系表 |
| 16       | tbl_alarm_unit_conf         | 报警单元表             |
| 17       | tbl_alarm_item2unit_conf    | 报警单元和报警项关系表 |
| 18       | tbl_display_unit_conf       | 显示单元表             |
| 19       | tbl_display_items2unit_conf | 显示单元和显示项关系表 |
| 20       | tbl_report_unit_conf        | 报表单元表             |
| 21       | tbl_report_items2unit_conf  | 报表单元和报表项关系表 |
| 22       | tbl_protocolinstance        | 采控实例表             |
| 23       | tbl_protocolalarminstance   | 报警实例表             |
| 24       | tbl_protocoldisplayinstance | 显示实例表             |
| 25       | tbl_protocolreportinstance  | 报表实例表             |
| 26       | tbl_protocolsmsinstance     | 短信实例表             |
| 27       | tbl_rpcdevice               | 抽油机信息表           |
| 28       | tbl_pcpdevice               | 螺杆泵信息表           |
| 29       | tbl\_smsdevice              | 短信设备信息表         |
| 30       | tbl_pumpingmodel            | 抽油机型号表           |
| 31       | tbl_rpc_worktype            | 工况表                 |
| 32       | tbl\_rpcacqdata_latest      | 抽油机实时数据表       |
| 33       | tbl\_rpcacqdata_hist        | 抽油机历史数据表       |
| 34       | tbl\_rpcacqrawdata          | 抽油机原始采集数据表   |
| 35       | tbl\_rpcalarminfo_latest    | 抽油机报警实时数据表   |
| 36       | tbl\_rpcalarminfo_hist      | 抽油机报警历史数据表   |
| 37       | tbl_rpcdailycalculationdata | 抽油机汇总数据表       |
| 38       | tbl\_rpcdevicegraphicset    | 抽油机图形设置表       |
| 39       | tbl\_pcpacqdata_latest      | 螺杆泵实时数据表       |
| 40       | tbl\_pcpacqdata\_ hist      | 螺杆泵历史数据表       |
| 41       | tbl\_pcpacqrawdata          | 螺杆泵原始采集数据表   |
| 42       | tbl\_pcpalarminfo_latest    | 螺杆泵报警实时数据表   |
| 43       | tbl\_pcpalarminfo_hist      | 螺杆泵报警历史数据表   |
| 44       | tbl_pcpdailycalculationdata | 螺杆泵汇总数据表       |
| 45       | tbl\_pcpdevicegraphicset    | 螺杆泵图形设置表       |
| 46       | tbl_deviceoperationlog      | 设备操作日志表         |
| 47       | tbl_systemlog               | 系统日志表             |
| 48       | tbl_resourcemonitoring      | 资源监测数据表         |

## 1.2 详述

### 1.2.1 tbl\_org

表1-2 组织数据表

| **序号** | **代码**   | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|------------|--------------|----------------|----------|----------|--------|----------|
| 1        | ORG_ID     | 单位序号     | NUMBER(10)     |          | N        | 主键   |          |
| 2        | ORG_CODE   | 单位编码     | VARCHAR2(20)   |          | Y        |        |          |
| 3        | ORG_NAME   | 单位名称     | VARCHAR2(100)  |          | N        |        |          |
| 4        | ORG_MEMO   | 单位说明     | VARCHAR2(4000) |          | Y        |        |          |
| 5        | ORG_PARENT | 父级单位编号 | NUMBER(10)     |          | N        |        |          |
| 6        | ORG_SEQ    | 单位排序     | NUMBER(10)     |          | Y        |        |          |

### 1.2.2 tbl\_user

表1-3 用户数据表

| **序号** | **代码**         | **名称**         | **类型**     | **单位** | **为空** | **键** | **备注**                    |
|----------|------------------|------------------|--------------|----------|----------|--------|-----------------------------|
| 1        | USER_NO          | 用户序号         | NUMBER(10)   |          | N        | 主键   |                             |
| 2        | USER_ID          | 用户账号         | VARCHAR2(20) |          | N        |        |                             |
| 3        | USER_PWD         | 用户密码         | VARCHAR2(20) |          | Y        |        |                             |
| 4        | USER_NAME        | 用户名称         | VARCHAR2(40) |          | N        |        |                             |
| 5        | USER_IN_EMAIL    | 内部邮箱         | VARCHAR2(40) |          | Y        |        |                             |
| 6        | USER_PHONE       | 用户电话         | VARCHAR2(40) |          | Y        |        |                             |
| 7        | USER_TYPE        | 用户类型         | NUMBER(10)   |          | Y        |        | 对应tbl_role表中role_id字段 |
| 8        | USER_ORGID       | 用户所属组织     | NUMBER(10)   |          | N        |        | 对应tbl_org表中org_id字段   |
| 9        | USER_REGTIME     | 用户注册时间     | DATE         |          | Y        |        |                             |
| 10       | USER_QUICKLOGIN  | 是否快捷登录     | NUMBER(1)    |          | Y        |        | 0-不是 1-是                 |
| 11       | USER_ENABLE      | 使能或者失效     | NUMBER(1)    |          |          |        | 0-失效 1-使能               |
| 12       | USER_RECEIVESMS  | 是否接收报警短信 | NUMBER(10)   |          |          |        | 0-否，1-是                  |
| 13       | USER_RECEIVEMAIL | 是否接收报警邮件 | NUMBER(10)   |          |          |        | 0-否，1-是                  |

### 1.2.3 tbl\_role

表1-4 角色数据表

| **序号** | **代码**   | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注**   |
|----------|------------|--------------|----------------|----------|----------|--------|------------|
| 1        | ROLE_ID    | 角色序号     | NUMBER(10)     |          | N        | 主键   |            |
| 3        | ROLE_NAME  | 角色名称     | VARCHAR2(40)   |          | N        |        |            |
| 4        | ROLE_FLAG  | 控制权限     | NUMBER(10)     |          | Y        |        | 0-无，1-是 |
| 5        | ROLE_LEVEL | 角色级别     | NUMBER(3)      |          | Y        |        |            |
| 6        | SHOWLEVEL  | 数据显示级别 | NUMBER(10)     |          | Y        |        |            |
| 7        | REMARK     | 角色描述     | VARCHAR2(2000) |          | Y        |        |            |

### 1.2.4 tbl\_module

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

### 1.2.5 tbl\_module2role

表1-6 模块角色关系表

| **序号** | **代码**    | **名称** | **类型**    | **单位** | **为空** | **键** | **备注**                    |
|----------|-------------|----------|-------------|----------|----------|--------|-----------------------------|
| 1        | RM_ID       | 序号     | NUMBER(10)  |          | N        | 主键   |                             |
| 2        | RM_ROLEID   | 角色编号 | NUMBER(10)  |          | N        |        | 对应tbl_role表中role_id字段 |
| 3        | RM_MODULEID | 模块序号 | NUMBER(10)  |          | N        |        | 对应module表中md_id字段     |
| 4        | RM_MATRIX   | 权限矩阵 | VARCHAR2(8) |          | N        |        |                             |

### 1.2.6 tbl_dist_name

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

### 1.2.7 tbl_dist_item

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

### 1.2.8 tbl_code

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

### 1.2.9 tbl_protocol

表1-10 协议表

| **序号** | **代码**   | **名称**   | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|------------|------------|--------------|----------|----------|--------|----------|
| 1        | ID         | 记录编号   | NUMBER(10)   |          | N        | 主键   |          |
| 2        | NAME       | 协议名称   | VARCHAR2(50) |          | N        |        |          |
| 3        | CODE       | 协议代码   | VARCHAR2(50) |          | Y        |        |          |
|          | DEVICETYPE | 设备类型   | NUMBER(1)    |          | Y        |        |          |
| 4        | ITEMS      | 协议地址项 | CLOB         |          | Y        |        |          |
| 5        | SORT       | 排序编号   | NUMBER(10)   |          | Y        |        |          |

### 1.2.10 tbl_datamapping

表1-11 字段映射表

| **序号** | **代码**        | **名称**       | **类型**     | **单位** | **为空** | **键** | **备注**                  |
|----------|-----------------|----------------|--------------|----------|----------|--------|---------------------------|
| 1        | ID              | 记录编号       | NUMBER(10)   |          | N        | 主键   |                           |
| 2        | NAME            | 协议采控项名称 | VARCHAR2(50) |          | Y        |        |                           |
| 3        | MAPPINGCOLUMN   | 映射字段       | VARCHAR2(30) |          | Y        |        |                           |
| 4        | CALCOLUMN       | 计算字段       | VARCHAR2(30) |          | Y        |        |                           |
| 5        | PROTOCOLTYPE    | 协议类型       | NUMBER(1)    |          | Y        |        | 0-抽油机协议 1-螺杆泵协议 |
| 6        | REPETITIONTIMES | 重复次数       | NUMBER(2)    |          | Y        |        |                           |
| 7        | MAPPINGMODE     | 映射模式       | NUMBER(1)    |          | Y        |        | 0-以地址为准 1-以名称为准 |

### 1.2.11 tbl_runstatusconfig

表1-12 运行状态配置表

| **序号** | **代码**          | **名称**   | **类型**     | **单位** | **为空** | **键** | **备注**                  |
|----------|-------------------|------------|--------------|----------|----------|--------|---------------------------|
| 1        | ID                | 记录编号   | NUMBER(10)   |          | N        | 主键   |                           |
| 2        | PROTOCOL          | 协议编码   | VARCHAR2(50) |          | Y        |        |                           |
| 3        | ITEMNAME          | 项名称     | VARCHAR2(50) |          | Y        |        |                           |
| 4        | ITEMMAPPINGCOLUMN | 项映射字段 | VARCHAR2(50) |          | Y        |        |                           |
| 5        | RUNVALUE          | 运行值     | VARCHAR2(50) |          | Y        |        |                           |
| 6        | STOPVALUE         | 停止值     | VARCHAR2(50) |          | Y        |        |                           |
| 7        | PROTOCOLTYPE      | 协议类型   | NUMBER(1)    |          | Y        |        | 0-抽油机协议 1-螺杆泵协议 |

### 1.2.12 tbl_acq_unit_conf

表1-13 采控单元名称表

| **序号** | **代码**  | **名称** | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|-----------|----------|----------------|----------|----------|--------|----------|
| 1        | ID        | 记录编号 | NUMBER(10)     |          | N        | 主键   |          |
| 2        | UNIT_CODE | 单元代码 | VARCHAR2(50)   |          | N        |        |          |
| 3        | UNIT_NAME | 单元名称 | VARCHAR2(50)   |          | Y        |        |          |
| 4        | PROTOCOL  | 协议     | VARCHAR2(50)   |          | Y        |        |          |
| 5        | REMARK    | 单元描述 | VARCHAR2(2000) |          | Y        |        |          |

### 1.2.13 tbl_acq_group_conf

表1-14 采控组名称表

| **序号** | **代码**   | **名称** | **类型**       | **单位** | **为空** | **键** | **备注**          |
|----------|------------|----------|----------------|----------|----------|--------|-------------------|
| 1        | id         | 记录编号 | NUMBER(10)     |          | N        | 主键   |                   |
| 2        | GROUP_CODE | 组代码   | VARCHAR2(50)   |          | N        |        |                   |
| 3        | GROUP_NAME | 组名称   | VARCHAR2(50)   |          | Y        |        |                   |
| 4        | ACQ_CYCLE  | 采集周期 | NUMBER(10)     | 秒       | Y        |        |                   |
| 5        | SAVE_CYCLE | 保存周期 | NUMBER(10)     | 秒       | Y        |        |                   |
| 6        | PROTOCOL   | 协议     | VARCHAR2(50)   |          | Y        |        |                   |
| 7        | TYPE       | 类型     | NUMBER(1)      |          | Y        |        | 0-采集组 1-控制组 |
| 8        | REMARK     | 组描述   | VARCHAR2(2000) |          | Y        |        |                   |

### 1.2.14 tbl_acq_item2group_conf

表1-15 采控组和采集项关系表

| **序号** | **代码** | **名称**   | **类型**      | **单位** | **为空** | **键** | **备注**         |
|----------|----------|------------|---------------|----------|----------|--------|------------------|
| 1        | ID       | 记录编号   | NUMBER(10)    |          | N        | 主键   |                  |
| 2        | GROUPID  | 采控组编号 | NUMBER(10)    |          | N        |        |                  |
| 3        | ITEMID   | 采控项编号 | NUMBER(10)    |          | Y        |        |                  |
| 4        | ITEMCODE | 采控项代码 | VARCHAR2(100) |          | Y        |        |                  |
| 5        | ITEMNAME | 采控项名称 | VARCHAR2(100) |          | Y        |        |                  |
| 6        | BITINDEX | 位索引     | NUMBER(10)    |          | Y        |        | 位数组中的位索引 |
| 7        | MATRIX   | 阵列       | VARCHAR2(8)   |          | Y        |        |                  |

### 1.2.15 tbl_acq_group2unit_conf

表1-16 采控单元和采集采控组关系表

| **序号** | **代码** | **名称**     | **类型**    | **单位** | **为空** | **键** | **备注**                          |
|----------|----------|--------------|-------------|----------|----------|--------|-----------------------------------|
| 1        | ID       | 记录编号     | NUMBER(10)  |          | N        | 主键   |                                   |
| 2        | UNITID   | 采控单元编号 | NUMBER(10)  |          | N        |        | 对应tbl_acq\_unit\_conf表中id字段 |
| 3        | GROUPID  | 采控组编号   | NUMBER(10)  |          | N        |        | 对应tbl_acq_group_conf表中id字段  |
| 4        | MATRIX   | 阵列         | VARCHAR2(8) |          | N        |        |                                   |

### 1.2.16 tbl\_alarm\_unit_conf

表1-17 报警单元名称表

| **序号** | **代码**  | **名称** | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|-----------|----------|----------------|----------|----------|--------|----------|
| 1        | ID        | 记录编号 | NUMBER(10)     |          | N        | 主键   |          |
| 2        | UNIT_CODE | 单元代码 | VARCHAR2(50)   |          | N        |        |          |
| 3        | UNIT_NAME | 单元名称 | VARCHAR2(50)   |          | Y        |        |          |
| 4        | PROTOCOL  | 协议     | VARCHAR2(50)   |          | Y        |        |          |
| 5        | REMARK    | 单元描述 | VARCHAR2(2000) |          | Y        |        |          |

### 1.2.17 tbl_alarm_item2unit_conf

表1-18 报警单元和报警项关系表

| **序号** | **代码**      | **名称**     | **类型**      | **单位** | **为空** | **键** | **备注**       |
|----------|---------------|--------------|---------------|----------|----------|--------|----------------|
| 1        | ID            | 记录编号     | NUMBER(10)    |          | N        | 主键   |                |
| 2        | UNITID        | 单元编号     | NUMBER(10)    |          | N        |        |                |
| 3        | ITEMID        | 项编号       | NUMBER(10)    |          | Y        |        |                |
| 4        | ITEMNAME      | 项名称       | VARCHAR2(100) |          | Y        |        |                |
| 5        | ITEMCODE      | 项代码       | VARCHAR2(100) |          | Y        |        |                |
| 6        | ITEMADDR      | 项地址       | NUMBER(10)    |          | Y        |        |                |
| 7        | UPPERLIMIT    | 报警上限     | NUMBER(10,3)  |          | Y        |        |                |
| 8        | LOWERLIMIT    | 报警下线     | NUMBER(10,3)  |          | Y        |        |                |
| 9        | HYSTERSIS     | 回差         | NUMBER(10,3)  |          | Y        |        |                |
| 10       | DELAY         | 延时         | NUMBER(10)    |          | Y        |        |                |
| 11       | ALARMLEVEL    | 报警级别     | NUMBER(3)     |          | Y        |        |                |
| 12       | ALARMSIGN     | 报警使能     | NUMBER(1)     |          | Y        |        |                |
| 13       | TYPE          | 报警类型     | NUMBER(1)     |          | Y        |        |                |
| 14       | VALUE         | 报警值       | NUMBER(10,3)  |          | Y        |        |                |
| 15       | BITINDEX      | 位索引       | NUMBER(3)     |          | Y        |        | 位数组的位索引 |
| 16       | ISSENDMESSAGE | 是否发送短信 | NUMBER(1)     |          | Y        |        | 0-否 1-是      |
| 17       | ISSENDMAIL    | 是否发送邮件 | NUMBER(1)     |          | Y        |        | 0-否 1-是      |

### 1.2.18 tbl\_display\_unit_conf

表1-19 显示单元名称表

| **序号** | **代码**  | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|-----------|--------------|----------------|----------|----------|--------|----------|
| 1        | ID        | 记录编号     | NUMBER(10)     |          | N        | 主键   |          |
| 2        | UNIT_CODE | 单元代码     | VARCHAR2(50)   |          | N        |        |          |
| 3        | UNIT_NAME | 单元名称     | VARCHAR2(50)   |          | Y        |        |          |
| 4        | PROTOCOL  | 协议         | VARCHAR2(50)   |          | Y        |        |          |
| 5        | ACQUNITID | 采集单元编号 | NUMBER(10)     |          |          |        |          |
| 6        | REMARK    | 单元描述     | VARCHAR2(2000) |          | Y        |        |          |

### 1.2.19 tbl\_display\_item2unit_conf

表1-20 显示单元和显示项关系表

| **序号** | **代码**          | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注**                   |
|----------|-------------------|--------------|----------------|----------|----------|--------|----------------------------|
| 1        | ID                | 记录编号     | NUMBER(10)     |          | N        | 主键   |                            |
| 2        | UNITID            | 单元编号     | NUMBER(10)     |          | N        |        |                            |
| 3        | ITEMID            | 项编号       | NUMBER(10)     |          | Y        |        |                            |
| 4        | ITEMNAME          | 项名称       | VARCHAR2(100)  |          | Y        |        |                            |
| 5        | ITEMCODE          | 项代码       | VARCHAR2(100)  |          | Y        |        |                            |
| 6        | SORT              | 排序序号     | NUMBER(10)     |          | Y        |        |                            |
| 7        | BITINDEX          | 位索引       | NUMBER(10)     |          | Y        |        | 位数组中的位索引           |
| 8        | SHOWLEVEL         | 显示级别     | NUMBER(10)     |          | Y        |        |                            |
| 9        | REALTIMECURVECONF | 实时曲线配置 | VARCHAR2(4000) |          | Y        |        | 为空不显示曲线             |
| 10       | HISTORYCURVECONF  | 历史曲线配置 | VARCHAR2(4000) |          | Y        |        | 为空不显示曲线             |
| 13       | TYPE              | 项类型       | NUMBER(1)      |          | Y        |        | 0-采集项 1-计算项 2-控制项 |
| 14       | MATRIX            | 阵列         | VARCHAR2(8)    |          | Y        |        |                            |

### 1.2.20 tbl_report_unit_conf

表1-21 报表单元名称表

| **序号** | **代码**                 | **名称**     | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|--------------------------|--------------|--------------|----------|----------|--------|----------|
| 1        | ID                       | 记录编号     | NUMBER(10)   |          | N        | 主键   |          |
| 2        | UNIT_CODE                | 单元代码     | VARCHAR2(50) |          | N        |        |          |
| 3        | UNIT_NAME                | 单元名称     | VARCHAR2(50) |          | Y        |        |          |
| 4        | SINGLEWELLREPORTTEMPLATE | 单井报表模板 | VARCHAR2(50) |          | Y        |        |          |
| 5        | PRODUCTIONREPORTTEMPLATE | 区域报表模板 | VARCHAR2(50) |          |          |        |          |
|          | DEVICETYPE               | 设备类型     | NUMBER(1)    |          |          |        |          |
| 6        | SORT                     | 排序编号     | NUMBER(10)   |          | Y        |        |          |

### 1.2.21 tbl_report_items2unit_conf

表1-22 报表单元和报表项关系表

| **序号** | **代码**        | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|-----------------|--------------|----------------|----------|----------|--------|----------|
| 1        | ID              | 记录编号     | NUMBER(10)     |          | N        | 主键   |          |
| 2        | UNITID          | 单元编号     | NUMBER(10)     |          | N        |        |          |
| 3        | ITEMID          | 项编号       | NUMBER(10)     |          | Y        |        |          |
| 4        | ITEMNAME        | 项名称       | VARCHAR2(100)  |          | Y        |        |          |
| 5        | ITEMCODE        | 项代码       | VARCHAR2(100)  |          | Y        |        |          |
| 6        | SORT            | 排序序号     | NUMBER(10)     |          | Y        |        |          |
| 7        | SHOWLEVEL       | 显示级别     | NUMBER(10)     |          | Y        |        |          |
| 8        | SUMSIGN         | 是否求和     | NUMBER(1)      |          |          |        |          |
| 9        | AVERAGESIGN     | 是否求平均   | NUMBER(1)      |          | Y        |        |          |
| 10       | CURVESTATTYPE   | 曲线统计类型 | NUMBER(1)      |          | Y        |        |          |
| 11       | DATATYPE        | 数据类型     | NUMBER(10)     |          | Y        |        |          |
| 12       | REPORTTYPE      | 报表类型     | NUMBER(1)      |          | Y        |        |          |
| 13       | REPORTCURVECONF | 报表曲线配置 | VARCHAR2(4000) |          | Y        |        |          |
| 14       | MATRIX          | 阵列         | VARCHAR2(8)    |          | Y        |        |          |

### 1.2.22 tbl_protocolinstance

表1-23 采控实例表

| **序号** | **代码**           | **名称**     | **类型**     | **单位** | **为空** | **键** | **备注**          |
|----------|--------------------|--------------|--------------|----------|----------|--------|-------------------|
| 1        | ID                 | 记录编号     | NUMBER(10)   |          | N        | 主键   |                   |
| 2        | NAME               | 实例名称     | VARCHAR2(50) |          | Y        |        |                   |
| 3        | CODE               | 实例代码     | VARCHAR2(50) |          | Y        |        |                   |
| 4        | ACQPROTOCOLTYPE    | 采集实例类型 | VARCHAR2(50) |          | N        |        |                   |
| 5        | CTRLPROTOCOLTYPE   | 控制实例类型 | VARCHAR2(50) |          | Y        |        |                   |
| 6        | PREFIXSUFFIXHEX    | 前后缀16进制 | NUMBER(1)    |          | Y        |        | 0-否 1-是         |
| 7        | SIGNINPREFIX       | 注册包前缀   | VARCHAR2(50) |          | Y        |        |                   |
| 8        | SIGNINSUFFIX       | 注册包后缀   | VARCHAR2(50) |          | Y        |        |                   |
| 9        | HEARTBEATPREFIX    | 心跳包前缀   | VARCHAR2(50) |          | Y        |        |                   |
| 10       | HEARTBEATSUFFIX    | 心跳包后缀   | VARCHAR2(50) |          | Y        |        |                   |
| 11       | PACKETSENDINTERVAL | 单包发送间隔 | NUMBER(10)   | ms       | Y        |        |                   |
| 12       | UNITID             | 采控单元编号 | NUMBER(10)   |          | Y        |        |                   |
| 13       | DEVICETYPE         | 设备类型     | NUMBER(1)    |          | Y        |        | 0-抽油机 1-螺杆泵 |
| 14       | SORT               | 排序编号     | NUMBER(10)   |          | Y        |        |                   |

### 1.2.23 tbl_protocolalarminstance

表1-24 报警实例表

| **序号** | **代码**    | **名称**     | **类型**     | **单位** | **为空** | **键** | **备注**          |
|----------|-------------|--------------|--------------|----------|----------|--------|-------------------|
| 1        | ID          | 记录编号     | NUMBER(10)   |          | N        | 主键   |                   |
| 2        | NAME        | 实例名称     | VARCHAR2(50) |          | Y        |        |                   |
| 3        | CODE        | 实例代码     | VARCHAR2(50) |          | Y        |        |                   |
| 4        | ALARMUNITID | 报警单元编号 | NUMBER(10)   |          | N        |        |                   |
| 5        | DEVICETYPE  | 设备类型     | NUMBER(1)    |          | Y        |        | 0-抽油机 1-螺杆泵 |
| 6        | SORT        | 排序编号     | NUMBER(10)   |          | Y        |        |                   |

### 1.2.24 tbl_protocoldisplayinstance

表1-25 显示实例表

| **序号** | **代码**      | **名称**     | **类型**     | **单位** | **为空** | **键** | **备注**          |
|----------|---------------|--------------|--------------|----------|----------|--------|-------------------|
| 1        | ID            | 记录编号     | NUMBER(10)   |          | N        | 主键   |                   |
| 2        | NAME          | 实例名称     | VARCHAR2(50) |          | Y        |        |                   |
| 3        | CODE          | 实例代码     | VARCHAR2(50) |          | Y        |        |                   |
| 4        | DISPLAYUNITID | 显示单元编号 | NUMBER(10)   |          | N        |        |                   |
| 5        | DEVICETYPE    | 设备类型     | NUMBER(1)    |          | Y        |        | 0-抽油机 1-螺杆泵 |
| 6        | SORT          | 排序编号     | NUMBER(10)   |          | Y        |        |                   |

### 1.2.25 tbl_protocolreportinstance

表1-26 报表实例表

| **序号** | **代码**   | **名称**     | **类型**     | **单位** | **为空** | **键** | **备注**          |
|----------|------------|--------------|--------------|----------|----------|--------|-------------------|
| 1        | ID         | 记录编号     | NUMBER(10)   |          | N        | 主键   |                   |
| 2        | NAME       | 实例名称     | VARCHAR2(50) |          | Y        |        |                   |
| 3        | CODE       | 实例代码     | VARCHAR2(50) |          | Y        |        |                   |
| 4        | UNITID     | 报表单元编号 | NUMBER(10)   |          | N        |        |                   |
| 5        | DEVICETYPE | 设备类型     | NUMBER(1)    |          | Y        |        | 0-抽油机 1-螺杆泵 |
| 6        | SORT       | 排序编号     | NUMBER(10)   |          | Y        |        |                   |

### 1.2.26 tbl_protocolsmsinstance

表1-27 短信实例表

| **序号** | **代码**         | **名称**     | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|------------------|--------------|--------------|----------|----------|--------|----------|
| 1        | ID               | 记录编号     | NUMBER(10)   |          | N        | 主键   |          |
| 2        | NAME             | 实例名称     | VARCHAR2(50) |          | Y        |        |          |
| 3        | CODE             | 实例代码     | VARCHAR2(50) |          | Y        |        |          |
| 4        | ACQPROTOCOLTYPE  | 采集协议类型 | VARCHAR2(50) |          | N        |        |          |
| 5        | CTRLPROTOCOLTYPE | 控制协议类型 | VARCHAR2(50) |          | Y        |        |          |
| 6        | SORT             | 排序编号     | NUMBER(10)   |          | Y        |        |          |

### 1.2.27 tbl\_rpcdevice

表1-28 抽油机信息表

| **序号** | **代码**                 | **名称**         | **类型**       | **单位** | **为空** | **键** | **备注**                 |
|----------|--------------------------|------------------|----------------|----------|----------|--------|--------------------------|
| 1        | ID                       | 记录编号         | NUMBER(10)     |          | N        | 主键   |                          |
| 2        | ORGID                    | 单位编号         | NUMBER(10)     |          | Y        |        |                          |
| 3        | WELLNAME                 | 设备名称         | VARCHAR2(200)  |          | Y        |        |                          |
| 4        | DEVICETYPE               | 设备类型         | NUMBER(1)      |          | Y        |        |                          |
| 5        | APPLICATIONSCENARIOS     | 应用场景         | NUMBER(2)      |          | Y        |        |                          |
| 6        | TCPTYPE                  | 下位机TCP类型    | VARCHAR2(20)   |          | Y        |        | TCP Server或者TCP Client |
| 7        | SIGNINID                 | 注册包ID         | VARCHAR2(200)  |          | Y        |        |                          |
| 8        | IPPORT                   | 下位机IP端口     | VARCHAR2(200)  |          | Y        |        |                          |
| 9        | SLAVE                    | 设备从地址       | VARCHAR2(200)  |          | Y        |        |                          |
| 10       | PEAKDELAY                | 错峰延时         | NUMBER(10)     | s        | Y        |        |                          |
| 11       | INSTANCECODE             | 采控实例代码     | VARCHAR2(50)   |          | Y        |        |                          |
| 12       | ALARMINSTANCECODE        | 报警实例代码     | VARCHAR2(50)   |          | Y        |        |                          |
| 13       | DISPLAYINSTANCECODE      | 显示实例代码     | VARCHAR2(50)   |          | Y        |        |                          |
| 14       | REPORTINSTANCECODE       | 报表实例代码     | VARCHAR2(50)   |          | Y        |        |                          |
| 15       | VIDEOURL                 | 视频监控路径     | VARCHAR2(400)  |          | Y        |        | 多个以;隔开              |
| 16       | VIDEOACCESSTOKEN         | 视频访问令牌     | VARCHAR2(400)  |          | Y        |        | 预留多个以;隔开          |
| 17       | PRODUCTIONDATA           | 生产数据         | VARCHAR2(4000) |          | Y        |        | json格式字符串           |
| 18       | PRODUCTIONDATAUPDATETIME | 生产数据更新时间 | DATE           |          | Y        |        |                          |
| 19       | PUMPINGMODELID           | 抽油机型号ID     | NUMBER(10)     |          | Y        |        |                          |
| 20       | STROKE                   | 铭牌冲程         | NUMBER(8,2)    |          | Y        |        |                          |
| 21       | BALANCEINFO              | 平衡块信息       | VARCHAR2(400)  |          | Y        |        | json格式字符串           |
| 22       | STATUS                   | 状态             | NUMBER(1)      |          | Y        |        | 0-失效 1-使能            |
| 23       | SORTNUM                  | 排序编号         | NUMBER(10)     |          | Y        |        |                          |

### 1.2.28 tbl\_pcpdevice

表1-29 螺杆泵信息表

| **序号** | **代码**                 | **名称**         | **类型**       | **单位** | **为空** | **键** | **备注**                 |
|----------|--------------------------|------------------|----------------|----------|----------|--------|--------------------------|
| 1        | ID                       | 记录编号         | NUMBER(10)     |          | N        | 主键   |                          |
| 2        | ORGID                    | 单位编号         | NUMBER(10)     |          | Y        |        |                          |
| 3        | WELLNAME                 | 设备名称         | VARCHAR2(200)  |          | Y        |        |                          |
| 4        | DEVICETYPE               | 设备类型         | NUMBER(1)      |          | Y        |        |                          |
| 5        | APPLICATIONSCENARIOS     | 应用场景         | NUMBER(2)      |          | Y        |        |                          |
| 6        | TCPTYPE                  | 下位机TCP类型    | VARCHAR2(20)   |          | Y        |        | TCP Server或者TCP Client |
| 7        | SIGNINID                 | 注册包ID         | VARCHAR2(200)  |          | Y        |        |                          |
| 8        | SLAVE                    | 设备从地址       | VARCHAR2(200)  |          | Y        |        |                          |
| 9        | PEAKDELAY                | 错峰延时         | NUMBER(10)     | s        | Y        |        |                          |
| 10       | INSTANCECODE             | 采控实例代码     | VARCHAR2(50)   |          | Y        |        |                          |
| 11       | ALARMINSTANCECODE        | 报警实例代码     | VARCHAR2(50)   |          | Y        |        |                          |
| 12       | DISPLAYINSTANCECODE      | 显示实例代码     | VARCHAR2(50)   |          | Y        |        |                          |
| 13       | REPORTINSTANCECODE       | 报表实例代码     | VARCHAR2(50)   |          | Y        |        |                          |
| 14       | VIDEOURL                 | 视频地址         | VARCHAR2(400)  |          | Y        |        |                          |
| 15       | VIDEOACCESSTOKEN         | 视频访问令牌     | VARCHAR2(400)  |          | Y        |        | 预留多个以;隔开          |
| 16       | PRODUCTIONDATA           | 生产数据         | VARCHAR2(4000) |          | Y        |        | json格式字符串           |
| 17       | PRODUCTIONDATAUPDATETIME | 生产数据更新时间 | DATE           |          | Y        |        |                          |
| 18       | STATUS                   | 状态             | NUMBER(1)      |          | Y        |        | 0-失效 1-使能            |
| 19       | SORTNUM                  | 排序编号         | NUMBER(10)     |          | Y        |        |                          |

### 1.2.29 tbl\_smsdevice

表1-30 短信设备信息表

| **序号** | **代码**     | **名称**     | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|--------------|--------------|---------------|----------|----------|--------|----------|
| 1        | ID           | 记录编号     | NUMBER(10)    |          | N        | 主键   |          |
| 2        | ORGID        | 单位编号     | NUMBER(10)    |          | Y        |        |          |
| 3        | WELLNAME     | 设备名称     | VARCHAR2(200) |          | Y        |        |          |
| 4        | SIGNINID     | 注册包ID     | VARCHAR2(200) |          | N        |        |          |
| 5        | INSTANCECODE | 短信实例代码 | VARCHAR2(50)  |          | Y        |        |          |
| 6        | SORTNUM      | 排序编号     | NUMBER(10)    |          | Y        |        |          |

### 1.2.30 tbl_pumpingmodel

表1-31 抽油机型号表

| **序号** | **代码**               | **名称**       | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|------------------------|----------------|---------------|----------|----------|--------|----------|
| 1        | ID                     | 记录编号       | NUMBER(10)    |          | N        | 主键   |          |
| 2        | MANUFACTURER           | 厂家           | VARCHAR2(200) |          | Y        |        |          |
| 3        | MODEL                  | 型号           | VARCHAR2(200) |          | Y        |        |          |
| 4        | STROKE                 | 冲程           | VARCHAR2(200) |          | Y        |        |          |
| 5        | CRANKROTATIONDIRECTION | 曲柄旋转方向   | VARCHAR2(200) |          | Y        |        |          |
| 6        | OFFSETANGLEOFCRANK     | 曲柄偏置角     | NUMBER(8,2)   |          | Y        |        |          |
| 7        | CRANKGRAVITYRADIUS     | 曲柄重心半径   | NUMBER(10,4)  |          | Y        |        |          |
| 8        | SINGLECRANKWEIGHT      | 单块曲柄重量   | NUMBER(8,2)   |          | Y        |        |          |
| 9        | SINGLECRANKPINWEIGHT   | 单块曲柄销重量 | NUMBER(10,4)  |          | Y        |        |          |
| 10       | STRUCTURALUNBALANCE    | 结构不平衡重   | NUMBER(8,2)   |          | Y        |        |          |
| 11       | BALANCEWEIGHT          | 平衡块重量     | VARCHAR2(200) |          | Y        |        |          |
| 12       | PRTF                   | 位置扭矩因数   | CLOB          |          | Y        |        |          |

### 1.2.31 tbl_rpc_worktype

表1-32 工况表

| **序号** | **代码**               | **名称** | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|------------------------|----------|---------------|----------|----------|--------|----------|
| 1        | ID                     | 记录编号 | NUMBER(10)    |          | N        | 主键   |          |
| 2        | RESULTCODE             | 工况代码 | NUMBER(4)     |          | Y        |        |          |
| 3        | RESULTNAME             | 工况名称 | VARCHAR2(200) |          | Y        |        |          |
| 4        | RESULTDESCRIPTION      | 工况描述 | VARCHAR2(200) |          | Y        |        |          |
| 5        | RESULTTEMPLATE         | 模板     | BLOB          |          | Y        |        |          |
| 6        | OPTIMIZATIONSUGGESTION | 优化建议 | VARCHAR2(200) |          | Y        |        |          |
| 7        | REMARK                 | 备注     | VARCHAR2(200) |          | Y        |        |          |

### 1.2.32 tbl\_rpcacqdata_latest

表1-33 抽油机实时数据表

| **序号** | **代码**                       | **名称**                 | **类型**       | **单位** | **为空** | **键** | **备注**               |
|----------|--------------------------------|--------------------------|----------------|----------|----------|--------|------------------------|
| 1        | ID                             | 记录编号                 | NUMBER(10)     |          | N        | 主键   |                        |
| 2        | WELLID                         | 设备编号                 | NUMBER(10)     |          | N        |        |                        |
| 3        | ACQTIME                        | 采集时间                 | DATE           |          | Y        |        |                        |
| 4        | COMMSTATUS                     | 通信状态                 | NUMBER(2)      |          | Y        |        | 0-离线 1-在线          |
| 5        | COMMTIME                       | 在线时间                 | NUMBER(8,2)    |          | Y        |        |                        |
| 6        | COMMTIMEEFFICIENCY             | 在线时率                 | NUMBER(10,4)   |          | Y        |        |                        |
| 7        | COMMRANGE                      | 在线区间                 | CLOB           |          | Y        |        |                        |
| 8        | RUNSTATUS                      | 运行状态                 | NUMBER(2)      |          | Y        |        |                        |
| 9        | RUNTIMEEFFICIENCY              | 运行时率                 | NUMBER(8,2)    |          | Y        |        |                        |
| 10       | RUNTIME                        | 运行时间                 | NUMBER(10,4)   |          | Y        |        |                        |
| 11       | RUNRANGE                       | 运行区间                 | CLOB           |          | Y        |        |                        |
| 12       | PRODUCTIONDATA                 | 生产数据                 | VARCHAR2(4000) |          | Y        |        |                        |
| 13       | PUMPINGMODELID                 | 抽油机型号编号           | NUMBER(10)     |          | Y        |        |                        |
| 14       | BALANCEINFO                    | 平衡块数据               | VARCHAR2(400)  |          | Y        |        |                        |
| 15       | FESDIAGRAMACQTIME              | 功图采集时间             | DATE           |          | Y        |        |                        |
| 16       | FESDIAGRAMSRC                  | 功图来源                 | NUMBER(2)      |          | Y        |        | 0-功图仪 1-电参反演    |
| 17       | STROKE                         | 冲程                     | NUMBER(8,2)    |          | Y        |        |                        |
| 18       | SPM                            | 冲次                     | NUMBER(8,2)    |          | Y        |        |                        |
| 19       | FMAX                           | 最大载荷                 | NUMBER(8,2)    |          | Y        |        |                        |
| 20       | FMIN                           | 最小载荷                 | NUMBER(8,2)    |          | Y        |        |                        |
| 21       | POSITION_CURVE                 | 功图位移数据             | CLOB           |          | Y        |        |                        |
| 22       | ANGLE_CURVE                    | 角度曲线数据             | CLOB           |          | Y        |        |                        |
| 23       | LOAD_CURVE                     | 功图载荷数据             | CLOB           |          | Y        |        |                        |
| 24       | POWER_CURVE                    | 功率曲线数据             | CLOB           |          | Y        |        |                        |
| 25       | CURRENT_CURVE                  | 电流曲线数据             | CLOB           |          | Y        |        |                        |
| 26       | RESULTCODE                     | 工况代码                 | NUMBER(4)      |          | Y        |        |                        |
| 27       | FULLNESSCOEFFICIENT            | 充满系数                 | NUMBER(8,2)    |          | Y        |        |                        |
| 28       | UPPERLOADLINE                  | 理论上载荷               | NUMBER(8,2)    |          | Y        |        |                        |
| 29       | UPPERLOADLINEOFEXACT           | 考虑沉没压力的理论上载荷 | NUMBER(8,2)    |          | Y        |        |                        |
| 30       | LOWERLOADLINE                  | 理论下载荷               | NUMBER(8,2)    |          | Y        |        |                        |
| 31       | PUMPFSDIAGRAM                  | 泵功图                   | CLOB           |          | Y        |        |                        |
| 32       | THEORETICALPRODUCTION          | 理论排量                 | NUMBER(8,2)    |          | Y        |        |                        |
| 33       | LIQUIDVOLUMETRICPRODUCTION     | 产液量                   | NUMBER(8,2)    | 方       | Y        |        |                        |
| 34       | OILVOLUMETRICPRODUCTION        | 产油量                   | NUMBER(8,2)    | 方       | Y        |        |                        |
| 35       | WATERVOLUMETRICPRODUCTION      | 产水量                   | NUMBER(8,2)    | 方       | Y        |        |                        |
| 36       | AVAILABLEPLUNGERSTROKEPROD_V   | 有效冲程计算产量         | NUMBER(8,2)    | 方       | Y        |        |                        |
| 37       | PUMPCLEARANCELEAKPROD_V        | 泵间隙漏失量             | NUMBER(8,2)    | 方       | Y        |        |                        |
| 38       | TVLEAKVOLUMETRICPRODUCTION     | 游动凡尔漏失量           | NUMBER(8,2)    | 方       | Y        |        |                        |
| 39       | SVLEAKVOLUMETRICPRODUCTION     | 固定凡尔漏失量           | NUMBER(8,2)    | 方       | Y        |        |                        |
| 40       | GASINFLUENCEPROD_V             | 气影响                   | NUMBER(8,2)    | 方       | Y        |        |                        |
| 41       | LIQUIDWEIGHTPRODUCTION         | 产液量                   | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 42       | OILWEIGHTPRODUCTION            | 产油量                   | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 43       | WATERWEIGHTPRODUCTION          | 产水量                   | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 44       | AVAILABLEPLUNGERSTROKEPROD_W   | 有效冲程计算产量         | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 45       | PUMPCLEARANCELEAKPROD_W        | 泵间隙漏失量             | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 46       | TVLEAKWEIGHTPRODUCTION         | 游动凡尔漏失量           | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 47       | SVLEAKWEIGHTPRODUCTION         | 固定凡尔漏失量           | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 48       | GASINFLUENCEPROD_W             | 气影响                   | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 49       | AVERAGEWATT                    | 有功功率                 | NUMBER(8,2)    |          | Y        |        |                        |
| 50       | POLISHRODPOWER                 | 光杆功率                 | NUMBER(8,2)    |          | Y        |        |                        |
| 51       | WATERPOWER                     | 水功率                   | NUMBER(8,2)    |          | Y        |        |                        |
| 52       | SURFACESYSTEMEFFICIENCY        | 地面效率                 | NUMBER(12,3)   |          | Y        |        |                        |
| 53       | WELLDOWNSYSTEMEFFICIENCY       | 井下效率                 | NUMBER(12,3)   |          | Y        |        |                        |
| 54       | SYSTEMEFFICIENCY               | 系统效率                 | NUMBER(12,3)   |          | Y        |        |                        |
| 55       | ENERGYPER100MLIFT              | 吨液百米耗电量           | NUMBER(8,2)    |          | Y        |        |                        |
| 56       | AREA                           | 功图面积                 | NUMBER(8,2)    |          | Y        |        |                        |
| 57       | RODFLEXLENGTH                  | 抽油杆伸长量             | NUMBER(8,2)    |          | Y        |        |                        |
| 58       | TUBINGFLEXLENGTH               | 油管伸缩值               | NUMBER(8,2)    |          | Y        |        |                        |
| 59       | INERTIALENGTH                  | 惯性载荷增量             | NUMBER(8,2)    |          | Y        |        |                        |
| 60       | PUMPEFF1                       | 冲程损失系数             | NUMBER(12,3)   |          | Y        |        |                        |
| 61       | PUMPEFF2                       | 充满系数                 | NUMBER(12,3)   |          | Y        |        |                        |
| 62       | PUMPEFF3                       | 间隙漏失系数             | NUMBER(12,3)   |          | Y        |        |                        |
| 63       | PUMPEFF4                       | 液体收缩系数             | NUMBER(12,3)   |          | Y        |        |                        |
| 64       | PUMPEFF                        | 总泵效                   | NUMBER(12,3)   |          | Y        |        |                        |
| 65       | PUMPINTAKEP                    | 泵入口压力               | NUMBER(8,2)    |          | Y        |        |                        |
| 66       | PUMPINTAKET                    | 泵入口温度               | NUMBER(8,2)    |          | Y        |        |                        |
| 67       | PUMPINTAKEGOL                  | 泵入口就地气液比         | NUMBER(8,2)    |          | Y        |        |                        |
| 68       | PUMPINTAKEVISL                 | 泵入口粘度               | NUMBER(8,2)    |          | Y        |        |                        |
| 69       | PUMPINTAKEBO                   | 泵入口原油体积系数       | NUMBER(8,2)    |          | Y        |        |                        |
| 70       | PUMPOUTLETP                    | 泵出口压力               | NUMBER(8,2)    |          | Y        |        |                        |
| 71       | PUMPOUTLETT                    | 泵出口温度               | NUMBER(8,2)    |          | Y        |        |                        |
| 72       | PUMPOUTLETGOL                  | 泵出口就地气液比         | NUMBER(8,2)    |          | Y        |        |                        |
| 73       | PUMPOUTLETVISL                 | 泵出口粘度               | NUMBER(8,2)    |          | Y        |        |                        |
| 74       | PUMPOUTLETBO                   | 泵出口原油体积系数       | NUMBER(8,2)    |          | Y        |        |                        |
| 75       | RODSTRING                      | 抽油杆参数               | VARCHAR2(200)  |          | Y        |        |                        |
| 76       | PLUNGERSTROKE                  | 柱塞冲程                 | NUMBER(8,2)    |          | Y        |        |                        |
| 77       | AVAILABLEPLUNGERSTROKE         | 柱塞有效冲程             | NUMBER(8,2)    |          | Y        |        |                        |
| 78       | LEVELCORRECTVALUE              | 反演液面校正值           | NUMBER(8,2)    |          | Y        |        |                        |
| 79       | INVERPRODUCINGFLUIDLEVEL       | 反演液面                 | NUMBER(8,2)    |          | Y        |        |                        |
| 80       | NOLIQUIDFULLNESSCOEFFICIENT    | 抽空充满系数             | NUMBER(10,4)   |          | Y        |        |                        |
| 81       | NOLIQUIDAVAILABLEPLUNGERSTROKE | 抽空柱塞有效冲程         | NUMBER(10,4)   |          | Y        |        |                        |
| 82       | SMAXINDEX                      | 位移最大值索引           | NUMBER(10)     |          | Y        |        |                        |
| 83       | SMININDEX                      | 位移最小值索引           | NUMBER(10)     |          | Y        |        |                        |
| 84       | UPSTROKEIMAX                   | 上冲程最大电流           | NUMBER(8,2)    |          | Y        |        |                        |
| 85       | DOWNSTROKEIMAX                 | 下冲程最大电流           | NUMBER(8,2)    |          | Y        |        |                        |
| 86       | UPSTROKEWATTMAX                | 上冲程最大功率           | NUMBER(8,2)    |          | Y        |        |                        |
| 87       | DOWNSTROKEWATTMAX              | 下冲程最大功率           | NUMBER(8,2)    |          | Y        |        |                        |
| 88       | IDEGREEBALANCE                 | 电流平衡度               | NUMBER(8,2)    |          | Y        |        |                        |
| 89       | WATTDEGREEBALANCE              | 功率平衡度               | NUMBER(8,2)    |          | Y        |        |                        |
| 90       | DELTARADIUS                    | 移动距离                 | NUMBER(8,2)    |          | Y        |        |                        |
| 91       | CRANKANGLE                     | 曲柄转角                 | CLOB           |          | Y        |        |                        |
| 92       | POLISHRODV                     | 光杆速度                 | CLOB           |          | Y        |        |                        |
| 93       | POLISHRODA                     | 光杆加速度               | CLOB           |          | Y        |        |                        |
| 94       | PR                             | 位置因数                 | CLOB           |          | Y        |        |                        |
| 95       | TF                             | 扭矩因数                 | CLOB           |          | Y        |        |                        |
| 96       | LOADTORQUE                     | 载荷扭矩                 | CLOB           |          | Y        |        |                        |
| 97       | CRANKTORQUE                    | 曲柄扭矩                 | CLOB           |          | Y        |        |                        |
| 98       | CURRENTBALANCETORQUE           | 目前平衡块扭矩           | CLOB           |          | Y        |        |                        |
| 99       | CURRENTNETTORQUE               | 目前净扭矩               | CLOB           |          | Y        |        |                        |
| 100      | EXPECTEDBALANCETORQUE          | 预期平衡块扭矩           | CLOB           |          | Y        |        |                        |
| 101      | EXPECTEDNETTORQUE              | 预期前净扭矩             | CLOB           |          | Y        |        |                        |
| 102      | WELLBORESLICE                  | 井身切片                 | CLOB           |          | Y        |        |                        |
| 103      | RESULTSTATUS                   | 计算状态                 | NUMBER(2)      |          | Y        |        |                        |
| 104      | TOTALKWATTH                    | 累计电量                 | NUMBER(12,3)   |          | Y        |        |                        |
| 105      | TODAYKWATTH                    | 日用电量                 | NUMBER(12,3)   |          | Y        |        |                        |
| 106      | LIQUIDVOLUMETRICPRODUCTION_L   | 日累计产液量             | NUMBER(8,2)    | 方       | Y        |        |                        |
| 107      | OILVOLUMETRICPRODUCTION_L      | 日累计产油量             | NUMBER(8,2)    | 方       | Y        |        |                        |
| 108      | WATERVOLUMETRICPRODUCTION_L    | 日累计产水量             | NUMBER(8,2)    | 方       | Y        |        |                        |
| 109      | LIQUIDWEIGHTPRODUCTION_L       | 日累计产液量             | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 110      | OILWEIGHTPRODUCTION_L          | 日累计产油量             | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 111      | WATERWEIGHTPRODUCTION_L        | 日累计产水量             | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 112      | SUBMERGENCE                    | 沉没度                   | NUMBER(8,2)    | 米       | Y        |        |                        |
| 113      | SAVETIME                       | 保存时间                 | DATE           |          | Y        |        |                        |
| …        | …                              | …                        |                |          | Y        |        | 根据驱动自动生成的字段 |
| …        | …                              | …                        |                |          | Y        |        |                        |

### 1.2.33 tbl\_rpcacqdata_hist

同tbl\_rpcacqdata\_latest

### 1.2.34 tbl\_rpcacqrawdata

表1-34 抽油机原始采集数据表

| **序号** | **代码** | **名称** | **类型**       | **单位** | **为空** | **键** | **备注**                               |
|----------|----------|----------|----------------|----------|----------|--------|----------------------------------------|
| 1        | ID       | 记录编号 | NUMBER(10)     |          | N        | 主键   |                                        |
| 2        | WELLID   | 设备编号 | NUMBER(10)     |          | N        |        |                                        |
| 3        | ACQTIME  | 采集时间 | DATE           |          | Y        |        |                                        |
| 4        | RAWDATA  | 原始数据 | VARCHAR2(4000) |          | Y        |        | 设备采集的未解析原始数据，16进制字符串 |

### 1.2.35 tbl\_rpcalarminfo_latest

表1-35 抽油机报警实时数据表

| **序号** | **代码**      | **名称**     | **类型**      | **单位** | **为空** | **键** | **备注**                                        |
|----------|---------------|--------------|---------------|----------|----------|--------|-------------------------------------------------|
| 1        | ID            | 记录编号     | NUMBER(10)    |          | N        | 主键   |                                                 |
| 2        | WELLID        | 设备编号     | NUMBER(10)    |          | N        |        |                                                 |
| 3        | ALARMTIME     | 报警时间     | DATE          |          | Y        |        |                                                 |
| 4        | ITEMNAME      | 报警项       | VARCHAR2(100) |          | Y        |        |                                                 |
| 5        | ALARMTYPE     | 报警类型     | NUMBER(1)     |          | Y        |        | 0-通信报警 1-限值报警 2-枚举量报警 3-开关量报警 |
| 6        | ALARMVALUE    | 报警值       | NUMBER(10,3)  |          | Y        |        |                                                 |
| 7        | ALARMINFO     | 报警信息     | VARCHAR2(100) |          | Y        |        |                                                 |
| 8        | ALARMLIMIT    | 报警限值     | NUMBER(10,3)  |          | Y        |        |                                                 |
| 9        | HYSTERSIS     | 回差         | NUMBER(10,3)  |          | Y        |        |                                                 |
| 10       | ALARMLEVEL    | 报警级别     | NUMBER(3)     |          | Y        |        | 100-一级报警 200-二级报警 300-三级报警          |
| 11       | RECOVERYTIME  | 恢复时间     | DATE          |          | Y        |        |                                                 |
| 12       | ISSENDMESSAGE | 是否发送短信 | NUMBER(1)     |          | Y        |        | 0-否 1-是                                       |
| 13       | ISSENDMAIL    | 是否发送邮件 | NUMBER(1)     |          | Y        |        | 0-否 1-是                                       |

### 1.2.36 tbl\_rpcalarminfo_hist

同tbl\_rpcalarminfo_latest

### 1.2.37 tbl_rpcdailycalculationdata

表1-36 抽油机汇总数据表

| **序号** | **代码**                   | **名称**       | **类型**     | **单位** | **为空** | **键** | **备注**      |
|----------|----------------------------|----------------|--------------|----------|----------|--------|---------------|
| 1        | ID                         | 记录编号       | NUMBER(10)   |          | N        | 主键   |               |
| 2        | WELLID                     | 设备编号       | NUMBER(10)   |          | N        |        |               |
| 3        | CALDATE                    | 汇总日期       | DATE         |          | Y        |        |               |
| 4        | COMMSTATUS                 | 通信状态       | NUMBER(2)    |          | Y        |        | 0-离线 1-在线 |
| 5        | COMMTIME                   | 在线时间       | NUMBER(8,2)  |          | Y        |        |               |
| 6        | COMMTIMEEFFICIENCY         | 在线时率       | NUMBER(10,4) |          | Y        |        |               |
| 7        | COMMRANGE                  | 在线区间       | CLOB         |          | Y        |        |               |
| 8        | RUNSTATUS                  | 运行状态       | NUMBER(2)    |          | Y        |        |               |
| 9        | RUNTIMEEFFICIENCY          | 运行时率       | NUMBER(8,2)  |          | Y        |        |               |
| 10       | RUNTIME                    | 运行时间       | NUMBER(10,4) |          | Y        |        |               |
| 11       | RUNRANGE                   | 运行区间       | CLOB         |          | Y        |        |               |
| 12       | STROKE                     | 冲程           | NUMBER(8,2)  |          | Y        |        |               |
| 13       | SPM                        | 冲次           | NUMBER(8,2)  |          | Y        |        |               |
| 14       | FMAX                       | 最大载荷       | NUMBER(8,2)  |          | Y        |        |               |
| 15       | FMIN                       | 最小载荷       | NUMBER(8,2)  |          | Y        |        |               |
| 16       | RESULTCODE                 | 工况代码       | NUMBER(4)    |          | Y        |        |               |
| 17       | RESULTSTRING               | 工况字符串     | CLOB         |          | Y        |        |               |
| 18       | FULLNESSCOEFFICIENT        | 充满系数       | NUMBER(8,2)  |          | Y        |        |               |
| 19       | THEORETICALPRODUCTION      | 理论排量       | NUMBER(8,2)  |          | Y        |        |               |
| 20       | LIQUIDVOLUMETRICPRODUCTION | 产液量         | NUMBER(8,2)  | 方       | Y        |        |               |
| 21       | OILVOLUMETRICPRODUCTION    | 产油量         | NUMBER(8,2)  | 方       | Y        |        |               |
| 22       | WATERVOLUMETRICPRODUCTION  | 产水量         | NUMBER(8,2)  | 方       | Y        |        |               |
| 23       | VOLUMEWATERCUT             | 体积含水率     | NUMBER(8,2)  |          | Y        |        |               |
| 24       | LIQUIDWEIGHTPRODUCTION     | 产液量         | NUMBER(8,2)  | 吨       | Y        |        |               |
| 25       | OILWEIGHTPRODUCTION        | 产油量         | NUMBER(8,2)  | 吨       | Y        |        |               |
| 26       | WATERWEIGHTPRODUCTION      | 产水量         | NUMBER(8,2)  | 吨       | Y        |        |               |
| 27       | WEIGHTWATERCUT             | 重量含水率     | NUMBER(8,2)  |          | Y        |        |               |
| 28       | SURFACESYSTEMEFFICIENCY    | 地面效率       | NUMBER(12,3) |          | Y        |        |               |
| 29       | WELLDOWNSYSTEMEFFICIENCY   | 井下效率       | NUMBER(12,3) |          | Y        |        |               |
| 30       | SYSTEMEFFICIENCY           | 系统效率       | NUMBER(12,3) |          | Y        |        |               |
| 31       | ENERGYPER100MLIFT          | 吨液百米耗电量 | NUMBER(8,2)  |          | Y        |        |               |
| 32       | PUMPEFF1                   | 冲程损失系数   | NUMBER(12,3) |          | Y        |        |               |
| 33       | PUMPEFF2                   | 充满系数       | NUMBER(12,3) |          | Y        |        |               |
| 34       | PUMPEFF3                   | 间隙漏失系数   | NUMBER(12,3) |          | Y        |        |               |
| 35       | PUMPEFF4                   | 液体收缩系数   | NUMBER(12,3) |          | Y        |        |               |
| 36       | PUMPEFF                    | 总泵效         | NUMBER(12,3) |          | Y        |        |               |
| 37       | IDEGREEBALANCE             | 电流平衡度     | NUMBER(8,2)  |          | Y        |        |               |
| 38       | WATTDEGREEBALANCE          | 功率平衡度     | NUMBER(8,2)  |          | Y        |        |               |
| 39       | DELTARADIUS                | 移动距离       | NUMBER(8,2)  |          | Y        |        |               |
| 40       | TOTALKWATTH                | 累计电量       | NUMBER(12,3) |          | Y        |        |               |
| 41       | TODAYKWATTH                | 日用电量       | NUMBER(12,3) |          | Y        |        |               |
| 42       | EXTENDEDDAYS               | 沿用天数       | NUMBER(5)    |          | Y        |        |               |
| 43       | RESULTSTATUS               | 计算状态       | NUMBER(2)    |          | Y        |        |               |

### 1.2.38 tbl\_rpcdevicegraphicset

表1-37 抽油机图形设置表

| **序号** | **代码**     | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注**       |
|----------|--------------|--------------|----------------|----------|----------|--------|----------------|
| 1        | ID           | 记录编号     | NUMBER(10)     |          | N        | 主键   |                |
| 2        | WELLID       | 设备编号     | NUMBER(10)     |          | N        |        |                |
| 3        | GRAPHICSTYLE | 图形设置内容 | VARCHAR2(4000) |          | Y        |        | json格式字符串 |

### 1.2.39 tbl\_pcpacqdata_latest

表1-38 螺杆泵实时数据表

| **序号** | **代码**                     | **名称**           | **类型**       | **单位** | **为空** | **键** | **备注**               |
|----------|------------------------------|--------------------|----------------|----------|----------|--------|------------------------|
| 1        | ID                           | 记录编号           | NUMBER(10)     |          | N        | 主键   |                        |
| 2        | WELLID                       | 设备编号           | NUMBER(10)     |          | N        |        |                        |
| 3        | ACQTIME                      | 采集时间           | DATE           |          | Y        |        |                        |
| 4        | COMMSTATUS                   | 通信状态           | NUMBER(2)      |          | Y        |        | 0-离线 1-在线          |
| 5        | COMMTIME                     | 在线时间           | NUMBER(8,2)    |          | Y        |        |                        |
| 6        | COMMTIMEEFFICIENCY           | 在线时率           | NUMBER(10,4)   |          | Y        |        |                        |
| 7        | COMMRANGE                    | 在线区间           | CLOB           |          | Y        |        |                        |
| 8        | RUNSTATUS                    | 运行状态           | NUMBER(2)      |          | Y        |        |                        |
| 9        | RUNTIMEEFFICIENCY            | 运行时率           | NUMBER(8,2)    |          | Y        |        |                        |
| 10       | RUNTIME                      | 运行时间           | NUMBER(10,4)   |          | Y        |        |                        |
| 11       | RUNRANGE                     | 运行区间           | CLOB           |          | Y        |        |                        |
| 12       | PRODUCTIONDATA               | 生产数据           | VARCHAR2(4000) |          | Y        |        |                        |
| 13       | RPM                          | 转速               | NUMBER(8,2)    |          | Y        |        |                        |
| 14       | TORQUE                       | 扭矩               | NUMBER(8,2)    |          | Y        |        |                        |
| 15       | RESULTCODE                   | 工况代码           | NUMBER(4)      |          | Y        |        |                        |
| 16       | THEORETICALPRODUCTION        | 理论排量           | NUMBER(8,2)    |          | Y        |        |                        |
| 17       | LIQUIDVOLUMETRICPRODUCTION   | 产液量             | NUMBER(8,2)    | 方       | Y        |        |                        |
| 18       | OILVOLUMETRICPRODUCTION      | 产油量             | NUMBER(8,2)    | 方       | Y        |        |                        |
| 19       | WATERVOLUMETRICPRODUCTION    | 产水量             | NUMBER(8,2)    | 方       | Y        |        |                        |
| 20       | LIQUIDWEIGHTPRODUCTION       | 产液量             | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 21       | OILWEIGHTPRODUCTION          | 产油量             | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 22       | WATERWEIGHTPRODUCTION        | 产水量             | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 23       | AVERAGEWATT                  | 有功功率           | NUMBER(8,2)    |          | Y        |        |                        |
| 24       | WATERPOWER                   | 水功率             | NUMBER(8,2)    |          | Y        |        |                        |
| 25       | SYSTEMEFFICIENCY             | 系统效率           | NUMBER(12,3)   |          | Y        |        |                        |
| 26       | ENERGYPER100MLIFT            | 吨液百米耗电量     | NUMBER(8,2)    |          | Y        |        |                        |
| 27       | PUMPEFF1                     | 容积效率           | NUMBER(12,3)   |          | Y        |        |                        |
| 28       | PUMPEFF2                     | 液体收缩系数       | NUMBER(12,3)   |          | Y        |        |                        |
| 29       | PUMPEFF                      | 总泵效             | NUMBER(12,3)   |          | Y        |        |                        |
| 30       | PUMPINTAKEP                  | 泵入口压力         | NUMBER(8,2)    |          | Y        |        |                        |
| 31       | PUMPINTAKET                  | 泵入口温度         | NUMBER(8,2)    |          | Y        |        |                        |
| 32       | PUMPINTAKEGOL                | 泵入口就地气液比   | NUMBER(8,2)    |          | Y        |        |                        |
| 33       | PUMPINTAKEVISL               | 泵入口粘度         | NUMBER(8,2)    |          | Y        |        |                        |
| 34       | PUMPINTAKEBO                 | 泵入口原油体积系数 | NUMBER(8,2)    |          | Y        |        |                        |
| 35       | PUMPOUTLETP                  | 泵出口压力         | NUMBER(8,2)    |          | Y        |        |                        |
| 36       | PUMPOUTLETT                  | 泵出口温度         | NUMBER(8,2)    |          | Y        |        |                        |
| 37       | PUMPOUTLETGOL                | 泵出口就地气液比   | NUMBER(8,2)    |          | Y        |        |                        |
| 38       | PUMPOUTLETVISL               | 泵出口粘度         | NUMBER(8,2)    |          | Y        |        |                        |
| 39       | PUMPOUTLETBO                 | 泵出口原油体积系数 | NUMBER(8,2)    |          | Y        |        |                        |
| 40       | RODSTRING                    | 抽油杆参数         | VARCHAR2(200)  |          | Y        |        |                        |
| 41       | RESULTSTATUS                 | 计算状态           | NUMBER(2)      |          | Y        |        |                        |
| 42       | TOTALKWATTH                  | 累计电量           | NUMBER(12,3)   |          | Y        |        |                        |
| 43       | TODAYKWATTH                  | 日用电量           | NUMBER(12,3)   |          | Y        |        |                        |
| 44       | LIQUIDVOLUMETRICPRODUCTION_L | 日累计产液量       | NUMBER(8,2)    | 方       | Y        |        |                        |
| 45       | OILVOLUMETRICPRODUCTION_L    | 日累计产油量       | NUMBER(8,2)    | 方       | Y        |        |                        |
| 46       | WATERVOLUMETRICPRODUCTION_L  | 日累计产水量       | NUMBER(8,2)    | 方       | Y        |        |                        |
| 47       | LIQUIDWEIGHTPRODUCTION_L     | 日累计产液量       | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 48       | OILWEIGHTPRODUCTION_L        | 日累计产油量       | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 49       | WATERWEIGHTPRODUCTION_L      | 日累计产水量       | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 50       | SAVETIME                     | 保存时间           | DATE           |          | Y        |        |                        |
| …        | …                            | …                  |                |          | Y        |        | 根据驱动自动生成的字段 |
| …        | …                            | …                  |                |          | Y        |        |                        |

### 1.2.40 tbl\_pcpacqdata_hist

同tbl\_pcpacqdata\_latest

### 1.2.41 tbl\_pcpacqrawdata

表1-39 螺杆泵原始采集数据表

| **序号** | **代码** | **名称** | **类型**       | **单位** | **为空** | **键** | **备注**                               |
|----------|----------|----------|----------------|----------|----------|--------|----------------------------------------|
| 1        | ID       | 记录编号 | NUMBER(10)     |          | N        | 主键   |                                        |
| 2        | WELLID   | 设备编号 | NUMBER(10)     |          | N        |        |                                        |
| 3        | ACQTIME  | 采集时间 | DATE           |          | Y        |        |                                        |
| 4        | RAWDATA  | 原始数据 | VARCHAR2(4000) |          | Y        |        | 设备采集的未解析原始数据，16进制字符串 |

### 1.2.42 tbl\_pcpalarminfo_latest

表1-40 抽油机报警实时数据表

| **序号** | **代码**      | **名称**     | **类型**      | **单位** | **为空** | **键** | **备注**                                        |
|----------|---------------|--------------|---------------|----------|----------|--------|-------------------------------------------------|
| 1        | ID            | 记录编号     | NUMBER(10)    |          | N        | 主键   |                                                 |
| 2        | WELLID        | 设备编号     | NUMBER(10)    |          | N        |        |                                                 |
| 3        | ALARMTIME     | 报警时间     | DATE          |          | Y        |        |                                                 |
| 4        | ITEMNAME      | 报警项       | VARCHAR2(100) |          | Y        |        |                                                 |
| 5        | ALARMTYPE     | 报警类型     | NUMBER(1)     |          | Y        |        | 0-通信报警 1-限值报警 2-枚举量报警 3-开关量报警 |
| 6        | ALARMVALUE    | 报警值       | NUMBER(10,3)  |          | Y        |        |                                                 |
| 7        | ALARMINFO     | 报警信息     | VARCHAR2(100) |          | Y        |        |                                                 |
| 8        | ALARMLIMIT    | 报警限值     | NUMBER(10,3)  |          | Y        |        |                                                 |
| 9        | HYSTERSIS     | 回差         | NUMBER(10,3)  |          | Y        |        |                                                 |
| 10       | ALARMLEVEL    | 报警级别     | NUMBER(3)     |          | Y        |        | 100-一级报警 200-二级报警 300-三级报警          |
| 11       | RECOVERYTIME  | 恢复时间     | DATE          |          | Y        |        |                                                 |
| 12       | ISSENDMESSAGE | 是否发送短信 | NUMBER(1)     |          | Y        |        | 0-否 1-是                                       |
| 13       | ISSENDMAIL    | 是否发送邮件 | NUMBER(1)     |          | Y        |        | 0-否 1-是                                       |

### 1.2.43 tbl\_pcpalarminfo_hist

同tbl\_pcpalarminfo_latest

### 1.2.44 tbl_pcpdailycalculationdata

表1-41 螺杆泵汇总数据表

| **序号** | **代码**                   | **名称**       | **类型**     | **单位** | **为空** | **键** | **备注**      |
|----------|----------------------------|----------------|--------------|----------|----------|--------|---------------|
| 1        | ID                         | 记录编号       | NUMBER(10)   |          | N        | 主键   |               |
| 2        | WELLID                     | 设备编号       | NUMBER(10)   |          | N        |        |               |
| 3        | CALDATE                    | 汇总日期       | DATE         |          | Y        |        |               |
| 4        | COMMSTATUS                 | 通信状态       | NUMBER(2)    |          | Y        |        | 0-离线 1-在线 |
| 5        | COMMTIME                   | 在线时间       | NUMBER(8,2)  |          | Y        |        |               |
| 6        | COMMTIMEEFFICIENCY         | 在线时率       | NUMBER(10,4) |          | Y        |        |               |
| 7        | COMMRANGE                  | 在线区间       | CLOB         |          | Y        |        |               |
| 8        | RUNSTATUS                  | 运行状态       | NUMBER(2)    |          | Y        |        |               |
| 9        | RUNTIMEEFFICIENCY          | 运行时率       | NUMBER(8,2)  |          | Y        |        |               |
| 10       | RUNTIME                    | 运行时间       | NUMBER(10,4) |          | Y        |        |               |
| 11       | RUNRANGE                   | 运行区间       | CLOB         |          | Y        |        |               |
| 12       | RPM                        | 转速           | NUMBER(8,2)  |          | Y        |        |               |
| 13       | RESULTCODE                 | 工况代码       | NUMBER(4)    |          | Y        |        |               |
| 14       | THEORETICALPRODUCTION      | 理论排量       | NUMBER(8,2)  |          | Y        |        |               |
| 15       | LIQUIDVOLUMETRICPRODUCTION | 产液量         | NUMBER(8,2)  | 方       | Y        |        |               |
| 16       | OILVOLUMETRICPRODUCTION    | 产油量         | NUMBER(8,2)  | 方       | Y        |        |               |
| 17       | WATERVOLUMETRICPRODUCTION  | 产水量         | NUMBER(8,2)  | 方       | Y        |        |               |
| 18       | VOLUMEWATERCUT             | 体积含水率     | NUMBER(8,2)  |          | Y        |        |               |
| 19       | LIQUIDWEIGHTPRODUCTION     | 产液量         | NUMBER(8,2)  | 吨       | Y        |        |               |
| 20       | OILWEIGHTPRODUCTION        | 产油量         | NUMBER(8,2)  | 吨       | Y        |        |               |
| 21       | WATERWEIGHTPRODUCTION      | 产水量         | NUMBER(8,2)  | 吨       | Y        |        |               |
| 22       | WEIGHTWATERCUT             | 重量含水率     | NUMBER(8,2)  |          | Y        |        |               |
| 23       | SYSTEMEFFICIENCY           | 系统效率       | NUMBER(12,3) |          | Y        |        |               |
| 24       | ENERGYPER100MLIFT          | 吨液百米耗电量 | NUMBER(8,2)  |          | Y        |        |               |
| 25       | PUMPEFF1                   | 容积效率       | NUMBER(12,3) |          | Y        |        |               |
| 26       | PUMPEFF2                   | 液体收缩系数   | NUMBER(12,3) |          | Y        |        |               |
| 27       | PUMPEFF                    | 总泵效         | NUMBER(12,3) |          | Y        |        |               |
| 28       | TOTALKWATTH                | 累计电量       | NUMBER(12,3) |          | Y        |        |               |
| 29       | TODAYKWATTH                | 日用电量       | NUMBER(12,3) |          | Y        |        |               |
| 30       | EXTENDEDDAYS               | 沿用天数       | NUMBER(5)    |          | Y        |        |               |
| 31       | RESULTSTATUS               | 计算状态       | NUMBER(2)    |          | Y        |        |               |

### 1.2.45 tbl\_pcpdevicegraphicset

表1-42 螺杆泵图形设置表

| **序号** | **代码**     | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注**       |
|----------|--------------|--------------|----------------|----------|----------|--------|----------------|
| 1        | ID           | 记录编号     | NUMBER(10)     |          | N        | 主键   |                |
| 2        | WELLID       | 设备编号     | NUMBER(10)     |          | N        |        |                |
| 3        | GRAPHICSTYLE | 图形设置内容 | VARCHAR2(4000) |          | Y        |        | json格式字符串 |

### 1.2.46 tbl_deviceoperationlog

表1-43 设备操作日志表

| **序号** | **代码**   | **名称**     | **类型** | **单位** | **为空** | **键** | **备注**                                    |
|----------|------------|--------------|----------|----------|----------|--------|---------------------------------------------|
| 1        | ID         | 记录编号     | NUMBER   |          | N        | 主键   |                                             |
| 2        | WELLNAME   | 设备名称     | VARCHAR2 |          | Y        |        |                                             |
| 3        | CREATETIME | 创建时间     | DATE     |          | Y        |        |                                             |
| 4        | USER_ID    | 操作用户账号 | VARCHAR2 |          | Y        |        |                                             |
| 5        | LOGINIP    | 用户登录IP   | VARCHAR2 |          | Y        |        |                                             |
| 6        | ACTION     | 操作         | NUMBER   |          | Y        |        | 0-添加设备 1-修改设备 2-删除设备 3-控制设备 |
| 7        | DEVICETYPE | 设备类型     | NUMBER   |          | Y        |        | 0-抽油机 1-螺杆泵                           |
| 8        | REMARK     | 备注         | VARCHAR2 |          | Y        |        |                                             |

### 1.2.47 tbl_systemlog

表1-44 系统日志表

| **序号** | **代码**   | **名称** | **类型**      | **单位** | **为空** | **键** | **备注**              |
|----------|------------|----------|---------------|----------|----------|--------|-----------------------|
| 1        | ID         | 记录编号 | NUMBER(10)    |          | N        | 主键   |                       |
| 2        | CREATETIME | 创建时间 | DATE          |          | N        |        |                       |
| 3        | USER_ID    | 用户账号 | VARCHAR2(20)  |          | Y        |        |                       |
| 4        | LOGINIP    | 登录IP   | VARCHAR2(20)  | m        | Y        |        |                       |
| 5        | ACTION     | 操作     | NUMBER(2)     | 次/min   | Y        |        | 0-用户登录 1-用户退出 |
| 6        | REMARK     | 备注     | VARCHAR2(200) | kN       | Y        |        |                       |

### 1.2.48 tbl_resourcemonitoring

表1-45 资源监测数据表

| **序号** | **代码**       | **名称**         | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|----------------|------------------|--------------|----------|----------|--------|----------|
| 1        | ID             | 记录编号         | NUMBER(10)   |          | N        | 主键   |          |
| 2        | ACQTIME        | 采集时间         | DATE         |          | Y        |        |          |
| 3        | APPRUNSTATUS   | AC运行状态       | NUMBER(2)    |          | Y        |        |          |
| 4        | APPVERSION     | AC版本信息       | VARCHAR2(50) |          | Y        |        |          |
| 5        | ADRUNSTATUS    | 驱动程序运行状态 | NUMBER(2)    |          | Y        |        |          |
| 6        | ADVERSION      | 驱动程序版本信息 | VARCHAR2(50) |          | Y        |        |          |
| 7        | CPUUSEDPERCENT | CPU使用率        | VARCHAR2(50) | %        | Y        |        |          |
| 8        | MEMUSEDPERCENT | 内存使用率       | NUMBER(8,2)  | %        | Y        |        |          |
| 9        | TABLESPACESIZE | 数据库表空间大小 | NUMBER(10,2) | Mb       | Y        |        |          |
| 10       | JEDISSTATUS    | REDIS状态        | NUMBER(2)    |          | Y        |        |          |

# 二、视图

## 2.1 概览

表2-1 视图概览

| **序号** | **名称**                    | **描述**               |
|----------|-----------------------------|------------------------|
| 1        | viw_org                     | 单位组织视图           |
| 2        | viw\_rpcdevice              | 抽油机信息视图         |
| 3        | viw\_pcpdevice              | 螺杆泵信息视图         |
| 4        | viw_smsdevice               | 短信设备信息视图       |
| 5        | viw\_rpcacqrawdata          | 抽油机原始采集数据视图 |
| 6        | viw\_pcpacqrawdata          | 螺杆泵原始采集数据视图 |
| 7        | viw\_rpcalarminfo_latest    | 抽油机实时报警信息视图 |
| 8        | viw\_rpcalarminfo_hist      | 抽油机历史报警信息视图 |
| 9        | viw\_pcpalarminfo_latest    | 螺杆泵实时报警信息视图 |
| 10       | viw\_pcpalarminfo_hist      | 螺杆泵历史报警信息视图 |
| 11       | viw_deviceoperationlog      | 设备操作日志视图       |
| 12       | viw_systemlog               | 系统日志视图           |
| 13       | viw_rpcdailycalculationdata | 抽油机汇总视图         |
| 14       | viw_pcpdailycalculationdata | 螺杆泵汇总视图         |
| 15       | viw_rpc_calculatemain       | 抽油机计算维护视图     |
| 16       | viw_pcp_calculatemain       | 螺杆泵计算维护视图     |

## 2.2 详述

### 2.2.1 viw\_org

表2-1 单位组织视图

| **序号** | **代码**   | **名称**     | **类型** | **备注** |
|----------|------------|--------------|----------|----------|
| 1        | ORG_ID     | 单位序号     | NUMBER   |          |
| 2        | ORG_CODE   | 单位编码     | VARCHAR2 |          |
| 3        | ORG_NAME   | 单位名称     | VARCHAR2 |          |
| 4        | ORG_MEMO   | 单位说明     | VARCHAR2 |          |
| 5        | ORG_PARENT | 父级单位编号 | NUMBER   |          |
| 6        | ORG_SEQ    | 单位排序     | NUMBER   |          |
| 7        | ALLPATH    | 组织全路径   | VARCHAR2 |          |

### 2.2.2 viw\_rpcdevice

表2-2 抽油机信息视图

| **序号** | **代码**                 | **名称**       | **类型** | **备注**      |
|----------|--------------------------|----------------|----------|---------------|
| 1        | ID                       | 记录编号       | NUMBER   |               |
| 2        | ORGNAME                  | 组织名称       | VARCHAR2 |               |
| 3        | ORGID                    | 组织编号       | NUMBER   |               |
| 4        | ALLPATH                  | 组织全路径     | VARCHAR2 |               |
| 5        | WELLNAME                 | 设备名称       | VARCHAR2 |               |
| 6        | DEVICETYPE               | 设备类型       | NUMBER   |               |
| 7        | DEVICETYPENAME           | 设备类型名称   | VARCHAR2 |               |
| 8        | APPLICATIONSCENARIOS     | 应用场景       | NUMBER   |               |
| 9        | APPLICATIONSCENARIOSNAME | 应用场景名称   | VARCHAR2 |               |
| 10       | TCPTYPE                  | 下位机TCP类型  | VARCHAR2 |               |
| 11       | SIGNINID                 | 注册包ID       | VARCHAR2 |               |
| 12       | SLAVE                    | 设备从地址     | VARCHAR2 |               |
| 13       | PEAKDELAY                | 错峰延时       | NUMBER   |               |
| 14       | VIDEOURL                 | 视频监控路径   | VARCHAR2 |               |
| 15       | VIDEOACCESSTOKEN         | 视频访问令牌   | VARCHAR2 | 预留          |
| 16       | INSTANCECODE             | 采控实例编码   | VARCHAR2 |               |
| 17       | INSTANCENAME             | 采控实例名称   | VARCHAR2 |               |
| 18       | ALARMINSTANCECODE        | 报警实例编码   | VARCHAR2 |               |
| 19       | ALARMINSTANCENAME        | 报警实例名称   | VARCHAR2 |               |
| 20       | DISPLAYINSTANCECODE      | 显示实例编码   | VARCHAR2 |               |
| 21       | DISPLAYINSTANCENAME      | 显示实例名称   | VARCHAR2 |               |
| 22       | REPORTINSTANCECODE       | 报表实例编码   | VARCHAR2 |               |
| 23       | REPORTINSTANCENAME       | 报表实例名称   | VARCHAR2 |               |
| 24       | STATUS                   | 状态           | NUMBER   | 0-失效 1-使能 |
| 25       | STATUSNAME               | 状态名称       | VARCHAR2 | 失效或使能    |
| 26       | PRODUCTIONDATA           | 生产数据       | VARCHAR2 |               |
| 27       | BALANCEINFO              | 平衡块数据     | VARCHAR2 |               |
| 28       | STROKE                   | 铭牌冲程       | NUMBER   |               |
| 29       | PUMPINGMODELID           | 抽油机型号编号 | NUMBER   |               |
| 30       | MANUFACTURER             | 抽油机厂家     | VARCHAR2 |               |
| 31       | MODEL                    | 抽油机型号     | VARCHAR2 |               |
| 32       | CRANKROTATIONDIRECTION   | 曲柄旋转方向   | VARCHAR2 |               |
| 33       | OFFSETANGLEOFCRANK       | 曲柄偏置角     | NUMBER   |               |
| 34       | CRANKGRAVITYRADIUS       | 曲柄重心半径   | NUMBER   |               |
| 35       | SINGLECRANKWEIGHT        | 单块曲柄重要   | NUMBER   |               |
| 36       | SINGLECRANKPINWEIGHT     | 单块曲柄销重量 | NUMBER   |               |
| 37       | STRUCTURALUNBALANCE      | 结构不平衡重   | NUMBER   |               |
| 38       | SORTNUM                  | 排序编号       | NUMBER   |               |

### 2.2.3 viw\_pcpdevice

表2-3 螺杆泵信息视图

| **序号** | **代码**                 | **名称**      | **类型** | **备注**      |
|----------|--------------------------|---------------|----------|---------------|
| 1        | ID                       | 记录编号      | NUMBER   |               |
| 2        | ORGNAME                  | 组织名称      | VARCHAR2 |               |
| 3        | ORGID                    | 组织编号      | NUMBER   |               |
| 4        | ALLPATH                  | 组织全路径    | VARCHAR2 |               |
| 5        | WELLNAME                 | 设备名称      | VARCHAR2 |               |
| 6        | APPLICATIONSCENARIOS     | 应用场景      | NUMBER   |               |
| 7        | APPLICATIONSCENARIOSNAME | 应用场景名称  | VARCHAR2 |               |
| 8        | DEVICETYPE               | 设备类型      | NUMBER   |               |
| 9        | DEVICETYPENAME           | 设备类型名称  | VARCHAR2 |               |
| 10       | TCPTYPE                  | 下位机TCP类型 | VARCHAR2 |               |
| 11       | SIGNINID                 | 注册包ID      | VARCHAR2 |               |
| 12       | SLAVE                    | 设备从地址    | VARCHAR2 |               |
| 13       | PEAKDELAY                | 错峰延时      | NUMBER   |               |
| 14       | VIDEOURL                 | 视频路径      | VARCHAR2 |               |
| 15       | VIDEOACCESSTOKEN         | 视频访问令牌  | VARCHAR2 | 预留          |
| 16       | INSTANCECODE             | 采控实例编码  | VARCHAR2 |               |
| 17       | INSTANCENAME             | 采控实例名称  | VARCHAR2 |               |
| 18       | ALARMINSTANCECODE        | 报警实例编码  | VARCHAR2 |               |
| 19       | ALARMINSTANCENAME        | 报警实例名称  | VARCHAR2 |               |
| 20       | DISPLAYINSTANCECODE      | 显示实例编码  | VARCHAR2 |               |
| 21       | DISPLAYINSTANCENAME      | 显示实例名称  | VARCHAR2 |               |
| 22       | REPORTINSTANCECODE       | 报表实例编码  | VARCHAR2 |               |
| 23       | REPORTINSTANCENAME       | 报表实例名称  | VARCHAR2 |               |
| 24       | STATUS                   | 状态          | NUMBER   | 0-失效 1-使能 |
| 25       | STATUSNAME               | 状态名称      | VARCHAR2 | 失效或使能    |
| 26       | PRODUCTIONDATA           | 生产数据      | VARCHAR2 |               |
| 27       | SORTNUM                  | 排序编号      | NUMBER   |               |

### 2.2.4 viw\_smsdevice

表2-4 短信设备信息视图

| **序号** | **代码**     | **名称**     | **类型** | **备注** |
|----------|--------------|--------------|----------|----------|
| 1        | ID           | 记录编号     | NUMBER   |          |
| 2        | ORGNAME      | 组织名称     | VARCHAR2 |          |
| 3        | ORGID        | 组织编号     | NUMBER   |          |
| 4        | WELLNAME     | 设备名称     | VARCHAR2 |          |
| 5        | SIGNINID     | 注册包ID     | VARCHAR2 |          |
| 6        | INSTANCECODE | 短信实例编码 | VARCHAR2 |          |
| 7        | INSTANCENAME | 短信实例名称 | VARCHAR2 |          |
| 8        | SORTNUM      | 排序编号     | NUMBER   |          |

### 2.2.5 viw\_rpcacqrawdata

表2-5 抽油机原始采集数据视图

| **序号** | **代码**       | **名称**       | **类型** | **备注**                                       |
|----------|----------------|----------------|----------|------------------------------------------------|
| 1        | ID             | 记录编号       | NUMBER   |                                                |
| 2        | WELLID         | 设备编号       | NUMBER   |                                                |
| 3        | WELLNAME       | 设备名称       | VARCHAR2 |                                                |
| 4        | DEVICETYPE     | 设备类型       | NUMBER   |                                                |
| 5        | DEVICETYPENAME | 设备类型名称   | VARCHAR2 |                                                |
| 6        | SIGNINID       | 设备注册包ID   | VARCHAR2 |                                                |
| 7        | SLAVE          | 设备从地址     | VARCHAR2 |                                                |
| 8        | ACQTIME        | 采集时间       | DATE     |                                                |
| 9        | RAWDATA        | 原始数据       | VARCHAR2 | 采集的设备上传的未解析的原始数据，16进制字符串 |
| 10       | ORGID          | 设备组织编号   | NUMBER   |                                                |
| 11       | ALLPATH        | 设备组织全路径 | VARCHAR2 |                                                |

### 2.2.6 viw\_pcpacqrawdata

表2-6 螺杆泵原始采集数据视图

| **序号** | **代码**       | **名称**       | **类型** | **备注**                                       |
|----------|----------------|----------------|----------|------------------------------------------------|
| 1        | ID             | 记录编号       | NUMBER   |                                                |
| 2        | WELLID         | 设备编号       | NUMBER   |                                                |
| 3        | WELLNAME       | 设备名称       | VARCHAR2 |                                                |
| 4        | DEVICETYPE     | 设备类型       | NUMBER   |                                                |
| 5        | DEVICETYPENAME | 设备类型名称   | VARCHAR2 |                                                |
| 6        | SIGNINID       | 设备注册包ID   | VARCHAR2 |                                                |
| 7        | SLAVE          | 设备从地址     | VARCHAR2 |                                                |
| 8        | ACQTIME        | 采集时间       | DATE     |                                                |
| 9        | RAWDATA        | 原始数据       | VARCHAR2 | 采集的设备上传的未解析的原始数据，16进制字符串 |
| 10       | ORGID          | 设备组织编号   | NUMBER   |                                                |
| 11       | ALLPATH        | 设备组织全路径 | VARCHAR2 |                                                |

### 2.2.7 viw\_rpcalarminfo_latest

表2-7 抽油机报警实时数据视图

| **序号** | **代码**       | **名称**     | **类型** | **备注**                                      |
|----------|----------------|--------------|----------|-----------------------------------------------|
| 1        | ID             | 记录编号     | NUMBER   |                                               |
| 2        | WELLID         | 设备编号     | NUMBER   |                                               |
| 3        | WELLNAME       | 设备名称     | VARCHAR2 |                                               |
| 4        | DEVICETYPE     | 设备类型     | NUMBER   | 0或1                                          |
| 5        | DEVICETYPENAME | 设备类型名称 | VARCHAR2 | 抽油机或螺杆泵                                |
| 6        | ALARMTIME      | 报警时间     | DATE     |                                               |
| 7        | ITEMNAME       | 报警项       | VARCHAR2 |                                               |
| 8        | ALARMTYPE      | 报警类型     | NUMBER   | 0、1、2、3                                    |
| 9        | ALARMTYPENAME  | 报警类型名称 | VARCHAR2 | 通信报警、数值量报警、 枚举量报警、开关量报警 |
| 10       | ALARMVALUE     | 报警值       | NUMBER   |                                               |
| 11       | ALARMINFO      | 报警信息     | VARCHAR2 |                                               |
| 12       | ALARMLIMIT     | 报警限值     | NUMBER   |                                               |
| 13       | HYSTERSIS      | 回差         | NUMBER   |                                               |
| 14       | ALARMLEVEL     | 报警级别     | NUMBER   | 100、200、300                                 |
| 15       | ALARMLEVELNAME | 报警级别名称 | VARCHAR2 | 一级报警、二级报警、三级报警                  |
| 16       | ISSENDMESSAGE  | 是否发送短信 | NUMBER   |                                               |
| 17       | ISSENDMAIL     | 是否发送邮件 | NUMBER   |                                               |
| 18       | RECOVERYTIME   | 恢复时间     | DATE     |                                               |
| 19       | ORGID          | 组织编号     | NUMBER   |                                               |

### 2.2.8 viw\_rpcalarminfo\_hist

同viw\_rpcalarminfo_latest

### 2.2.9 viw\_pcpalarminfo_latest

表2-8 螺杆泵报警实时数据视图

| **序号** | **代码**       | **名称**     | **类型** | **备注**                                      |
|----------|----------------|--------------|----------|-----------------------------------------------|
| 1        | ID             | 记录编号     | NUMBER   |                                               |
| 2        | WELLID         | 设备编号     | NUMBER   |                                               |
| 3        | WELLNAME       | 设备名称     | VARCHAR2 |                                               |
| 4        | DEVICETYPE     | 设备类型     | NUMBER   | 0或1                                          |
| 5        | DEVICETYPENAME | 设备类型名称 | VARCHAR2 | 抽油机或螺杆泵                                |
| 6        | ALARMTIME      | 报警时间     | DATE     |                                               |
| 7        | ITEMNAME       | 报警项       | VARCHAR2 |                                               |
| 8        | ALARMTYPE      | 报警类型     | NUMBER   | 0、1、2、3                                    |
| 9        | ALARMTYPENAME  | 报警类型名称 | VARCHAR2 | 通信报警、数值量报警、 枚举量报警、开关量报警 |
| 10       | ALARMVALUE     | 报警值       | NUMBER   |                                               |
| 11       | ALARMINFO      | 报警信息     | VARCHAR2 |                                               |
| 12       | ALARMLIMIT     | 报警限值     | NUMBER   |                                               |
| 13       | HYSTERSIS      | 回差         | NUMBER   |                                               |
| 14       | ALARMLEVEL     | 报警级别     | NUMBER   | 100、200、300                                 |
| 15       | ALARMLEVELNAME | 报警级别名称 | VARCHAR2 | 一级报警、二级报警、三级报警                  |
| 16       | ISSENDMESSAGE  | 是否发送短信 | NUMBER   |                                               |
| 17       | ISSENDMAIL     | 是否发送邮件 | NUMBER   |                                               |
| 18       | RECOVERYTIME   | 恢复时间     | DATE     |                                               |
| 19       | ORGID          | 组织编号     | NUMBER   |                                               |

### 2.2.10 viw\_pcpalarminfo\_hist

同viw\_pcpalarminfo_latest

### 2.2.11 viw_deviceoperationlog

表2-9 设备操作日志视图

| **序号** | **代码**       | **名称**     | **类型** | **备注**                               |
|----------|----------------|--------------|----------|----------------------------------------|
| 1        | ID             | 记录编号     | NUMBER   |                                        |
| 2        | DEVICETYPE     | 设备类型     | NUMBER   | 0或1                                   |
| 3        | DEVICETYPENAME | 设备类型名称 | VARCHAR2 | 抽油机或螺杆泵                         |
| 4        | WELLNAME       | 设备名称     | VARCHAR2 |                                        |
| 5        | CREATETIME     | 创建时间     | DATE     |                                        |
| 6        | USER_NO        | 操作用户编号 | NUMBER   |                                        |
| 7        | USER_ID        | 操作用户账号 | VARCHAR2 |                                        |
| 8        | ROLE_ID        | 角色编号     | NUMBER   |                                        |
| 9        | ROLE_LEVEL     | 角色等级     | NUMBER   |                                        |
| 10       | LOGINIP        | 用户登录IP   | VARCHAR2 |                                        |
| 11       | ACTION         | 操作         | NUMBER   | 0、1、2、3                             |
| 12       | ACTIONNAME     | 操作名称     | VARCHAR2 | 添加设备、修改设备、删除设备、控制设备 |
| 13       | REMARK         | 备注         | VARCHAR2 |                                        |
| 14       | ORGID          | 组织编号     | NUMBER   |                                        |

### 2.2.12 viw_systemlog

表2-10 系统日志视图

| **序号** | **代码**   | **名称** | **类型** | **备注**           |
|----------|------------|----------|----------|--------------------|
| 1        | ID         | 记录编号 | NUMBER   |                    |
| 2        | CREATETIME | 创建时间 | DATE     |                    |
| 3        | USER_NO    | 用户编号 | NUMBER   |                    |
| 4        | USER_ID    | 用户账号 | VARCHAR2 |                    |
| 5        | ROLE_ID    | 角色编号 | NUMBER   |                    |
| 6        | ROLE_LEVEL | 角色等级 | NUMBER   |                    |
| 7        | LOGINIP    | 登录IP   | VARCHAR2 |                    |
| 8        | ACTION     | 操作     | NUMBER   | 0、1               |
| 9        | ACTIONNAME | 操作名称 | VARCHAR2 | 用户登录、用户退出 |
| 10       | REMARK     | 备注     | VARCHAR2 |                    |
| 11       | ORGID      | 组织编号 | NUMBER   |                    |

### 2.2.13 viw_rpcdailycalculationdata

表2-11 抽油机汇总视图

| **序号** | **代码**                   | **名称**       | **类型** | **备注** |
|----------|----------------------------|----------------|----------|----------|
| 1        | ID                         | 记录编号       | NUMBER   |          |
| 2        | WELLNAME                   | 设备名称       | VARCHAR2 |          |
| 3        | WELLID                     | 设备编号       | NUMBER   |          |
| 4        | CALDATE                    | 汇总日期       | DATE     |          |
| 5        | EXTENDEDDAYS               | 沿用天数       | NUMBER   |          |
| 6        | ACQUISITIONDATE            | 功图采集日期   | DATE     |          |
| 7        | COMMSTATUS                 | 通信状态       | NUMBER   |          |
| 8        | COMMSTATUSNAME             | 通信状态名称   | VARCHAR2 |          |
| 9        | COMMTIME                   | 在线时间       | NUMBER   |          |
| 10       | COMMTIMEEFFICIENCY         | 在线时率       | NUMBER   |          |
| 11       | COMMRANGE                  | 在线区间       | CLOB     |          |
| 12       | RUNSTATUS                  | 运行状态       | NUMBER   |          |
| 13       | RUNSTATUSNAME              | 运行状态名称   | VARCHAR2 |          |
| 14       | RUNTIMEEFFICIENCY          | 运行时率       | NUMBER   |          |
| 15       | RUNTIME                    | 运行时间       | CLOB     |          |
| 16       | RUNTIMEEFFICIENCY          | 运行区间       | NUMBER   |          |
| 17       | RESULTCODE                 | 工况代码       | NUMBER   |          |
| 18       | RESULTNAME                 | 工况名称       | VARCHAR2 |          |
| 19       | RESULTSTRING               | 工况字符串     | CLOB     |          |
| 20       | OPTIMIZATIONSUGGESTION     | 优化建议       | VARCHAR2 |          |
| 21       | THEORETICALPRODUCTION      | 理论排量       | NUMBER   |          |
| 22       | LIQUIDWEIGHTPRODUCTION     | 产液量         | NUMBER   |          |
| 23       | OILWEIGHTPRODUCTION        | 产油量         | NUMBER   |          |
| 24       | WATERWEIGHTPRODUCTION      | 产水量         | NUMBER   |          |
| 25       | WEIGHTWATERCUT             | 体积含水率     | NUMBER   |          |
| 26       | LIQUIDVOLUMETRICPRODUCTION | 产液量         | NUMBER   |          |
| 27       | OILVOLUMETRICPRODUCTION    | 产油量         | NUMBER   |          |
| 28       | WATERVOLUMETRICPRODUCTION  | 产水量         | NUMBER   |          |
| 29       | VOLUMEWATERCUT             | 重量含水率     | NUMBER   |          |
| 30       | STROKE                     | 冲程           | NUMBER   |          |
| 31       | SPM                        | 冲次           | NUMBER   |          |
| 32       | FMAX                       | 最大载荷       | NUMBER   |          |
| 33       | FMIN                       | 最小载荷       | NUMBER   |          |
| 34       | FULLNESSCOEFFICIENT        | 充满系数       | NUMBER   |          |
| 35       | PUMPEFF                    | 总泵效         | NUMBER   |          |
| 36       | PUMPEFF1                   | 冲程损失系数   | NUMBER   |          |
| 37       | PUMPEFF2                   | 充满系数       | NUMBER   |          |
| 38       | PUMPEFF3                   | 间隙漏失系数   | NUMBER   |          |
| 39       | PUMPEFF4                   | 液体收缩系数   | NUMBER   |          |
| 40       | SYSTEMEFFICIENCY           | 系统效率       | NUMBER   |          |
| 41       | SURFACESYSTEMEFFICIENCY    | 地面效率       | NUMBER   |          |
| 42       | WELLDOWNSYSTEMEFFICIENCY   | 井下效率       | NUMBER   |          |
| 43       | ENERGYPER100MLIFT          | 吨液百米耗电量 | NUMBER   |          |
| 44       | TODAYKWATTH                | 日用电量       | NUMBER   |          |
| 45       | IDEGREEBALANCE             | 电流平衡度     | NUMBER   |          |
| 46       | WATTDEGREEBALANCE          | 功率平衡度     | NUMBER   |          |
| 47       | DELTARADIUS                | 移动距离       | NUMBER   |          |
| 48       | SORTNUM                    | 排序编号       | NUMBER   |          |
| 49       | ORG_CODE                   | 组织编码       | VARCHAR2 |          |
| 50       | ORG_ID                     | 组织编号       | NUMBER   |          |
| 51       | REMARK                     | 备注           | VARCHAR2 |          |

### 2.2.14 viw_pcpdailycalculationdata

表2-12 螺杆泵汇总视图

| **序号** | **代码**                   | **名称**       | **类型** | **备注** |
|----------|----------------------------|----------------|----------|----------|
| 1        | ID                         | 记录编号       | NUMBER   |          |
| 2        | WELLNAME                   | 设备名称       | VARCHAR2 |          |
| 3        | WELLID                     | 设备编号       | NUMBER   |          |
| 4        | CALDATE                    | 汇总日期       | DATE     |          |
| 5        | EXTENDEDDAYS               | 沿用天数       | NUMBER   |          |
| 6        | ACQUISITIONDATE            | 功图采集日期   | DATE     |          |
| 7        | COMMSTATUS                 | 通信状态       | NUMBER   |          |
| 8        | COMMSTATUSNAME             | 通信状态名称   | VARCHAR2 |          |
| 9        | COMMTIME                   | 在线时间       | NUMBER   |          |
| 10       | COMMTIMEEFFICIENCY         | 在线时率       | NUMBER   |          |
| 11       | COMMRANGE                  | 在线区间       | CLOB     |          |
| 12       | RUNSTATUS                  | 运行状态       | NUMBER   |          |
| 13       | RUNSTATUSNAME              | 运行状态名称   | VARCHAR2 |          |
| 14       | RUNTIMEEFFICIENCY          | 运行时率       | NUMBER   |          |
| 15       | RUNTIME                    | 运行时间       | CLOB     |          |
| 16       | RUNRANGE                   | 运行区间       | NUMBER   |          |
| 17       | RPM                        | 转速           | NUMBER   |          |
| 18       | THEORETICALPRODUCTION      | 理论排量       | NUMBER   |          |
| 19       | LIQUIDWEIGHTPRODUCTION     | 产液量         | NUMBER   |          |
| 20       | OILWEIGHTPRODUCTION        | 产油量         | NUMBER   |          |
| 21       | WATERWEIGHTPRODUCTION      | 产水量         | NUMBER   |          |
| 22       | WEIGHTWATERCUT             | 体积含水率     | NUMBER   |          |
| 23       | LIQUIDVOLUMETRICPRODUCTION | 产液量         | NUMBER   |          |
| 24       | OILVOLUMETRICPRODUCTION    | 产油量         | NUMBER   |          |
| 25       | WATERVOLUMETRICPRODUCTION  | 产水量         | NUMBER   |          |
| 26       | VOLUMEWATERCUT             | 重量含水率     | NUMBER   |          |
| 27       | PUMPEFF                    | 总泵效         | NUMBER   |          |
| 28       | PUMPEFF1                   | 容积效率       | NUMBER   |          |
| 29       | PUMPEFF2                   | 液体收缩系数   | NUMBER   |          |
| 30       | SYSTEMEFFICIENCY           | 系统效率       | NUMBER   |          |
| 31       | ENERGYPER100MLIFT          | 吨液百米耗电量 | NUMBER   |          |
| 32       | TODAYKWATTH                | 日用电量       | NUMBER   |          |
| 33       | SORTNUM                    | 排序编号       | NUMBER   |          |
| 34       | ORG_CODE                   | 组织编码       | VARCHAR2 |          |
| 35       | ORG_ID                     | 组织编号       | NUMBER   |          |
| 36       | REMARK                     | 备注           | VARCHAR2 |          |

### 2.2.15 viw_rpc_calculatemain

表2-13 抽油机计算维护视图

| **序号** | **代码**                   | **名称**       | **类型** | **备注** |
|----------|----------------------------|----------------|----------|----------|
| 1        | ID                         | 记录编号       | NUMBER   |          |
| 2        | WELLNAME                   | 设备名称       | VARCHAR2 |          |
| 3        | WELLID                     | 设备编号       | NUMBER   |          |
| 4        | FESDIAGRAMACQTIME          | 功图采集时间   | DATE     |          |
| 5        | RESULTSTATUS               | 计算状态       | NUMBER   |          |
| 6        | RESULTCODE                 | 工况代码       | NUMBER   |          |
| 7        | RESULTNAME                 | 工况名称       | VARCHAR2 |          |
| 8        | LIQUIDWEIGHTPRODUCTION     | 产液量         | NUMBER   |          |
| 9        | OILWEIGHTPRODUCTION        | 产油量         | NUMBER   |          |
| 10       | LIQUIDVOLUMETRICPRODUCTION | 产液量         | NUMBER   |          |
| 11       | OILVOLUMETRICPRODUCTION    | 产油量         | NUMBER   |          |
| 12       | LEVELCORRECTVALUE          | 反演液面校正值 | NUMBER   |          |
| 13       | INVERPRODUCINGFLUIDLEVEL   | 反演液面       | NUMBER   |          |
| 14       | PRODUCTIONDATA             | 生产数据       | NUMBER   |          |
| 15       | ORG_ID                     | 组织编号       | NUMBER   |          |

### 2.2.16 viw\_pcp\_calculatemain

表2-14 螺杆泵计算维护视图

| **序号** | **代码**                   | **名称** | **类型** | **备注** |
|----------|----------------------------|----------|----------|----------|
| 1        | ID                         | 记录编号 | NUMBER   |          |
| 2        | WELLNAME                   | 设备名称 | VARCHAR2 |          |
| 3        | WELLID                     | 设备编号 | NUMBER   |          |
| 4        | ACQTIME                    | 采集时间 | DATE     |          |
| 5        | RESULTSTATUS               | 计算状态 | NUMBER   |          |
| 6        | LIQUIDWEIGHTPRODUCTION     | 产液量   | NUMBER   |          |
| 7        | OILWEIGHTPRODUCTION        | 产油量   | NUMBER   |          |
| 8        | LIQUIDVOLUMETRICPRODUCTION | 产液量   | NUMBER   |          |
| 9        | OILVOLUMETRICPRODUCTION    | 产油量   | NUMBER   |          |
| 10       | PRODUCTIONDATA             | 生产数据 | NUMBER   |          |
| 11       | RPM                        | 转速     | NUMBER   |          |
| 12       | ORG_ID                     | 组织编号 | NUMBER   |          |

# 三、存储过程

表3-1 存储过程概览

| **序号** | **名称**                       | **描述**                   | **备注**                                             |
|----------|--------------------------------|----------------------------|------------------------------------------------------|
| 1        | PRD_RESET_SEQUENCE             | 重置序列                   |                                                      |
| 2        | PRD_CLEAR_DATA                 | 清理数据并重置序列         | 执行后将删除抽油机、螺杆泵及相关配置数据和采集数据。 |
| 3        | PRD_SAVE\_RPCDEVICE            | 保存抽油机数据             |                                                      |
| 4        | PRD_SAVE\_PCPDEVICE            | 保存螺杆泵数据             |                                                      |
| 5        | PRD_SAVE_SMSDEVICE             | 保存短信设备数据           |                                                      |
| 6        | PRD_SAVE_PUMPINGMODEL          | 保存抽油机型号             |                                                      |
| 7        | PRD_UPDATE\_RPCDEVICE          | 修改抽油机数据             |                                                      |
| 8        | PRD_UPDATE\_PCPDEVICE          | 修改螺杆泵数据             |                                                      |
| 9        | PRD_UPDATE_SMSDEVICE           | 修改短信设备数据           |                                                      |
| 10       | PRD_UPDATE_PUMPINGMODEL        | 修改抽油机型号             |                                                      |
| 11       | PRD_SAVE_RPC_DIAGRAM           | 保存抽油机功图数据         |                                                      |
| 12       | PRD_SAVE_RPC_DIAGRAMCALDATA    | 保存抽油机功图重新计算数据 |                                                      |
| 13       | PRD_SAVE_RPC_DIAGRAMDAILY      | 保存抽油机汇总数据         |                                                      |
| 14       | PRD_SAVE_RPC_DIAGRAMDAILYRECAL | 保存抽油机重新汇总数据     |                                                      |
| 15       | PRD_SAVE_PCP_RPM               | 保存螺杆泵转速数据         |                                                      |
| 16       | PRD_SAVE_PCP_RPMCALDATA        | 保存螺杆泵转速重新计算数据 |                                                      |
| 17       | PRD_SAVE_PCP_RPMDAILY          | 保存螺杆泵汇总数据         |                                                      |
| 18       | PRD_SAVE_PCP_RPMDAILYRECAL     | 保存螺杆泵重新汇总数据     |                                                      |
| 19       | PRD_SAVE\_RPCALARMINFO         | 保存抽油机报警数据         |                                                      |
| 20       | PRD_SAVE\_PCPALARMINFO         | 保存螺杆泵报警数据         |                                                      |
| 21       | PRD_SAVE_ALARMCOLOR            | 保存报警等级颜色           |                                                      |
| 22       | PRD_SAVE_DEVICEOPERATIONLOG    | 保存设备操作日志           |                                                      |
| 23       | PRD_SAVE_RESOURCEMONITORING    | 保存资源监测数据           |                                                      |
| 24       | PRD_SAVE_SYSTEMLOG             | 保存系统日志数据           |                                                      |
| 25       | PRD_INIT_DEVICE_DAILY          | 初始化汇总数据             |                                                      |

# 四、触发器

表4-1 触发器概览

| **序号** | **名称**                       | **描述**                               |
|----------|--------------------------------|----------------------------------------|
| 1        | TRG_B_ACQ_GROUP2UNIT_CONF_I    | 采控单元和采控组关系表插入数据前触发   |
| 2        | TRG_B_ACQ_GROUP_CONF_I         | 采控组表插入数据前触发                 |
| 3        | TRG_B_ACQ_ITEM2GROUP_CONF_I    | 采控组和采控项项关系表插入数据前触发   |
| 4        | TRG_B_ACQ_UNIT_CONF_I          | 采控单元表插入数据前触发               |
| 5        | TRG_B_ALARM_ITEM2UNIT_CONF_I   | 报警单元和报警项关系表插入数据前触发   |
| 6        | TRG_B_ALARM_UNIT_CONF_I        | 报警单元表插入数据前触发               |
| 7        | TRG_B_DISPLAY_ITEM2UNIT_CONF_I | 显示单元和显示项关系表插入数据前触发   |
| 8        | TRG_B_DISPLAY_UNIT_CONF_I      | 显示单元表插入数据前触发               |
| 9        | TRG_B_CODE_I                   | 代码表插入数据前触发                   |
| 10       | TRG_B_DATAMAPPING_I            | 字段映射表插入数据前触发               |
| 11       | TRG_B_DEVICEOPERATIONLOG_I     | 设备操作日志表插入数据前触发           |
| 12       | BEF_HIBERNATE_SEQUENCE_INSERT  | 数据字典项数据表插入数据前触发         |
| 13       | TRG_B_MODULE_I                 | 模块表插入数据前触发                   |
| 14       | TRG_B_MODULE2ROLE_I            | 模块和角色关系表插入数据前触发         |
| 15       | TRG_B_ORG_I_U                  | 组织表插入、修改数据前触发             |
| 16       | TRG_B_PCPACQDATA_HIST_I        | 螺杆泵历史数据表插入数据前触发         |
| 17       | TRG_B_PCPACQDATA_LATEST_I      | 螺杆泵实时数据表插入数据前触发         |
| 18       | TRG_B_PCPACQRAWDATA_I          | 螺杆泵原始采集数据表插入数据前触发     |
| 19       | TRG_B_PCPALARMINFO_HIST_I      | 螺杆泵报警历史数据表插入数据前触发触发 |
| 20       | TRG_B_PCPALARMINFO_L_I         | 螺杆泵报警实时数据表插入数据前触发触发 |
| 21       | TRG_B_PCPDAILY_I               | 螺杆泵汇总表插入数据后触发             |
| 22       | TRG_A_PCPDEVICE_I              | 螺杆泵信息表插入数据前触发             |
| 23       | TRG_B_PCPDEVICE_I              | 螺杆泵信息表插入数据后触发             |
| 24       | TRG_B_PCPDEVICEGRAPHSET_I      | 螺杆泵图形设置表插入数据前触发         |
| 25       | TRG_B_PROTOCOLALARMINSTANCE_I  | 报警实例表插入数据前触发               |
| 26       | TRG_B_PROTOCOLDISPLAYINST_I    | 显示实例表插入数据前触发               |
| 27       | TRG_B_PROTOCOLINSTANCE_I       | 采控实例表插入数据前触发               |
| 28       | TRG_B_PROTOCOLSMSINSTANCE_I    | 短信实例表插入数据前触发               |
| 29       | TRG_B_PUMPINGMODEL_I           | 抽油机型号表插入数据前触发             |
| 30       | TRG_B_RESOURCEMONITORING_I     | 资源监测表插入数据前触发               |
| 31       | TRG_B_ROLE_I                   | 角色表插入数据前触发                   |
| 32       | TRG_B_RPCACQDATA_HIST_I        | 抽油机历史数据表插入数据前触发         |
| 33       | TRG_B_RPCACQDATA_LATEST_I      | 抽油机实时数据表插入数据前触发         |
| 34       | TRG_B_RPCACQRAWDATA_I          | 抽油机原始采集数据表插入数据前触发     |
| 35       | TRG_B_RPCALARMINFO_HIST_I      | 抽油机报警历史数据表插入数据前触发触发 |
| 36       | TRG_B_RPCALARMINFO_LATEST_I    | 抽油机报警实时数据表插入数据前触发触发 |
| 37       | TRG_B_RPCDAILY_I               | 抽油机汇总表插入数据前触发             |
| 38       | TRG_A_RPCDEVICE_I              | 抽油机信息表插入数据后触发             |
| 39       | TRG_B_RPCDEVICE_I              | 抽油机信息表插入数据前触发             |
| 40       | TRG_B_RPCDEVICEGRAPHICSET_I    | 抽油机图形设置表插入数据前触发         |
| 41       | TRG_B_RPC_WORKTYPE_I_U         | 工况表插入数据后触发                   |
| 42       | TRG_B_SMSDEVICE_I              | 短信设备信息表插入数据后触发           |
| 43       | TRG_B_SYSTEMLOG_I              | 系统日志表插入数据前触发               |
| 44       | TRG_B_USER_I                   | 用户表插入数据前触发                   |
