**![](media/5252bf75494a0c7053f290f63b8bb70d.png)**

**数据库手册**

**北京科斯奇石油科技有限公司 制作**

**目 录**

[一、表](#一表)

[1.1 概览](#11-概览)

[1.2 详述](#12-详述)

[1.2.1 tbl_devicetypeinfo](#121-tbl_devicetypeinfo)

[1.2.2 tbl_protocol](#122-tbl_protocol)

[1.2.3 tbl_org](#123-tbl_org)

[1.2.4 tbl_role](#124-tbl_role)

[1.2.5 tbl_devicetype2 role](#125-tbl_devicetype2-role)

[1.2.6 tbl_module](#126-tbl_module)

[1.2.7 tbl_module2role](#127-tbl_module2role)

[1.2.8 tbl_language2role](#128-tbl_language2role)

[1.2.9 tbl_user](#129-tbl_user)

[1.2.10 tbl_dist_name](#1210-tbl_dist_name)

[1.2.11 tbl_dist_item](#1211-tbl_dist_item)

[1.2.12 tbl_code](#1212-tbl_code)

[1.2.13 tbl_datamapping](#1213-tbl_datamapping)

[1.2.14 tbl_runstatusconfig](#1214-tbl_runstatusconfig)

[1.2.15 tbl_acq_unit_conf](#1215-tbl_acq_unit_conf)

[1.2.16 tbl_acq_group_conf](#1216-tbl_acq_group_conf)

[1.2.17 tbl_acq_item2group_conf](#1217-tbl_acq_item2group_conf)

[1.2.18 tbl_acq_group2unit_conf](#1218-tbl_acq_group2unit_conf)

[1.2.19 tbl_alarm_unit_conf](#1219-tbl_alarm_unit_conf)

[1.2.20 tbl_alarm_item2unit_conf](#1220-tbl_alarm_item2unit_conf)

[1.2.21 tbl_display_unit_conf](#1221-tbl_display_unit_conf)

[1.2.22 tbl_display_item2unit_conf](#1222-tbl_display_item2unit_conf)

[1.2.23 tbl_report_unit_conf](#1223-tbl_report_unit_conf)

[1.2.24 tbl_report_items2unit_conf](#1224-tbl_report_items2unit_conf)

[1.2.25 tbl_protocolinstance](#1225-tbl_protocolinstance)

[1.2.26 tbl_protocolalarminstance](#1226-tbl_protocolalarminstance)

[1.2.27 tbl_protocoldisplayinstance](#1227-tbl_protocoldisplayinstance)

[1.2.28 tbl_protocolreportinstance](#1228-tbl_protocolreportinstance)

[1.2.29 tbl_protocolsmsinstance](#1229-tbl_protocolsmsinstance)

[1.2.30 tbl_device](#1230-tbl_device)

[1.2.31 tbl_smsdevice](#1231-tbl_smsdevice)

[1.2.32 tbl_deviceaddinfo](#1232-tbl_deviceaddinfo)

[1.2.33 tbl_auxiliarydevice](#1233-tbl_auxiliarydevice)

[1.2.34 tbl_auxiliarydeviceaddinfo](#1234-tbl_auxiliarydeviceaddinfo)

[1.2.35 tbl_auxiliary2master](#1235-tbl_auxiliary2master)

[1.2.36 tbl_devicegraphicset](#1236-tbl_devicegraphicset)

[1.2.37 tbl_acqdata_latest](#1237-tbl_acqdata_latest)

[1.2.38 tbl_acqdata_hist](#1238-tbl_acqdata_hist)

[1.2.39 tbl_acqdata_vacute](#1239-tbl_acqdata_vacute)

[1.2.40 tbl_acqrawdata](#1240-tbl_acqrawdata)

[1.2.41 tbl_alarminfo_latest](#1241-tbl_alarminfo_latest)

[1.2.42 tbl_alarminfo_hist](#1242-tbl_alarminfo_hist)

[1.2.43 tbl_dailytotalcalculate_latest](#1243-tbl_dailytotalcalculate_latest)

[1.2.44 tbl_dailytotalcalculate_hist](#1244-tbl_dailytotalcalculate_hist)

[1.2.45 tbl_dailycalculationdata](#1245-tbl_dailycalculationdata)

[1.2.46 tbl_timingcalculationdata](#1246-tbl_timingcalculationdata)

[1.2.47 tbl_realtimetotalcalculationdata](#1247-tbl_realtimetotalcalculationdata)

[1.2.48 tbl_srpacqdata_latest](#1248-tbl_srpacqdata_latest)

[1.2.49 tbl_srpacqdata_hist](#1249-tbl_srpacqdata_hist)

[1.2.50 tbl_srpacqdata_vacute](#1250-tbl_srpacqdata_vacute)

[1.2.51 tbl_srpdailycalculationdata](#1251-tbl_srpdailycalculationdata)

[1.2.52 tbl_srptimingcalculationdata](#1252-tbl_srptimingcalculationdata)

[1.2.53 tbl_pcpacqdata_latest](#1253-tbl_pcpacqdata_latest)

[1.2.54 tbl_pcpacqdata_hist](#1254-tbl_pcpacqdata_hist)

[1.2.55 tbl_pcpdailycalculationdata](#1255-tbl_pcpdailycalculationdata)

[1.2.56 tbl_pcptimingcalculationdata](#1256-tbl_pcptimingcalculationdata)

[1.2.57 tbl_deviceoperationlog](#1257-tbl_deviceoperationlog)

[1.2.58 tbl_systemlog](#1258-tbl_systemlog)

[1.2.59 tbl_resourcemonitoring](#1259-tbl_resourcemonitoring)

[1.2.60 tbl_dbmonitoring](#1260-tbl_dbmonitoring)

[1.2.61 tbl_videokey](#1261-tbl_videokey)

[二、视图](#二视图)

[2.1 概览](#21-概览)

[2.2 详述](#22-详述)

[2.2.1 viw_org](#221-viw_org)

[2.2.2 viw_devicetypeinfo](#222-viw_devicetypeinfo)

[2.2.3 viw_role](#223-viw_role)

[2.2.4 viw_device](#224-viw_device)

[2.2.5 viw_smsdevice](#225-viw_smsdevice)

[2.2.6 viw_acqrawdata](#226-viw_acqrawdata)

[2.2.7 viw_alarminfo_latest](#227-viw_alarminfo_latest)

[2.2.8 viw_alarminfo_hist](#228-viw_alarminfo_hist)

[2.2.9 viw_timingcalculationdata](#229-viw_timingcalculationdata)

[2.2.10 viw_dailycalculationdata](#2210-viw_dailycalculationdata)

[2.2.11 viw_srpacqdata_hist](#2211-viw_srpacqdata_hist)

[2.2.12 viw_srptimingcalculationdata](#2212-viw_srptimingcalculationdata)

[2.2.13 viw_srpdailycalculationdata](#2213-viw_srpdailycalculationdata)

[2.2.14 viw_srp_calculatemain](#2214-viw_srp_calculatemain)

[2.2.15 viw_pcpacqdata_hist](#2215-viw_pcpacqdata_hist)

[2.2.16 viw_pcptimingcalculationdata](#2216-viw_pcptimingcalculationdata)

[2.2.17 viw_pcpdailycalculationdata](#2217-viw_pcpdailycalculationdata)

[2.2.18 viw_pcp_calculatemain](#2218-viw_pcp_calculatemain)

[2.2.19 viw_deviceoperationlog](#2219-viw_deviceoperationlog)

[2.2.20 viw_systemlog](#2220-viw_systemlog)

[三、存储过程](#三存储过程)

[四、触发器](#四触发器)

# 一、表

## 1.1 概览

| **序号** | **名称**                         | **描述**               |
|----------|----------------------------------|------------------------|
| 1        | tbl_devicetypeinfo               | 设备类型表             |
| 2        | tbl_protocol                     | 协议表                 |
| 3        | tbl_org                          | 组织数据表             |
| 4        | tbl_role                         | 角色数据表             |
| 5        | tbl_devicetype2 role             | 设备类型角色关系表     |
| 6        | tbl_module                       | 模块数据表             |
| 7        | tbl_module2role                  | 模块角色关系表         |
| 8        | tbl_language2role                | 角色语言权限           |
| 9        | tbl_user                         | 用户数据表             |
| 10       | tbl_dist_name                    | 字典名称表             |
| 11       | tbl_dist_item                    | 字典数据项表           |
| 12       | tbl_code                         | 代码表                 |
| 13       | tbl_datamapping                  | 字段映射表             |
| 14       | tbl_runstatusconfig              | 运行状态配置表         |
| 15       | tbl_acq_unit_conf                | 采控单元表             |
| 16       | tbl_acq_group_conf               | 采控组表               |
| 17       | tbl_acq_item2group_conf          | 采控组和采控项关系表   |
| 18       | tbl_acq_group2unit_conf          | 采控单元和采控组关系表 |
| 19       | tbl_alarm_unit_conf              | 报警单元表             |
| 20       | tbl_alarm_item2unit_conf         | 报警单元和报警项关系表 |
| 21       | tbl_display_unit_conf            | 显示单元表             |
| 22       | tbl_display_items2unit_conf      | 显示单元和显示项关系表 |
| 23       | tbl_report_unit_conf             | 报表单元表             |
| 24       | tbl_report_items2unit_conf       | 报表单元和报表项关系表 |
| 25       | tbl_protocolinstance             | 采控实例表             |
| 26       | tbl_protocolalarminstance        | 报警实例表             |
| 27       | tbl_protocoldisplayinstance      | 显示实例表             |
| 28       | tbl_protocolreportinstance       | 报表实例表             |
| 29       | tbl_protocolsmsinstance          | 短信实例表             |
| 30       | tbl_device                       | 设备信息表             |
| 31       | tbl_smsdevice                    | 短信设备信息表         |
| 32       | tbl_deviceaddinfo                | 设备附加信息表         |
| 33       | tbl_auxiliarydevice              | 辅件设备信息表         |
| 34       | tbl_auxiliarydeviceaddinfo       | 辅件设备附加信息表     |
| 35       | tbl_auxiliary2master             | 主设备与辅件设备关系表 |
| 36       | tbl_devicegraphicset             | 抽油机图形设置表       |
| 37       | tbl_acqdata_latest               | 实时数据表             |
| 38       | tbl_acqdata_hist                 | 历史数据表             |
| 39       | tbl_acqdata_vacuate              | 历史数据表抽稀表       |
| 40       | tbl_acqrawdata                   | 原始采集数据表         |
| 41       | tbl_alarminfo_latest             | 报警实时数据表         |
| 42       | tbl_alarminfo_hist               | 报警历史数据表         |
| 43       | tbl_dailytotalcalculate_latest   | 日累计计算实时数据表   |
| 44       | tbl_dailytotalcalculate_hist     | 日累计计算历史数据表   |
| 45       | tbl_dailycalculationdata         | 日汇总数据表           |
| 46       | tbl_timingcalculationdata        | 定时汇总数据表         |
| 47       | tbl_realtimetotalcalculationdata | 实时汇总数据表         |
| 48       | tbl_srpacqdata_latest            | 功图计算实时数据表     |
| 49       | tbl_srpacqdata_hist              | 功图计算历史数据表     |
| 50       | tbl_srpacqdata_vacuate           | 功图计算历史数据抽稀表 |
| 51       | tbl_srpdailycalculationdata      | 功图计算日汇总数据表   |
| 52       | tbl_srptimingcalculationdata     | 功图计算定时汇总数据表 |
| 53       | tbl_pcpacqdata_latest            | 转速计产实时数据表     |
| 54       | tbl_pcpacqdata_hist              | 转速计产历史数据表     |
| 55       | tbl_pcpacqdata_vacuate           | 转速计产历史数据抽稀表 |
| 56       | tbl_pcpdailycalculationdata      | 转速计产日汇总数据表   |
| 57       | tbl_pcptimingcalculationdata     | 转速计产定时汇总数据表 |
| 58       | tbl_deviceoperationlog           | 设备操作日志表         |
| 59       | tbl_systemlog                    | 系统日志表             |
| 60       | tbl_resourcemonitoring           | 资源监测数据表         |
| 61       | tbl_dbmonitoring                 | 数据库监测数据表       |
| 62       | tbl_videokey                     | 视频秘钥表             |

## 1.2 详述

### 1.2.1 tbl_devicetypeinfo

设备类型表

| **序号** | **代码**   | **名称**      | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|------------|---------------|---------------|----------|----------|--------|----------|
| 1        | ID         | 编号          | NUMBER(10)    |          | N        | 主键   |          |
| 2        | PARENTID   | 父级编号      | NUMBER(10)    |          | N        |        |          |
| 3        | NAME_ZH_CN | 类型名称-中文 | VARCHAR2(100) |          | Y        |        |          |
| 4        | NAME_EN    | 类型名称-英文 | VARCHAR2(100) |          | Y        |        |          |
| 5        | NAME_RU    | 类型名称-俄文 | VARCHAR2(100) |          | Y        |        |          |
| 6        | SORTNUM    | 排序编号      | NUMBER(10)    |          | Y        |        |          |

### 1.2.2 tbl_protocol

协议表

| **序号** | **代码**   | **名称** | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|------------|----------|--------------|----------|----------|--------|----------|
| 1        | ID         | 编号     | NUMBER(10)   |          | N        | 主键   |          |
| 2        | NAME       | 协议名称 | VARCHAR2(50) |          | Y        |        |          |
| 3        | CODE       | 协议编码 | VARCHAR2(50) |          | Y        |        |          |
| 4        | ITEMS      | 协议内容 | CLOB         |          | Y        |        |          |
| 5        | DEVICETYPE | 设备类型 | NUMBER(10)   |          | Y        |        |          |
| 6        | SORT       | 排序编号 | NUMBER(10)   |          | Y        |        |          |

### 1.2.3 tbl_org

组织数据表

| **序号** | **代码**       | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|----------------|--------------|----------------|----------|----------|--------|----------|
| 1        | ORG_ID         | 单位序号     | NUMBER(10)     |          | N        | 主键   |          |
| 2        | ORG_CODE       | 单位编码     | VARCHAR2(20)   |          | Y        |        |          |
| 3        | ORG_NAME_ZH_CN | 单位中文名称 | VARCHAR2(100)  |          | N        |        |          |
| 4        | ORG_NAME_EN    | 单位英文名称 | VARCHAR2(100)  |          | N        |        |          |
| 5        | ORG_NAME_RU    | 单位俄文名称 | VARCHAR2(100)  |          | N        |        |          |
| 6        | ORG_MEMO       | 单位说明     | VARCHAR2(4000) |          | Y        |        |          |
| 7        | ORG_PARENT     | 父级单位编号 | NUMBER(10)     |          | N        |        |          |
| 8        | ORG_SEQ        | 单位排序     | NUMBER(10)     |          | Y        |        |          |

### 1.2.4 tbl_role

角色数据表

| **序号** | **代码**          | **名称**         | **类型**       | **单位** | **为空** | **键** | **备注**   |
|----------|-------------------|------------------|----------------|----------|----------|--------|------------|
| 1        | ROLE_ID           | 角色序号         | NUMBER(10)     |          | N        | 主键   |            |
| 2        | ROLE_NAME         | 角色名称         | VARCHAR2(40)   |          | N        |        |            |
| 3        | ROLE_LEVEL        | 角色级别         | NUMBER(3)      |          | Y        |        |            |
| 4        | SHOWLEVEL         | 数据显示级别     | NUMBER(10)     |          | Y        |        |            |
| 5        | ROLE_VIDEOKEYEDIT | 视频账号编辑权限 | NUMBER(10)     |          | Y        |        | 0-无，1-是 |
|          | ROLE_LANGUAGEEDIT | 语言编辑权限     | NUMBER(10)     |          | Y        |        | 预留       |
| 6        | REMARK            | 角色描述         | VARCHAR2(2000) |          | Y        |        |            |

### 1.2.5 tbl_devicetype2 role

设备类型角色关系表

| **序号** | **代码**        | **名称** | **类型**    | **单位** | **为空** | **键** | **备注**                     |
|----------|-----------------|----------|-------------|----------|----------|--------|------------------------------|
| 1        | RD_ID           | 序号     | NUMBER(10)  |          | N        | 主键   |                              |
| 2        | RD_DEVICETYPEID | 类型编号 | NUMBER(10)  |          | N        |        | 对应tbl_devicetype表中id字段 |
| 3        | RD_ROLEID       | 角色编号 | NUMBER(10)  |          | N        |        | 对应tbl_role表中role_id字段  |
| 4        | RD_MATRIX       | 权限矩阵 | VARCHAR2(8) |          | N        |        |                              |

### 1.2.6 tbl_module

模块数据表

| **序号** | **代码**          | **名称**     | **类型**      | **单位** | **为空** | **键** | **备注**               |
|----------|-------------------|--------------|---------------|----------|----------|--------|------------------------|
| 1        | MD_ID             | 模块序号     | NUMBER(10)    |          | N        | 主键   |                        |
| 2        | MD_PARENTID       | 父级模块序号 | NUMBER(10)    |          | N        |        |                        |
| 3        | MD_NAME_ZH_CN     | 模块中文名称 | VARCHAR2(100) |          | N        |        |                        |
| 4        | MD_NAME_EN        | 模块英文名称 | VARCHAR2(100) |          | N        |        |                        |
| 5        | MD_NAME_RU        | 模块名称     | VARCHAR2(100) |          | N        |        |                        |
| 6        | MD_SHOWNAME_ZH_CN | 模块中文简介 | VARCHAR2(100) |          | Y        |        |                        |
| 7        | MD_SHOWNAME_EN    | 模块英文简介 | VARCHAR2(100) |          | Y        |        |                        |
| 8        | MD_SHOWNAME_RU    | 模块俄文简介 | VARCHAR2(100) |          | Y        |        |                        |
| 9        | MD_URL            | 模块URL      | VARCHAR2(200) |          | Y        |        |                        |
| 10       | MD_CODE           | 模块编码     | VARCHAR2(200) |          | Y        |        |                        |
| 11       | MD_SEQ            | 模块排序     | NUMBER(20)    |          | Y        |        |                        |
| 12       | MD_LEVEL          | 模块级别     | NUMBER(10)    |          | Y        |        |                        |
| 13       | MD_FLAG           | 模块标志     | NUMBER(10)    |          | Y        |        |                        |
| 14       | MD_ICON           | 模块图标     | VARCHAR2(100) |          | Y        |        |                        |
| 15       | MD_TYPE           | 模块类型     | NUMBER(1)     |          | Y        |        | 0-启用模块，2-备用模块 |
| 16       | MD_CONTROL        | 模块控制器   | VARCHAR2(100) |          | Y        |        |                        |

### 1.2.7 tbl_module2role

模块角色关系表

| **序号** | **代码**    | **名称** | **类型**    | **单位** | **为空** | **键** | **备注**                    |
|----------|-------------|----------|-------------|----------|----------|--------|-----------------------------|
| 1        | RM_ID       | 序号     | NUMBER(10)  |          | N        | 主键   |                             |
| 2        | RM_ROLEID   | 角色编号 | NUMBER(10)  |          | N        |        | 对应tbl_role表中role_id字段 |
| 3        | RM_MODULEID | 模块序号 | NUMBER(10)  |          | N        |        | 对应module表中md_id字段     |
| 4        | RM_MATRIX   | 权限矩阵 | VARCHAR2(8) |          | N        |        |                             |

### 1.2.8 tbl_language2role

角色语言权限

| **序号** | **代码** | **名称** | **类型**    | **单位** | **为空** | **键** | **备注**                    |
|----------|----------|----------|-------------|----------|----------|--------|-----------------------------|
| 1        | ID       | 序号     | NUMBER(10)  |          | N        | 主键   |                             |
| 2        | LANGUAGE | 语言     | NUMBER(1)   |          | N        |        | 1-zh_CN 2-en 3-ru           |
| 3        | ROLEID   | 角色编号 | NUMBER(10)  |          | N        |        | 对应tbl_role表中role_id字段 |
| 4        | MATRIX   | 权限矩阵 | VARCHAR2(8) |          | N        |        |                             |

### 1.2.9 tbl_user

用户数据表

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
| 11       | USER_ENABLE      | 使能或者失效     | NUMBER(1)    |          | Y        |        | 0-失效 1-使能               |
| 12       | USER_RECEIVESMS  | 是否接收报警短信 | NUMBER(10)   |          | Y        |        | 0-否，1-是                  |
| 13       | USER_RECEIVEMAIL | 是否接收报警邮件 | NUMBER(10)   |          | Y        |        | 0-否，1-是                  |
| 14       | USER_LANGUAGE    | 用户语言         | NUMBER(1)    |          | Y        |        | 1-中文，2-英文，3-俄文      |

### 1.2.10 tbl_dist_name

字典名称表

| **序号** | **代码**   | **名称** | **类型**     | **单位** | **为空** | **键** | **备注**       |
|----------|------------|----------|--------------|----------|----------|--------|----------------|
| 1        | SYSDATAID  | 字典编码 | VARCHAR2(32) |          | N        | 主键   |                |
| 2        | TENANTID   | 组织编号 | VARCHAR2(50) |          | Y        |        |                |
| 3        | NAME_ZH_CN | 中文名称 | VARCHAR2(50) |          | Y        |        |                |
| 4        | NAME_EN    | 英文名称 | VARCHAR2(50) |          | Y        |        |                |
| 5        | NAME_RU    | 俄文名称 | VARCHAR2(50) |          | Y        |        |                |
| 6        | CODE       | 编码     | VARCHAR2(50) |          | Y        |        |                |
| 7        | SORTS      | 排序     | NUMBER       |          | Y        |        |                |
| 8        | STATUS     | 显示状态 | NUMBER       |          | Y        |        | 0-显示，1-隐藏 |
| 9        | CREATOR    | 创建人   | VARCHAR2(50) |          | Y        |        |                |
| 10       | UPDATEUSER | 修改人   | VARCHAR2(50) |          | Y        |        |                |
| 11       | UPDATETIME | 创建时间 | DATE         |          | Y        |        | SYSDATE        |
| 12       | CREATEDATE | 修改时间 | DATE         |          | N        |        | SYSDATE        |

### 1.2.11 tbl_dist_item

字典数据项表

| **序号** | **代码**   | **名称**   | **类型**      | **单位** | **为空** | **键** | **备注**       |
|----------|------------|------------|---------------|----------|----------|--------|----------------|
| 1        | DATAITEMID | 数据项编码 | VARCHAR2(32)  |          | N        | 主键   |                |
| 2        | TENANTID   | 组织编号   | VARCHAR2(50)  |          | Y        |        |                |
| 3        | SYSDATAID  | 字典编码   | VARCHAR2(50)  |          | Y        |        |                |
| 4        | NAME_ZH_CN | 中文名称   | VARCHAR2(50)  |          | Y        |        |                |
| 5        | NAME_EN    | 英文名称   | VARCHAR2(50)  |          | Y        |        |                |
| 6        | NAME_RU    | 俄文名称   | VARCHAR2(50)  |          | Y        |        |                |
| 7        | CODE       | 编码       | VARCHAR2(200) |          | Y        |        |                |
| 8        | DATAVALUE  | 数据项值   | VARCHAR2(200) |          | Y        |        |                |
| 9        | SORTS      | 排序       | NUMBER        |          | Y        |        |                |
| 10       | STATUS     | 显示状态   | NUMBER        |          | Y        |        | 0-显示，1-隐藏 |
| 11       | CREATOR    | 创建人     | VARCHAR2(50)  |          | Y        |        |                |
| 12       | UPDATEUSER | 修改人     | VARCHAR2(50)  |          | Y        |        |                |
| 13       | UPDATETIME | 创建时间   | DATE          |          | Y        |        | SYSDATE        |
| 14       | CREATEDATE | 修改时间   | DATE          |          | Y        |        | SYSDATE        |

### 1.2.12 tbl_code

代码表

| **序号** | **代码**  | **名称**   | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|-----------|------------|---------------|----------|----------|--------|----------|
| 1        | ID        | 记录编号   | NUMBER(10)    |          | N        | 主键   |          |
| 2        | TABLECODE | 数据表代码 | VARCHAR2(200) |          | Y        |        |          |
| 3        | ITEMCODE  | 数据项代码 | VARCHAR2(200) |          | Y        |        |          |
| 4        | ITEMVALUE | 代码       | VARCHAR2(20)  |          | Y        |        |          |
| 5        | ITEMNAME  | 名称       | VARCHAR2(200) |          | Y        |        |          |
| 6        | STATE     | 状态       | NUMBER(10)    |          | Y        |        |          |
| 7        | REMARK    | 备注       | VARCHAR2(200) |          | Y        |        |          |

### 1.2.13 tbl_datamapping

字段映射表

| **序号** | **代码**        | **名称**       | **类型**     | **单位** | **为空** | **键** | **备注**                  |
|----------|-----------------|----------------|--------------|----------|----------|--------|---------------------------|
| 1        | ID              | 记录编号       | NUMBER(10)   |          | N        | 主键   |                           |
| 2        | NAME            | 协议采控项名称 | VARCHAR2(50) |          | Y        |        |                           |
| 3        | MAPPINGCOLUMN   | 映射字段       | VARCHAR2(30) |          | Y        |        |                           |
| 4        | CALCOLUMN       | 计算字段       | VARCHAR2(30) |          | Y        |        | 设置计算字段，供系统识别  |
| 5        | CALCULATEENABLE | 计算使能       | NUMBER(1)    |          | Y        |        | 1-使能 0-失效             |
| 6        | PROTOCOLTYPE    | 协议类型       | NUMBER(1)    |          | Y        |        | 0-抽油机协议 1-螺杆泵协议 |
| 7        | REPETITIONTIMES | 重复次数       | NUMBER(2)    |          | Y        |        |                           |
| 8        | MAPPINGMODE     | 映射模式       | NUMBER(1)    |          | Y        |        | 0-以地址为准 1-以名称为准 |

### 1.2.14 tbl_runstatusconfig

表1-12 运行状态配置表

| **序号** | **代码**          | **名称**   | **类型**     | **单位** | **为空** | **键** | **备注**                   |
|----------|-------------------|------------|--------------|----------|----------|--------|----------------------------|
| 1        | ID                | 记录编号   | NUMBER(10)   |          | N        | 主键   |                            |
| 2        | PROTOCOL          | 协议编码   | VARCHAR2(50) |          | Y        |        |                            |
| 3        | ITEMNAME          | 项名称     | VARCHAR2(50) |          | Y        |        |                            |
| 4        | ITEMMAPPINGCOLUMN | 项映射字段 | VARCHAR2(50) |          | Y        |        |                            |
| 5        | RUNVALUE          | 运行值     | VARCHAR2(50) |          | Y        |        |                            |
| 6        | STOPVALUE         | 停止值     | VARCHAR2(50) |          | Y        |        |                            |
| 7        | PROTOCOLTYPE      | 协议类型   | NUMBER(1)    |          | Y        |        |                            |
| 8        | RESOLUTIONMODE    | 解析模式   | NUMBER(1)    |          | Y        |        | 0-开关量 1-枚举量 2-数据量 |
| 9        | RUNCONDITION      | 运行条件   | VARCHAR2(50) |          | Y        |        |                            |
| 10       | STOPCONDITION     | 停止条件   | VARCHAR2(50) |          | Y        |        |                            |

### 1.2.15 tbl_acq_unit_conf

采控单元名称表

| **序号** | **代码**  | **名称** | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|-----------|----------|----------------|----------|----------|--------|----------|
| 1        | ID        | 记录编号 | NUMBER(10)     |          | N        | 主键   |          |
| 2        | UNIT_CODE | 单元代码 | VARCHAR2(50)   |          | N        |        |          |
| 3        | UNIT_NAME | 单元名称 | VARCHAR2(50)   |          | Y        |        |          |
| 4        | PROTOCOL  | 协议     | VARCHAR2(50)   |          | Y        |        |          |
| 5        | SORT      | 排序     | NUMBER(10)     |          | Y        |        |          |
| 6        | REMARK    | 单元描述 | VARCHAR2(2000) |          | Y        |        |          |

### 1.2.16 tbl_acq_group_conf

采控组名称表

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

### 1.2.17 tbl_acq_item2group_conf

采控组和采集项关系表

| **序号** | **代码**                | **名称**       | **类型**      | **单位** | **为空** | **键** | **备注**              |
|----------|-------------------------|----------------|---------------|----------|----------|--------|-----------------------|
| 1        | ID                      | 记录编号       | NUMBER(10)    |          | N        | 主键   |                       |
| 2        | GROUPID                 | 采控组编号     | NUMBER(10)    |          | N        |        |                       |
| 3        | ITEMID                  | 采控项编号     | NUMBER(10)    |          | Y        |        |                       |
| 4        | ITEMCODE                | 采控项代码     | VARCHAR2(100) |          | Y        |        |                       |
| 5        | ITEMNAME                | 采控项名称     | VARCHAR2(100) |          | Y        |        |                       |
| 6        | BITINDEX                | 位索引         | NUMBER(10)    |          | Y        |        | 位数组中的位索引      |
| 7        | DAILYTOTALCALCULATE     | 日累计计算     | NUMBER(1)     |          | Y        |        | 1-计算 0-不计算 默认0 |
| 8        | DAILYTOTALCALCULATENAME | 日累计字段名称 | VARCHAR2(100) |          | Y        |        |                       |
| 9        | MATRIX                  | 阵列           | VARCHAR2(8)   |          | Y        |        |                       |

### 1.2.18 tbl_acq_group2unit_conf

采控单元和采集采控组关系表

| **序号** | **代码** | **名称**     | **类型**    | **单位** | **为空** | **键** | **备注**                         |
|----------|----------|--------------|-------------|----------|----------|--------|----------------------------------|
| 1        | ID       | 记录编号     | NUMBER(10)  |          | N        | 主键   |                                  |
| 2        | UNITID   | 采控单元编号 | NUMBER(10)  |          | N        |        | 对应tbl_acq_unit_conf表中id字段  |
| 3        | GROUPID  | 采控组编号   | NUMBER(10)  |          | N        |        | 对应tbl_acq_group_conf表中id字段 |
| 4        | MATRIX   | 阵列         | VARCHAR2(8) |          | N        |        |                                  |

### 1.2.19 tbl_alarm_unit_conf

报警单元名称表

| **序号** | **代码**  | **名称** | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|-----------|----------|----------------|----------|----------|--------|----------|
| 1        | ID        | 记录编号 | NUMBER(10)     |          | N        | 主键   |          |
| 2        | UNIT_CODE | 单元代码 | VARCHAR2(50)   |          | N        |        |          |
| 3        | UNIT_NAME | 单元名称 | VARCHAR2(50)   |          | Y        |        |          |
| 4        | PROTOCOL  | 协议     | VARCHAR2(50)   |          | Y        |        |          |
| 5        | SORT      | 排序     | NUMBER(10)     |          | Y        |        |          |
| 6        | REMARK    | 单元描述 | VARCHAR2(2000) |          | Y        |        |          |

### 1.2.20 tbl_alarm_item2unit_conf

报警单元和报警项关系表

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

### 1.2.21 tbl_display_unit_conf

显示单元名称表

| **序号** | **代码**      | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注**                   |
|----------|---------------|--------------|----------------|----------|----------|--------|----------------------------|
| 1        | ID            | 记录编号     | NUMBER(10)     |          | N        | 主键   |                            |
| 2        | UNIT_CODE     | 单元代码     | VARCHAR2(50)   |          | N        |        |                            |
| 3        | UNIT_NAME     | 单元名称     | VARCHAR2(50)   |          | Y        |        |                            |
| 4        | PROTOCOL      | 协议         | VARCHAR2(50)   |          | Y        |        |                            |
| 5        | ACQUNITID     | 采集单元编号 | NUMBER(10)     |          | Y        |        |                            |
| 7        | CALCULATETYPE | 计算类型     | NUMBER(2)      |          | Y        |        | 1-功图计算 2-转速计产 0-无 |
| 8        | SORT          | 排序         | NUMBER(10)     |          | Y        |        |                            |
| 9        | REMARK        | 单元描述     | VARCHAR2(2000) |          | Y        |        |                            |

### 1.2.22 tbl_display_item2unit_conf

显示单元和显示项关系表

| **序号** | **代码**          | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注**                   |
|----------|-------------------|--------------|----------------|----------|----------|--------|----------------------------|
| 1        | ID                | 记录编号     | NUMBER(10)     |          | N        | 主键   |                            |
| 2        | UNITID            | 单元编号     | NUMBER(10)     |          | N        |        |                            |
| 3        | ITEMID            | 项编号       | NUMBER(10)     |          | Y        |        |                            |
| 4        | ITEMNAME          | 项名称       | VARCHAR2(100)  |          | Y        |        |                            |
| 5        | ITEMCODE          | 项代码       | VARCHAR2(100)  |          | Y        |        |                            |
| 6        | REALTIMESORT      | 实时字段顺序 | NUMBER(10)     |          | Y        |        |                            |
| 7        | HISTORYSORT       | 历史字段顺序 | NUMBER(10)     |          |          |        |                            |
| 8        | BITINDEX          | 位索引       | NUMBER(10)     |          | Y        |        | 位数组中的位索引           |
| 9        | SHOWLEVEL         | 显示级别     | NUMBER(10)     |          | Y        |        |                            |
| 10       | REALTIMECURVECONF | 实时曲线配置 | VARCHAR2(4000) |          | Y        |        | 为空不显示曲线             |
| 11       | HISTORYCURVECONF  | 历史曲线配置 | VARCHAR2(4000) |          | Y        |        | 为空不显示曲线             |
| 12       | TYPE              | 项类型       | NUMBER(1)      |          | Y        |        | 0-采集项 1-计算项 2-控制项 |
| 13       | MATRIX            | 阵列         | VARCHAR2(8)    |          | Y        |        |                            |

### 1.2.23 tbl_report_unit_conf

报表单元名称表

| **序号** | **代码**                      | **名称**       | **类型**     | **单位** | **为空** | **键** | **备注**                   |
|----------|-------------------------------|----------------|--------------|----------|----------|--------|----------------------------|
| 1        | ID                            | 记录编号       | NUMBER(10)   |          | N        | 主键   |                            |
| 2        | UNIT_CODE                     | 单元代码       | VARCHAR2(50) |          | N        |        |                            |
| 3        | UNIT_NAME                     | 单元名称       | VARCHAR2(50) |          | Y        |        |                            |
| 4        | SINGLEWELLDAILYREPORTTEMPLATE | 单井班报表模板 | VARCHAR2(50) |          | Y        |        |                            |
|          | SINGLEWELLRANGEREPORTTEMPLATE | 单井日报表模板 | VARCHAR2(50) |          |          |        |                            |
| 5        | PRODUCTIONREPORTTEMPLATE      | 区域日报表模板 | VARCHAR2(50) |          |          |        |                            |
|          | CALCULATETYPE                 | 计算类型       | NUMBER(2)    |          |          |        | 1-功图计算 2-转速计产 0-无 |
| 6        | SORT                          | 排序编号       | NUMBER(10)   |          | Y        |        |                            |

### 1.2.24 tbl_report_items2unit_conf

报表单元和报表项关系表

| **序号** | **代码**        | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注**                                                       |
|----------|-----------------|--------------|----------------|----------|----------|--------|----------------------------------------------------------------|
| 1        | ID              | 记录编号     | NUMBER(10)     |          | N        | 主键   |                                                                |
| 2        | UNITID          | 单元编号     | NUMBER(10)     |          | N        |        |                                                                |
| 3        | ITEMID          | 项编号       | NUMBER(10)     |          | Y        |        |                                                                |
| 4        | ITEMNAME        | 项名称       | VARCHAR2(100)  |          | Y        |        |                                                                |
| 5        | ITEMCODE        | 项代码       | VARCHAR2(100)  |          | Y        |        |                                                                |
| 6        | SORT            | 排序序号     | NUMBER(10)     |          | Y        |        |                                                                |
| 7        | SHOWLEVEL       | 显示级别     | NUMBER(10)     |          | Y        |        |                                                                |
| 8        | SUMSIGN         | 是否求和     | NUMBER(1)      |          |          |        |                                                                |
| 9        | AVERAGESIGN     | 是否求平均   | NUMBER(1)      |          | Y        |        |                                                                |
| 10       | CURVESTATTYPE   | 曲线统计类型 | NUMBER(1)      |          | Y        |        |                                                                |
| 11       | DATATYPE        | 数据类型     | NUMBER(10)     |          | Y        |        |                                                                |
| 12       | REPORTTYPE      | 报表类型     | NUMBER(1)      |          | Y        |        |                                                                |
| 13       | REPORTCURVECONF | 报表曲线配置 | VARCHAR2(4000) |          | Y        |        |                                                                |
| 14       | PREC            | 小数位数     | NUMBER(2)      |          | Y        |        |                                                                |
| 15       | TOTALTYPE       | 统计方式     | NUMBER(2)      |          | Y        |        | 1-最大值 2-最小值 3-平均值 4-最新值 5-最旧值 6-日累计 0-原始值 |
| 16       | DATASOURCE      | 数据来源     | VARCHAR2(8)    |          | Y        |        | 采集、录入、计算                                               |
| 17       | MATRIX          | 阵列         | VARCHAR2(8)    |          | Y        |        |                                                                |

### 1.2.25 tbl_protocolinstance

采控实例表

| **序号** | **代码**                 | **名称**             | **类型**     | **单位** | **为空** | **键** | **备注**  |
|----------|--------------------------|----------------------|--------------|----------|----------|--------|-----------|
| 1        | ID                       | 记录编号             | NUMBER(10)   |          | N        | 主键   |           |
| 2        | NAME                     | 实例名称             | VARCHAR2(50) |          | Y        |        |           |
| 3        | CODE                     | 实例代码             | VARCHAR2(50) |          | Y        |        |           |
| 4        | ACQPROTOCOLTYPE          | 采集实例类型         | VARCHAR2(50) |          | N        |        |           |
| 5        | CTRLPROTOCOLTYPE         | 控制实例类型         | VARCHAR2(50) |          | Y        |        |           |
| 6        | SIGNINPREFIXSUFFIXHEX    | 注册包前后缀十六进制 | NUMBER(1)    |          | Y        |        | 0-否 1-是 |
| 7        | SIGNINPREFIX             | 注册包前缀           | VARCHAR2(50) |          | Y        |        |           |
| 8        | SIGNINSUFFIX             | 注册包后缀           | VARCHAR2(50) |          | Y        |        |           |
| 9        | SIGNINIDHEX              | 注册包ID十六进制     | NUMBER(1)    |          |          |        |           |
| 10       | HEARTBEATPREFIXSUFFIXHEX | 心跳包前后缀十六进制 | NUMBER(1)    |          |          |        |           |
| 11       | HEARTBEATPREFIX          | 心跳包前缀           | VARCHAR2(50) |          | Y        |        |           |
| 12       | HEARTBEATSUFFIX          | 心跳包后缀           | VARCHAR2(50) |          | Y        |        |           |
| 13       | PACKETSENDINTERVAL       | 单包发送间隔         | NUMBER(10)   | ms       | Y        |        |           |
| 14       | UNITID                   | 采控单元编号         | NUMBER(10)   |          | Y        |        |           |
| 15       | SORT                     | 排序编号             | NUMBER(10)   |          | Y        |        |           |

### 1.2.26 tbl_protocolalarminstance

报警实例表

| **序号** | **代码**    | **名称**     | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|-------------|--------------|--------------|----------|----------|--------|----------|
| 1        | ID          | 记录编号     | NUMBER(10)   |          | N        | 主键   |          |
| 2        | NAME        | 实例名称     | VARCHAR2(50) |          | Y        |        |          |
| 3        | CODE        | 实例代码     | VARCHAR2(50) |          | Y        |        |          |
| 4        | ALARMUNITID | 报警单元编号 | NUMBER(10)   |          | N        |        |          |
| 5        | SORT        | 排序编号     | NUMBER(10)   |          | Y        |        |          |

### 1.2.27 tbl_protocoldisplayinstance

显示实例表

| **序号** | **代码**      | **名称**     | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|---------------|--------------|--------------|----------|----------|--------|----------|
| 1        | ID            | 记录编号     | NUMBER(10)   |          | N        | 主键   |          |
| 2        | NAME          | 实例名称     | VARCHAR2(50) |          | Y        |        |          |
| 3        | CODE          | 实例代码     | VARCHAR2(50) |          | Y        |        |          |
| 4        | DISPLAYUNITID | 显示单元编号 | NUMBER(10)   |          | N        |        |          |
| 5        | SORT          | 排序编号     | NUMBER(10)   |          | Y        |        |          |

### 1.2.28 tbl_protocolreportinstance

表1-26 报表实例表

| **序号** | **代码** | **名称**     | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|----------|--------------|--------------|----------|----------|--------|----------|
| 1        | ID       | 记录编号     | NUMBER(10)   |          | N        | 主键   |          |
| 2        | NAME     | 实例名称     | VARCHAR2(50) |          | Y        |        |          |
| 3        | CODE     | 实例代码     | VARCHAR2(50) |          | Y        |        |          |
| 4        | UNITID   | 报表单元编号 | NUMBER(10)   |          | N        |        |          |
| 5        | SORT     | 排序编号     | NUMBER(10)   |          | Y        |        |          |

### 1.2.29 tbl_protocolsmsinstance

表1-27 短信实例表

| **序号** | **代码**         | **名称**     | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|------------------|--------------|--------------|----------|----------|--------|----------|
| 1        | ID               | 记录编号     | NUMBER(10)   |          | N        | 主键   |          |
| 2        | NAME             | 实例名称     | VARCHAR2(50) |          | Y        |        |          |
| 3        | CODE             | 实例代码     | VARCHAR2(50) |          | Y        |        |          |
| 4        | ACQPROTOCOLTYPE  | 采集协议类型 | VARCHAR2(50) |          | N        |        |          |
| 5        | CTRLPROTOCOLTYPE | 控制协议类型 | VARCHAR2(50) |          | Y        |        |          |
| 6        | SORT             | 排序编号     | NUMBER(10)   |          | Y        |        |          |

### 1.2.30 tbl_device

设备信息表

| **序号** | **代码**                 | **名称**         | **类型**       | **单位** | **为空** | **键** | **备注**                   |
|----------|--------------------------|------------------|----------------|----------|----------|--------|----------------------------|
| 1        | ID                       | 记录编号         | NUMBER(10)     |          | N        | 主键   |                            |
| 2        | ORGID                    | 单位编号         | NUMBER(10)     |          | Y        |        |                            |
| 3        | DEVICENAME               | 设备名称         | VARCHAR2(200)  |          | Y        |        |                            |
| 4        | DEVICETYPE               | 设备类型         | NUMBER(1)      |          | Y        |        |                            |
| 5        | APPLICATIONSCENARIOS     | 应用场景         | NUMBER(2)      |          | Y        |        |                            |
| 6        | TCPTYPE                  | 下位机TCP类型    | VARCHAR2(20)   |          | Y        |        | TCP Server或者TCP Client   |
| 7        | SIGNINID                 | 注册包ID         | VARCHAR2(200)  |          | Y        |        |                            |
| 8        | IPPORT                   | 下位机IP端口     | VARCHAR2(200)  |          | Y        |        |                            |
| 9        | SLAVE                    | 设备从地址       | VARCHAR2(200)  |          | Y        |        |                            |
| 10       | PEAKDELAY                | 错峰延时         | NUMBER(10)     | s        | Y        |        |                            |
| 11       | INSTANCECODE             | 采控实例代码     | VARCHAR2(50)   |          | Y        |        |                            |
| 12       | ALARMINSTANCECODE        | 报警实例代码     | VARCHAR2(50)   |          | Y        |        |                            |
| 13       | DISPLAYINSTANCECODE      | 显示实例代码     | VARCHAR2(50)   |          | Y        |        |                            |
| 14       | REPORTINSTANCECODE       | 报表实例代码     | VARCHAR2(50)   |          | Y        |        |                            |
| 15       | VIDEOURL1                | 视频1监控路径    | VARCHAR2(400)  |          | Y        |        |                            |
| 16       | VIDEOURL2                | 视频2监控路径    | VARCHAR2(400)  |          | Y        |        |                            |
| 17       | VIDEOKEYID1              | 视频1秘钥编号    | NUMBER(10)     |          | Y        |        | 关联视频秘钥表             |
| 18       | VIDEOKEYID2              | 视频2秘钥编号    | NUMBER(10)     |          | Y        |        | 关联视频秘钥表             |
| 19       | VIDEOACCESSTOKEN         | 视频访问令牌     | VARCHAR2(400)  |          | Y        |        | 预留多个以;隔开            |
| 20       | PRODUCTIONDATA           | 生产数据         | VARCHAR2(4000) |          | Y        |        | json格式字符串             |
| 21       | PRODUCTIONDATAUPDATETIME | 生产数据更新时间 | DATE           |          | Y        |        |                            |
| 22       | PUMPINGMODELID           | 抽油机型号ID     | NUMBER(10)     |          | Y        |        |                            |
| 23       | STROKE                   | 铭牌冲程         | NUMBER(8,2)    |          | Y        |        |                            |
| 24       | BALANCEINFO              | 平衡块信息       | VARCHAR2(400)  |          | Y        |        | json格式字符串             |
| 25       | STATUS                   | 状态             | NUMBER(1)      |          | Y        |        | 0-失效 1-使能              |
| 26       | CALCULATETYPE            | 计算类型         | NUMBER(2)      |          | Y        |        | 1-功图计算 2-转速计产 0-无 |
| 27       | CONSTRUCTIONDATA         | 功图构建参数     | VARCHAR2(4000) |          | Y        |        | 用于电参反演功图           |
| 28       | SORTNUM                  | 排序编号         | NUMBER(10)     |          | Y        |        |                            |

### 1.2.31 tbl_smsdevice

短信设备信息表

| **序号** | **代码**     | **名称**     | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|--------------|--------------|---------------|----------|----------|--------|----------|
| 1        | ID           | 记录编号     | NUMBER(10)    |          | N        | 主键   |          |
| 2        | ORGID        | 单位编号     | NUMBER(10)    |          | Y        |        |          |
| 3        | DEVICENAME   | 设备名称     | VARCHAR2(200) |          | Y        |        |          |
| 4        | SIGNINID     | 注册包ID     | VARCHAR2(200) |          | N        |        |          |
| 5        | INSTANCECODE | 短信实例代码 | VARCHAR2(50)  |          | Y        |        |          |
| 6        | SORTNUM      | 排序编号     | NUMBER(10)    |          | Y        |        |          |

### 1.2.32 tbl_deviceaddinfo

设备附加信息表

| **序号** | **代码**  | **名称** | **类型**      | **单位** | **为空** | **键** | **备注**               |
|----------|-----------|----------|---------------|----------|----------|--------|------------------------|
| 1        | ID        | 记录编号 | NUMBER(10)    |          | N        | 主键   |                        |
| 2        | DEVICEID  | 设备编号 | NUMBER(10)    |          | Y        |        | 对应tbl_device表id字段 |
| 3        | ITEMNAME  | 名称     | VARCHAR2(200) |          | Y        |        |                        |
| 4        | ITEMVALUE | 变量     | VARCHAR2(200) |          | Y        |        |                        |
| 5        | ITEMUNIT  | 单位     | VARCHAR2(200) |          | Y        |        |                        |

### 1.2.33 tbl_auxiliarydevice

辅件设备信息表

| **序号** | **代码**     | **名称**           | **类型**       | **单位** | **为空** | **键** | **备注**                       |
|----------|--------------|--------------------|----------------|----------|----------|--------|--------------------------------|
| 1        | ID           | 记录编号           | NUMBER(10)     |          | N        | 主键   |                                |
| 2        | NAME         | 设备名称           | VARCHAR2(200)  |          | Y        |        |                                |
| 3        | TYPE         | 设备类型           | NUMBER(2)      |          | Y        |        | 对应tbl_devicetypeinfo表中id   |
| 4        | MANUFACTURER | 厂家               | VARCHAR2(200)  |          | Y        |        |                                |
| 5        | MODEL        | 型号               | VARCHAR2(200)  |          | Y        |        |                                |
| 6        | SPECIFICTYPE | 指定类型           | NUMBER(2)      |          | Y        |        | 1-抽油机 其他-无               |
| 7        | PRTF         | 抽油机位置扭矩因数 | CLOB           |          | Y        |        | 指定类型为抽油机时，该字段生效 |
| 8        | SORT         | 排序编号           | NUMBER(10)     |          | Y        |        |                                |
| 9        | REMARK       | 备注               | VARCHAR2(2000) |          | Y        |        |                                |

### 1.2.34 tbl_auxiliarydeviceaddinfo

辅件设备附加信息表

| **序号** | **代码**  | **名称** | **类型**      | **单位** | **为空** | **键** | **备注**                        |
|----------|-----------|----------|---------------|----------|----------|--------|---------------------------------|
| 1        | ID        | 记录编号 | NUMBER(10)    |          | N        | 主键   |                                 |
| 2        | DEVICEID  | 设备编号 | NUMBER(10)    |          | Y        |        | 对应tbl_auxiliarydevice表id字段 |
| 3        | ITEMNAME  | 名称     | VARCHAR2(200) |          | Y        |        |                                 |
| 4        | ITEMVALUE | 变量     | VARCHAR2(200) |          | Y        |        |                                 |
| 5        | ITEMUNIT  | 单位     | VARCHAR2(200) |          | Y        |        |                                 |
| 6        | ITEMCODE  |          |               |          |          |        |                                 |

### 1.2.35 tbl_auxiliary2master

主设备与辅件设备关系表

| **序号** | **代码**    | **名称**     | **类型**    | **单位** | **为空** | **键** | **备注**                        |
|----------|-------------|--------------|-------------|----------|----------|--------|---------------------------------|
| 1        | ID          | 记录编号     | NUMBER(10)  |          | N        | 主键   |                                 |
| 2        | MASTERID    | 主设备编号   | NUMBER(10)  |          | Y        |        | 对应tbl_device表id字段          |
| 3        | AUXILIARYID | 辅件设备编号 | NUMBER(10)  |          | Y        |        | 对应tbl_auxiliarydevice表id字段 |
| 4        | MATRIX      | 权限矩阵     | VARCHAR2(8) |          | Y        |        |                                 |

### 1.2.36 tbl_devicegraphicset

设备图形设置表

| **序号** | **代码**     | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注**       |
|----------|--------------|--------------|----------------|----------|----------|--------|----------------|
| 1        | ID           | 记录编号     | NUMBER(10)     |          | N        | 主键   |                |
| 2        | DEVICEID     | 设备编号     | NUMBER(10)     |          | N        |        |                |
| 3        | GRAPHICSTYLE | 图形设置内容 | VARCHAR2(4000) |          | Y        |        | json格式字符串 |

### 1.2.37 tbl_acqdata_latest

实时数据表

| **序号** | **代码**           | **名称** | **类型**     | **单位** | **为空** | **键** | **备注**               |
|----------|--------------------|----------|--------------|----------|----------|--------|------------------------|
| 1        | ID                 | 记录编号 | NUMBER(10)   |          | N        | 主键   |                        |
| 2        | DEVICEID           | 设备编号 | NUMBER(10)   |          | N        |        |                        |
| 3        | ACQTIME            | 采集时间 | DATE         |          | Y        |        |                        |
| 4        | COMMSTATUS         | 通信状态 | NUMBER(2)    |          | Y        |        | 0-离线 1-在线          |
| 5        | COMMTIME           | 在线时间 | NUMBER(8,2)  |          | Y        |        |                        |
| 6        | COMMTIMEEFFICIENCY | 在线时率 | NUMBER(10,4) |          | Y        |        |                        |
| 7        | COMMRANGE          | 在线区间 | CLOB         |          | Y        |        |                        |
| 8        | RUNSTATUS          | 运行状态 | NUMBER(2)    |          | Y        |        |                        |
| 9        | RUNTIMEEFFICIENCY  | 运行时率 | NUMBER(8,2)  |          | Y        |        |                        |
| 10       | RUNTIME            | 运行时间 | NUMBER(10,4) |          | Y        |        |                        |
| 11       | RUNRANGE           | 运行区间 | CLOB         |          | Y        |        |                        |
| …        | …                  | …        |              |          | Y        |        | 根据协议自动生成的字段 |
| …        | …                  | …        |              |          | Y        |        |                        |

### 1.2.38 tbl_acqdata_hist

同tbl_acqdata_latest

### 1.2.39 tbl_acqdata_vacute

同tbl_acqdata_latest

### 1.2.40 tbl_acqrawdata

原始采集数据表

| **序号** | **代码**     | **名称**   | **类型**       | **单位** | **为空** | **键** | **备注**                               |
|----------|--------------|------------|----------------|----------|----------|--------|----------------------------------------|
| 1        | ID           | 记录编号   | NUMBER(10)     |          | N        | 主键   |                                        |
| 2        | DEVICEID     | 设备编号   | NUMBER(10)     |          | N        |        |                                        |
| 3        | ACQTIME      | 采集时间   | DATE           |          | Y        |        |                                        |
| 4        | RAWDATA      | 原始数据   | VARCHAR2(4000) |          | Y        |        | 设备采集的未解析原始数据，16进制字符串 |
| 5        | ACQGROUPDATA | 采集组数据 | CLOB           |          | Y        |        | 上传的采集组数据                       |

### 1.2.41 tbl_alarminfo_latest

报警实时数据表

| **序号** | **代码**      | **名称**     | **类型**      | **单位** | **为空** | **键** | **备注**                                        |
|----------|---------------|--------------|---------------|----------|----------|--------|-------------------------------------------------|
| 1        | ID            | 记录编号     | NUMBER(10)    |          | N        | 主键   |                                                 |
| 2        | DEVICEID      | 设备编号     | NUMBER(10)    |          | N        |        |                                                 |
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

### 1.2.42 tbl_alarminfo_hist

同tbl_alarminfo_latest

### 1.2.43 tbl_dailytotalcalculate_latest

日累计计算实时数据表

| **序号** | **代码**   | **名称** | **类型**       | **单位** | **为空** | **键** | **备注** |
|----------|------------|----------|----------------|----------|----------|--------|----------|
| 1        | ID         | 记录编号 | NUMBER(10)     |          | N        | 主键   |          |
| 2        | DEVICEID   | 设备编号 | NUMBER(10)     |          | N        |        |          |
| 3        | ACQTIME    | 采集时间 | DATE           |          | Y        |        |          |
| 4        | ITEMCOLUMN | 字段     | VARCHAR2(4000) |          | Y        |        |          |
| 5        | TOTALVALUE | 累计值   | NUMBER(12,3)   |          | Y        |        |          |
| 6        | TODAYVALUE | 日累计值 | NUMBER(12,3)   |          | Y        |        |          |
| 7        | ITEMNAME   | 字段名称 | VARCHAR2(50)   |          | Y        |        |          |

### 1.2.44 tbl_dailytotalcalculate_hist

同tbl_dailytotalcalculate_latest

### 1.2.45 tbl_dailycalculationdata

日汇总数据表

| **序号** | **代码**           | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注**      |
|----------|--------------------|--------------|----------------|----------|----------|--------|---------------|
| 1        | ID                 | 记录编号     | NUMBER(10)     |          | N        | 主键   |               |
| 2        | DEVICEID           | 设备编号     | NUMBER(10)     |          | N        |        |               |
| 3        | CALDATE            | 汇总日期     | DATE           |          | Y        |        |               |
| 4        | COMMSTATUS         | 通信状态     | NUMBER(2)      |          | Y        |        | 0-离线 1-在线 |
| 5        | COMMTIME           | 在线时间     | NUMBER(8,2)    |          | Y        |        |               |
| 6        | COMMTIMEEFFICIENCY | 在线时率     | NUMBER(10,4)   |          | Y        |        |               |
| 7        | COMMRANGE          | 在线区间     | CLOB           |          | Y        |        |               |
| 8        | RUNSTATUS          | 运行状态     | NUMBER(2)      |          | Y        |        |               |
| 9        | RUNTIMEEFFICIENCY  | 运行时率     | NUMBER(8,2)    |          | Y        |        |               |
| 10       | RUNTIME            | 运行时间     | NUMBER(10,4)   |          | Y        |        |               |
| 11       | RUNRANGE           | 运行区间     | CLOB           |          | Y        |        |               |
| 12       | HEADERLABELINFO    | 报表表头信息 | VARCHAR2(4000) |          | Y        |        |               |
| 13       | CALDATA            | 计算数据     | CLOB           |          | Y        |        |               |
| 14       | REMARK             | 备注         | VARCHAR2(4000) |          | Y        |        |               |

### 1.2.46 tbl_timingcalculationdata

定时汇总数据表

| **序号** | **代码**           | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注**      |
|----------|--------------------|--------------|----------------|----------|----------|--------|---------------|
| 1        | ID                 | 记录编号     | NUMBER(10)     |          | N        | 主键   |               |
| 2        | DEVICEID           | 设备编号     | NUMBER(10)     |          | N        |        |               |
| 3        | CALTIME            | 汇总时间     | DATE           |          | Y        |        |               |
| 4        | COMMSTATUS         | 通信状态     | NUMBER(2)      |          | Y        |        | 0-离线 1-在线 |
| 5        | COMMTIME           | 在线时间     | NUMBER(8,2)    |          | Y        |        |               |
| 6        | COMMTIMEEFFICIENCY | 在线时率     | NUMBER(10,4)   |          | Y        |        |               |
| 7        | COMMRANGE          | 在线区间     | CLOB           |          | Y        |        |               |
| 8        | RUNSTATUS          | 运行状态     | NUMBER(2)      |          | Y        |        |               |
| 9        | RUNTIMEEFFICIENCY  | 运行时率     | NUMBER(8,2)    |          | Y        |        |               |
| 10       | RUNTIME            | 运行时间     | NUMBER(10,4)   |          | Y        |        |               |
| 11       | RUNRANGE           | 运行区间     | CLOB           |          | Y        |        |               |
| 12       | HEADERLABELINFO    | 报表表头信息 | VARCHAR2(4000) |          | Y        |        |               |
| 13       | CALDATA            | 计算数据     | CLOB           |          | Y        |        |               |
| 14       | REMARK             | 备注         | VARCHAR2(4000) |          | Y        |        |               |

### 1.2.47 tbl_realtimetotalcalculationdata

实时汇总数据表

| **序号** | **代码**           | **名称**     | **类型**       | **单位** | **为空** | **键** | **备注**      |
|----------|--------------------|--------------|----------------|----------|----------|--------|---------------|
| 1        | ID                 | 记录编号     | NUMBER(10)     |          | N        | 主键   |               |
| 2        | DEVICEID           | 设备编号     | NUMBER(10)     |          | N        |        |               |
| 3        | CALTIME            | 汇总时间     | DATE           |          | Y        |        |               |
| 4        | COMMSTATUS         | 通信状态     | NUMBER(2)      |          | Y        |        | 0-离线 1-在线 |
| 5        | COMMTIME           | 在线时间     | NUMBER(8,2)    |          | Y        |        |               |
| 6        | COMMTIMEEFFICIENCY | 在线时率     | NUMBER(10,4)   |          | Y        |        |               |
| 7        | COMMRANGE          | 在线区间     | CLOB           |          | Y        |        |               |
| 8        | RUNSTATUS          | 运行状态     | NUMBER(2)      |          | Y        |        |               |
| 9        | RUNTIMEEFFICIENCY  | 运行时率     | NUMBER(8,2)    |          | Y        |        |               |
| 10       | RUNTIME            | 运行时间     | NUMBER(10,4)   |          | Y        |        |               |
| 11       | RUNRANGE           | 运行区间     | CLOB           |          | Y        |        |               |
| 12       | HEADERLABELINFO    | 报表表头信息 | VARCHAR2(4000) |          | Y        |        |               |
| 13       | CALDATA            | 计算数据     | CLOB           |          | Y        |        |               |
| 14       | REMARK             | 备注         | VARCHAR2(4000) |          | Y        |        |               |

### 1.2.48 tbl_srpacqdata_latest

功图计算实时数据表

| **序号** | **代码**                           | **名称**                 | **类型**       | **单位** | **为空** | **键** | **备注**            |
|----------|------------------------------------|--------------------------|----------------|----------|----------|--------|---------------------|
| 1        | ID                                 | 记录编号                 | NUMBER(10)     |          | N        | 主键   |                     |
| 2        | DEVICEID                           | 设备编号                 | NUMBER(10)     |          | N        |        |                     |
| 3        | ACQTIME                            | 采集时间                 | DATE           |          | Y        |        |                     |
| 4        | COMMSTATUS                         | 通信状态                 | NUMBER(2)      |          | Y        |        | 0-离线 1-在线       |
| 5        | COMMTIME                           | 在线时间                 | NUMBER(8,2)    |          | Y        |        |                     |
| 6        | COMMTIMEEFFICIENCY                 | 在线时率                 | NUMBER(10,4)   |          | Y        |        |                     |
| 7        | COMMRANGE                          | 在线区间                 | CLOB           |          | Y        |        |                     |
| 8        | RUNSTATUS                          | 运行状态                 | NUMBER(2)      |          | Y        |        |                     |
| 9        | RUNTIMEEFFICIENCY                  | 运行时率                 | NUMBER(8,2)    |          | Y        |        |                     |
| 10       | RUNTIME                            | 运行时间                 | NUMBER(10,4)   |          | Y        |        |                     |
| 11       | RUNRANGE                           | 运行区间                 | CLOB           |          | Y        |        |                     |
| 12       | PRODUCTIONDATA                     | 生产数据                 | VARCHAR2(4000) |          | Y        |        |                     |
| 13       | PUMPINGMODELID                     | 抽油机型号编号           | NUMBER(10)     |          | Y        |        |                     |
| 14       | BALANCEINFO                        | 平衡块数据               | VARCHAR2(400)  |          | Y        |        |                     |
| 15       | FESDIAGRAMACQTIME                  | 功图采集时间             | DATE           |          | Y        |        |                     |
| 16       | FESDIAGRAMSRC                      | 功图来源                 | NUMBER(2)      |          | Y        |        | 0-功图仪 1-电参反演 |
| 17       | STROKE                             | 冲程                     | NUMBER(8,2)    |          | Y        |        |                     |
| 18       | SPM                                | 冲次                     | NUMBER(8,2)    |          | Y        |        |                     |
| 19       | FMAX                               | 最大载荷                 | NUMBER(8,2)    |          | Y        |        |                     |
| 20       | FMIN                               | 最小载荷                 | NUMBER(8,2)    |          | Y        |        |                     |
| 21       | POSITION_CURVE                     | 功图位移数据             | CLOB           |          | Y        |        |                     |
| 22       | ANGLE_CURVE                        | 角度曲线数据             | CLOB           |          | Y        |        |                     |
| 23       | LOAD_CURVE                         | 功图载荷数据             | CLOB           |          | Y        |        |                     |
| 24       | POWER_CURVE                        | 功率曲线数据             | CLOB           |          | Y        |        |                     |
| 25       | CURRENT_CURVE                      | 电流曲线数据             | CLOB           |          | Y        |        |                     |
| 26       | RESULTCODE                         | 工况代码                 | NUMBER(4)      |          | Y        |        |                     |
| 27       | FULLNESSCOEFFICIENT                | 充满系数                 | NUMBER(8,2)    |          | Y        |        |                     |
| 28       | UPPERLOADLINE                      | 理论上载荷               | NUMBER(8,2)    |          | Y        |        |                     |
| 29       | UPPERLOADLINEOFEXACT               | 考虑沉没压力的理论上载荷 | NUMBER(8,2)    |          | Y        |        |                     |
| 30       | LOWERLOADLINE                      | 理论下载荷               | NUMBER(8,2)    |          | Y        |        |                     |
| 31       | PUMPFSDIAGRAM                      | 泵功图                   | CLOB           |          | Y        |        |                     |
| 32       | THEORETICALPRODUCTION              | 理论排量                 | NUMBER(8,2)    |          | Y        |        |                     |
| 33       | LIQUIDVOLUMETRICPRODUCTION         | 产液量                   | NUMBER(8,2)    | 方       | Y        |        |                     |
| 34       | OILVOLUMETRICPRODUCTION            | 产油量                   | NUMBER(8,2)    | 方       | Y        |        |                     |
| 35       | WATERVOLUMETRICPRODUCTION          | 产水量                   | NUMBER(8,2)    | 方       | Y        |        |                     |
| 36       | AVAILABLEPLUNGERSTROKEPROD_V       | 有效冲程计算产量         | NUMBER(8,2)    | 方       | Y        |        |                     |
| 37       | PUMPCLEARANCELEAKPROD_V            | 泵间隙漏失量             | NUMBER(8,2)    | 方       | Y        |        |                     |
| 38       | TVLEAKVOLUMETRICPRODUCTION         | 游动凡尔漏失量           | NUMBER(8,2)    | 方       | Y        |        |                     |
| 39       | SVLEAKVOLUMETRICPRODUCTION         | 固定凡尔漏失量           | NUMBER(8,2)    | 方       | Y        |        |                     |
| 40       | GASINFLUENCEPROD_V                 | 气影响                   | NUMBER(8,2)    | 方       | Y        |        |                     |
| 41       | LIQUIDWEIGHTPRODUCTION             | 产液量                   | NUMBER(8,2)    | 吨       | Y        |        |                     |
| 42       | OILWEIGHTPRODUCTION                | 产油量                   | NUMBER(8,2)    | 吨       | Y        |        |                     |
| 43       | WATERWEIGHTPRODUCTION              | 产水量                   | NUMBER(8,2)    | 吨       | Y        |        |                     |
| 44       | AVAILABLEPLUNGERSTROKEPROD_W       | 有效冲程计算产量         | NUMBER(8,2)    | 吨       | Y        |        |                     |
| 45       | PUMPCLEARANCELEAKPROD_W            | 泵间隙漏失量             | NUMBER(8,2)    | 吨       | Y        |        |                     |
| 46       | TVLEAKWEIGHTPRODUCTION             | 游动凡尔漏失量           | NUMBER(8,2)    | 吨       | Y        |        |                     |
| 47       | SVLEAKWEIGHTPRODUCTION             | 固定凡尔漏失量           | NUMBER(8,2)    | 吨       | Y        |        |                     |
| 48       | GASINFLUENCEPROD_W                 | 气影响                   | NUMBER(8,2)    | 吨       | Y        |        |                     |
| 49       | AVERAGEWATT                        | 有功功率                 | NUMBER(8,2)    |          | Y        |        |                     |
| 50       | POLISHRODPOWER                     | 光杆功率                 | NUMBER(8,2)    |          | Y        |        |                     |
| 51       | WATERPOWER                         | 水功率                   | NUMBER(8,2)    |          | Y        |        |                     |
| 52       | SURFACESYSTEMEFFICIENCY            | 地面效率                 | NUMBER(12,3)   |          | Y        |        |                     |
| 53       | WELLDOWNSYSTEMEFFICIENCY           | 井下效率                 | NUMBER(12,3)   |          | Y        |        |                     |
| 54       | SYSTEMEFFICIENCY                   | 系统效率                 | NUMBER(12,3)   |          | Y        |        |                     |
| 55       | ENERGYPER100MLIFT                  | 吨液百米耗电量           | NUMBER(8,2)    |          | Y        |        |                     |
| 56       | AREA                               | 功图面积                 | NUMBER(8,2)    |          | Y        |        |                     |
| 57       | RODFLEXLENGTH                      | 抽油杆伸长量             | NUMBER(8,2)    |          | Y        |        |                     |
| 58       | TUBINGFLEXLENGTH                   | 油管伸缩值               | NUMBER(8,2)    |          | Y        |        |                     |
| 59       | INERTIALENGTH                      | 惯性载荷增量             | NUMBER(8,2)    |          | Y        |        |                     |
| 60       | PUMPEFF1                           | 冲程损失系数             | NUMBER(12,3)   |          | Y        |        |                     |
| 61       | PUMPEFF2                           | 充满系数                 | NUMBER(12,3)   |          | Y        |        |                     |
| 62       | PUMPEFF3                           | 间隙漏失系数             | NUMBER(12,3)   |          | Y        |        |                     |
| 63       | PUMPEFF4                           | 液体收缩系数             | NUMBER(12,3)   |          | Y        |        |                     |
| 64       | PUMPEFF                            | 总泵效                   | NUMBER(12,3)   |          | Y        |        |                     |
| 65       | PUMPINTAKEP                        | 泵入口压力               | NUMBER(8,2)    |          | Y        |        |                     |
| 66       | PUMPINTAKET                        | 泵入口温度               | NUMBER(8,2)    |          | Y        |        |                     |
| 67       | PUMPINTAKEGOL                      | 泵入口就地气液比         | NUMBER(8,2)    |          | Y        |        |                     |
| 68       | PUMPINTAKEVISL                     | 泵入口粘度               | NUMBER(8,2)    |          | Y        |        |                     |
| 69       | PUMPINTAKEBO                       | 泵入口原油体积系数       | NUMBER(8,2)    |          | Y        |        |                     |
| 70       | PUMPOUTLETP                        | 泵出口压力               | NUMBER(8,2)    |          | Y        |        |                     |
| 71       | PUMPOUTLETT                        | 泵出口温度               | NUMBER(8,2)    |          | Y        |        |                     |
| 72       | PUMPOUTLETGOL                      | 泵出口就地气液比         | NUMBER(8,2)    |          | Y        |        |                     |
| 73       | PUMPOUTLETVISL                     | 泵出口粘度               | NUMBER(8,2)    |          | Y        |        |                     |
| 74       | PUMPOUTLETBO                       | 泵出口原油体积系数       | NUMBER(8,2)    |          | Y        |        |                     |
| 75       | RODSTRING                          | 抽油杆参数               | VARCHAR2(200)  |          | Y        |        |                     |
| 76       | PLUNGERSTROKE                      | 柱塞冲程                 | NUMBER(8,2)    |          | Y        |        |                     |
| 77       | AVAILABLEPLUNGERSTROKE             | 柱塞有效冲程             | NUMBER(8,2)    |          | Y        |        |                     |
| 78       | LEVELDIFFERENCEVALUE               | 反演液面校正值           | NUMBER(8,2)    |          | Y        |        |                     |
| 79       | CALCPRODUCINGFLUIDLEVEL            | 反演液面                 | NUMBER(8,2)    |          | Y        |        |                     |
| 80       | NOLIQUIDFULLNESSCOEFFICIENT        | 抽空充满系数             | NUMBER(10,4)   |          | Y        |        |                     |
| 81       | NOLIQUIDAVAILABLEPLUNGERSTROKE     | 抽空柱塞有效冲程         | NUMBER(10,4)   |          | Y        |        |                     |
| 82       | SMAXINDEX                          | 位移最大值索引           | NUMBER(10)     |          | Y        |        |                     |
| 83       | SMININDEX                          | 位移最小值索引           | NUMBER(10)     |          | Y        |        |                     |
| 84       | UPSTROKEIMAX                       | 上冲程最大电流           | NUMBER(8,2)    |          | Y        |        |                     |
| 85       | DOWNSTROKEIMAX                     | 下冲程最大电流           | NUMBER(8,2)    |          | Y        |        |                     |
| 86       | UPSTROKEWATTMAX                    | 上冲程最大功率           | NUMBER(8,2)    |          | Y        |        |                     |
| 87       | DOWNSTROKEWATTMAX                  | 下冲程最大功率           | NUMBER(8,2)    |          | Y        |        |                     |
| 88       | IDEGREEBALANCE                     | 电流平衡度               | NUMBER(8,2)    |          | Y        |        |                     |
| 89       | WATTDEGREEBALANCE                  | 功率平衡度               | NUMBER(8,2)    |          | Y        |        |                     |
| 90       | DELTARADIUS                        | 移动距离                 | NUMBER(8,2)    |          | Y        |        |                     |
| 91       | CRANKANGLE                         | 曲柄转角                 | CLOB           |          | Y        |        |                     |
| 92       | POLISHRODV                         | 光杆速度                 | CLOB           |          | Y        |        |                     |
| 93       | POLISHRODA                         | 光杆加速度               | CLOB           |          | Y        |        |                     |
| 94       | PR                                 | 位置因数                 | CLOB           |          | Y        |        |                     |
| 95       | TF                                 | 扭矩因数                 | CLOB           |          | Y        |        |                     |
| 96       | LOADTORQUE                         | 载荷扭矩                 | CLOB           |          | Y        |        |                     |
| 97       | CRANKTORQUE                        | 曲柄扭矩                 | CLOB           |          | Y        |        |                     |
| 98       | CURRENTBALANCETORQUE               | 目前平衡块扭矩           | CLOB           |          | Y        |        |                     |
| 99       | CURRENTNETTORQUE                   | 目前净扭矩               | CLOB           |          | Y        |        |                     |
| 100      | EXPECTEDBALANCETORQUE              | 预期平衡块扭矩           | CLOB           |          | Y        |        |                     |
| 101      | EXPECTEDNETTORQUE                  | 预期前净扭矩             | CLOB           |          | Y        |        |                     |
| 102      | WELLBORESLICE                      | 井身切片                 | CLOB           |          | Y        |        |                     |
| 103      | RESULTSTATUS                       | 计算状态                 | NUMBER(2)      |          | Y        |        |                     |
| 104      | TOTALKWATTH                        | 累计电量                 | NUMBER(12,3)   |          | Y        |        |                     |
| 105      | TODAYKWATTH                        | 日用电量                 | NUMBER(12,3)   |          | Y        |        |                     |
| 106      | LIQUIDVOLUMETRICPRODUCTION_L       | 日累计产液量             | NUMBER(8,2)    | 方       | Y        |        |                     |
| 107      | OILVOLUMETRICPRODUCTION_L          | 日累计产油量             | NUMBER(8,2)    | 方       | Y        |        |                     |
| 108      | WATERVOLUMETRICPRODUCTION_L        | 日累计产水量             | NUMBER(8,2)    | 方       | Y        |        |                     |
| 109      | LIQUIDWEIGHTPRODUCTION_L           | 日累计产液量             | NUMBER(8,2)    | 吨       | Y        |        |                     |
| 110      | OILWEIGHTPRODUCTION_L              | 日累计产油量             | NUMBER(8,2)    | 吨       | Y        |        |                     |
| 111      | WATERWEIGHTPRODUCTION_L            | 日累计产水量             | NUMBER(8,2)    | 吨       | Y        |        |                     |
| 112      | SUBMERGENCE                        | 沉没度                   | NUMBER(8,2)    | 米       | Y        |        |                     |
| 113      | GASVOLUMETRICPRODUCTION            | 日产气量                 | NUMBER(12,3)   | 方       | Y        |        |                     |
| 114      | TOTALGASVOLUMETRICPRODUCTION       | 累计产气量               | NUMBER(12,3)   | 方       | Y        |        |                     |
| 115      | TOTALWATERVOLUMETRICPRODUCTION     | 累计产水量               | NUMBER(12,3)   | 方       | Y        |        |                     |
| 116      | RPM                                | 转速                     | NUMBER(8,2)    |          | Y        |        |                     |
| 117      | REALTIMEGASVOLUMETRICPRODUCTION    | 实时产气量               | NUMBER(12,3)   | 方       | Y        |        |                     |
| 118      | REALTIMEWATERVOLUMETRICPRODUCTION  | 实时产水量               | NUMBER(12,3)   | 方       | Y        |        |                     |
| 119      | REALTIMEOILVOLUMETRICPRODUCTION    | 实时产油量               | NUMBER(12,3)   | 方       | Y        |        |                     |
| 120      | REALTIMELIQUIDVOLUMETRICPRODUCTION | 实时产液量               | NUMBER(12,3)   | 方       | Y        |        |                     |
| 121      | REALTIMEWATERWEIGHTPRODUCTION      | 实时产水量               | NUMBER(12,3)   | 吨       | Y        |        |                     |
| 122      | REALTIMEOILWEIGHTPRODUCTION        | 实时产油量               | NUMBER(12,3)   | 吨       | Y        |        |                     |
| 123      | REALTIMELIQUIDWEIGHTPRODUCTION     | 实时产液量               | NUMBER(12,3)   | 吨       | Y        |        |                     |
| 124      | SAVETIME                           | 保存时间                 | DATE           |          | Y        |        |                     |

### 1.2.49 tbl_srpacqdata_hist

同tbl_rpcacqdata_latest

### 1.2.50 tbl_srpacqdata_vacute

同tbl_rpcacqdata_latest

### 1.2.51 tbl_srpdailycalculationdata

功图计算日汇总数据表

| **序号** | **代码**                       | **名称**       | **类型**       | **单位** | **为空** | **键** | **备注**      |
|----------|--------------------------------|----------------|----------------|----------|----------|--------|---------------|
| 1        | ID                             | 记录编号       | NUMBER(10)     |          | N        | 主键   |               |
| 2        | DEVICEID                       | 设备编号       | NUMBER(10)     |          | N        |        |               |
| 3        | CALDATE                        | 汇总日期       | DATE           |          | Y        |        |               |
| 4        | COMMSTATUS                     | 通信状态       | NUMBER(2)      |          | Y        |        | 0-离线 1-在线 |
| 5        | COMMTIME                       | 在线时间       | NUMBER(8,2)    |          | Y        |        |               |
| 6        | COMMTIMEEFFICIENCY             | 在线时率       | NUMBER(10,4)   |          | Y        |        |               |
| 7        | COMMRANGE                      | 在线区间       | CLOB           |          | Y        |        |               |
| 8        | RUNSTATUS                      | 运行状态       | NUMBER(2)      |          | Y        |        |               |
| 9        | RUNTIMEEFFICIENCY              | 运行时率       | NUMBER(8,2)    |          | Y        |        |               |
| 10       | RUNTIME                        | 运行时间       | NUMBER(10,4)   |          | Y        |        |               |
| 11       | RUNRANGE                       | 运行区间       | CLOB           |          | Y        |        |               |
| 12       | STROKE                         | 冲程           | NUMBER(8,2)    |          | Y        |        |               |
| 13       | SPM                            | 冲次           | NUMBER(8,2)    |          | Y        |        |               |
| 14       | FMAX                           | 最大载荷       | NUMBER(8,2)    |          | Y        |        |               |
| 15       | FMIN                           | 最小载荷       | NUMBER(8,2)    |          | Y        |        |               |
| 16       | RESULTCODE                     | 工况代码       | NUMBER(4)      |          | Y        |        |               |
| 17       | RESULTSTRING                   | 工况字符串     | CLOB           |          | Y        |        |               |
| 18       | FULLNESSCOEFFICIENT            | 充满系数       | NUMBER(8,2)    |          | Y        |        |               |
| 19       | THEORETICALPRODUCTION          | 理论排量       | NUMBER(8,2)    |          | Y        |        |               |
| 20       | LIQUIDVOLUMETRICPRODUCTION     | 产液量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 21       | OILVOLUMETRICPRODUCTION        | 产油量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 22       | WATERVOLUMETRICPRODUCTION      | 产水量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 23       | VOLUMEWATERCUT                 | 体积含水率     | NUMBER(8,2)    |          | Y        |        |               |
| 24       | LIQUIDWEIGHTPRODUCTION         | 产液量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 25       | OILWEIGHTPRODUCTION            | 产油量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 26       | WATERWEIGHTPRODUCTION          | 产水量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 27       | WEIGHTWATERCUT                 | 重量含水率     | NUMBER(8,2)    |          | Y        |        |               |
| 28       | SURFACESYSTEMEFFICIENCY        | 地面效率       | NUMBER(12,3)   |          | Y        |        |               |
| 29       | WELLDOWNSYSTEMEFFICIENCY       | 井下效率       | NUMBER(12,3)   |          | Y        |        |               |
| 30       | SYSTEMEFFICIENCY               | 系统效率       | NUMBER(12,3)   |          | Y        |        |               |
| 31       | ENERGYPER100MLIFT              | 吨液百米耗电量 | NUMBER(8,2)    |          | Y        |        |               |
| 32       | PUMPEFF1                       | 冲程损失系数   | NUMBER(12,3)   |          | Y        |        |               |
| 33       | PUMPEFF2                       | 充满系数       | NUMBER(12,3)   |          | Y        |        |               |
| 34       | PUMPEFF3                       | 间隙漏失系数   | NUMBER(12,3)   |          | Y        |        |               |
| 35       | PUMPEFF4                       | 液体收缩系数   | NUMBER(12,3)   |          | Y        |        |               |
| 36       | PUMPEFF                        | 总泵效         | NUMBER(12,3)   |          | Y        |        |               |
| 37       | IDEGREEBALANCE                 | 电流平衡度     | NUMBER(8,2)    |          | Y        |        |               |
| 38       | WATTDEGREEBALANCE              | 功率平衡度     | NUMBER(8,2)    |          | Y        |        |               |
| 39       | DELTARADIUS                    | 移动距离       | NUMBER(8,2)    |          | Y        |        |               |
| 40       | TOTALKWATTH                    | 累计电量       | NUMBER(12,3)   |          | Y        |        |               |
| 41       | TODAYKWATTH                    | 日用电量       | NUMBER(12,3)   |          | Y        |        |               |
| 42       | TUBINGPRESSURE                 | 油压           | NUMBER(8,2)    | MPa      | Y        |        |               |
| 43       | CASINGPRESSURE                 | 套压           | NUMBER(8,2)    | MPa      | Y        |        |               |
| 44       | BOTTOMHOLEPRESSURE             | 井底压力       | NUMBER(8,2)    | MPa      | Y        |        |               |
| 45       | PRODUCINGFLUIDLEVEL            | 动液面         | NUMBER(8,2)    | m        | Y        |        |               |
| 46       | PUMPSETTINGDEPTH               | 泵挂           | NUMBER(8,2)    | m        | Y        |        |               |
| 47       | SUBMERGENCE                    | 沉没度         | NUMBER(8,2)    | m        | Y        |        |               |
| 48       | CALCPRODUCINGFLUIDLEVEL        | 反演动液面     | NUMBER(8,2)    | m        | Y        |        |               |
| 49       | LEVELDIFFERENCEVALUE           | 液面反演差值   | NUMBER(8,2)    | MPa      | Y        |        |               |
| 50       | GASVOLUMETRICPRODUCTION        | 日产气量       | NUMBER(8,2)    | 方       | Y        |        |               |
| 51       | TOTALGASVOLUMETRICPRODUCTION   | 累计产气量     | NUMBER(8,2)    | 方       | Y        |        |               |
| 52       | TOTALWATERVOLUMETRICPRODUCTION | 累计产水量     | NUMBER(8,2)    | 方       | Y        |        |               |
| 53       | HEADERLABELINFO                | 报表表头信息   | VARCHAR2(4000) |          | Y        |        |               |
| 54       | EXTENDEDDAYS                   | 沿用天数       | NUMBER(5)      |          | Y        |        |               |
| 55       | RESULTSTATUS                   | 计算状态       | NUMBER(2)      |          | Y        |        |               |
| 56       | REMARK                         | 备注           | VARCHAR2(4000) |          | Y        |        |               |

### 1.2.52 tbl_srptimingcalculationdata

功图计算定时汇总数据表

| **序号** | **代码**                       | **名称**       | **类型**       | **单位** | **为空** | **键** | **备注**      |
|----------|--------------------------------|----------------|----------------|----------|----------|--------|---------------|
| 1        | ID                             | 记录编号       | NUMBER(10)     |          | N        | 主键   |               |
| 2        | DEVICEID                       | 设备编号       | NUMBER(10)     |          | N        |        |               |
| 3        | CALTIME                        | 计算时间       | DATE           |          | Y        |        |               |
| 4        | COMMSTATUS                     | 通信状态       | NUMBER(2)      |          | Y        |        | 0-离线 1-在线 |
| 5        | COMMTIME                       | 在线时间       | NUMBER(8,2)    |          | Y        |        |               |
| 6        | COMMTIMEEFFICIENCY             | 在线时率       | NUMBER(10,4)   |          | Y        |        |               |
| 7        | COMMRANGE                      | 在线区间       | CLOB           |          | Y        |        |               |
| 8        | RUNSTATUS                      | 运行状态       | NUMBER(2)      |          | Y        |        |               |
| 9        | RUNTIMEEFFICIENCY              | 运行时率       | NUMBER(8,2)    |          | Y        |        |               |
| 10       | RUNTIME                        | 运行时间       | NUMBER(10,4)   |          | Y        |        |               |
| 11       | RUNRANGE                       | 运行区间       | CLOB           |          | Y        |        |               |
| 12       | STROKE                         | 冲程           | NUMBER(8,2)    |          | Y        |        |               |
| 13       | SPM                            | 冲次           | NUMBER(8,2)    |          | Y        |        |               |
| 14       | FMAX                           | 最大载荷       | NUMBER(8,2)    |          | Y        |        |               |
| 15       | FMIN                           | 最小载荷       | NUMBER(8,2)    |          | Y        |        |               |
| 16       | RESULTCODE                     | 工况代码       | NUMBER(4)      |          | Y        |        |               |
| 17       | RESULTSTRING                   | 工况字符串     | CLOB           |          | Y        |        |               |
| 18       | FULLNESSCOEFFICIENT            | 充满系数       | NUMBER(8,2)    |          | Y        |        |               |
| 19       | THEORETICALPRODUCTION          | 理论排量       | NUMBER(8,2)    |          | Y        |        |               |
| 20       | LIQUIDVOLUMETRICPRODUCTION     | 产液量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 21       | OILVOLUMETRICPRODUCTION        | 产油量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 22       | WATERVOLUMETRICPRODUCTION      | 产水量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 23       | VOLUMEWATERCUT                 | 体积含水率     | NUMBER(8,2)    |          | Y        |        |               |
| 24       | LIQUIDWEIGHTPRODUCTION         | 产液量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 25       | OILWEIGHTPRODUCTION            | 产油量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 26       | WATERWEIGHTPRODUCTION          | 产水量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 27       | WEIGHTWATERCUT                 | 重量含水率     | NUMBER(8,2)    |          | Y        |        |               |
| 28       | SURFACESYSTEMEFFICIENCY        | 地面效率       | NUMBER(12,3)   |          | Y        |        |               |
| 29       | WELLDOWNSYSTEMEFFICIENCY       | 井下效率       | NUMBER(12,3)   |          | Y        |        |               |
| 30       | SYSTEMEFFICIENCY               | 系统效率       | NUMBER(12,3)   |          | Y        |        |               |
| 31       | ENERGYPER100MLIFT              | 吨液百米耗电量 | NUMBER(8,2)    |          | Y        |        |               |
| 32       | PUMPEFF1                       | 冲程损失系数   | NUMBER(12,3)   |          | Y        |        |               |
| 33       | PUMPEFF2                       | 充满系数       | NUMBER(12,3)   |          | Y        |        |               |
| 34       | PUMPEFF3                       | 间隙漏失系数   | NUMBER(12,3)   |          | Y        |        |               |
| 35       | PUMPEFF4                       | 液体收缩系数   | NUMBER(12,3)   |          | Y        |        |               |
| 36       | PUMPEFF                        | 总泵效         | NUMBER(12,3)   |          | Y        |        |               |
| 37       | IDEGREEBALANCE                 | 电流平衡度     | NUMBER(8,2)    |          | Y        |        |               |
| 38       | WATTDEGREEBALANCE              | 功率平衡度     | NUMBER(8,2)    |          | Y        |        |               |
| 39       | DELTARADIUS                    | 移动距离       | NUMBER(8,2)    |          | Y        |        |               |
| 40       | TOTALKWATTH                    | 累计电量       | NUMBER(12,3)   |          | Y        |        |               |
| 41       | TODAYKWATTH                    | 日用电量       | NUMBER(12,3)   |          | Y        |        |               |
| 42       | TUBINGPRESSURE                 | 油压           | NUMBER(8,2)    | MPa      | Y        |        |               |
| 43       | CASINGPRESSURE                 | 套压           | NUMBER(8,2)    | MPa      | Y        |        |               |
| 44       | BOTTOMHOLEPRESSURE             | 井底压力       | NUMBER(8,2)    | MPa      | Y        |        |               |
| 45       | PRODUCINGFLUIDLEVEL            | 动液面         | NUMBER(8,2)    | m        | Y        |        |               |
| 46       | PUMPSETTINGDEPTH               | 泵挂           | NUMBER(8,2)    | m        | Y        |        |               |
| 47       | SUBMERGENCE                    | 沉没度         | NUMBER(8,2)    | m        | Y        |        |               |
| 48       | CALCPRODUCINGFLUIDLEVEL        | 反演动液面     | NUMBER(8,2)    | m        | Y        |        |               |
| 49       | LEVELDIFFERENCEVALUE           | 液面反演差值   | NUMBER(8,2)    | MPa      | Y        |        |               |
| 50       | GASVOLUMETRICPRODUCTION        | 日产气量       | NUMBER(8,2)    | 方       | Y        |        |               |
| 51       | TOTALGASVOLUMETRICPRODUCTION   | 累计产气量     | NUMBER(8,2)    | 方       | Y        |        |               |
| 52       | TOTALWATERVOLUMETRICPRODUCTION | 累计产水量     | NUMBER(8,2)    | 方       | Y        |        |               |
| 53       | HEADERLABELINFO                | 报表表头信息   | VARCHAR2(4000) |          | Y        |        |               |
| 54       | EXTENDEDDAYS                   | 沿用天数       | NUMBER(5)      |          | Y        |        |               |
| 55       | RESULTSTATUS                   | 计算状态       | NUMBER(2)      |          | Y        |        |               |
| 56       | REMARK                         | 备注           | VARCHAR2(4000) |          | Y        |        |               |

### 1.2.53 tbl_pcpacqdata_latest

转速计产实时数据表

| **序号** | **代码**                       | **名称**           | **类型**       | **单位** | **为空** | **键** | **备注**               |
|----------|--------------------------------|--------------------|----------------|----------|----------|--------|------------------------|
| 1        | ID                             | 记录编号           | NUMBER(10)     |          | N        | 主键   |                        |
| 2        | DEVICEID                       | 设备编号           | NUMBER(10)     |          | N        |        |                        |
| 3        | ACQTIME                        | 采集时间           | DATE           |          | Y        |        |                        |
| 4        | COMMSTATUS                     | 通信状态           | NUMBER(2)      |          | Y        |        | 0-离线 1-在线          |
| 5        | COMMTIME                       | 在线时间           | NUMBER(8,2)    |          | Y        |        |                        |
| 6        | COMMTIMEEFFICIENCY             | 在线时率           | NUMBER(10,4)   |          | Y        |        |                        |
| 7        | COMMRANGE                      | 在线区间           | CLOB           |          | Y        |        |                        |
| 8        | RUNSTATUS                      | 运行状态           | NUMBER(2)      |          | Y        |        |                        |
| 9        | RUNTIMEEFFICIENCY              | 运行时率           | NUMBER(8,2)    |          | Y        |        |                        |
| 10       | RUNTIME                        | 运行时间           | NUMBER(10,4)   |          | Y        |        |                        |
| 11       | RUNRANGE                       | 运行区间           | CLOB           |          | Y        |        |                        |
| 12       | PRODUCTIONDATA                 | 生产数据           | VARCHAR2(4000) |          | Y        |        |                        |
| 13       | RPM                            | 转速               | NUMBER(8,2)    |          | Y        |        |                        |
| 14       | TORQUE                         | 扭矩               | NUMBER(8,2)    |          | Y        |        |                        |
| 15       | RESULTCODE                     | 工况代码           | NUMBER(4)      |          | Y        |        |                        |
| 16       | THEORETICALPRODUCTION          | 理论排量           | NUMBER(8,2)    |          | Y        |        |                        |
| 17       | LIQUIDVOLUMETRICPRODUCTION     | 产液量             | NUMBER(8,2)    | 方       | Y        |        |                        |
| 18       | OILVOLUMETRICPRODUCTION        | 产油量             | NUMBER(8,2)    | 方       | Y        |        |                        |
| 19       | WATERVOLUMETRICPRODUCTION      | 产水量             | NUMBER(8,2)    | 方       | Y        |        |                        |
| 20       | LIQUIDWEIGHTPRODUCTION         | 产液量             | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 21       | OILWEIGHTPRODUCTION            | 产油量             | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 22       | WATERWEIGHTPRODUCTION          | 产水量             | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 23       | AVERAGEWATT                    | 有功功率           | NUMBER(8,2)    |          | Y        |        |                        |
| 24       | WATERPOWER                     | 水功率             | NUMBER(8,2)    |          | Y        |        |                        |
| 25       | SYSTEMEFFICIENCY               | 系统效率           | NUMBER(12,3)   |          | Y        |        |                        |
| 26       | ENERGYPER100MLIFT              | 吨液百米耗电量     | NUMBER(8,2)    |          | Y        |        |                        |
| 27       | PUMPEFF1                       | 容积效率           | NUMBER(12,3)   |          | Y        |        |                        |
| 28       | PUMPEFF2                       | 液体收缩系数       | NUMBER(12,3)   |          | Y        |        |                        |
| 29       | PUMPEFF                        | 总泵效             | NUMBER(12,3)   |          | Y        |        |                        |
| 30       | PUMPINTAKEP                    | 泵入口压力         | NUMBER(8,2)    |          | Y        |        |                        |
| 31       | PUMPINTAKET                    | 泵入口温度         | NUMBER(8,2)    |          | Y        |        |                        |
| 32       | PUMPINTAKEGOL                  | 泵入口就地气液比   | NUMBER(8,2)    |          | Y        |        |                        |
| 33       | PUMPINTAKEVISL                 | 泵入口粘度         | NUMBER(8,2)    |          | Y        |        |                        |
| 34       | PUMPINTAKEBO                   | 泵入口原油体积系数 | NUMBER(8,2)    |          | Y        |        |                        |
| 35       | PUMPOUTLETP                    | 泵出口压力         | NUMBER(8,2)    |          | Y        |        |                        |
| 36       | PUMPOUTLETT                    | 泵出口温度         | NUMBER(8,2)    |          | Y        |        |                        |
| 37       | PUMPOUTLETGOL                  | 泵出口就地气液比   | NUMBER(8,2)    |          | Y        |        |                        |
| 38       | PUMPOUTLETVISL                 | 泵出口粘度         | NUMBER(8,2)    |          | Y        |        |                        |
| 39       | PUMPOUTLETBO                   | 泵出口原油体积系数 | NUMBER(8,2)    |          | Y        |        |                        |
| 40       | RODSTRING                      | 抽油杆参数         | VARCHAR2(200)  |          | Y        |        |                        |
| 41       | RESULTSTATUS                   | 计算状态           | NUMBER(2)      |          | Y        |        |                        |
| 42       | TOTALKWATTH                    | 累计电量           | NUMBER(12,3)   |          | Y        |        |                        |
| 43       | TODAYKWATTH                    | 日用电量           | NUMBER(12,3)   |          | Y        |        |                        |
| 44       | LIQUIDVOLUMETRICPRODUCTION_L   | 日累计产液量       | NUMBER(8,2)    | 方       | Y        |        |                        |
| 45       | OILVOLUMETRICPRODUCTION_L      | 日累计产油量       | NUMBER(8,2)    | 方       | Y        |        |                        |
| 46       | WATERVOLUMETRICPRODUCTION_L    | 日累计产水量       | NUMBER(8,2)    | 方       | Y        |        |                        |
| 47       | LIQUIDWEIGHTPRODUCTION_L       | 日累计产液量       | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 48       | OILWEIGHTPRODUCTION_L          | 日累计产油量       | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 49       | WATERWEIGHTPRODUCTION_L        | 日累计产水量       | NUMBER(8,2)    | 吨       | Y        |        |                        |
| 50       | SUBMERGENCE                    | 沉没度             | NUMBER(8,2)    | 米       | Y        |        |                        |
| 51       | GASVOLUMETRICPRODUCTION        | 日产气量           | NUMBER(12,3)   | 方       | Y        |        |                        |
| 52       | TOTALGASVOLUMETRICPRODUCTION   | 累计产气量         | NUMBER(12,3)   | 方       | Y        |        |                        |
| 53       | TOTALWATERVOLUMETRICPRODUCTION | 累计产水量         | NUMBER(12,3)   | 方       | Y        |        |                        |
| 54       | SAVETIME                       | 保存时间           | DATE           |          | Y        |        |                        |
| …        | …                              | …                  |                |          | Y        |        | 根据驱动自动生成的字段 |
| …        | …                              | …                  |                |          | Y        |        |                        |

### 1.2.54 tbl_pcpacqdata_hist

同tbl_pcpacqdata_latest

### 1.2.55 tbl_pcpdailycalculationdata

转速计产日汇总数据表

| **序号** | **代码**                       | **名称**       | **类型**       | **单位** | **为空** | **键** | **备注**      |
|----------|--------------------------------|----------------|----------------|----------|----------|--------|---------------|
| 1        | ID                             | 记录编号       | NUMBER(10)     |          | N        | 主键   |               |
| 2        | DEVICEID                       | 设备编号       | NUMBER(10)     |          | N        |        |               |
| 3        | CALDATE                        | 汇总日期       | DATE           |          | Y        |        |               |
| 4        | COMMSTATUS                     | 通信状态       | NUMBER(2)      |          | Y        |        | 0-离线 1-在线 |
| 5        | COMMTIME                       | 在线时间       | NUMBER(8,2)    |          | Y        |        |               |
| 6        | COMMTIMEEFFICIENCY             | 在线时率       | NUMBER(10,4)   |          | Y        |        |               |
| 7        | COMMRANGE                      | 在线区间       | CLOB           |          | Y        |        |               |
| 8        | RUNSTATUS                      | 运行状态       | NUMBER(2)      |          | Y        |        |               |
| 9        | RUNTIMEEFFICIENCY              | 运行时率       | NUMBER(8,2)    |          | Y        |        |               |
| 10       | RUNTIME                        | 运行时间       | NUMBER(10,4)   |          | Y        |        |               |
| 11       | RUNRANGE                       | 运行区间       | CLOB           |          | Y        |        |               |
| 12       | RPM                            | 转速           | NUMBER(8,2)    |          | Y        |        |               |
| 13       | RESULTCODE                     | 工况代码       | NUMBER(4)      |          | Y        |        |               |
| 14       | THEORETICALPRODUCTION          | 理论排量       | NUMBER(8,2)    |          | Y        |        |               |
| 15       | LIQUIDVOLUMETRICPRODUCTION     | 产液量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 16       | OILVOLUMETRICPRODUCTION        | 产油量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 17       | WATERVOLUMETRICPRODUCTION      | 产水量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 18       | VOLUMEWATERCUT                 | 体积含水率     | NUMBER(8,2)    |          | Y        |        |               |
| 19       | LIQUIDWEIGHTPRODUCTION         | 产液量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 20       | OILWEIGHTPRODUCTION            | 产油量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 21       | WATERWEIGHTPRODUCTION          | 产水量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 22       | WEIGHTWATERCUT                 | 重量含水率     | NUMBER(8,2)    |          | Y        |        |               |
| 23       | SYSTEMEFFICIENCY               | 系统效率       | NUMBER(12,3)   |          | Y        |        |               |
| 24       | ENERGYPER100MLIFT              | 吨液百米耗电量 | NUMBER(8,2)    |          | Y        |        |               |
| 25       | PUMPEFF1                       | 容积效率       | NUMBER(12,3)   |          | Y        |        |               |
| 26       | PUMPEFF2                       | 液体收缩系数   | NUMBER(12,3)   |          | Y        |        |               |
| 27       | PUMPEFF                        | 总泵效         | NUMBER(12,3)   |          | Y        |        |               |
| 28       | TOTALKWATTH                    | 累计电量       | NUMBER(12,3)   |          | Y        |        |               |
| 29       | TODAYKWATTH                    | 日用电量       | NUMBER(12,3)   |          | Y        |        |               |
| 30       | TUBINGPRESSURE                 | 油压           | NUMBER(8,2)    | MPa      | Y        |        |               |
| 31       | CASINGPRESSURE                 | 套压           | NUMBER(8,2)    | MPa      | Y        |        |               |
| 32       | BOTTOMHOLEPRESSURE             | 井底压力       | NUMBER(8,2)    | MPa      | Y        |        |               |
| 33       | PRODUCINGFLUIDLEVEL            | 动液面         | NUMBER(8,2)    | m        | Y        |        |               |
| 34       | PUMPSETTINGDEPTH               | 泵挂           | NUMBER(8,2)    | m        | Y        |        |               |
| 35       | SUBMERGENCE                    | 沉没度         | NUMBER(8,2)    | m        | Y        |        |               |
| 36       | GASVOLUMETRICPRODUCTION        | 日产气量       | NUMBER(8,2)    | 方       | Y        |        |               |
| 37       | TOTALGASVOLUMETRICPRODUCTION   | 累计产气量     | NUMBER(8,2)    | 方       | Y        |        |               |
| 38       | TOTALWATERVOLUMETRICPRODUCTION | 累计产水量     | NUMBER(8,2)    | 方       | Y        |        |               |
| 39       | HEADERLABELINFO                | 报表表头信息   | VARCHAR2(4000) |          | Y        |        |               |
| 40       | EXTENDEDDAYS                   | 沿用天数       | NUMBER(5)      |          | Y        |        |               |
| 41       | RESULTSTATUS                   | 计算状态       | NUMBER(2)      |          | Y        |        |               |
| 42       | REMARK                         | 备注           | VARCHAR2(4000) |          | Y        |        |               |

### 1.2.56 tbl_pcptimingcalculationdata

转速计产定时汇总数据表

| **序号** | **代码**                       | **名称**       | **类型**       | **单位** | **为空** | **键** | **备注**      |
|----------|--------------------------------|----------------|----------------|----------|----------|--------|---------------|
| 1        | ID                             | 记录编号       | NUMBER(10)     |          | N        | 主键   |               |
| 2        | DEVICEID                       | 设备编号       | NUMBER(10)     |          | N        |        |               |
| 3        | CALTIME                        | 计算时间       | DATE           |          | Y        |        |               |
| 4        | COMMSTATUS                     | 通信状态       | NUMBER(2)      |          | Y        |        | 0-离线 1-在线 |
| 5        | COMMTIME                       | 在线时间       | NUMBER(8,2)    |          | Y        |        |               |
| 6        | COMMTIMEEFFICIENCY             | 在线时率       | NUMBER(10,4)   |          | Y        |        |               |
| 7        | COMMRANGE                      | 在线区间       | CLOB           |          | Y        |        |               |
| 8        | RUNSTATUS                      | 运行状态       | NUMBER(2)      |          | Y        |        |               |
| 9        | RUNTIMEEFFICIENCY              | 运行时率       | NUMBER(8,2)    |          | Y        |        |               |
| 10       | RUNTIME                        | 运行时间       | NUMBER(10,4)   |          | Y        |        |               |
| 11       | RUNRANGE                       | 运行区间       | CLOB           |          | Y        |        |               |
| 12       | RPM                            | 转速           | NUMBER(8,2)    |          | Y        |        |               |
| 13       | RESULTCODE                     | 工况代码       | NUMBER(4)      |          | Y        |        |               |
| 14       | THEORETICALPRODUCTION          | 理论排量       | NUMBER(8,2)    |          | Y        |        |               |
| 15       | LIQUIDVOLUMETRICPRODUCTION     | 产液量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 16       | OILVOLUMETRICPRODUCTION        | 产油量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 17       | WATERVOLUMETRICPRODUCTION      | 产水量         | NUMBER(8,2)    | 方       | Y        |        |               |
| 18       | VOLUMEWATERCUT                 | 体积含水率     | NUMBER(8,2)    |          | Y        |        |               |
| 19       | LIQUIDWEIGHTPRODUCTION         | 产液量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 20       | OILWEIGHTPRODUCTION            | 产油量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 21       | WATERWEIGHTPRODUCTION          | 产水量         | NUMBER(8,2)    | 吨       | Y        |        |               |
| 22       | WEIGHTWATERCUT                 | 重量含水率     | NUMBER(8,2)    |          | Y        |        |               |
| 23       | SYSTEMEFFICIENCY               | 系统效率       | NUMBER(12,3)   |          | Y        |        |               |
| 24       | ENERGYPER100MLIFT              | 吨液百米耗电量 | NUMBER(8,2)    |          | Y        |        |               |
| 25       | PUMPEFF1                       | 容积效率       | NUMBER(12,3)   |          | Y        |        |               |
| 26       | PUMPEFF2                       | 液体收缩系数   | NUMBER(12,3)   |          | Y        |        |               |
| 27       | PUMPEFF                        | 总泵效         | NUMBER(12,3)   |          | Y        |        |               |
| 28       | TOTALKWATTH                    | 累计电量       | NUMBER(12,3)   |          | Y        |        |               |
| 29       | TODAYKWATTH                    | 日用电量       | NUMBER(12,3)   |          | Y        |        |               |
| 30       | TUBINGPRESSURE                 | 油压           | NUMBER(8,2)    | MPa      | Y        |        |               |
| 31       | CASINGPRESSURE                 | 套压           | NUMBER(8,2)    | MPa      | Y        |        |               |
| 32       | BOTTOMHOLEPRESSURE             | 井底压力       | NUMBER(8,2)    | MPa      | Y        |        |               |
| 33       | PRODUCINGFLUIDLEVEL            | 动液面         | NUMBER(8,2)    | m        | Y        |        |               |
| 34       | PUMPSETTINGDEPTH               | 泵挂           | NUMBER(8,2)    | m        | Y        |        |               |
| 35       | SUBMERGENCE                    | 沉没度         | NUMBER(8,2)    | m        | Y        |        |               |
| 36       | GASVOLUMETRICPRODUCTION        | 日产气量       | NUMBER(8,2)    | 方       | Y        |        |               |
| 37       | TOTALGASVOLUMETRICPRODUCTION   | 累计产气量     | NUMBER(8,2)    | 方       | Y        |        |               |
| 38       | TOTALWATERVOLUMETRICPRODUCTION | 累计产水量     | NUMBER(8,2)    | 方       | Y        |        |               |
| 39       | HEADERLABELINFO                | 报表表头信息   | VARCHAR2(4000) |          | Y        |        |               |
| 40       | EXTENDEDDAYS                   | 沿用天数       | NUMBER(5)      |          | Y        |        |               |
| 41       | RESULTSTATUS                   | 计算状态       | NUMBER(2)      |          | Y        |        |               |
| 42       | REMARK                         | 备注           | VARCHAR2(4000) |          | Y        |        |               |

### 1.2.57 tbl_deviceoperationlog

设备操作日志表

| **序号** | **代码**   | **名称**     | **类型** | **单位** | **为空** | **键** | **备注**                                    |
|----------|------------|--------------|----------|----------|----------|--------|---------------------------------------------|
| 1        | ID         | 记录编号     | NUMBER   |          | N        | 主键   |                                             |
| 2        | DEVICENAME | 设备名称     | VARCHAR2 |          | Y        |        |                                             |
| 3        | CREATETIME | 创建时间     | DATE     |          | Y        |        |                                             |
| 4        | USER_ID    | 操作用户账号 | VARCHAR2 |          | Y        |        |                                             |
| 5        | LOGINIP    | 用户登录IP   | VARCHAR2 |          | Y        |        |                                             |
| 6        | ACTION     | 操作         | NUMBER   |          | Y        |        | 0-添加设备 1-修改设备 2-删除设备 3-控制设备 |
| 7        | DEVICETYPE | 设备类型     | NUMBER   |          | Y        |        | 0-抽油机 1-螺杆泵                           |
| 8        | REMARK     | 备注         | VARCHAR2 |          | Y        |        |                                             |

### 1.2.58 tbl_systemlog

系统日志表

| **序号** | **代码**   | **名称** | **类型**      | **单位** | **为空** | **键** | **备注**              |
|----------|------------|----------|---------------|----------|----------|--------|-----------------------|
| 1        | ID         | 记录编号 | NUMBER(10)    |          | N        | 主键   |                       |
| 2        | CREATETIME | 创建时间 | DATE          |          | N        |        |                       |
| 3        | USER_ID    | 用户账号 | VARCHAR2(20)  |          | Y        |        |                       |
| 4        | LOGINIP    | 登录IP   | VARCHAR2(20)  | m        | Y        |        |                       |
| 5        | ACTION     | 操作     | NUMBER(2)     | 次/min   | Y        |        | 0-用户登录 1-用户退出 |
| 6        | REMARK     | 备注     | VARCHAR2(200) | kN       | Y        |        |                       |

### 1.2.59 tbl_resourcemonitoring

资源监测数据表

| **序号** | **代码**        | **名称**         | **类型**     | **单位** | **为空** | **键** | **备注** |
|----------|-----------------|------------------|--------------|----------|----------|--------|----------|
| 1        | ID              | 记录编号         | NUMBER(10)   |          | N        | 主键   |          |
| 2        | ACQTIME         | 采集时间         | DATE         |          | Y        |        |          |
| 3        | APPRUNSTATUS    | AC运行状态       | NUMBER(2)    |          | Y        |        |          |
| 4        | APPVERSION      | AC版本信息       | VARCHAR2(50) |          | Y        |        |          |
| 5        | ADRUNSTATUS     | 驱动程序运行状态 | NUMBER(2)    |          | Y        |        |          |
| 6        | ADVERSION       | 驱动程序版本信息 | VARCHAR2(50) |          | Y        |        |          |
| 7        | CPUUSEDPERCENT  | CPU使用率        | VARCHAR2(50) | %        | Y        |        |          |
| 8        | MEMUSEDPERCENT  | 内存使用率       | NUMBER(8,2)  | %        | Y        |        |          |
| 9        | TABLESPACESIZE  | 数据库表空间大小 | NUMBER(10,2) | Mb       | Y        |        |          |
| 10       | JEDISSTATUS     | REDIS状态        | NUMBER(2)    |          | Y        |        |          |
| 11       | CACHEMAXMEMORY  | REDIS最大内存    | NUMBER(20)   | kb       | Y        |        |          |
| 12       | CACHEUSEDMEMORY | REDIS已用内存    | NUMBER(20)   | kb       | Y        |        |          |

### 1.2.60 tbl_dbmonitoring

数据库资源监测数据表

| **序号** | **代码**              | **名称**                   | **类型**     | **单位** | **为空** | **键** | **备注**                                                        |
|----------|-----------------------|----------------------------|--------------|----------|----------|--------|-----------------------------------------------------------------|
| 1        | ID                    | 记录编号                   | NUMBER(10)   |          | N        | 主键   |                                                                 |
| 2        | ACQTIME               | 采集时间                   | DATE         |          | Y        |        |                                                                 |
| 3        | CONNSTATUS            | 连接状态                   | NUMBER(2)    |          | Y        |        |                                                                 |
| 4        | TABLESPACEUSEDSIZE    | 表空间已使用大小           | NUMBER(10,2) |          | Y        |        |                                                                 |
| 5        | TABLESPACEUSEDPERCENT | 表空间已使用百分比         | NUMBER(10,2) |          | Y        |        |                                                                 |
| 6        | TABLESIZE             | 具体到每个表的所占空间大小 | CLOB         |          | Y        |        | Json字符换，记录每个表数据大小、lob大小，索引大小，以及总记录数 |

### 1.2.61 tbl_videokey

视频秘钥表

| **序号** | **代码** | **名称** | **类型**      | **单位** | **为空** | **键** | **备注** |
|----------|----------|----------|---------------|----------|----------|--------|----------|
| 1        | ID       | 记录编号 | NUMBER(10)    |          | N        | 主键   |          |
| 2        | ORGID    | 单位编号 | NUMBER(10)    |          | Y        |        |          |
| 3        | ACCOUNT  | 账号名称 | VARCHAR2(200) |          | Y        |        |          |
| 4        | APPKEY   | APPKEY   | VARCHAR2(400) |          | Y        |        |          |
| 5        | SECRET   | SECRET   | VARCHAR2(400) |          | Y        |        |          |

# 二、视图

## 2.1 概览

表2-1 视图概览

| **序号** | **名称**                     | **描述**               |
|----------|------------------------------|------------------------|
| 1        | viw_org                      | 单位组织视图           |
| 2        | viw_devicetypeinfo           | 设备类型视图           |
| 3        | viw_role                     | 角色视图               |
| 4        | viw_device                   | 设备信息视图           |
| 5        | viw_smsdevice                | 短信设备信息视图       |
| 6        | viw_acqrawdata               | 原始采集数据视图       |
| 7        | viw_alarminfo_latest         | 报警实时数据视图       |
| 8        | viw_alarminfo_latest         | 报警历史数据视图       |
| 9        | viw_timingcalculationdata    | 定时汇总数据视图       |
| 10       | viw_dailycalculationdata     | 日汇总数据视图         |
| 11       | viw_srpacqdata_hist          | 抽油机功图计算数据视图 |
| 12       | viw_srptimingcalculationdata | 抽油机定时汇总数据视图 |
| 13       | viw_srpdailycalculationdata  | 抽油机日汇总数据视图   |
| 14       | viw_srp_calculatemain        | 抽油机计算维护数据视图 |
| 15       | viw_srpacqdata_hist          | 螺杆泵转速计产数据视图 |
| 16       | viw_srptimingcalculationdata | 螺杆泵定时汇总数据视图 |
| 17       | viw_srpdailycalculationdata  | 螺杆泵日汇总数据视图   |
| 18       | viw_srp_calculatemain        | 螺杆泵计算维护数据视图 |
| 19       | viw_deviceoperationlog       | 设备操作日志视图       |
| 20       | viw_systemlog                | 系统日志视图           |

## 2.2 详述

### 2.2.1 viw_org

表2-2 单位组织视图

| **序号** | **代码**   | **名称**     | **类型** | **备注** |
|----------|------------|--------------|----------|----------|
| 1        | ORG_ID     | 单位序号     | NUMBER   |          |
| 2        | ORG_CODE   | 单位编码     | VARCHAR2 |          |
| 3        | ORG_NAME   | 单位名称     | VARCHAR2 |          |
| 4        | ORG_MEMO   | 单位说明     | VARCHAR2 |          |
| 5        | ORG_PARENT | 父级单位编号 | NUMBER   |          |
| 6        | ORG_SEQ    | 单位排序     | NUMBER   |          |
| 7        | ALLPATH    | 组织全路径   | VARCHAR2 |          |

### 2.2.2 viw_devicetypeinfo

表2-3 设备类型视图

| **序号** | **代码**      | **名称**       | **类型** | **备注** |
|----------|---------------|----------------|----------|----------|
| 1        | ID            | 序号           | NUMBER   |          |
| 2        | PARENTID      | 父节点序号     | NUMBER   |          |
| 3        | SORTNUM       | 排序序号       | NUMBER   |          |
| 4        | NAME_ZH_CN    | 中文名称       | VARCHAR2 |          |
| 5        | ALLPATH_ZH_CN | 全路径中文名称 | VARCHAR2 |          |
| 6        | NAME_EN       | 英文名称       | VARCHAR2 |          |
| 7        | ALLPATH_EN    | 全路径英文名称 | VARCHAR2 |          |
| 8        | NAME_RU       | 俄文名称       | VARCHAR2 |          |
| 9        | ALLPATH_RU    | 全路径俄文名称 | VARCHAR2 |          |

### 2.2.3 viw_role

表2-4 角色视图

| **序号** | **代码**          | **名称**         | **类型** | **备注** |
|----------|-------------------|------------------|----------|----------|
| 1        | ROLE_ID           | 角色ID           | NUMBER   |          |
| 2        | ROLE_NAME         | 角色名称         | VARCHAR2 |          |
| 3        | ROLE_LEVEL        | 角色等级         | NUMBER   |          |
| 4        | SHOWLEVEL         | 数据显示级别     | NUMBER   |          |
| 5        | ROLE_VIDEOKEYEDIT | 视频密钥编辑权限 | NUMBER   |          |
| 6        | ROLE_LANGUAGEEDIT | 语言编辑权限     | NUMBER   | 预留     |
| 7        | REMARK            | 备注             | VARCHAR2 |          |
| 8        | TABS              | 授权的设备类型   | CLOB     |          |

### 2.2.4 viw_device

表2-5 设备信息视图

| **序号** | **代码**                 | **名称**               | **类型** | **备注**                   |
|----------|--------------------------|------------------------|----------|----------------------------|
| 1        | ID                       | 记录编号               | NUMBER   |                            |
| 2        | ORGID                    | 组织编号               | NUMBER   |                            |
| 3        | ORGNAME_ZH_CN            | 组织中文名称           | VARCHAR2 |                            |
| 4        | ALLPATH_ZH_CN            | 组织中文全路径         | VARCHAR2 |                            |
| 5        | ORGNAME_EN               | 组织英文名称           | VARCHAR2 |                            |
| 6        | ALLPATH_EN               | 组织英文全路径         | VARCHAR2 |                            |
| 7        | ORGNAME_RU               | 组织俄文名称           | VARCHAR2 |                            |
| 8        | ALLPATH_RU               | 组织俄文全路径         | VARCHAR2 |                            |
| 9        | DEVICENAME               | 设备名称               | VARCHAR2 |                            |
| 10       | DEVICETYPE               | 设备类型               | NUMBER   |                            |
| 11       | DEVICETYPENAME_ZH_CN     | 设备类型中文名称       | VARCHAR2 |                            |
| 12       | DEVICETYPEALLPATH_ZH_CN  | 设备类型全路径中文名称 | VARCHAR2 |                            |
| 13       | DEVICETYPENAME_EN        | 设备类型英文名称       | VARCHAR2 |                            |
| 14       | DEVICETYPEALLPATH_EN     | 设备类型全路径英文名称 | VARCHAR2 |                            |
| 15       | DEVICETYPENAME_RU        | 设备类型俄文名称       | VARCHAR2 |                            |
| 16       | DEVICETYPEALLPATH_RU     | 设备类型全路径俄文名称 | VARCHAR2 |                            |
| 17       | CALCULATETYPE            | 计算类型               | NUMBER   | 1-功图计算 2-转速计产 0-无 |
| 18       | APPLICATIONSCENARIOS     | 应用场景               | NUMBER   |                            |
| 19       | TCPTYPE                  | 下位机TCP类型          | VARCHAR2 |                            |
| 20       | SIGNINID                 | 注册包ID               | VARCHAR2 |                            |
| 21       | IPPORT                   | 下位机ip和端口         | VARCHAR2 |                            |
| 22       | SLAVE                    | 设备从地址             | VARCHAR2 |                            |
| 23       | PEAKDELAY                | 错峰延时               | NUMBER   |                            |
| 24       | VIDEOURL1                | 视频1监控路径          | VARCHAR2 |                            |
| 25       | VIDEOURL2                | 视频2监控路径          | VARCHAR2 |                            |
| 26       | VIDEOKEYID1              | 视频1秘钥编号          | NUMBER   |                            |
| 27       | VIDEOKEYID2              | 视频2秘钥编号          | NUMBER   |                            |
| 28       | VIDEOKEYNAME1            | 视频1秘钥账号名称      | VARCHAR2 |                            |
| 29       | VIDEOKEYNAME2            | 视频2秘钥账号名称      | VARCHAR2 |                            |
| 30       | VIDEOACCESSTOKEN         | 视频访问令牌           | VARCHAR2 | 预留                       |
| 31       | INSTANCECODE             | 采控实例编码           | VARCHAR2 |                            |
| 32       | INSTANCENAME             | 采控实例名称           | VARCHAR2 |                            |
| 33       | ALARMINSTANCECODE        | 报警实例编码           | VARCHAR2 |                            |
| 34       | ALARMINSTANCENAME        | 报警实例名称           | VARCHAR2 |                            |
| 35       | DISPLAYINSTANCECODE      | 显示实例编码           | VARCHAR2 |                            |
| 36       | DISPLAYINSTANCENAME      | 显示实例名称           | VARCHAR2 |                            |
| 37       | REPORTINSTANCECODE       | 报表实例编码           | VARCHAR2 |                            |
| 38       | REPORTINSTANCENAME       | 报表实例名称           | VARCHAR2 |                            |
| 39       | STATUS                   | 状态                   | NUMBER   | 0-失效 1-使能              |
| 40       | STATUSNAME               | 状态名称               | VARCHAR2 | 失效或使能                 |
| 41       | PRODUCTIONDATA           | 生产数据               | VARCHAR2 |                            |
| 42       | BALANCEINFO              | 平衡块数据             | VARCHAR2 |                            |
| 43       | STROKE                   | 铭牌冲程               | NUMBER   |                            |
| 44       | SORTNUM                  | 排序编号               | NUMBER   |                            |
| 45       | PRODUCTIONDATAUPDATETIME | 生产数据更新时间       | DATE     |                            |

### 2.2.5 viw_smsdevice

表2-6 短信设备信息视图

| **序号** | **代码**      | **名称**     | **类型** | **备注** |
|----------|---------------|--------------|----------|----------|
| 1        | ID            | 记录编号     | NUMBER   |          |
| 2        | ORGNAME_ZH_CN | 组织中文名称 | VARCHAR2 |          |
| 3        | ORGNAME_EN    | 组织英文名称 | VARCHAR2 |          |
| 4        | ORGNAME_ZH_CN | 组织俄文名称 | VARCHAR2 |          |
| 5        | ORGID         | 组织编号     | NUMBER   |          |
| 6        | DEVICENAME    | 设备名称     | VARCHAR2 |          |
| 7        | SIGNINID      | 注册包ID     | VARCHAR2 |          |
| 8        | INSTANCECODE  | 短信实例编码 | VARCHAR2 |          |
| 9        | INSTANCENAME  | 短信实例名称 | VARCHAR2 |          |
| 10       | SORTNUM       | 排序编号     | NUMBER   |          |

### 2.2.6 viw_acqrawdata

表2-7 原始采集数据视图

| **序号** | **代码**             | **名称**           | **类型** | **备注**                                       |
|----------|----------------------|--------------------|----------|------------------------------------------------|
| 1        | ID                   | 记录编号           | NUMBER   |                                                |
| 2        | DEVICEID             | 设备编号           | NUMBER   |                                                |
| 3        | DEVICENAME           | 设备名称           | VARCHAR2 |                                                |
| 4        | DEVICETYPE           | 设备类型           | NUMBER   |                                                |
| 5        | DEVICETYPENAME_ZH_CN | 设备类型中文名称   | VARCHAR2 |                                                |
| 6        | DEVICETYPENAME_EN    | 设备类型英文名称   | VARCHAR2 |                                                |
| 7        | DEVICETYPENAME_RU    | 设备类型俄文名称   | VARCHAR2 |                                                |
| 8        | SIGNINID             | 设备注册包ID       | VARCHAR2 |                                                |
| 9        | SLAVE                | 设备从地址         | VARCHAR2 |                                                |
| 10       | ACQTIME              | 采集时间           | DATE     |                                                |
| 11       | RAWDATA              | 原始数据           | VARCHAR2 | 采集的设备上传的未解析的原始数据，16进制字符串 |
| 12       | ORGID                | 设备组织编号       | NUMBER   |                                                |
| 13       | ALLPATH_ZH_CN        | 设备组织中文全路径 | VARCHAR2 |                                                |
| 14       | ALLPATH_EN           | 设备组织英文全路径 | VARCHAR2 |                                                |
| 15       | ALLPATH_RU           | 设备组织俄文全路径 | VARCHAR2 |                                                |

### 2.2.7 viw_alarminfo_latest

表2-8 报警实时数据视图

| **序号** | **代码**             | **名称**         | **类型** | **备注**      |
|----------|----------------------|------------------|----------|---------------|
| 1        | ID                   | 记录编号         | NUMBER   |               |
| 2        | DEVICEID             | 设备编号         | NUMBER   |               |
| 3        | DEVICENAME           | 设备名称         | VARCHAR2 |               |
| 4        | DEVICETYPE           | 设备类型         | NUMBER   | 0或1          |
| 5        | DEVICETYPENAME_ZH_CN | 设备类型中文名称 | VARCHAR2 |               |
| 6        | DEVICETYPENAME_EN    | 设备类型英文名称 | VARCHAR2 |               |
| 7        | DEVICETYPENAME_RU    | 设备类型俄文名称 | VARCHAR2 |               |
| 8        | ALARMTIME            | 报警时间         | DATE     |               |
| 9        | ITEMNAME             | 报警项           | VARCHAR2 |               |
| 10       | ALARMTYPE            | 报警类型         | NUMBER   |               |
| 11       | ALARMVALUE           | 报警值           | NUMBER   |               |
| 12       | ALARMINFO            | 报警信息         | VARCHAR2 |               |
| 13       | ALARMLIMIT           | 报警限值         | NUMBER   |               |
| 14       | HYSTERSIS            | 回差             | NUMBER   |               |
| 15       | ALARMLEVEL           | 报警级别         | NUMBER   | 100、200、300 |
| 16       | ISSENDMESSAGE        | 是否发送短信     | NUMBER   |               |
| 17       | ISSENDMAIL           | 是否发送邮件     | NUMBER   |               |
| 18       | RECOVERYTIME         | 恢复时间         | DATE     |               |
| 19       | ORGID                | 组织编号         | NUMBER   |               |

### 2.2.8 viw_alarminfo_hist

报警历史数据视图，结构同viw_alarminfo_latest

### 2.2.9 viw_timingcalculationdata

表2-9 定时汇总计算数据视图

| **序号** | **代码**           | **名称**     | **类型** | **备注**      |
|----------|--------------------|--------------|----------|---------------|
| 1        | ID                 | 记录编号     | NUMBER   |               |
| 2        | DEVICENAME         | 设备名称     | VARCHAR2 |               |
| 3        | DEVICEID           | 设备编号     | NUMBER   |               |
| 4        | DEVICETYPE         | 设备类型     | NUMBER   | 0或1          |
| 5        | CALTIME            | 计算时间     | DATE     |               |
| 6        | COMMSTATUS         | 通信状态     | NUMBER   |               |
| 7        | COMMSTATUSNAME     | 通信状态名称 | VARCHAR2 |               |
| 8        | COMMTIME           | 在线时间     | NUMBER   |               |
| 9        | COMMTIMEEFFICIENCY | 在线时率     | NUMBER   |               |
| 10       | COMMRANGE          | 在线区间     | CLOB     |               |
| 11       | RUNSTATUS          | 运行状态     | NUMBER   |               |
| 12       | RUNSTATUSNAME      | 运行状态名称 | VARCHAR2 |               |
| 13       | RUNTIME            | 运行时间     | NUMBER   |               |
| 14       | RUNRANGE           | 运行区间     | CLOB     |               |
| 15       | RUNTIMEEFFICIENCY  | 运行时率     | NUMBER   | 100、200、300 |
| 16       | HEADERLABELINFO    | 报表表头信息 | VARCHAR2 |               |
| 17       | CALDATA            | 计算数据     | CLOB     |               |
| 18       | REPORTINSTANCECODE | 报表实例代码 | VARCHAR2 |               |
| 19       | SORTNUM            | 排序编号     | NUMBER   |               |
| 20       | ORG_CODE           | 组织代码     | VARCHAR2 |               |
| 21       | ORG_ID             | 组织ID       | NUMBER   |               |
| 22       | REMARK             | 备注         | VARCHAR2 |               |

### 2.2.10 viw_dailycalculationdata

表2-10 日汇总计算数据视图

| **序号** | **代码**           | **名称**     | **类型** | **备注**      |
|----------|--------------------|--------------|----------|---------------|
| 1        | ID                 | 记录编号     | NUMBER   |               |
| 2        | DEVICENAME         | 设备名称     | VARCHAR2 |               |
| 3        | DEVICEID           | 设备编号     | NUMBER   |               |
| 4        | DEVICETYPE         | 设备类型     | NUMBER   | 0或1          |
| 5        | CALDATE            | 计算日期     | DATE     |               |
| 6        | COMMSTATUS         | 通信状态     | NUMBER   |               |
| 7        | COMMSTATUSNAME     | 通信状态名称 | VARCHAR2 |               |
| 8        | COMMTIME           | 在线时间     | NUMBER   |               |
| 9        | COMMTIMEEFFICIENCY | 在线时率     | NUMBER   |               |
| 10       | COMMRANGE          | 在线区间     | CLOB     |               |
| 11       | RUNSTATUS          | 运行状态     | NUMBER   |               |
| 12       | RUNSTATUSNAME      | 运行状态名称 | VARCHAR2 |               |
| 13       | RUNTIME            | 运行时间     | NUMBER   |               |
| 14       | RUNRANGE           | 运行区间     | CLOB     |               |
| 15       | RUNTIMEEFFICIENCY  | 运行时率     | NUMBER   | 100、200、300 |
| 16       | HEADERLABELINFO    | 报表表头信息 | VARCHAR2 |               |
| 17       | CALDATA            | 计算数据     | CLOB     |               |
| 18       | REPORTINSTANCECODE | 报表实例代码 | VARCHAR2 |               |
| 19       | SORTNUM            | 排序编号     | NUMBER   |               |
| 20       | ORG_CODE           | 组织代码     | VARCHAR2 |               |
| 21       | ORG_ID             | 组织ID       | NUMBER   |               |
| 22       | REMARK             | 备注         | VARCHAR2 |               |

### 2.2.11 viw_srpacqdata_hist

表2-11 抽油机历史数据视图

| **序号** | **代码**                       | **名称**                 | **类型** | **备注** |
|----------|--------------------------------|--------------------------|----------|----------|
| 1        | ID                             | 记录编号                 | NUMBER   |          |
| 2        | DEVICEID                       | 设备编号                 | NUMBER   |          |
| 3        | ORGID                          | 设备组织ID               | NUMBER   |          |
| 4        | CALCULATETYPE                  | 计算类型                 | NUMBER   |          |
| 5        | ACQTIME                        | 采集时间                 | DATE     |          |
| 6        | COMMSTATUS                     | 通信状态                 | NUMBER   |          |
| 7        | COMMTIME                       | 在线时间                 | NUMBER   |          |
| 8        | COMMTIMEEFFICIENCY             | 在线时率                 | NUMBER   |          |
| 9        | COMMRANGE                      | 在线区间                 | CLOB     |          |
| 10       | RUNSTATUS                      | 运行状态                 | NUMBER   |          |
| 11       | RUNTIMEEFFICIENCY              | 运行时率                 | NUMBER   |          |
| 12       | RUNTIME                        | 运行时间                 | NUMBER   |          |
| 13       | RUNRANGE                       | 运行区间                 | CLOB     |          |
| 14       | PRODUCTIONDATA                 | 生产数据                 | VARCHAR2 |          |
| 15       | RESULTCODE                     | 工况代码                 | NUMBER   |          |
| 16       | STROKE                         | 冲程                     | NUMBER   |          |
| 17       | SPM                            | 冲次                     | NUMBER   |          |
| 18       | FMAX                           | 最大载荷                 | NUMBER   |          |
| 19       | FMIN                           | 最小载荷                 | NUMBER   |          |
| 20       | UPSTROKEIMAX                   | 上冲程最大电流           | NUMBER   |          |
| 21       | DOWNSTROKEIMAX                 | 下冲程最大电流           | NUMBER   |          |
| 22       | UPSTROKEWATTMAX                | 上冲程最大功率           | NUMBER   |          |
| 23       | DOWNSTROKEWATTMAX              | 下冲程最大功率           | NUMBER   |          |
| 24       | IDEGREEBALANCE                 | 电流平衡度               | NUMBER   |          |
| 25       | WATTDEGREEBALANCE              | 功率平衡度               | NUMBER   |          |
| 26       | DELTARADIUS                    | 移动距离                 | NUMBER   |          |
| 27       | FULLNESSCOEFFICIENT            | 充满系数                 | NUMBER   |          |
| 28       | NOLIQUIDFULLNESSCOEFFICIENT    | 抽空充满系数             | NUMBER   |          |
| 29       | PLUNGERSTROKE                  | 柱塞冲程                 | NUMBER   |          |
| 30       | AVAILABLEPLUNGERSTROKE         | 柱塞有效冲程             | NUMBER   |          |
| 31       | NOLIQUIDAVAILABLEPLUNGERSTROKE | 抽空柱塞有效冲程         | NUMBER   |          |
| 32       | UPPERLOADLINE                  | 理论上载荷               | NUMBER   |          |
| 33       | UPPERLOADLINEOFEXACT           | 考虑沉没压力的理论上载荷 | NUMBER   |          |
| 34       | LOWERLOADLINE                  | 理论下载荷               | NUMBER   |          |
| 35       | SMAXINDEX                      | 位移最大值索引           | NUMBER   |          |
| 36       | SMININDEX                      | 位移最小值索引           | NUMBER   |          |
| 37       | THEORETICALPRODUCTION          | 理论排量                 | NUMBER   |          |
| 38       | LIQUIDVOLUMETRICPRODUCTION     | 产液量                   | NUMBER   |          |
| 39       | OILVOLUMETRICPRODUCTION        | 产油量                   | NUMBER   |          |
| 40       | WATERVOLUMETRICPRODUCTION      | 产水量                   | NUMBER   |          |
| 41       | AVAILABLEPLUNGERSTROKEPROD_V   | 有效冲程计算产量         | NUMBER   |          |
| 42       | PUMPCLEARANCELEAKPROD_V        | 泵间隙漏失量             | NUMBER   |          |
| 43       | TVLEAKVOLUMETRICPRODUCTION     | 游动凡尔漏失量           | NUMBER   |          |
| 44       | SVLEAKVOLUMETRICPRODUCTION     | 固定凡尔漏失量           | NUMBER   |          |
| 45       | GASINFLUENCEPROD_V             | 气影响                   | NUMBER   |          |
| 46       | LIQUIDVOLUMETRICPRODUCTION_L   | 日累计产液量             | NUMBER   |          |
| 47       | OILVOLUMETRICPRODUCTION_L      | 日累计产油量             | NUMBER   |          |
| 48       | WATERVOLUMETRICPRODUCTION_L    | 日累计产水量             | NUMBER   |          |
| 49       | LIQUIDWEIGHTPRODUCTION         | 产液量                   | NUMBER   |          |
| 50       | OILWEIGHTPRODUCTION            | 产油量                   | NUMBER   |          |
| 51       | WATERWEIGHTPRODUCTION          | 产水量                   | NUMBER   |          |
| 52       | AVAILABLEPLUNGERSTROKEPROD_W   | 有效冲程计算产量         | NUMBER   |          |
| 53       | PUMPCLEARANCELEAKPROD_W        | 泵间隙漏失量             | NUMBER   |          |
| 54       | TVLEAKWEIGHTPRODUCTION         | 游动凡尔漏失量           | NUMBER   |          |
| 55       | SVLEAKWEIGHTPRODUCTION         | 固定凡尔漏失量           | NUMBER   |          |
| 56       | GASINFLUENCEPROD_W             | 气影响                   | NUMBER   |          |
| 57       | LIQUIDWEIGHTPRODUCTION_L       | 日累计产液量             | NUMBER   |          |
| 58       | OILWEIGHTPRODUCTION_L          | 日累计产油量             | NUMBER   |          |
| 59       | WATERWEIGHTPRODUCTION_L        | 日累计产水量             | NUMBER   |          |
| 60       | LEVELDIFFERENCEVALUE           | 反演液面校正值           | NUMBER   |          |
| 61       | CALCPRODUCINGFLUIDLEVEL        | 反演液面                 | NUMBER   |          |
| 62       | SUBMERGENCE                    | 沉没度                   | NUMBER   |          |
| 63       | AVERAGEWATT                    | 有功功率                 | NUMBER   |          |
| 64       | POLISHRODPOWER                 | 光杆功率                 | NUMBER   |          |
| 65       | WATERPOWER                     | 水功率                   | NUMBER   |          |
| 66       | SURFACESYSTEMEFFICIENCY        | 地面效率                 | NUMBER   |          |
| 67       | WELLDOWNSYSTEMEFFICIENCY       | 井下效率                 | NUMBER   |          |
| 68       | SYSTEMEFFICIENCY               | 系统效率                 | NUMBER   |          |
| 69       | ENERGYPER100MLIFT              | 吨液百米耗电量           | NUMBER   |          |
| 70       | AREA                           | 功图面积                 | NUMBER   |          |
| 71       | RODFLEXLENGTH                  | 抽油杆伸长量             | NUMBER   |          |
| 72       | TUBINGFLEXLENGTH               | 油管伸缩值               | NUMBER   |          |
| 73       | INERTIALENGTH                  | 惯性载荷增量             | NUMBER   |          |
| 74       | PUMPEFF1                       | 冲程损失系数             | NUMBER   |          |
| 75       | PUMPEFF2                       | 充满系数                 | NUMBER   |          |
| 76       | PUMPEFF3                       | 间隙漏失系数             | NUMBER   |          |
| 77       | PUMPEFF4                       | 液体收缩系数             | NUMBER   |          |
| 78       | PUMPEFF                        | 总泵效                   | NUMBER   |          |
| 79       | PUMPINTAKEP                    | 泵入口压力               | NUMBER   |          |
| 80       | PUMPINTAKET                    | 泵入口温度               | NUMBER   |          |
| 81       | PUMPINTAKEGOL                  | 泵入口就地气液比         | NUMBER   |          |
| 82       | PUMPINTAKEVISL                 | 泵入口粘度               | NUMBER   |          |
| 83       | PUMPINTAKEBO                   | 泵入口原油体积系数       | NUMBER   |          |
| 84       | PUMPOUTLETP                    | 泵出口压力               | NUMBER   |          |
| 85       | PUMPOUTLETT                    | 泵出口温度               | NUMBER   |          |
| 86       | PUMPOUTLETGOL                  | 泵出口就地气液比         | NUMBER   |          |
| 87       | PUMPOUTLETVISL                 | 泵出口粘度               | NUMBER   |          |
| 88       | PUMPOUTLETBO                   | 泵出口原油体积系数       | NUMBER   |          |

### 2.2.12 viw_srptimingcalculationdata

表2-12 抽油机定时汇总数据视图

| **序号** | **代码**                           | **名称**           | **类型** | **备注** |
|----------|------------------------------------|--------------------|----------|----------|
| 1        | ID                                 | 记录编号           | NUMBER   |          |
| 2        | DEVICENAME                         | 设备名称           | VARCHAR2 |          |
| 3        | DEVICEID                           | 设备编号           | NUMBER   |          |
| 4        | DEVICETYPE                         | 设备类型           | NUMBER   |          |
| 5        | CALTIME                            | 计算时间           | DATE     |          |
| 6        | EXTENDEDDAYS                       | 功图延用天数       | NUMBER   |          |
| 7        | ACQUISITIONDATE                    | 功图采集日期       | DATE     |          |
| 8        | COMMSTATUS                         | 通信状态           | NUMBER   |          |
| 9        | COMMSTATUSNAME                     | 通信状态名称       | VARCHAR2 |          |
| 10       | COMMTIME                           | 在线时间           | NUMBER   |          |
| 11       | COMMTIMEEFFICIENCY                 | 在线时率           | NUMBER   |          |
| 12       | COMMRANGE                          | 在线区间           | CLOB     |          |
| 13       | RUNSTATUS                          | 运行状态           | NUMBER   |          |
| 14       | RUNSTATUSNAME                      | 运行状态名称       | VARCHAR2 |          |
| 15       | RUNTIMEEFFICIENCY                  | 运行时率           | NUMBER   |          |
| 16       | RUNTIME                            | 运行时间           | CLOB     |          |
| 17       | RUNRANGE                           | 运行区间           | NUMBER   |          |
| 18       | RESULTCODE                         | 工况代码           | NUMBER   |          |
| 19       | RESULTSTRING                       | 工况字符串         | CLOB     |          |
| 20       | THEORETICALPRODUCTION              | 理论排量           | NUMBER   |          |
| 21       | LIQUIDVOLUMETRICPRODUCTION         | 产液量             | NUMBER   |          |
| 22       | OILVOLUMETRICPRODUCTION            | 产油量             | NUMBER   |          |
| 23       | WATERVOLUMETRICPRODUCTION          | 产水量             | NUMBER   |          |
| 24       | VOLUMEWATERCUT                     | 体积含水率         | NUMBER   |          |
| 25       | LIQUIDWEIGHTPRODUCTION             | 产液量             | NUMBER   |          |
| 26       | OILWEIGHTPRODUCTION                | 产油量             | NUMBER   |          |
| 27       | WATERWEIGHTPRODUCTION              | 产水量             | NUMBER   |          |
| 28       | WEIGHTWATERCUT                     | 重量含水率         | NUMBER   |          |
| 29       | STROKE                             | 冲程               | NUMBER   |          |
| 30       | SPM                                | 冲次               | NUMBER   |          |
| 31       | FMAX                               | 最大载荷           | NUMBER   |          |
| 32       | FMIN                               | 最小载荷           | NUMBER   |          |
| 33       | FULLNESSCOEFFICIENT                | 充满系数           | NUMBER   |          |
| 34       | PUMPEFF1                           | 冲程损失系数       | NUMBER   |          |
| 35       | PUMPEFF2                           | 充满系数           | NUMBER   |          |
| 36       | PUMPEFF3                           | 间隙漏失系数       | NUMBER   |          |
| 37       | PUMPEFF4                           | 液体收缩系数       | NUMBER   |          |
| 38       | PUMPEFF                            | 总泵效             | NUMBER   |          |
| 39       | SURFACESYSTEMEFFICIENCY            | 地面效率           | NUMBER   |          |
| 40       | WELLDOWNSYSTEMEFFICIENCY           | 井下效率           | NUMBER   |          |
| 41       | SYSTEMEFFICIENCY                   | 系统效率           | NUMBER   |          |
| 42       | ENERGYPER100MLIFT                  | 吨液百米耗电量     | NUMBER   |          |
| 43       | TODAYKWATTH                        | 日用电量           | NUMBER   |          |
| 44       | IDEGREEBALANCE                     | 电流平衡度         | NUMBER   |          |
| 45       | WATTDEGREEBALANCE                  | 功率平衡度         | NUMBER   |          |
| 46       | DELTARADIUS                        | 移动距离           | NUMBER   |          |
| 47       | TUBINGPRESSURE                     | 油压               | NUMBER   |          |
| 48       | CASINGPRESSURE                     | 套压               | NUMBER   |          |
| 49       | BOTTOMHOLEPRESSURE                 | 井底压力           | NUMBER   |          |
| 50       | PUMPSETTINGDEPTH                   | 泵挂               | NUMBER   |          |
| 51       | PRODUCINGFLUIDLEVEL                | 动液面             | NUMBER   |          |
| 52       | CALCPRODUCINGFLUIDLEVEL            | 反演动液面         | NUMBER   |          |
| 53       | LEVELDIFFERENCEVALUE               | 液面反演差值       | NUMBER   |          |
| 54       | SUBMERGENCE                        | 沉没度             | NUMBER   |          |
| 55       | GASVOLUMETRICPRODUCTION            | 日产气量           | NUMBER   |          |
| 56       | TOTALGASVOLUMETRICPRODUCTION       | 累计产气量         | NUMBER   |          |
| 57       | TOTALWATERVOLUMETRICPRODUCTION     | 累计产水量         | NUMBER   |          |
| 58       | REALTIMELIQUIDVOLUMETRICPRODUCTION | 瞬时产液量(m\^3/d) | NUMBER   |          |
| 59       | REALTIMEOILVOLUMETRICPRODUCTION    | 瞬时产油量(m\^3/d) | NUMBER   |          |
| 60       | REALTIMEWATERVOLUMETRICPRODUCTION  | 瞬时产水量(m\^3/d) | NUMBER   |          |
| 61       | REALTIMEGASVOLUMETRICPRODUCTION    | 瞬时产气量(m\^3/d) | NUMBER   |          |
| 62       | REALTIMELIQUIDWEIGHTPRODUCTION     | 瞬时产液量(t/d)    | NUMBER   |          |
| 63       | REALTIMEOILWEIGHTPRODUCTION        | 瞬时产油量(t/d)    | NUMBER   |          |
| 64       | REALTIMEWATERWEIGHTPRODUCTION      | 瞬时产水量(t/d)    | NUMBER   |          |
| 65       | RPM                                | 转速               | NUMBER   |          |
| 66       | HEADERLABELINFO                    | 报表表头信息       | VARCHAR2 |          |
| 67       | REPORTINSTANCECODE                 | 报表实例编码       | VARCHAR2 |          |
| 68       | SORTNUM                            | 设备排序编号       | NUMBER   |          |
| 69       | ORG_CODE                           | 组织编码           | VARCHAR2 |          |
| 70       | ORG_ID                             | 组织ID             | NUMBER   |          |
| 71       | REMARK                             | 备注               | VARCHAR2 |          |
| 72       | RESERVEDCOL1                       | 备用1              | VARCHAR2 |          |
| 73       | RESERVEDCOL2                       | 备用2              | VARCHAR2 |          |
| 74       | RESERVEDCOL3                       | 备用3              | VARCHAR2 |          |
| 75       | RESERVEDCOL4                       | 备用4              | VARCHAR2 |          |
| 76       | RESERVEDCOL5                       | 备用5              | VARCHAR2 |          |

### 2.2.13 viw_srpdailycalculationdata

表2-13 抽油机日汇总视图

| **序号** | **代码**                       | **名称**       | **类型** | **备注** |
|----------|--------------------------------|----------------|----------|----------|
| 1        | ID                             | 记录编号       | NUMBER   |          |
| 2        | DEVICENAME                     | 设备名称       | VARCHAR2 |          |
| 3        | DEVICEID                       | 设备编号       | NUMBER   |          |
| 4        | DEVICETYPE                     | 设备类型       | NUMBER   |          |
| 5        | CALDATE                        | 汇总日期       | DATE     |          |
| 6        | EXTENDEDDAYS                   | 沿用天数       | NUMBER   |          |
| 7        | ACQUISITIONDATE                | 功图采集日期   | DATE     |          |
| 8        | COMMSTATUS                     | 通信状态       | NUMBER   |          |
| 9        | COMMSTATUSNAME                 | 通信状态名称   | VARCHAR2 |          |
| 10       | COMMTIME                       | 在线时间       | NUMBER   |          |
| 11       | COMMTIMEEFFICIENCY             | 在线时率       | NUMBER   |          |
| 12       | COMMRANGE                      | 在线区间       | CLOB     |          |
| 13       | RUNSTATUS                      | 运行状态       | NUMBER   |          |
| 14       | RUNSTATUSNAME                  | 运行状态名称   | VARCHAR2 |          |
| 15       | RUNTIMEEFFICIENCY              | 运行时率       | NUMBER   |          |
| 16       | RUNTIME                        | 运行时间       | CLOB     |          |
| 17       | RUNTIMEEFFICIENCY              | 运行区间       | NUMBER   |          |
| 18       | RESULTCODE                     | 工况代码       | NUMBER   |          |
| 19       | RESULTSTRING                   | 工况字符串     | CLOB     |          |
| 20       | THEORETICALPRODUCTION          | 理论排量       | NUMBER   |          |
| 21       | LIQUIDWEIGHTPRODUCTION         | 产液量         | NUMBER   |          |
| 22       | OILWEIGHTPRODUCTION            | 产油量         | NUMBER   |          |
| 23       | WATERWEIGHTPRODUCTION          | 产水量         | NUMBER   |          |
| 24       | WEIGHTWATERCUT                 | 体积含水率     | NUMBER   |          |
| 25       | LIQUIDVOLUMETRICPRODUCTION     | 产液量         | NUMBER   |          |
| 26       | OILVOLUMETRICPRODUCTION        | 产油量         | NUMBER   |          |
| 27       | WATERVOLUMETRICPRODUCTION      | 产水量         | NUMBER   |          |
| 28       | VOLUMEWATERCUT                 | 重量含水率     | NUMBER   |          |
| 29       | STROKE                         | 冲程           | NUMBER   |          |
| 30       | SPM                            | 冲次           | NUMBER   |          |
| 31       | FMAX                           | 最大载荷       | NUMBER   |          |
| 32       | FMIN                           | 最小载荷       | NUMBER   |          |
| 33       | FULLNESSCOEFFICIENT            | 充满系数       | NUMBER   |          |
| 34       | PUMPEFF                        | 总泵效         | NUMBER   |          |
| 35       | PUMPEFF1                       | 冲程损失系数   | NUMBER   |          |
| 36       | PUMPEFF2                       | 充满系数       | NUMBER   |          |
| 37       | PUMPEFF3                       | 间隙漏失系数   | NUMBER   |          |
| 38       | PUMPEFF4                       | 液体收缩系数   | NUMBER   |          |
| 39       | SYSTEMEFFICIENCY               | 系统效率       | NUMBER   |          |
| 40       | SURFACESYSTEMEFFICIENCY        | 地面效率       | NUMBER   |          |
| 41       | WELLDOWNSYSTEMEFFICIENCY       | 井下效率       | NUMBER   |          |
| 42       | ENERGYPER100MLIFT              | 吨液百米耗电量 | NUMBER   |          |
| 43       | TODAYKWATTH                    | 日用电量       | NUMBER   |          |
| 44       | IDEGREEBALANCE                 | 电流平衡度     | NUMBER   |          |
| 45       | WATTDEGREEBALANCE              | 功率平衡度     | NUMBER   |          |
| 46       | DELTARADIUS                    | 移动距离       | NUMBER   |          |
| 47       | TUBINGPRESSURE                 | 油压           | NUMBER   |          |
| 48       | CASINGPRESSURE                 | 套压           | NUMBER   |          |
| 49       | BOTTOMHOLEPRESSURE             | 井底压力       | NUMBER   |          |
| 50       | PRODUCINGFLUIDLEVEL            | 动液面         | NUMBER   |          |
| 51       | PUMPSETTINGDEPTH               | 泵挂           | NUMBER   |          |
| 52       | SUBMERGENCE                    | 沉没度         | NUMBER   |          |
| 53       | CALCPRODUCINGFLUIDLEVEL        | 反演动液面     | NUMBER   |          |
| 54       | LEVELDIFFERENCEVALUE           | 液面反演差值   | NUMBER   |          |
| 55       | GASVOLUMETRICPRODUCTION        | 日产气量       | NUMBER   |          |
| 56       | TOTALGASVOLUMETRICPRODUCTION   | 累计产气量     | NUMBER   |          |
| 57       | TOTALWATERVOLUMETRICPRODUCTION | 累计产水量     | NUMBER   |          |
| 58       | RPM                            | 转速           | NUMBER   |          |
| 59       | HEADERLABELINFO                | 报表表头信息   | VARCHAR2 |          |
| 60       | REPORTINSTANCECODE             | 报表示例代码   | VARCHAR2 |          |
| 61       | SORTNUM                        | 排序编号       | NUMBER   |          |
| 62       | ORG_CODE                       | 组织编码       | VARCHAR2 |          |
| 63       | ORG_ID                         | 组织编号       | NUMBER   |          |
| 64       | REMARK                         | 备注           | VARCHAR2 |          |

### 2.2.14 viw_srp_calculatemain

表2-14 抽油机计算维护视图

| **序号** | **代码**                   | **名称**       | **类型** | **备注** |
|----------|----------------------------|----------------|----------|----------|
| 1        | ID                         | 记录编号       | NUMBER   |          |
| 2        | DEVICENAME                 | 设备名称       | VARCHAR2 |          |
| 3        | DEVICEID                   | 设备编号       | NUMBER   |          |
| 4        | ACQTIME                    | 采集时间       | DATE     |          |
| 5        | FESDIAGRAMACQTIME          | 功图采集时间   | DATE     |          |
| 6        | RESULTSTATUS               | 计算状态       | NUMBER   |          |
| 7        | RESULTCODE                 | 工况代码       | NUMBER   |          |
| 8        | LIQUIDWEIGHTPRODUCTION     | 产液量         | NUMBER   |          |
| 9        | OILWEIGHTPRODUCTION        | 产油量         | NUMBER   |          |
| 10       | WATERWEIGHTPRODUCTION      | 产水量         | NUMBER   |          |
| 11       | LIQUIDVOLUMETRICPRODUCTION | 产液量         | NUMBER   |          |
| 12       | OILVOLUMETRICPRODUCTION    | 产油量         | NUMBER   |          |
| 13       | WATERVOLUMETRICPRODUCTION  | 产水量         | NUMBER   |          |
| 14       | LEVELDIFFERENCEVALUE       | 反演液面校正值 | NUMBER   |          |
| 15       | CALCPRODUCINGFLUIDLEVEL    | 反演液面       | NUMBER   |          |
| 16       | PRODUCTIONDATA             | 生产数据       | NUMBER   |          |
| 17       | ORG_ID                     | 组织编号       | NUMBER   |          |

### 2.2.15 viw_pcpacqdata_hist

表2-15 螺杆泵历史数据视图

| **序号** | **代码**                     | **名称**           | **类型** | **备注** |
|----------|------------------------------|--------------------|----------|----------|
| 1        | ID                           | 记录编号           | NUMBER   |          |
| 2        | DEVICEID                     | 设备编号           | NUMBER   |          |
| 3        | ORGID                        | 设备组织ID         | NUMBER   |          |
| 4        | CALCULATETYPE                | 计算类型           | NUMBER   |          |
| 5        | ACQTIME                      | 采集时间           | DATE     |          |
| 6        | COMMSTATUS                   | 通信状态           | NUMBER   |          |
| 7        | COMMTIME                     | 在线时间           | NUMBER   |          |
| 8        | COMMTIMEEFFICIENCY           | 在线时率           | NUMBER   |          |
| 9        | COMMRANGE                    | 在线区间           | CLOB     |          |
| 10       | RUNSTATUS                    | 运行状态           | NUMBER   |          |
| 11       | RUNTIMEEFFICIENCY            | 运行时率           | NUMBER   |          |
| 12       | RUNTIME                      | 运行时间           | NUMBER   |          |
| 13       | RUNRANGE                     | 运行区间           | CLOB     |          |
| 14       | PRODUCTIONDATA               | 生产数据           | VARCHAR2 |          |
| 15       | THEORETICALPRODUCTION        | 理论排量           | NUMBER   |          |
| 16       | LIQUIDVOLUMETRICPRODUCTION   | 产液量             | NUMBER   |          |
| 17       | OILVOLUMETRICPRODUCTION      | 产油量             | NUMBER   |          |
| 18       | WATERVOLUMETRICPRODUCTION    | 产水量             | NUMBER   |          |
| 19       | LIQUIDVOLUMETRICPRODUCTION_L | 日累计产液量       | NUMBER   |          |
| 20       | OILVOLUMETRICPRODUCTION_L    | 日累计产油量       | NUMBER   |          |
| 21       | WATERVOLUMETRICPRODUCTION_L  | 日累计产水量       | NUMBER   |          |
| 22       | LIQUIDWEIGHTPRODUCTION       | 产液量             | NUMBER   |          |
| 23       | OILWEIGHTPRODUCTION          | 产油量             | NUMBER   |          |
| 24       | WATERWEIGHTPRODUCTION        | 产水量             | NUMBER   |          |
| 25       | LIQUIDWEIGHTPRODUCTION_L     | 日累计产液量       | NUMBER   |          |
| 26       | OILWEIGHTPRODUCTION_L        | 日累计产油量       | NUMBER   |          |
| 27       | WATERWEIGHTPRODUCTION_L      | 日累计产水量       | NUMBER   |          |
| 28       | AVERAGEWATT                  | 有功功率           | NUMBER   |          |
| 29       | WATERPOWER                   | 水功率             | NUMBER   |          |
| 30       | SYSTEMEFFICIENCY             | 系统效率           | NUMBER   |          |
| 31       | PUMPEFF                      | 总泵效             | NUMBER   |          |
| 32       | PUMPEFF1                     | 容积效率           | NUMBER   |          |
| 33       | PUMPEFF2                     | 液体收缩系数       | NUMBER   |          |
| 34       | PUMPINTAKEP                  | 泵入口压力         | NUMBER   |          |
| 35       | PUMPINTAKET                  | 泵入口温度         | NUMBER   |          |
| 36       | PUMPINTAKEGOL                | 泵入口就地气液比   | NUMBER   |          |
| 37       | PUMPINTAKEVISL               | 泵入口粘度         | NUMBER   |          |
| 38       | PUMPINTAKEBO                 | 泵入口原油体积系数 | NUMBER   |          |
| 39       | PUMPOUTLETP                  | 泵出口压力         | NUMBER   |          |
| 40       | PUMPOUTLETT                  | 泵出口温度         | NUMBER   |          |
| 41       | PUMPOUTLETGOL                | 泵出口就地气液比   | NUMBER   |          |
| 42       | PUMPOUTLETVISL               | 泵出口粘度         | NUMBER   |          |
| 43       | PUMPOUTLETBO                 | 泵出口原油体积系数 | NUMBER   |          |

### 2.2.16 viw_pcptimingcalculationdata

表2-16 螺杆泵定时汇总视图

| **序号** | **代码**                           | **名称**           | **类型** | **备注** |
|----------|------------------------------------|--------------------|----------|----------|
| 1        | ID                                 | 记录编号           | NUMBER   |          |
| 2        | DEVICENAME                         | 设备名称           | VARCHAR2 |          |
| 3        | DEVICEID                           | 设备编号           | NUMBER   |          |
| 4        | DEVICETYPE                         | 设备类型           | NUMBER   |          |
| 5        | CALTIME                            | 计算时间           | DATE     |          |
| 6        | EXTENDEDDAYS                       | 沿用天数           | NUMBER   |          |
| 7        | ACQUISITIONDATE                    | 采集日期           | DATE     |          |
| 8        | COMMSTATUS                         | 通信状态           | NUMBER   |          |
| 9        | COMMSTATUSNAME                     | 通信状态名称       | VARCHAR2 |          |
| 10       | COMMTIME                           | 在线时间           | NUMBER   |          |
| 11       | COMMTIMEEFFICIENCY                 | 在线时率           | NUMBER   |          |
| 12       | COMMRANGE                          | 在线区间           | CLOB     |          |
| 13       | RUNSTATUS                          | 运行状态           | NUMBER   |          |
| 14       | RUNSTATUSNAME                      | 运行状态名称       | VARCHAR2 |          |
| 15       | RUNTIMEEFFICIENCY                  | 运行时率           | NUMBER   |          |
| 16       | RUNTIME                            | 运行时间           | CLOB     |          |
| 17       | RUNRANGE                           | 运行区间           | NUMBER   |          |
| 18       | RPM                                | 转速               | NUMBER   |          |
| 19       | THEORETICALPRODUCTION              | 理论排量           | NUMBER   |          |
| 20       | LIQUIDWEIGHTPRODUCTION             | 产液量             | NUMBER   |          |
| 21       | OILWEIGHTPRODUCTION                | 产油量             | NUMBER   |          |
| 22       | WATERWEIGHTPRODUCTION              | 产水量             | NUMBER   |          |
| 23       | WEIGHTWATERCUT                     | 体积含水率         | NUMBER   |          |
| 24       | LIQUIDVOLUMETRICPRODUCTION         | 产液量             | NUMBER   |          |
| 25       | OILVOLUMETRICPRODUCTION            | 产油量             | NUMBER   |          |
| 26       | WATERVOLUMETRICPRODUCTION          | 产水量             | NUMBER   |          |
| 27       | VOLUMEWATERCUT                     | 重量含水率         | NUMBER   |          |
| 28       | PUMPEFF                            | 总泵效             | NUMBER   |          |
| 29       | PUMPEFF1                           | 容积效率           | NUMBER   |          |
| 30       | PUMPEFF2                           | 液体收缩系数       | NUMBER   |          |
| 31       | SYSTEMEFFICIENCY                   | 系统效率           | NUMBER   |          |
| 32       | ENERGYPER100MLIFT                  | 吨液百米耗电量     | NUMBER   |          |
| 33       | TODAYKWATTH                        | 日用电量           | NUMBER   |          |
| 34       | TUBINGPRESSURE                     | 油压               | NUMBER   |          |
| 35       | CASINGPRESSURE                     | 套压               | NUMBER   |          |
| 36       | BOTTOMHOLEPRESSURE                 | 井底压力           | NUMBER   |          |
| 37       | PRODUCINGFLUIDLEVEL                | 动液面             | NUMBER   |          |
| 38       | PUMPSETTINGDEPTH                   | 泵挂               | NUMBER   |          |
| 39       | SUBMERGENCE                        | 沉没度             | NUMBER   |          |
| 40       | GASVOLUMETRICPRODUCTION            | 日产气量           | NUMBER   |          |
| 41       | TOTALGASVOLUMETRICPRODUCTION       | 累计产气量         | NUMBER   |          |
| 42       | TOTALWATERVOLUMETRICPRODUCTION     | 累计产水量         | NUMBER   |          |
| 43       | REALTIMELIQUIDVOLUMETRICPRODUCTION | 瞬时产液量(m\^3/d) | NUMBER   |          |
| 44       | REALTIMEOILVOLUMETRICPRODUCTION    | 瞬时产油量(m\^3/d) | NUMBER   |          |
| 45       | REALTIMEWATERVOLUMETRICPRODUCTION  | 瞬时产水量(m\^3/d) | NUMBER   |          |
| 46       | REALTIMEGASVOLUMETRICPRODUCTION    | 瞬时产气量(m\^3/d) | NUMBER   |          |
| 47       | REALTIMELIQUIDWEIGHTPRODUCTION     | 瞬时产液量(t/d)    | NUMBER   |          |
| 48       | REALTIMEOILWEIGHTPRODUCTION        | 瞬时产油量(t/d)    | NUMBER   |          |
| 49       | REALTIMEWATERWEIGHTPRODUCTION      | 瞬时产水量(t/d)    | NUMBER   |          |
| 50       | HEADERLABELINFO                    | 报表表头信息       | VARCHAR2 |          |
| 51       | REPORTINSTANCECODE                 | 报表示例代码       | VARCHAR2 |          |
| 52       | SORTNUM                            | 排序编号           | NUMBER   |          |
| 53       | ORG_CODE                           | 组织编码           | VARCHAR2 |          |
| 54       | ORG_ID                             | 组织编号           | NUMBER   |          |
| 55       | REMARK                             | 备注               | VARCHAR2 |          |
| 56       | RESERVEDCOL1                       | 备用1              | VARCHAR2 |          |
| 57       | RESERVEDCOL2                       | 备用2              | VARCHAR2 |          |
| 58       | RESERVEDCOL3                       | 备用3              | VARCHAR2 |          |
| 59       | RESERVEDCOL4                       | 备用4              | VARCHAR2 |          |
| 60       | RESERVEDCOL5                       | 备用5              | VARCHAR2 |          |

### 2.2.17 viw_pcpdailycalculationdata

表2-17 螺杆泵日汇总视图

| **序号** | **代码**                       | **名称**       | **类型** | **备注** |
|----------|--------------------------------|----------------|----------|----------|
| 1        | ID                             | 记录编号       | NUMBER   |          |
| 2        | DEVICENAME                     | 设备名称       | VARCHAR2 |          |
| 3        | DEVICEID                       | 设备编号       | NUMBER   |          |
| 4        | DEVICETYPE                     | 设备类型       | NUMBER   |          |
| 5        | CALDATE                        | 汇总日期       | DATE     |          |
| 6        | EXTENDEDDAYS                   | 沿用天数       | NUMBER   |          |
| 7        | ACQUISITIONDATE                | 采集日期       | DATE     |          |
| 8        | COMMSTATUS                     | 通信状态       | NUMBER   |          |
| 9        | COMMSTATUSNAME                 | 通信状态名称   | VARCHAR2 |          |
| 10       | COMMTIME                       | 在线时间       | NUMBER   |          |
| 11       | COMMTIMEEFFICIENCY             | 在线时率       | NUMBER   |          |
| 12       | COMMRANGE                      | 在线区间       | CLOB     |          |
| 13       | RUNSTATUS                      | 运行状态       | NUMBER   |          |
| 14       | RUNSTATUSNAME                  | 运行状态名称   | VARCHAR2 |          |
| 15       | RUNTIMEEFFICIENCY              | 运行时率       | NUMBER   |          |
| 16       | RUNTIME                        | 运行时间       | CLOB     |          |
| 17       | RUNRANGE                       | 运行区间       | NUMBER   |          |
| 18       | RPM                            | 转速           | NUMBER   |          |
| 19       | THEORETICALPRODUCTION          | 理论排量       | NUMBER   |          |
| 20       | LIQUIDWEIGHTPRODUCTION         | 产液量         | NUMBER   |          |
| 21       | OILWEIGHTPRODUCTION            | 产油量         | NUMBER   |          |
| 22       | WATERWEIGHTPRODUCTION          | 产水量         | NUMBER   |          |
| 23       | WEIGHTWATERCUT                 | 体积含水率     | NUMBER   |          |
| 24       | LIQUIDVOLUMETRICPRODUCTION     | 产液量         | NUMBER   |          |
| 25       | OILVOLUMETRICPRODUCTION        | 产油量         | NUMBER   |          |
| 26       | WATERVOLUMETRICPRODUCTION      | 产水量         | NUMBER   |          |
| 27       | VOLUMEWATERCUT                 | 重量含水率     | NUMBER   |          |
| 28       | PUMPEFF                        | 总泵效         | NUMBER   |          |
| 29       | PUMPEFF1                       | 容积效率       | NUMBER   |          |
| 30       | PUMPEFF2                       | 液体收缩系数   | NUMBER   |          |
| 31       | SYSTEMEFFICIENCY               | 系统效率       | NUMBER   |          |
| 32       | ENERGYPER100MLIFT              | 吨液百米耗电量 | NUMBER   |          |
| 33       | TODAYKWATTH                    | 日用电量       | NUMBER   |          |
| 34       | TUBINGPRESSURE                 | 油压           | NUMBER   |          |
| 35       | CASINGPRESSURE                 | 套压           | NUMBER   |          |
| 36       | BOTTOMHOLEPRESSURE             | 井底压力       | NUMBER   |          |
| 37       | PRODUCINGFLUIDLEVEL            | 动液面         | NUMBER   |          |
| 38       | PUMPSETTINGDEPTH               | 泵挂           | NUMBER   |          |
| 39       | SUBMERGENCE                    | 沉没度         | NUMBER   |          |
| 40       | GASVOLUMETRICPRODUCTION        | 日产气量       | NUMBER   |          |
| 41       | TOTALGASVOLUMETRICPRODUCTION   | 累计产气量     | NUMBER   |          |
| 42       | TOTALWATERVOLUMETRICPRODUCTION | 累计产水量     | NUMBER   |          |
| 43       | HEADERLABELINFO                | 报表表头信息   | VARCHAR2 |          |
| 44       | REPORTINSTANCECODE             | 报表实例代码   | VARCHAR2 |          |
| 45       | SORTNUM                        | 排序编号       | NUMBER   |          |
| 46       | ORG_CODE                       | 组织编码       | VARCHAR2 |          |
| 47       | ORG_ID                         | 组织编号       | NUMBER   |          |
| 48       | REMARK                         | 备注           | VARCHAR2 |          |

### 2.2.18 viw_pcp_calculatemain

表2-18 螺杆泵计算维护视图

| **序号** | **代码**                   | **名称** | **类型** | **备注** |
|----------|----------------------------|----------|----------|----------|
| 1        | ID                         | 记录编号 | NUMBER   |          |
| 2        | DEVICENAME                 | 设备名称 | VARCHAR2 |          |
| 3        | DEVICEID                   | 设备编号 | NUMBER   |          |
| 4        | ACQTIME                    | 采集时间 | DATE     |          |
| 5        | RESULTSTATUS               | 计算状态 | NUMBER   |          |
| 6        | LIQUIDWEIGHTPRODUCTION     | 产液量   | NUMBER   |          |
| 7        | OILWEIGHTPRODUCTION        | 产油量   | NUMBER   |          |
|          | WATERWEIGHTPRODUCTION      | 产水量   | NUMBER   |          |
| 8        | LIQUIDVOLUMETRICPRODUCTION | 产液量   | NUMBER   |          |
| 9        | OILVOLUMETRICPRODUCTION    | 产油量   | NUMBER   |          |
|          | WATERVOLUMETRICPRODUCTION  | 产水量   | NUMBER   |          |
| 10       | PRODUCTIONDATA             | 生产数据 | NUMBER   |          |
| 11       | RPM                        | 转速     | NUMBER   |          |
| 12       | ORG_ID                     | 组织编号 | NUMBER   |          |

### 2.2.19 viw_deviceoperationlog

表2-19 设备操作日志视图

| **序号** | **代码**       | **名称**     | **类型** | **备注**                               |
|----------|----------------|--------------|----------|----------------------------------------|
| 1        | ID             | 记录编号     | NUMBER   |                                        |
| 2        | DEVICETYPE     | 设备类型     | NUMBER   | 0或1                                   |
| 3        | DEVICETYPENAME | 设备类型名称 | VARCHAR2 | 抽油机或螺杆泵                         |
| 4        | DEVICENAME     | 设备名称     | VARCHAR2 |                                        |
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

### 2.2.20 viw_systemlog

表2-20 系统日志视图

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

# 三、存储过程

表3-1 存储过程概览

| **序号** | **名称**                       | **描述**                   | **备注**                                             |
|----------|--------------------------------|----------------------------|------------------------------------------------------|
| 1        | PRD_RESET_SEQUENCE             | 重置序列                   |                                                      |
| 2        | PRD_CLEAR_DATA                 | 清理数据并重置序列         | 执行后将删除抽油机、螺杆泵及相关配置数据和采集数据。 |
| 3        | PRD_SAVE_RPCDEVICE             | 保存抽油机数据             |                                                      |
| 4        | PRD_SAVE_PCPDEVICE             | 保存螺杆泵数据             |                                                      |
| 5        | PRD_SAVE_SMSDEVICE             | 保存短信设备数据           |                                                      |
| 6        | PRD_SAVE_PUMPINGMODEL          | 保存抽油机型号             |                                                      |
| 7        | PRD_UPDATE_RPCDEVICE           | 修改抽油机数据             |                                                      |
| 8        | PRD_UPDATE_PCPDEVICE           | 修改螺杆泵数据             |                                                      |
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
| 19       | PRD_SAVE_RPCALARMINFO          | 保存抽油机报警数据         |                                                      |
| 20       | PRD_SAVE_PCPALARMINFO          | 保存螺杆泵报警数据         |                                                      |
| 21       | PRD_SAVE_ALARMCOLOR            | 保存报警等级颜色           |                                                      |
| 22       | PRD_SAVE_DEVICEOPERATIONLOG    | 保存设备操作日志           |                                                      |
| 23       | PRD_SAVE_RESOURCEMONITORING    | 保存资源监测数据           |                                                      |
| 24       | PRD_SAVE_SYSTEMLOG             | 保存系统日志数据           |                                                      |
| 25       | PRD_INIT_DEVICE_DAILY          | 初始化汇总数据             |                                                      |

# 四、触发器

表4-1 触发器概览

| **序号** | **名称**                       | **描述**                               |
|----------|--------------------------------|----------------------------------------|
| 1        | TRG_B_PROTOCOL_I               | 协议表插入数据前触发                   |
| 2        | TRG_B_ACQ_GROUP2UNIT_CONF_I    | 采控单元和采控组关系表插入数据前触发   |
| 3        | TRG_B_ACQ_GROUP_CONF_I         | 采控组表插入数据前触发                 |
| 4        | TRG_B_ACQ_ITEM2GROUP_CONF_I    | 采控组和采控项项关系表插入数据前触发   |
| 5        | TRG_B_ACQ_UNIT_CONF_I          | 采控单元表插入数据前触发               |
| 6        | TRG_B_ALARM_ITEM2UNIT_CONF_I   | 报警单元和报警项关系表插入数据前触发   |
| 7        | TRG_B_ALARM_UNIT_CONF_I        | 报警单元表插入数据前触发               |
| 8        | TRG_B_DISPLAY_ITEM2UNIT_CONF_I | 显示单元和显示项关系表插入数据前触发   |
| 9        | TRG_B_DISPLAY_UNIT_CONF_I      | 显示单元表插入数据前触发               |
| 10       | TRG_B_REPORT_ITEM2UNIT_CONF_I  | 报表单元和报表项关系表插入数据前触发   |
| 11       | TRG_B_REPORT_UNIT_CONF_I       | 报表单元表插入数据前触发               |
| 12       | TRG_B_CODE_I                   | 代码表插入数据前触发                   |
| 13       | TRG_B_DATAMAPPING_I            | 字段映射表插入数据前触发               |
| 14       | TRG_B_DEVICEOPERATIONLOG_I     | 设备操作日志表插入数据前触发           |
| 15       | BEF_HIBERNATE_SEQUENCE_INSERT  | 数据字典项数据表插入数据前触发         |
| 16       | TRG_B_MODULE_I                 | 模块表插入数据前触发                   |
| 17       | TRG_B_MODULE2ROLE_I            | 模块和角色关系表插入数据前触发         |
| 18       | TRG_B_ORG_I_U                  | 组织表插入、修改数据前触发             |
| 19       | TRG_B_PCPACQDATA_HIST_I        | 螺杆泵历史数据表插入数据前触发         |
| 20       | TRG_B_PCPACQDATA_LATEST_I      | 螺杆泵实时数据表插入数据前触发         |
| 21       | TRG_B_PCPACQRAWDATA_I          | 螺杆泵原始采集数据表插入数据前触发     |
| 22       | TRG_B_PCPALARMINFO_HIST_I      | 螺杆泵报警历史数据表插入数据前触发触发 |
| 23       | TRG_B_PCPALARMINFO_L_I         | 螺杆泵报警实时数据表插入数据前触发触发 |
| 24       | TRG_B_PCPDAILY_I               | 螺杆泵汇总表插入数据后触发             |
| 25       | TRG_A_PCPDEVICE_I              | 螺杆泵信息表插入数据前触发             |
| 26       | TRG_B_PCPDEVICE_I              | 螺杆泵信息表插入数据后触发             |
| 27       | TRG_B_PCPDEVICEGRAPHSET_I      | 螺杆泵图形设置表插入数据前触发         |
| 28       | TRG_B_PROTOCOLALARMINSTANCE_I  | 报警实例表插入数据前触发               |
| 29       | TRG_B_PROTOCOLDISPLAYINST_I    | 显示实例表插入数据前触发               |
| 30       | TRG_B_PROTOCOLINSTANCE_I       | 采控实例表插入数据前触发               |
| 31       | TRG_B_PROTOCOLSMSINSTANCE_I    | 短信实例表插入数据前触发               |
| 32       | TRG_B_PROTOCOLREPORTINST_I     | 报表实例表插入数据前触发               |
| 33       | TRG_B_PUMPINGMODEL_I           | 抽油机型号表插入数据前触发             |
| 34       | TRG_B_RESOURCEMONITORING_I     | 资源监测表插入数据前触发               |
| 35       | TRG_B_ROLE_I                   | 角色表插入数据前触发                   |
| 36       | TRG_B_RPCACQDATA_HIST_I        | 抽油机历史数据表插入数据前触发         |
| 37       | TRG_B_RPCACQDATA_LATEST_I      | 抽油机实时数据表插入数据前触发         |
| 38       | TRG_B_RPCACQRAWDATA_I          | 抽油机原始采集数据表插入数据前触发     |
| 39       | TRG_B_RPCALARMINFO_HIST_I      | 抽油机报警历史数据表插入数据前触发触发 |
| 40       | TRG_B_RPCALARMINFO_LATEST_I    | 抽油机报警实时数据表插入数据前触发触发 |
| 41       | TRG_B_RPCDAILY_I               | 抽油机汇总表插入数据前触发             |
| 42       | TRG_A_RPCDEVICE_I              | 抽油机信息表插入数据后触发             |
| 43       | TRG_B_RPCDEVICE_I              | 抽油机信息表插入数据前触发             |
| 44       | TRG_B_RPCDEVICEGRAPHICSET_I    | 抽油机图形设置表插入数据前触发         |
| 45       | TRG_B_RPC_WORKTYPE_I_U         | 工况表插入数据后触发                   |
| 46       | TRG_B_SMSDEVICE_I              | 短信设备信息表插入数据后触发           |
| 47       | TRG_B_SYSTEMLOG_I              | 系统日志表插入数据前触发               |
| 48       | TRG_B_USER_I                   | 用户表插入数据前触发                   |
| 49       | TRG_B_RUNSTATUSCONFIG_I        | 运行状态配置表插入数据前触发           |
| 50       | TRG_B_VIDEOKEY_I               | 视频秘钥表插入数据前触发               |
