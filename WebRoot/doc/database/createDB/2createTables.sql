/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     2021-3-24                                    */
/*==============================================================*/


/*==============================================================*/
/* Table: TBL_A9RAWDATA_HIST                                    */
/*==============================================================*/
create table TBL_A9RAWDATA_HIST
(
  ID               NUMBER(10) not null,
  DEVICEID         VARCHAR2(200),
  ACQTIME          DATE,
  SIGNAL           NUMBER(8,2),
  DEVICEVER        VARCHAR2(50),
  INTERVAL         CLOB,
  A                CLOB,
  F                CLOB,
  WATT             CLOB,
  I                CLOB,
  TRANSFERINTERVEL NUMBER(10)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_A9RAWDATA_HIST
  add constraint PK_A9RAWDATA_HIST primary key (ID)
/
create index IDX_A9RAWDATA_HIST_KEY on TBL_A9RAWDATA_HIST (DEVICEID)
/
create index IDX_A9RAWDATA_HIST_TIME on TBL_A9RAWDATA_HIST (ACQTIME)
/

/*==============================================================*/
/* Table: TBL_A9RAWDATA_LATEST                                  */
/*==============================================================*/
create table TBL_A9RAWDATA_LATEST
(
  ID               NUMBER(10) not null,
  DEVICEID         VARCHAR2(200),
  ACQTIME          DATE,
  SIGNAL           NUMBER(8,2),
  DEVICEVER        VARCHAR2(50),
  INTERVAL         CLOB,
  A                CLOB,
  F                CLOB,
  WATT             CLOB,
  I                CLOB,
  TRANSFERINTERVEL NUMBER(10)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_A9RAWDATA_LATEST
  add constraint PK_A9RAWDATA_LATEST primary key (ID)
/
create index IDX_A9RAWDATA_LATEST_KEY on TBL_A9RAWDATA_LATEST (DEVICEID)
/
create index IDX_A9RAWDATA_LATEST_TIME on TBL_A9RAWDATA_LATEST (ACQTIME)
/

/*==============================================================*/
/* Table: TBL_ACQ_GROUP2UNIT_CONF                               */
/*==============================================================*/
create table TBL_ACQ_GROUP2UNIT_CONF
(
  ID      NUMBER(10) not null,
  GROUPID NUMBER(10) not null,
  MATRIX  VARCHAR2(8) not null,
  UNITID  NUMBER(10) not null
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ACQ_GROUP2UNIT_CONF
  add constraint PK_ACQ_UNIT_GROUP primary key (ID)
/
create index IDX_ACQ_UNIT_GROUP_GROUPID on TBL_ACQ_GROUP2UNIT_CONF (GROUPID)
/
create index IDX_ACQ_UNIT_GROUP_UNITID on TBL_ACQ_GROUP2UNIT_CONF (UNITID)
/

/*==============================================================*/
/* Table: TBL_ACQ_GROUP_CONF                                    */
/*==============================================================*/
create table TBL_ACQ_GROUP_CONF
(
  ID         NUMBER(10) not null,
  GROUP_CODE VARCHAR2(50) not null,
  GROUP_NAME VARCHAR2(50),
  ACQ_CYCLE  NUMBER(10) default 1,
  SAVE_CYCLE NUMBER(10) default 5,
  PROTOCOL   VARCHAR2(50),
  REMARK     VARCHAR2(2000)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ACQ_GROUP_CONF
  add constraint PK_ACQUISITIONGROUP primary key (ID)
/

/*==============================================================*/
/* Table: TBL_ACQ_ITEM2GROUP_CONF                               */
/*==============================================================*/
create table TBL_ACQ_ITEM2GROUP_CONF
(
  ID      NUMBER(10) not null,
  ITEMID  NUMBER(10),
  ITEMNAME VARCHAR2(100),
  ITEMCODE VARCHAR2(100),
  MATRIX  VARCHAR2(8),
  GROUPID NUMBER(10) not null
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ACQ_ITEM2GROUP_CONF
  add constraint PK_ACQ_GROUP_ITEM primary key (ID)
/
create index IDX_ACQ_GROUP_ITEM_GROUPID on TBL_ACQ_ITEM2GROUP_CONF (GROUPID)
/
create index IDX_ACQ_GROUP_ITEM_ITEMID on TBL_ACQ_ITEM2GROUP_CONF (ITEMID)
/

/*==============================================================*/
/* Table: TBL_ACQ_UNIT_CONF                                    */
/*==============================================================*/
create table TBL_ACQ_UNIT_CONF
(
  ID        NUMBER(10) not null,
  UNIT_CODE VARCHAR2(50) not null,
  UNIT_NAME VARCHAR2(50),
  PROTOCOL   VARCHAR2(50),
  REMARK    VARCHAR2(2000)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ACQ_UNIT_CONF
  add constraint PK_T_ACQUISITIONUNIT primary key (ID)
/

/*==============================================================*/
/* Table: TBL_CODE                                    */
/*==============================================================*/
create table TBL_CODE
(
  ID        NUMBER(10) not null,
  ITEMCODE  VARCHAR2(200),
  ITEMNAME  VARCHAR2(200),
  REMARK    VARCHAR2(200),
  STATE     NUMBER(10),
  ITEMVALUE VARCHAR2(20),
  TABLECODE VARCHAR2(200)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_CODE
  add constraint PK_T_CODE primary key (ID)
/
create index IDX_000_01_DM on TBL_CODE (ITEMVALUE)
/
create index IDX_000_01_SJXDM on TBL_CODE (ITEMCODE)
/
create index IDX_000_01_ZT on TBL_CODE (STATE)
/

/*==============================================================*/
/* Table: TBL_DIST_NAME                                    */
/*==============================================================*/
create table TBL_DIST_NAME
(
  SYSDATAID  VARCHAR2(32) not null,
  TENANTID   VARCHAR2(50),
  CNAME      VARCHAR2(50),
  ENAME      VARCHAR2(50),
  SORTS      NUMBER,
  STATUS     NUMBER,
  CREATOR    VARCHAR2(50),
  UPDATEUSER VARCHAR2(50),
  UPDATETIME DATE default sysdate not null,
  CREATEDATE DATE default sysdate
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DIST_NAME
  add constraint PK_SYSTEMDATAINFO primary key (SYSDATAID)
/

/*==============================================================*/
/* Table: TBL_DIST_ITEM                                    */
/*==============================================================*/
create table TBL_DIST_ITEM
(
  DATAITEMID VARCHAR2(32) not null,
  TENANTID   VARCHAR2(50),
  SYSDATAID  VARCHAR2(50),
  CNAME      VARCHAR2(50),
  ENAME      VARCHAR2(200),
  DATAVALUE  VARCHAR2(200),
  SORTS      NUMBER,
  STATUS     NUMBER,
  CREATOR    VARCHAR2(50),
  UPDATEUSER VARCHAR2(50),
  UPDATETIME DATE default sysdate,
  CREATEDATE DATE default sysdate
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DIST_ITEM
  add constraint PK_DATAITEMSINFO primary key (DATAITEMID)
/
alter table TBL_DIST_ITEM
  add constraint FK_PK_DATAITEMSINFO_SYSID foreign key (SYSDATAID)
  references TBL_DIST_NAME (SYSDATAID) on delete cascade
/

/*==============================================================*/
/* Table: TBL_MODULE                                    */
/*==============================================================*/
create table TBL_MODULE
(
  MD_ID       NUMBER(10) not null,
  MD_PARENTID NUMBER(10) default 0 not null,
  MD_NAME     VARCHAR2(100) not null,
  MD_SHOWNAME VARCHAR2(100),
  MD_URL      VARCHAR2(200),
  MD_CODE     VARCHAR2(200),
  MD_SEQ      NUMBER(20),
  MD_LEVEL    NUMBER(10),
  MD_FLAG     NUMBER(10),
  MD_ICON     VARCHAR2(100),
  MD_TYPE     NUMBER(1) default 0,
  MD_CONTROL  VARCHAR2(100)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_MODULE
  add constraint P_MD primary key (MD_ID)
/
create index INDEXD_MODULE_MDCODE on TBL_MODULE (MD_CODE)
/
create index INDEX_MODULE_PARENTID on TBL_MODULE (MD_PARENTID)
/

/*==============================================================*/
/* Table: TBL_ROLE                                    */
/*==============================================================*/
create table TBL_ROLE
(
  ROLE_ID   NUMBER(10) not null,
  ROLE_CODE VARCHAR2(50) not null,
  ROLE_NAME VARCHAR2(40) not null,
  ROLE_FLAG NUMBER(10),
  REMARK    VARCHAR2(2000)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ROLE
  add constraint PK_ROLE_ID primary key (ROLE_ID)
/
create index IDX_ROLE_CODE on TBL_ROLE (ROLE_CODE)
/

/*==============================================================*/
/* Table: TBL_MODULE2ROLE                                    */
/*==============================================================*/
create table TBL_MODULE2ROLE
(
  RM_MODULEID NUMBER(10) not null,
  RM_MATRIX   VARCHAR2(8) not null,
  RM_ID       NUMBER(10) not null,
  RM_ROLEID   NUMBER(10) not null
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_MODULE2ROLE
  add constraint PK_RM_ID primary key (RM_ID)
/
alter table TBL_MODULE2ROLE
  add constraint FK_ORG_MODULEID foreign key (RM_MODULEID)
  references TBL_MODULE (MD_ID) on delete cascade
/
alter table TBL_MODULE2ROLE
  add constraint FK_ORG_ROLEID foreign key (RM_ROLEID)
  references TBL_ROLE (ROLE_ID) on delete cascade
/
create index IDX_RM_MODULEID on TBL_MODULE2ROLE (RM_MODULEID)
/

/*==============================================================*/
/* Table: TBL_ORG                                    */
/*==============================================================*/
create table TBL_ORG
(
  ORG_ID     NUMBER(10) not null,
  ORG_CODE   VARCHAR2(20),
  ORG_NAME   VARCHAR2(100) not null,
  ORG_MEMO   VARCHAR2(4000),
  ORG_PARENT NUMBER(10) default 0 not null,
  ORG_SEQ    NUMBER(10),
  ORG_FLAG   CHAR(1) default '1',
  ORG_REALID NUMBER(10),
  ORG_LEVEL  NUMBER(1),
  ORG_TYPE   NUMBER(1) default '1',
  ORG_COORDX NUMBER(10,6) default 0.00,
  ORG_COORDY NUMBER(10,6) default 0.00,
  SHOW_LEVEL NUMBER(2) default 1
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ORG
  add constraint PK_SC_ORG primary key (ORG_ID)
/
create unique index IDX_ORG_CODE on TBL_ORG (ORG_CODE)
/
create index IDX_ORG_CODE_PARENT on TBL_ORG (ORG_CODE, ORG_PARENT)
/
create index IDX_ORG_PARENT on TBL_ORG (ORG_PARENT)
/

/*==============================================================*/
/* Table: TBL_PCP_DISCRETE_HIST                                    */
/*==============================================================*/
create table TBL_PCP_DISCRETE_HIST
(
  ID                       NUMBER(10) not null,
  WELLID                   NUMBER(10) not null,
  ACQTIME                  DATE,
  COMMSTATUS               NUMBER(2) default 0,
  COMMTIME                 NUMBER(8,2) default 0,
  COMMTIMEEFFICIENCY       NUMBER(10,4) default 0,
  RUNSTATUS                NUMBER(2) default 0,
  RUNTIMEEFFICIENCY        NUMBER(10,4) default 0,
  RUNTIME                  NUMBER(8,2) default 0,
  IA                       NUMBER(8,2),
  IB                       NUMBER(8,2),
  IC                       NUMBER(8,2),
  VA                       NUMBER(8,2),
  VB                       NUMBER(8,2),
  VC                       NUMBER(8,2),
  TOTALKWATTH              NUMBER(8,2),
  TOTALKVARH               NUMBER(8,2),
  WATTSUM                  NUMBER(8,2),
  VARSUM                   NUMBER(8,2),
  REVERSEPOWER             NUMBER(8,2),
  PFSUM                    NUMBER(8,2),
  FREQUENCYSETVALUE        NUMBER(8,2),
  FREQUENCYRUNVALUE        NUMBER(8,2),
  TUBINGPRESSURE           NUMBER(8,2),
  CASINGPRESSURE           NUMBER(8,2),
  BACKPRESSURE             NUMBER(8,2),
  WELLHEADFLUIDTEMPERATURE NUMBER(8,2),
  TODAYKWATTH              NUMBER(8,2) default 0,
  WORKINGCONDITIONCODE     NUMBER(4) default 0,
  IAALARM                  VARCHAR2(20) default '0/0/0/0',
  IBALARM                  VARCHAR2(20) default '0/0/0/0',
  ICALARM                  VARCHAR2(20) default '0/0/0/0',
  VAALARM                  VARCHAR2(20) default '0/0/0/0',
  VBALARM                  VARCHAR2(20) default '0/0/0/0',
  VCALARM                  VARCHAR2(20) default '0/0/0/0',
  IAUPLIMIT                NUMBER(8,2),
  IADOWNLIMIT              NUMBER(8,2),
  IBUPLIMIT                NUMBER(8,2),
  IBDOWNLIMIT              NUMBER(8,2),
  ICUPLIMIT                NUMBER(8,2),
  ICDOWNLIMIT              NUMBER(8,2),
  VAUPLIMIT                NUMBER(8,2),
  VADOWNLIMIT              NUMBER(8,2),
  VBUPLIMIT                NUMBER(8,2),
  VBDOWNLIMIT              NUMBER(8,2),
  VCUPLIMIT                NUMBER(8,2),
  VCDOWNLIMIT              NUMBER(8,2),
  IAZERO                   NUMBER(8,2),
  IBZERO                   NUMBER(8,2),
  ICZERO                   NUMBER(8,2),
  VAZERO                   NUMBER(8,2),
  VBZERO                   NUMBER(8,2),
  VCZERO                   NUMBER(8,2),
  TOTALPKWATTH             NUMBER(10,2),
  TOTALNKWATTH             NUMBER(10,2),
  TOTALPKVARH              NUMBER(10,2),
  TOTALNKVARH              NUMBER(10,2),
  TOTALKVAH                NUMBER(10,2),
  TODAYPKWATTH             NUMBER(10,2) default 0,
  TODAYNKWATTH             NUMBER(10,2) default 0,
  TODAYKVARH               NUMBER(10,2) default 0,
  TODAYPKVARH              NUMBER(10,2) default 0,
  TODAYNKVARH              NUMBER(10,2) default 0,
  TODAYKVAH                NUMBER(10,2),
  VASUM                    NUMBER(8,2),
  SIGNAL                   NUMBER(8,2),
  INTERVAL                 NUMBER(10),
  DEVICEVER                VARCHAR2(50),
  VAVG                     NUMBER(8,2),
  IAVG                     NUMBER(8,2),
  WATTA                    NUMBER(8,2),
  WATTB                    NUMBER(8,2),
  WATTC                    NUMBER(8,2),
  VARA                     NUMBER(8,2),
  VARB                     NUMBER(8,2),
  VARC                     NUMBER(8,2),
  VAA                      NUMBER(8,2),
  VAB                      NUMBER(8,2),
  VAC                      NUMBER(8,2),
  PFA                      NUMBER(8,2),
  PFB                      NUMBER(8,2),
  PFC                      NUMBER(8,2),
  COMMRANGE                CLOB,
  RUNRANGE                 CLOB,
  WORKINGCONDITIONSTRING   CLOB
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCP_DISCRETE_HIST
  add constraint FK_PCP_DISCRETE_HIST primary key (ID)
/
create index IDX_PCP_DISCRETE_HIST_CODE on TBL_PCP_DISCRETE_HIST (WORKINGCONDITIONCODE)
/
create index IDX_PCP_DISCRETE_HIST_COMM on TBL_PCP_DISCRETE_HIST (COMMSTATUS)
/
create index IDX_PCP_DISCRETE_HIST_COMMEFF on TBL_PCP_DISCRETE_HIST (COMMTIMEEFFICIENCY)
/
create index IDX_PCP_DISCRETE_HIST_ENERGY on TBL_PCP_DISCRETE_HIST (TOTALKWATTH)
/
create index IDX_PCP_DISCRETE_HIST_RUN on TBL_PCP_DISCRETE_HIST (RUNSTATUS)
/
create index IDX_PCP_DISCRETE_HIST_RUNEFF on TBL_PCP_DISCRETE_HIST (RUNTIMEEFFICIENCY)
/
create index IDX_PCP_DISCRETE_HIST_TIME on TBL_PCP_DISCRETE_HIST (ACQTIME)
/
create index IDX_PCP_DISCRETE_HIST_WELLID on TBL_PCP_DISCRETE_HIST (WELLID)
/

/*==============================================================*/
/* Table: TBL_PCP_DISCRETE_LATEST                                    */
/*==============================================================*/
create table TBL_PCP_DISCRETE_LATEST
(
  ID                       NUMBER(10) not null,
  WELLID                   NUMBER(10) not null,
  ACQTIME                  DATE,
  COMMSTATUS               NUMBER(2) default 0,
  COMMTIME                 NUMBER(8,2) default 0,
  COMMTIMEEFFICIENCY       NUMBER(10,4) default 0,
  RUNSTATUS                NUMBER(2) default 0,
  RUNTIMEEFFICIENCY        NUMBER(10,4) default 0,
  RUNTIME                  NUMBER(8,2) default 0,
  IA                       NUMBER(8,2),
  IB                       NUMBER(8,2),
  IC                       NUMBER(8,2),
  VA                       NUMBER(8,2),
  VB                       NUMBER(8,2),
  VC                       NUMBER(8,2),
  TOTALKWATTH              NUMBER(8,2),
  TOTALKVARH               NUMBER(8,2),
  WATTSUM                  NUMBER(8,2),
  VARSUM                   NUMBER(8,2),
  REVERSEPOWER             NUMBER(8,2),
  PFSUM                    NUMBER(8,2),
  FREQUENCYSETVALUE        NUMBER(8,2),
  FREQUENCYRUNVALUE        NUMBER(8,2),
  TUBINGPRESSURE           NUMBER(8,2),
  CASINGPRESSURE           NUMBER(8,2),
  BACKPRESSURE             NUMBER(8,2),
  WELLHEADFLUIDTEMPERATURE NUMBER(8,2),
  TODAYKWATTH              NUMBER(8,2) default 0,
  WORKINGCONDITIONCODE     NUMBER(4) default 0,
  IAALARM                  VARCHAR2(20) default '0/0/0/0',
  IBALARM                  VARCHAR2(20) default '0/0/0/0',
  ICALARM                  VARCHAR2(20) default '0/0/0/0',
  VAALARM                  VARCHAR2(20) default '0/0/0/0',
  VBALARM                  VARCHAR2(20) default '0/0/0/0',
  VCALARM                  VARCHAR2(20) default '0/0/0/0',
  IAUPLIMIT                NUMBER(8,2),
  IADOWNLIMIT              NUMBER(8,2),
  IBUPLIMIT                NUMBER(8,2),
  IBDOWNLIMIT              NUMBER(8,2),
  ICUPLIMIT                NUMBER(8,2),
  ICDOWNLIMIT              NUMBER(8,2),
  VAUPLIMIT                NUMBER(8,2),
  VADOWNLIMIT              NUMBER(8,2),
  VBUPLIMIT                NUMBER(8,2),
  VBDOWNLIMIT              NUMBER(8,2),
  VCUPLIMIT                NUMBER(8,2),
  VCDOWNLIMIT              NUMBER(8,2),
  IAZERO                   NUMBER(8,2),
  IBZERO                   NUMBER(8,2),
  ICZERO                   NUMBER(8,2),
  VAZERO                   NUMBER(8,2),
  VBZERO                   NUMBER(8,2),
  VCZERO                   NUMBER(8,2),
  TOTALPKWATTH             NUMBER(10,2),
  TOTALNKWATTH             NUMBER(10,2),
  TOTALPKVARH              NUMBER(10,2),
  TOTALNKVARH              NUMBER(10,2),
  TOTALKVAH                NUMBER(10,2),
  TODAYPKWATTH             NUMBER(10,2) default 0,
  TODAYNKWATTH             NUMBER(10,2) default 0,
  TODAYKVARH               NUMBER(10,2) default 0,
  TODAYPKVARH              NUMBER(10,2) default 0,
  TODAYNKVARH              NUMBER(10,2) default 0,
  TODAYKVAH                NUMBER(10,2),
  VASUM                    NUMBER(8,2),
  SIGNAL                   NUMBER(8,2),
  INTERVAL                 NUMBER(10),
  DEVICEVER                VARCHAR2(50),
  VAVG                     NUMBER(8,2),
  IAVG                     NUMBER(8,2),
  WATTA                    NUMBER(8,2),
  WATTB                    NUMBER(8,2),
  WATTC                    NUMBER(8,2),
  VARA                     NUMBER(8,2),
  VARB                     NUMBER(8,2),
  VARC                     NUMBER(8,2),
  VAA                      NUMBER(8,2),
  VAB                      NUMBER(8,2),
  VAC                      NUMBER(8,2),
  PFA                      NUMBER(8,2),
  PFB                      NUMBER(8,2),
  PFC                      NUMBER(8,2),
  COMMRANGE                CLOB,
  RUNRANGE                 CLOB,
  WORKINGCONDITIONSTRING   CLOB
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCP_DISCRETE_LATEST
  add constraint FK_PCP_DISCRETE_LATEST primary key (ID, WELLID)
/
create index IDX_PCP_DISCRETE_LATEST_CODE on TBL_PCP_DISCRETE_LATEST (WORKINGCONDITIONCODE)
/
create index IDX_PCP_DISCRETE_LATEST_COMM on TBL_PCP_DISCRETE_LATEST (COMMSTATUS)
/
create index IDX_PCP_DISCRETE_LATEST_COMMEF on TBL_PCP_DISCRETE_LATEST (COMMTIMEEFFICIENCY)
/
create index IDX_PCP_DISCRETE_LATEST_ENERGY on TBL_PCP_DISCRETE_LATEST (TOTALKWATTH)
/
create index IDX_PCP_DISCRETE_LATEST_RUN on TBL_PCP_DISCRETE_LATEST (RUNSTATUS)
/
create index IDX_PCP_DISCRETE_LATEST_RUNEFF on TBL_PCP_DISCRETE_LATEST (RUNTIMEEFFICIENCY)
/
create index IDX_PCP_DISCRETE_LATEST_TIME on TBL_PCP_DISCRETE_LATEST (ACQTIME)
/
create index IDX_PCP_DISCRETE_LATEST_WELLID on TBL_PCP_DISCRETE_LATEST (WELLID)
/

/*==============================================================*/
/* Table: TBL_PCP_PRODUCTIONDATA_HIST                                    */
/*==============================================================*/
create table TBL_PCP_PRODUCTIONDATA_HIST
(
  ID                         NUMBER(10) not null,
  WELLID                     NUMBER(10) not null,
  ACQTIME                    DATE default SYSDATE,
  LIFTINGTYPE                NUMBER(10) default 201,
  DISPLACEMENTTYPE           NUMBER(1) default 1,
  RUNTIME                    NUMBER(8,2) default 24,
  CRUDEOILDENSITY            NUMBER(16,2) default 0.85,
  WATERDENSITY               NUMBER(16,2) default 1,
  NATURALGASRELATIVEDENSITY  NUMBER(16,2) default 0,
  SATURATIONPRESSURE         NUMBER(16,2) default 0,
  RESERVOIRDEPTH             NUMBER(16,2) default 0,
  RESERVOIRTEMPERATURE       NUMBER(16,2) default 0,
  WATERCUT                   NUMBER(8,2) default 1,
  WATERCUT_W                 NUMBER(8,2) default 1,
  TUBINGPRESSURE             NUMBER(8,2) default 0.5,
  CASINGPRESSURE             NUMBER(8,2) default 0.5,
  BACKPRESSURE               NUMBER(8,2) default 0,
  WELLHEADFLUIDTEMPERATURE   NUMBER(8,2) default 35,
  PRODUCINGFLUIDLEVEL        NUMBER(8,2),
  PUMPSETTINGDEPTH           NUMBER(8,2),
  PRODUCTIONGASOILRATIO      NUMBER(8,2) default 45,
  TUBINGSTRINGINSIDEDIAMETER NUMBER(8,2) default 62,
  CASINGSTRINGINSIDEDIAMETER NUMBER(8,2) default 73,
  RODSTRING                  VARCHAR2(200),
  PUMPTYPE                   VARCHAR2(20) default 'T',
  BARRELTYPE                 VARCHAR2(20) default 'L',
  BARRELLENGTH               NUMBER(8,2),
  BARRELSERIES               NUMBER(8,2),
  ROTORDIAMETER              NUMBER(8,2),
  QPR                        NUMBER(8,2),
  MANUALINTERVENTION         NUMBER(4) default 0,
  NETGROSSRATIO              NUMBER(8,2) default 1,
  ANCHORINGSTATE             NUMBER(1) default 1,
  PUMPGRADE                  NUMBER(1) default 1,
  PUMPBOREDIAMETER           NUMBER(8,2) default 38,
  PLUNGERLENGTH              NUMBER(8,2) default 1.2,
  RUNTIMEEFFICIENCYSOURCE    NUMBER(2) default 1,
  REMARK                     VARCHAR2(200)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCP_PRODUCTIONDATA_HIST
  add constraint PK_PCP_PRODUCTIONDATA_HIST primary key (ID)
/
create index IDX_PCP_PRODDATA_HIST_LIFT on TBL_PCP_PRODUCTIONDATA_HIST (LIFTINGTYPE)
/
create index IDX_PCP_PRODDATA_HIST_TIME on TBL_PCP_PRODUCTIONDATA_HIST (ACQTIME)
/
create index IDX_PCP_PRODDATA_HIST_WELLID on TBL_PCP_PRODUCTIONDATA_HIST (WELLID)
/

/*==============================================================*/
/* Table: TBL_PCP_PRODUCTIONDATA_LATEST                                    */
/*==============================================================*/
create table TBL_PCP_PRODUCTIONDATA_LATEST
(
  ID                         NUMBER(10) not null,
  WELLID                     NUMBER(10) not null,
  ACQTIME                    DATE default SYSDATE,
  LIFTINGTYPE                NUMBER(10) default 201,
  DISPLACEMENTTYPE           NUMBER(1) default 1,
  RUNTIME                    NUMBER(8,2) default 24,
  CRUDEOILDENSITY            NUMBER(16,2) default 0.85,
  WATERDENSITY               NUMBER(16,2) default 1,
  NATURALGASRELATIVEDENSITY  NUMBER(16,2) default 0,
  SATURATIONPRESSURE         NUMBER(16,2) default 0,
  RESERVOIRDEPTH             NUMBER(16,2) default 0,
  RESERVOIRTEMPERATURE       NUMBER(16,2) default 0,
  WATERCUT                   NUMBER(8,2) default 1,
  WATERCUT_W                 NUMBER(8,2) default 1,
  TUBINGPRESSURE             NUMBER(8,2) default 0.5,
  CASINGPRESSURE             NUMBER(8,2) default 0.5,
  BACKPRESSURE               NUMBER(8,2) default 0,
  WELLHEADFLUIDTEMPERATURE   NUMBER(8,2) default 35,
  PRODUCINGFLUIDLEVEL        NUMBER(8,2),
  PUMPSETTINGDEPTH           NUMBER(8,2),
  PRODUCTIONGASOILRATIO      NUMBER(8,2) default 45,
  TUBINGSTRINGINSIDEDIAMETER NUMBER(8,2) default 62,
  CASINGSTRINGINSIDEDIAMETER NUMBER(8,2) default 73,
  RODSTRING                  VARCHAR2(200),
  PUMPTYPE                   VARCHAR2(20) default 'T',
  BARRELTYPE                 VARCHAR2(20) default 'L',
  BARRELLENGTH               NUMBER(8,2),
  BARRELSERIES               NUMBER(8,2),
  ROTORDIAMETER              NUMBER(8,2),
  QPR                        NUMBER(8,2),
  MANUALINTERVENTION         NUMBER(4) default 0,
  NETGROSSRATIO              NUMBER(8,2) default 1,
  ANCHORINGSTATE             NUMBER(1) default 1,
  PUMPGRADE                  NUMBER(1) default 1,
  PUMPBOREDIAMETER           NUMBER(8,2) default 38,
  PLUNGERLENGTH              NUMBER(8,2) default 1.2,
  RUNTIMEEFFICIENCYSOURCE    NUMBER(2) default 1,
  REMARK                     VARCHAR2(200)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCP_PRODUCTIONDATA_LATEST
  add constraint PK_PCP_PRODUCTIONDATA_LATEST primary key (ID, WELLID)
/
create index IDX_PCP_PRODDATA_LATEST_LIFT on TBL_PCP_PRODUCTIONDATA_LATEST (LIFTINGTYPE)
/
create index IDX_PCP_PRODDATA_LATEST_TIME on TBL_PCP_PRODUCTIONDATA_LATEST (ACQTIME)
/
create index IDX_PCP_PRODDATA_LATEST_WELLID on TBL_PCP_PRODUCTIONDATA_LATEST (WELLID)
/

/*==============================================================*/
/* Table: TBL_PCP_RPM_HIST                                    */
/*==============================================================*/
create table TBL_PCP_RPM_HIST
(
  ID                         NUMBER(10) not null,
  WELLID                     NUMBER(10) not null,
  ACQTIME                    DATE,
  RPM                        NUMBER(8,2),
  TORQUE                     NUMBER(8,2),
  WORKINGCONDITIONCODE       NUMBER(4),
  THEORETICALPRODUCTION      NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTION NUMBER(8,2),
  OILVOLUMETRICPRODUCTION    NUMBER(8,2),
  WATERVOLUMETRICPRODUCTION  NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTION     NUMBER(8,2),
  OILWEIGHTPRODUCTION        NUMBER(8,2),
  WATERWEIGHTPRODUCTION      NUMBER(8,2),
  MOTORINPUTACTIVEPOWER      NUMBER(8,2),
  WATERPOWER                 NUMBER(8,2),
  SYSTEMEFFICIENCY           NUMBER(12,3),
  POWERCONSUMPTIONPERTHM     NUMBER(8,2),
  PUMPEFF1                   NUMBER(12,3),
  PUMPEFF2                   NUMBER(12,3),
  PUMPEFF                    NUMBER(12,3),
  PUMPINTAKEP                NUMBER(8,2),
  PUMPINTAKET                NUMBER(8,2),
  PUMPINTAKEGOL              NUMBER(8,2),
  PUMPINTAKEVISL             NUMBER(8,2),
  PUMPINTAKEBO               NUMBER(8,2),
  PUMPOUTLETP                NUMBER(8,2),
  PUMPOUTLETT                NUMBER(8,2),
  PUMPOUTLETGOL              NUMBER(8,2),
  PUMPOUTLETVISL             NUMBER(8,2),
  PUMPOUTLETBO               NUMBER(8,2),
  RODSTRING                  VARCHAR2(200),
  SAVETIME                   DATE default sysdate,
  PRODUCTIONDATAID           NUMBER(10),
  RESULTSTATUS               NUMBER(2) default 0,
  DISCRETEDATAID             NUMBER(10),
  REMARK                     VARCHAR2(200)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCP_RPM_HIST
  add constraint PK_TBL_PCP_RPM_HIST primary key (ID)
/
create index IDX_TBL_PCP_RPM_HIST_PROD on TBL_PCP_RPM_HIST (LIQUIDVOLUMETRICPRODUCTION)
/
create index IDX_TBL_PCP_RPM_HIST_PRODID on TBL_PCP_RPM_HIST (PRODUCTIONDATAID)
/
create index IDX_TBL_PCP_RPM_HIST_SYSEFF on TBL_PCP_RPM_HIST (SYSTEMEFFICIENCY)
/
create index IDX_TBL_PCP_RPM_HIST_TIME on TBL_PCP_RPM_HIST (ACQTIME)
/
create index IDX_TBL_PCP_RPM_HIST_WELLID on TBL_PCP_RPM_HIST (WELLID)
/

/*==============================================================*/
/* Table: TBL_PCP_RPM_LATEST                                    */
/*==============================================================*/
create table TBL_PCP_RPM_LATEST
(
  ID                         NUMBER(10) not null,
  WELLID                     NUMBER(10) not null,
  ACQTIME                    DATE,
  RPM                        NUMBER(8,2),
  TORQUE                     NUMBER(8,2),
  WORKINGCONDITIONCODE       NUMBER(4),
  THEORETICALPRODUCTION      NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTION NUMBER(8,2),
  OILVOLUMETRICPRODUCTION    NUMBER(8,2),
  WATERVOLUMETRICPRODUCTION  NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTION     NUMBER(8,2),
  OILWEIGHTPRODUCTION        NUMBER(8,2),
  WATERWEIGHTPRODUCTION      NUMBER(8,2),
  MOTORINPUTACTIVEPOWER      NUMBER(8,2),
  WATERPOWER                 NUMBER(8,2),
  SYSTEMEFFICIENCY           NUMBER(12,3),
  POWERCONSUMPTIONPERTHM     NUMBER(8,2),
  PUMPEFF1                   NUMBER(12,3),
  PUMPEFF2                   NUMBER(12,3),
  PUMPEFF                    NUMBER(12,3),
  PUMPINTAKEP                NUMBER(8,2),
  PUMPINTAKET                NUMBER(8,2),
  PUMPINTAKEGOL              NUMBER(8,2),
  PUMPINTAKEVISL             NUMBER(8,2),
  PUMPINTAKEBO               NUMBER(8,2),
  PUMPOUTLETP                NUMBER(8,2),
  PUMPOUTLETT                NUMBER(8,2),
  PUMPOUTLETGOL              NUMBER(8,2),
  PUMPOUTLETVISL             NUMBER(8,2),
  PUMPOUTLETBO               NUMBER(8,2),
  RODSTRING                  VARCHAR2(200),
  SAVETIME                   DATE default sysdate,
  PRODUCTIONDATAID           NUMBER(10),
  RESULTSTATUS               NUMBER(2) default 0,
  DISCRETEDATAID             NUMBER(10),
  REMARK                     VARCHAR2(200)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCP_RPM_LATEST
  add constraint PK_TBL_PCP_RPM_LATEST primary key (ID, WELLID)
/
create index IDX_TBL_PCP_RPM_LATEST_PROD on TBL_PCP_RPM_LATEST (LIQUIDVOLUMETRICPRODUCTION)
/
create index IDX_TBL_PCP_RPM_LATEST_PRODID on TBL_PCP_RPM_LATEST (PRODUCTIONDATAID)
/
create index IDX_TBL_PCP_RPM_LATEST_SYSEFF on TBL_PCP_RPM_LATEST (SYSTEMEFFICIENCY)
/
create index IDX_TBL_PCP_RPM_LATEST_TIME on TBL_PCP_RPM_LATEST (ACQTIME)
/
create index IDX_TBL_PCP_RPM_LATEST_WELLID on TBL_PCP_RPM_LATEST (WELLID)
/

/*==============================================================*/
/* Table: TBL_PCP_TOTAL_DAY                                    */
/*==============================================================*/
create table TBL_PCP_TOTAL_DAY
(
  ID                            NUMBER(10) not null,
  WELLID                        NUMBER(10),
  CALCULATEDATE                 DATE,
  COMMSTATUS                    NUMBER(2) default 0,
  RUNSTATUS                     NUMBER(2) default 0,
  COMMTIME                      NUMBER(8,2) default 0,
  COMMTIMEEFFICIENCY            NUMBER(12,3) default 0,
  RUNTIME                       NUMBER(8,2) default 0,
  RUNTIMEEFFICIENCY             NUMBER(12,3) default 0,
  WORKINGCONDITIONCODE          NUMBER(4) default 0,
  RPM                           NUMBER(8,2),
  RPMMAX                        NUMBER(8,2),
  RPMMIN                        NUMBER(8,2),
  TORQUE                        NUMBER(8,2),
  TORQUEMAX                     NUMBER(8,2),
  TORQUEMIN                     NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTION    NUMBER(8,2),
  OILVOLUMETRICPRODUCTION       NUMBER(8,2),
  WATERVOLUMETRICPRODUCTION     NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTION        NUMBER(8,2),
  OILWEIGHTPRODUCTION           NUMBER(8,2),
  WATERWEIGHTPRODUCTION         NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTIONMAX NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTIONMIN NUMBER(8,2),
  OILVOLUMETRICPRODUCTIONMAX    NUMBER(8,2),
  OILVOLUMETRICPRODUCTIONMIN    NUMBER(8,2),
  WATERVOLUMETRICPRODUCTIONMAX  NUMBER(8,2),
  WATERVOLUMETRICPRODUCTIONMIN  NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTIONMAX     NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTIONMIN     NUMBER(8,2),
  OILWEIGHTPRODUCTIONMAX        NUMBER(8,2),
  OILWEIGHTPRODUCTIONMIN        NUMBER(8,2),
  WATERWEIGHTPRODUCTIONMAX      NUMBER(8,2),
  WATERWEIGHTPRODUCTIONMIN      NUMBER(8,2),
  WATERCUT                      NUMBER(10,4),
  WATERCUT_W                    NUMBER(10,4),
  WATERCUTMAX                   NUMBER(10,4),
  WATERCUTMIN                   NUMBER(10,4),
  WATERCUTMAX_W                 NUMBER(10,4),
  WATERCUTMIN_W                 NUMBER(10,4),
  TUBINGPRESSURE                NUMBER(8,2),
  TUBINGPRESSUREMAX             NUMBER(8,2),
  TUBINGPRESSUREMIN             NUMBER(8,2),
  CASINGPRESSURE                NUMBER(8,2),
  CASINGPRESSUREMAX             NUMBER(8,2),
  CASINGPRESSUREMIN             NUMBER(8,2),
  WELLHEADFLUIDTEMPERATURE      NUMBER(8,2),
  WELLHEADFLUIDTEMPERATUREMAX   NUMBER(8,2),
  WELLHEADFLUIDTEMPERATUREMIN   NUMBER(8,2),
  PRODUCTIONGASOILRATIO         NUMBER(8,2),
  PRODUCTIONGASOILRATIOMAX      NUMBER(8,2),
  PRODUCTIONGASOILRATIOMIN      NUMBER(8,2),
  PRODUCINGFLUIDLEVEL           NUMBER(8,2),
  PRODUCINGFLUIDLEVELMAX        NUMBER(8,2),
  PRODUCINGFLUIDLEVELMIN        NUMBER(8,2),
  PUMPSETTINGDEPTH              NUMBER(8,2),
  PUMPSETTINGDEPTHMAX           NUMBER(8,2),
  PUMPSETTINGDEPTHMIN           NUMBER(8,2),
  SUBMERGENCE                   NUMBER(8,2),
  SUBMERGENCEMAX                NUMBER(8,2),
  SUBMERGENCEMIN                NUMBER(8,2),
  PUMPBOREDIAMETER              NUMBER(8,2),
  PUMPBOREDIAMETERMAX           NUMBER(8,2),
  PUMPBOREDIAMETERMIN           NUMBER(8,2),
  SYSTEMEFFICIENCY              NUMBER(10,4),
  SYSTEMEFFICIENCYMAX           NUMBER(10,4),
  SYSTEMEFFICIENCYMIN           NUMBER(10,4),
  SURFACESYSTEMEFFICIENCY       NUMBER(10,4),
  SURFACESYSTEMEFFICIENCYMAX    NUMBER(10,4),
  SURFACESYSTEMEFFICIENCYMIN    NUMBER(10,4),
  WELLDOWNSYSTEMEFFICIENCY      NUMBER(10,4),
  WELLDOWNSYSTEMEFFICIENCYMAX   NUMBER(10,4),
  WELLDOWNSYSTEMEFFICIENCYMIN   NUMBER(10,4),
  POWERCONSUMPTIONPERTHM        NUMBER(8,2),
  POWERCONSUMPTIONPERTHMMAX     NUMBER(8,2),
  POWERCONSUMPTIONPERTHMMIN     NUMBER(8,2),
  PUMPEFF                       NUMBER(10,4),
  PUMPEFFMAX                    NUMBER(10,4),
  PUMPEFFMIN                    NUMBER(10,4),
  IA                            NUMBER(8,2),
  IAMAX                         NUMBER(8,2),
  IAMIN                         NUMBER(8,2),
  IB                            NUMBER(8,2),
  IBMAX                         NUMBER(8,2),
  IBMIN                         NUMBER(8,2),
  IC                            NUMBER(8,2),
  ICMAX                         NUMBER(8,2),
  ICMIN                         NUMBER(8,2),
  VA                            NUMBER(8,2),
  VAMAX                         NUMBER(8,2),
  VAMIN                         NUMBER(8,2),
  VB                            NUMBER(8,2),
  VBMAX                         NUMBER(8,2),
  VBMIN                         NUMBER(8,2),
  VC                            NUMBER(8,2),
  VCMAX                         NUMBER(8,2),
  VCMIN                         NUMBER(8,2),
  WATTSUM                       NUMBER(8,2),
  WATTSUMMAX                    NUMBER(8,2),
  WATTSUMMIN                    NUMBER(8,2),
  VARSUM                        NUMBER(8,2),
  VARSUMMAX                     NUMBER(8,2),
  VARSUMMIN                     NUMBER(8,2),
  PFSUM                         NUMBER(8,2),
  PFSUMMAX                      NUMBER(8,2),
  PFSUMMIN                      NUMBER(8,2),
  FREQUENCY                     NUMBER(8,2),
  FREQUENCYMAX                  NUMBER(8,2),
  FREQUENCYMIN                  NUMBER(8,2),
  TODAYKWATTH                   NUMBER(10,2) default 0,
  TODAYPKWATTH                  NUMBER(10,2) default 0,
  TODAYNKWATTH                  NUMBER(10,2) default 0,
  TODAYKVARH                    NUMBER(10,2) default 0,
  TODAYPKVARH                   NUMBER(10,2) default 0,
  TODAYNKVARH                   NUMBER(10,2) default 0,
  TODAYKVAH                     NUMBER(10,2) default 0,
  TOTALKWATTH                   NUMBER(10,2) default 0,
  TOTALPKWATTH                  NUMBER(10,2) default 0,
  TOTALNKWATTH                  NUMBER(10,2) default 0,
  TOTALKVARH                    NUMBER(10,2) default 0,
  TOTALPKVARH                   NUMBER(10,2) default 0,
  TOTALNKVARH                   NUMBER(10,2) default 0,
  TOTALKVAH                     NUMBER(10,2) default 0,
  EXTENDEDDAYS                  NUMBER(5),
  RESULTSTATUS                  NUMBER(2),
  SIGNAL                        NUMBER(8,2),
  SIGNALMAX                     NUMBER(8,2),
  SIGNALMIN                     NUMBER(8,2),
  SAVETIME                      DATE default sysdate,
  THEORETICALPRODUCTION         NUMBER(8,2),
  THEORETICALPRODUCTIONMAX      NUMBER(8,2),
  THEORETICALPRODUCTIONMIN      NUMBER(8,2),
  AVGWATT                       NUMBER(8,2),
  AVGWATTMAX                    NUMBER(8,2),
  AVGWATTMIN                    NUMBER(8,2),
  WATERPOWER                    NUMBER(8,2),
  WATERPOWERMAX                 NUMBER(8,2),
  WATERPOWERMIN                 NUMBER(8,2),
  PUMPEFF1                      NUMBER(10,4),
  PUMPEFF1MAX                   NUMBER(10,4),
  PUMPEFF1MIN                   NUMBER(10,4),
  PUMPEFF2                      NUMBER(10,4),
  PUMPEFF2MAX                   NUMBER(10,4),
  PUMPEFF2MIN                   NUMBER(10,4),
  VASUM                         NUMBER(8,2),
  VASUMMAX                      NUMBER(8,2),
  VASUMMIN                      NUMBER(8,2),
  COMMRANGE                     CLOB,
  RUNRANGE                      CLOB,
  WORKINGCONDITIONSTRING        CLOB
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCP_TOTAL_DAY
  add constraint PK_PCP_TOTAL_DAY primary key (ID)
/
create index IDX_PCP_TOTAL_DAY_COMM on TBL_PCP_TOTAL_DAY (COMMSTATUS)
/
create index IDX_PCP_TOTAL_DAY_COMMEFF on TBL_PCP_TOTAL_DAY (COMMTIMEEFFICIENCY)
/
create index IDX_PCP_TOTAL_DAY_DATE on TBL_PCP_TOTAL_DAY (CALCULATEDATE)
/
create index IDX_PCP_TOTAL_DAY_DOWNEFF on TBL_PCP_TOTAL_DAY (WELLDOWNSYSTEMEFFICIENCY)
/
create index IDX_PCP_TOTAL_DAY_ENERGY on TBL_PCP_TOTAL_DAY (TODAYKWATTH)
/
create index IDX_PCP_TOTAL_DAY_PROD on TBL_PCP_TOTAL_DAY (LIQUIDVOLUMETRICPRODUCTION)
/
create index IDX_PCP_TOTAL_DAY_PUMPEFF on TBL_PCP_TOTAL_DAY (PUMPEFF)
/
create index IDX_PCP_TOTAL_DAY_RUN on TBL_PCP_TOTAL_DAY (RUNSTATUS)
/
create index IDX_PCP_TOTAL_DAY_RUNEFF on TBL_PCP_TOTAL_DAY (RUNTIMEEFFICIENCY)
/
create index IDX_PCP_TOTAL_DAY_SURFEFF on TBL_PCP_TOTAL_DAY (SURFACESYSTEMEFFICIENCY)
/
create index IDX_PCP_TOTAL_DAY_SYSEFF on TBL_PCP_TOTAL_DAY (SYSTEMEFFICIENCY)
/
create index IDX_PCP_TOTAL_DAY_WELLID on TBL_PCP_TOTAL_DAY (WELLID)
/
create index IDX_PCP_TOTAL_DAY_WELLID_DATE on TBL_PCP_TOTAL_DAY (WELLID, CALCULATEDATE)
/

/*==============================================================*/
/* Table: TBL_RESOURCEMONITORING                                    */
/*==============================================================*/
create table TBL_RESOURCEMONITORING
(
  ID             NUMBER(10) not null,
  ACQTIME        DATE,
  APPRUNSTATUS   NUMBER(2),
  APPVERSION     VARCHAR2(50),
  CPUUSEDPERCENT VARCHAR2(50),
  MEMUSEDPERCENT NUMBER(8,2),
  TABLESPACESIZE NUMBER(10,2)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RESOURCEMONITORING
  add constraint PK_TBL_RESOURCEMONITORING primary key (ID)
/
create index IDX_RESOURCEMONITORING_ACQTIME on TBL_RESOURCEMONITORING (ACQTIME)
/

/*==============================================================*/
/* Table: TBL_RPCINFORMATION                                    */
/*==============================================================*/
create table TBL_RPCINFORMATION
(
  ID                            NUMBER(10) not null,
  WELLID                        NUMBER(10),
  MANUFACTURER                  VARCHAR2(200),
  MODEL                         VARCHAR2(200),
  STROKE                        NUMBER(8,2),
  CRANKROTATIONDIRECTION        VARCHAR2(200),
  OFFSETANGLEOFCRANK            NUMBER(8,2),
  CRANKGRAVITYRADIUS            NUMBER(10,4),
  SINGLECRANKWEIGHT             NUMBER(8,2),
  SINGLECRANKPINWEIGHT          NUMBER(10,4),
  STRUCTURALUNBALANCE           NUMBER(8,2),
  GEARREDUCERRATIO              NUMBER(10,4),
  GEARREDUCERBELTPULLEYDIAMETER NUMBER(10,4),
  BALANCEPOSITION               VARCHAR2(200),
  BALANCEWEIGHT                 VARCHAR2(200),
  PRTF                          CLOB
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPCINFORMATION
  add constraint PK_TBL_RPCINFORMATION primary key (ID)
/

/*==============================================================*/
/* Table: TBL_RPC_ALARMTYPE_CONF                                    */
/*==============================================================*/
create table TBL_RPC_ALARMTYPE_CONF
(
  ID                   NUMBER(10) not null,
  WORKINGCONDITIONCODE NUMBER(10) not null,
  ALARMTYPE            NUMBER(3) not null,
  ALARMLEVEL           NUMBER(3) not null,
  ALARMSIGN            NUMBER(1) default 0,
  REMARK               VARCHAR2(200)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPC_ALARMTYPE_CONF
  add constraint PK_T_WORKSTATUSALARM primary key (ID)
/
create index IDX_031_BJBZ on TBL_RPC_ALARMTYPE_CONF (ALARMSIGN)
/
create index IDX_031_BJLX on TBL_RPC_ALARMTYPE_CONF (ALARMTYPE)
/
create index IDX_031_GKLX on TBL_RPC_ALARMTYPE_CONF (WORKINGCONDITIONCODE)
/
create index IDX_031_GKLX_BJLX on TBL_RPC_ALARMTYPE_CONF (ALARMTYPE, WORKINGCONDITIONCODE)
/

/*==============================================================*/
/* Table: TBL_RPC_DIAGRAM_HIST                                    */
/*==============================================================*/
create table TBL_RPC_DIAGRAM_HIST
(
  ID                             NUMBER(10) not null,
  WELLID                         NUMBER(10),
  ACQTIME                        DATE,
  STROKE                         NUMBER(8,2),
  SPM                            NUMBER(8,2),
  FMAX                           NUMBER(8,2),
  FMIN                           NUMBER(8,2),
  SMaxIndex                      NUMBER(10),
  SMinIndex                      NUMBER(10),
  POSITION_CURVE                 CLOB,
  ANGLE_CURVE                    CLOB,
  LOAD_CURVE                     CLOB,
  POWER_CURVE                    CLOB,
  CURRENT_CURVE                  CLOB,
  RPM_CURVE                      CLOB,
  RAWPOWER_CURVE                 CLOB,
  RAWCURRENT_CURVE               CLOB,
  RAWRPM_CURVE                   CLOB,
  UPSTROKEIMAX                   NUMBER(8,2),
  DOWNSTROKEIMAX                 NUMBER(8,2),
  UPSTROKEWATTMAX                NUMBER(8,2),
  DOWNSTROKEWATTMAX              NUMBER(8,2),
  IDEGREEBALANCE                 NUMBER(8,2),
  WATTDEGREEBALANCE              NUMBER(8,2),
  DATASOURCE                     NUMBER(1) default 0,
  WORKINGCONDITIONCODE           NUMBER(4),
  FULLNESSCOEFFICIENT            NUMBER(8,2),
  UPPERLOADLINE                  NUMBER(8,2),
  UPPERLOADLINEOFEXACT           NUMBER(8,2),
  LOWERLOADLINE                  NUMBER(8,2),
  PUMPFSDIAGRAM                  CLOB,
  THEORETICALPRODUCTION          NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTION     NUMBER(8,2),
  OILVOLUMETRICPRODUCTION        NUMBER(8,2),
  WATERVOLUMETRICPRODUCTION      NUMBER(8,2),
  AVAILABLEPLUNGERSTROKEPROD_V   NUMBER(8,2),
  PUMPCLEARANCELEAKPROD_V        NUMBER(8,2),
  TVLEAKVOLUMETRICPRODUCTION     NUMBER(8,2),
  SVLEAKVOLUMETRICPRODUCTION     NUMBER(8,2),
  GASINFLUENCEPROD_V             NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTION         NUMBER(8,2),
  OILWEIGHTPRODUCTION            NUMBER(8,2),
  WATERWEIGHTPRODUCTION          NUMBER(8,2),
  AVAILABLEPLUNGERSTROKEPROD_W   NUMBER(8,2),
  PUMPCLEARANCELEAKPROD_W        NUMBER(8,2),
  TVLEAKWEIGHTPRODUCTION         NUMBER(8,2),
  SVLEAKWEIGHTPRODUCTION         NUMBER(8,2),
  GASINFLUENCEPROD_W             NUMBER(8,2),
  MOTORINPUTACTIVEPOWER          NUMBER(8,2),
  POLISHRODPOWER                 NUMBER(8,2),
  WATERPOWER                     NUMBER(8,2),
  SURFACESYSTEMEFFICIENCY        NUMBER(12,3),
  WELLDOWNSYSTEMEFFICIENCY       NUMBER(12,3),
  SYSTEMEFFICIENCY               NUMBER(12,3),
  POWERCONSUMPTIONPERTHM         NUMBER(8,2),
  FSDIAGRAMAREA                  NUMBER(8,2),
  RODFLEXLENGTH                  NUMBER(8,2),
  TUBINGFLEXLENGTH               NUMBER(8,2),
  INERTIALENGTH                  NUMBER(8,2),
  PUMPEFF1                       NUMBER(12,3),
  PUMPEFF2                       NUMBER(12,3),
  PUMPEFF3                       NUMBER(12,3),
  PUMPEFF4                       NUMBER(12,3),
  PUMPEFF                        NUMBER(12,3),
  PUMPINTAKEP                    NUMBER(8,2),
  PUMPINTAKET                    NUMBER(8,2),
  PUMPINTAKEGOL                  NUMBER(8,2),
  PUMPINTAKEVISL                 NUMBER(8,2),
  PUMPINTAKEBO                   NUMBER(8,2),
  PUMPOUTLETP                    NUMBER(8,2),
  PUMPOUTLETT                    NUMBER(8,2),
  PUMPOUTLETGOL                  NUMBER(8,2),
  PUMPOUTLETVISL                 NUMBER(8,2),
  PUMPOUTLETBO                   NUMBER(8,2),
  RODSTRING                      VARCHAR2(200),
  SAVETIME                       DATE default sysdate,
  PRODUCTIONDATAID               NUMBER(10),
  RESULTSTATUS                   NUMBER(2) default 0,
  INVERRESULTSTATUS              NUMBER(2) default 0,
  REMARK                         VARCHAR2(200),
  POSITION360_CURVE              CLOB,
  ANGLE360_CURVE                 CLOB,
  LOAD360_CURVE                  CLOB,
  SIGNAL                         NUMBER(8,2),
  INTERVAL                       NUMBER(10),
  DEVICEVER                      VARCHAR2(50),
  DISCRETEDATAID                 NUMBER(10),
  PLUNGERSTROKE                  NUMBER(8,2),
  AVAILABLEPLUNGERSTROKE         NUMBER(8,2),
  IA_CURVE                       CLOB,
  IB_CURVE                       CLOB,
  IC_CURVE                       CLOB,
  DELTARADIUS                    NUMBER(8,2),
  CRANKANGLE                     CLOB,
  POLISHRODV                     CLOB,
  POLISHRODA                     CLOB,
  PR                             CLOB,
  TF                             CLOB,
  LOADTORQUE                     CLOB,
  CRANKTORQUE                    CLOB,
  CURRENTBALANCETORQUE           CLOB,
  CURRENTNETTORQUE               CLOB,
  EXPECTEDBALANCETORQUE          CLOB,
  EXPECTEDNETTORQUE              CLOB,
  WELLBORESLICE                  CLOB,
  LEVELCORRECTVALUE              NUMBER(8,2),
  INVERPRODUCINGFLUIDLEVEL       NUMBER(8,2),
  NOLIQUIDFULLNESSCOEFFICIENT    NUMBER(10,4),
  NOLIQUIDAVAILABLEPLUNGERSTROKE NUMBER(10,4)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPC_DIAGRAM_HIST
  add constraint PK_T_INDICATIONDIAGRAM primary key (ID)
/
create index IDX_T_FSDIAGRAM_ACQTIME on TBL_RPC_DIAGRAM_HIST (ACQTIME)
/
create index IDX_T_FSDIAGRAM_CODE on TBL_RPC_DIAGRAM_HIST (WORKINGCONDITIONCODE)
/
create index IDX_T_FSDIAGRAM_DATASOURCE on TBL_RPC_DIAGRAM_HIST (DATASOURCE)
/
create index IDX_T_FSDIAGRAM_DISDATAID on TBL_RPC_DIAGRAM_HIST (DISCRETEDATAID)
/
create index IDX_T_FSDIAGRAM_IBALANCE on TBL_RPC_DIAGRAM_HIST (IDEGREEBALANCE)
/
create index IDX_T_FSDIAGRAM_LIQUID on TBL_RPC_DIAGRAM_HIST (LIQUIDWEIGHTPRODUCTION)
/
create index IDX_T_FSDIAGRAM_LIQUID_F on TBL_RPC_DIAGRAM_HIST (LIQUIDVOLUMETRICPRODUCTION)
/
create index IDX_T_FSDIAGRAM_PRODID on TBL_RPC_DIAGRAM_HIST (PRODUCTIONDATAID)
/
create index IDX_T_FSDIAGRAM_RESULTSTATUS on TBL_RPC_DIAGRAM_HIST (RESULTSTATUS)
/
create index IDX_T_FSDIAGRAM_SAVETIME on TBL_RPC_DIAGRAM_HIST (SAVETIME)
/
create index IDX_T_FSDIAGRAM_SURFEFF on TBL_RPC_DIAGRAM_HIST (SURFACESYSTEMEFFICIENCY)
/
create index IDX_T_FSDIAGRAM_SYSEFF on TBL_RPC_DIAGRAM_HIST (SYSTEMEFFICIENCY)
/
create index IDX_T_FSDIAGRAM_WATTBALANCE on TBL_RPC_DIAGRAM_HIST (WATTDEGREEBALANCE)
/
create index IDX_T_FSDIAGRAM_WELLDOWNEFF on TBL_RPC_DIAGRAM_HIST (WELLDOWNSYSTEMEFFICIENCY)
/
create index IDX_T_FSDIAGRAM_WELLID on TBL_RPC_DIAGRAM_HIST (WELLID)
/

/*==============================================================*/
/* Table: TBL_RPC_DIAGRAM_LATEST                                    */
/*==============================================================*/
create table TBL_RPC_DIAGRAM_LATEST
(
  ID                             NUMBER(10) not null,
  WELLID                         NUMBER(10) not null,
  ACQTIME                        DATE,
  STROKE                         NUMBER(8,2),
  SPM                            NUMBER(8,2),
  FMAX                           NUMBER(8,2),
  FMIN                           NUMBER(8,2),
  SMaxIndex                      NUMBER(10),
  SMinIndex                      NUMBER(10),
  POSITION_CURVE                 CLOB,
  ANGLE_CURVE                    CLOB,
  LOAD_CURVE                     CLOB,
  POWER_CURVE                    CLOB,
  CURRENT_CURVE                  CLOB,
  RPM_CURVE                      CLOB,
  RAWPOWER_CURVE                 CLOB,
  RAWCURRENT_CURVE               CLOB,
  RAWRPM_CURVE                   CLOB,
  UPSTROKEIMAX                   NUMBER(8,2),
  DOWNSTROKEIMAX                 NUMBER(8,2),
  UPSTROKEWATTMAX                NUMBER(8,2),
  DOWNSTROKEWATTMAX              NUMBER(8,2),
  IDEGREEBALANCE                 NUMBER(8,2),
  WATTDEGREEBALANCE              NUMBER(8,2),
  DATASOURCE                     NUMBER(1),
  WORKINGCONDITIONCODE           NUMBER(4),
  FULLNESSCOEFFICIENT            NUMBER(8,2),
  UPPERLOADLINE                  NUMBER(8,2),
  UPPERLOADLINEOFEXACT           NUMBER(8,2),
  LOWERLOADLINE                  NUMBER(8,2),
  PUMPFSDIAGRAM                  CLOB,
  THEORETICALPRODUCTION          NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTION     NUMBER(8,2),
  OILVOLUMETRICPRODUCTION        NUMBER(8,2),
  WATERVOLUMETRICPRODUCTION      NUMBER(8,2),
  AVAILABLEPLUNGERSTROKEPROD_V   NUMBER(8,2),
  PUMPCLEARANCELEAKPROD_V        NUMBER(8,2),
  TVLEAKVOLUMETRICPRODUCTION     NUMBER(8,2),
  SVLEAKVOLUMETRICPRODUCTION     NUMBER(8,2),
  GASINFLUENCEPROD_V             NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTION         NUMBER(8,2),
  OILWEIGHTPRODUCTION            NUMBER(8,2),
  WATERWEIGHTPRODUCTION          NUMBER(8,2),
  AVAILABLEPLUNGERSTROKEPROD_W   NUMBER(8,2),
  PUMPCLEARANCELEAKPROD_W        NUMBER(8,2),
  TVLEAKWEIGHTPRODUCTION         NUMBER(8,2),
  SVLEAKWEIGHTPRODUCTION         NUMBER(8,2),
  GASINFLUENCEPROD_W             NUMBER(8,2),
  MOTORINPUTACTIVEPOWER          NUMBER(8,2),
  POLISHRODPOWER                 NUMBER(8,2),
  WATERPOWER                     NUMBER(8,2),
  SURFACESYSTEMEFFICIENCY        NUMBER(12,3),
  WELLDOWNSYSTEMEFFICIENCY       NUMBER(12,3),
  SYSTEMEFFICIENCY               NUMBER(12,3),
  POWERCONSUMPTIONPERTHM         NUMBER(8,2),
  FSDIAGRAMAREA                  NUMBER(8,2),
  RODFLEXLENGTH                  NUMBER(8,2),
  TUBINGFLEXLENGTH               NUMBER(8,2),
  INERTIALENGTH                  NUMBER(8,2),
  PUMPEFF1                       NUMBER(12,3),
  PUMPEFF2                       NUMBER(12,3),
  PUMPEFF3                       NUMBER(12,3),
  PUMPEFF4                       NUMBER(12,3),
  PUMPEFF                        NUMBER(12,3),
  PUMPINTAKEP                    NUMBER(8,2),
  PUMPINTAKET                    NUMBER(8,2),
  PUMPINTAKEGOL                  NUMBER(8,2),
  PUMPINTAKEVISL                 NUMBER(8,2),
  PUMPINTAKEBO                   NUMBER(8,2),
  PUMPOUTLETP                    NUMBER(8,2),
  PUMPOUTLETT                    NUMBER(8,2),
  PUMPOUTLETGOL                  NUMBER(8,2),
  PUMPOUTLETVISL                 NUMBER(8,2),
  PUMPOUTLETBO                   NUMBER(8,2),
  RODSTRING                      VARCHAR2(200),
  SAVETIME                       DATE default sysdate,
  PRODUCTIONDATAID               NUMBER(10),
  RESULTSTATUS                   NUMBER(2) default 0,
  INVERRESULTSTATUS              NUMBER(2) default 0,
  REMARK                         VARCHAR2(200),
  POSITION360_CURVE              CLOB,
  ANGLE360_CURVE                 CLOB,
  LOAD360_CURVE                  CLOB,
  SIGNAL                         NUMBER(8,2),
  INTERVAL                       NUMBER(10),
  DEVICEVER                      VARCHAR2(50),
  DISCRETEDATAID                 NUMBER(10),
  PLUNGERSTROKE                  NUMBER(8,2),
  AVAILABLEPLUNGERSTROKE         NUMBER(8,2),
  IA_CURVE                       CLOB,
  IB_CURVE                       CLOB,
  IC_CURVE                       CLOB,
  DELTARADIUS                    NUMBER(8,2),
  CRANKANGLE                     CLOB,
  POLISHRODV                     CLOB,
  POLISHRODA                     CLOB,
  PR                             CLOB,
  TF                             CLOB,
  LOADTORQUE                     CLOB,
  CRANKTORQUE                    CLOB,
  CURRENTBALANCETORQUE           CLOB,
  CURRENTNETTORQUE               CLOB,
  EXPECTEDBALANCETORQUE          CLOB,
  EXPECTEDNETTORQUE              CLOB,
  WELLBORESLICE                  CLOB,
  LEVELCORRECTVALUE              NUMBER(8,2),
  INVERPRODUCINGFLUIDLEVEL       NUMBER(8,2),
  NOLIQUIDFULLNESSCOEFFICIENT    NUMBER(10,4),
  NOLIQUIDAVAILABLEPLUNGERSTROKE NUMBER(10,4)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPC_DIAGRAM_LATEST
  add constraint PK_T_INDICATORDIAGRAM_R primary key (ID, WELLID)
/
create index IDX_T_FSDIAGRAM_R_ACQTIME on TBL_RPC_DIAGRAM_LATEST (ACQTIME)
/
create index IDX_T_FSDIAGRAM_R_CODE on TBL_RPC_DIAGRAM_LATEST (WORKINGCONDITIONCODE)
/
create index IDX_T_FSDIAGRAM_R_DATASOURCE on TBL_RPC_DIAGRAM_LATEST (DATASOURCE)
/
create index IDX_T_FSDIAGRAM_R_IBALANCE on TBL_RPC_DIAGRAM_LATEST (IDEGREEBALANCE)
/
create index IDX_T_FSDIAGRAM_R_LIQUID on TBL_RPC_DIAGRAM_LATEST (LIQUIDWEIGHTPRODUCTION)
/
create index IDX_T_FSDIAGRAM_R_PRODID on TBL_RPC_DIAGRAM_LATEST (PRODUCTIONDATAID)
/
create index IDX_T_FSDIAGRAM_R_RESULTSTATUS on TBL_RPC_DIAGRAM_LATEST (RESULTSTATUS)
/
create index IDX_T_FSDIAGRAM_R_SAVETIME on TBL_RPC_DIAGRAM_LATEST (SAVETIME)
/
create index IDX_T_FSDIAGRAM_R_SURFEFF on TBL_RPC_DIAGRAM_LATEST (SURFACESYSTEMEFFICIENCY)
/
create index IDX_T_FSDIAGRAM_R_SYSEFF on TBL_RPC_DIAGRAM_LATEST (SYSTEMEFFICIENCY)
/
create index IDX_T_FSDIAGRAM_R_WATTBALANCE on TBL_RPC_DIAGRAM_LATEST (WATTDEGREEBALANCE)
/
create index IDX_T_FSDIAGRAM_R_WELLDOWNEFF on TBL_RPC_DIAGRAM_LATEST (WELLDOWNSYSTEMEFFICIENCY)
/
create index IDX_T_FSDIAGRAM_R_WELLID on TBL_RPC_DIAGRAM_LATEST (WELLID)
/

/*==============================================================*/
/* Table: TBL_RPC_DIAGRAM_TOTAL                                    */
/*==============================================================*/
create table TBL_RPC_DIAGRAM_TOTAL
(
  id                         NUMBER(10) not null,
  wellid                     NUMBER(10),
  commstatus                 NUMBER(2) default 0,
  commtime                   NUMBER(8,2) default 0,
  commtimeefficiency         NUMBER(12,3) default 0,
  commrange                  CLOB,
  runstatus                  NUMBER(2) default 0,
  runtime                    NUMBER(8,2) default 0,
  runtimeefficiency          NUMBER(12,3) default 0,
  runrange                   CLOB,
  acqtime                    DATE,
  resultcode                 NUMBER(4),
  stroke                     NUMBER(8,2),
  spm                        NUMBER(8,2),
  fmax                       NUMBER(8,2),
  fmin                       NUMBER(8,2),
  fullnesscoefficient        NUMBER(10,4),
  liquidvolumetricproduction NUMBER(8,2),
  oilvolumetricproduction    NUMBER(8,2),
  watervolumetricproduction  NUMBER(8,2),
  volumewatercut             NUMBER(10,4),
  liquidweightproduction     NUMBER(8,2),
  oilweightproduction        NUMBER(8,2),
  waterweightproduction      NUMBER(8,2),
  weightwatercut             NUMBER(10,4),
  wattdegreebalance          NUMBER(8,2),
  idegreebalance             NUMBER(8,2),
  deltaradius                NUMBER(8,2),
  systemefficiency           NUMBER(10,4),
  surfacesystemefficiency    NUMBER(10,4),
  welldownsystemefficiency   NUMBER(10,4),
  energyper100mlift          NUMBER(8,2),
  pumpeff                    NUMBER(10,4)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table AGILE.TBL_RPC_DIAGRAM_TOTAL add constraint PK_TBL_RPC_DIAGRAM_TOTAL primary key (ID)
/
create index AGILE.IDX_RPC_DIAGRAM_TOTAL_ACQTIME on AGILE.TBL_RPC_DIAGRAM_TOTAL (ACQTIME)
/
create index AGILE.IDX_RPC_DIAGRAM_TOTAL_CODE on AGILE.TBL_RPC_DIAGRAM_TOTAL (RESULTCODE)
/
create index AGILE.IDX_RPC_DIAGRAM_TOTAL_WELLID on AGILE.TBL_RPC_DIAGRAM_TOTAL (WELLID)
/

/*==============================================================*/
/* Table: TBL_RPC_DISCRETE_HIST                                    */
/*==============================================================*/
create table TBL_RPC_DISCRETE_HIST
(
  ID                        NUMBER(10) not null,
  WELLID                    NUMBER(10) not null,
  ACQTIME                   DATE,
  COMMSTATUS                NUMBER(2) default 0,
  COMMTIME                  NUMBER(8,2) default 0,
  COMMTIMEEFFICIENCY        NUMBER(10,4) default 0,
  RUNSTATUS                 NUMBER(2) default 0,
  RUNTIMEEFFICIENCY         NUMBER(10,4) default 0,
  RUNTIME                   NUMBER(8,2) default 0,
  IA                        NUMBER(8,2),
  IB                        NUMBER(8,2),
  IC                        NUMBER(8,2),
  VA                        NUMBER(8,2),
  VB                        NUMBER(8,2),
  VC                        NUMBER(8,2),
  TOTALKWATTH               NUMBER(10,2),
  TOTALKVARH                NUMBER(10,2),
  WATTSUM                   NUMBER(8,2),
  VARSUM                    NUMBER(8,2),
  REVERSEPOWER              NUMBER(8,2),
  PFSUM                     NUMBER(8,2),
  ACQCYCLE_DIAGRAM          NUMBER(6),
  FREQUENCYSETVALUE         NUMBER(8,2),
  FREQUENCYRUNVALUE         NUMBER(8,2),
  TUBINGPRESSURE            NUMBER(8,2),
  CASINGPRESSURE            NUMBER(8,2),
  BACKPRESSURE              NUMBER(8,2),
  WELLHEADFLUIDTEMPERATURE  NUMBER(8,2),
  TODAYKWATTH               NUMBER(10,2) default 0,
  WORKINGCONDITIONCODE      NUMBER(4),
  IAALARM                   VARCHAR2(20) default '0/0/0/0',
  IBALARM                   VARCHAR2(20) default '0/0/0/0',
  ICALARM                   VARCHAR2(20) default '0/0/0/0',
  VAALARM                   VARCHAR2(20) default '0/0/0/0',
  VBALARM                   VARCHAR2(20) default '0/0/0/0',
  VCALARM                   VARCHAR2(20) default '0/0/0/0',
  IAUPLIMIT                 NUMBER(10,2),
  IADOWNLIMIT               NUMBER(10,2),
  IBUPLIMIT                 NUMBER(10,2),
  IBDOWNLIMIT               NUMBER(10,2),
  ICUPLIMIT                 NUMBER(10,2),
  ICDOWNLIMIT               NUMBER(10,2),
  VAUPLIMIT                 NUMBER(10,2),
  VADOWNLIMIT               NUMBER(10,2),
  VBUPLIMIT                 NUMBER(10,2),
  VBDOWNLIMIT               NUMBER(10,2),
  VCUPLIMIT                 NUMBER(10,2),
  VCDOWNLIMIT               NUMBER(10,2),
  IAZERO                    NUMBER(8,2),
  IBZERO                    NUMBER(8,2),
  ICZERO                    NUMBER(8,2),
  VAZERO                    NUMBER(8,2),
  VBZERO                    NUMBER(8,2),
  VCZERO                    NUMBER(8,2),
  TOTALPKWATTH              NUMBER(10,2),
  TOTALNKWATTH              NUMBER(10,2),
  TOTALPKVARH               NUMBER(10,2),
  TOTALNKVARH               NUMBER(10,2),
  TOTALKVAH                 NUMBER(10,2),
  TODAYPKWATTH              NUMBER(10,2) default 0,
  TODAYNKWATTH              NUMBER(10,2) default 0,
  TODAYKVARH                NUMBER(10,2) default 0,
  TODAYPKVARH               NUMBER(10,2) default 0,
  TODAYNKVARH               NUMBER(10,2) default 0,
  TODAYKVAH                 NUMBER(10,2),
  VASUM                     NUMBER(8,2),
  SIGNAL                    NUMBER(8,2),
  INTERVAL                  NUMBER(10),
  DEVICEVER                 VARCHAR2(50),
  VAVG                      NUMBER(8,2),
  IAVG                      NUMBER(8,2),
  WATTA                     NUMBER(8,2),
  WATTB                     NUMBER(8,2),
  WATTC                     NUMBER(8,2),
  VARA                      NUMBER(8,2),
  VARB                      NUMBER(8,2),
  VARC                      NUMBER(8,2),
  VAA                       NUMBER(8,2),
  VAB                       NUMBER(8,2),
  VAC                       NUMBER(8,2),
  PFA                       NUMBER(8,2),
  PFB                       NUMBER(8,2),
  PFC                       NUMBER(8,2),
  BALANCECONTROLMODE        NUMBER(10) default 0,
  BALANCECALCULATEMODE      NUMBER(10) default 1,
  BALANCEAWAYTIME           NUMBER(10) default 0,
  BALANCECLOSETIME          NUMBER(10) default 0,
  BALANCESTROKECOUNT        NUMBER(10) default 1,
  BALANCEOPERATIONUPLIMIT   NUMBER(10) default 100,
  BALANCEOPERATIONDOWNLIMIT NUMBER(10) default 85,
  BALANCEAUTOCONTROL        NUMBER(1),
  BALANCEFRONTLIMIT         NUMBER(1),
  BALANCEAFTERLIMIT         NUMBER(1),
  SPMAUTOCONTROL            NUMBER(1),
  BALANCEAWAYTIMEPERBEAT    NUMBER(10) default 0,
  BALANCECLOSETIMEPERBEAT   NUMBER(10) default 0,
  ACQCYCLE_DISCRETE         NUMBER(10),
  WATTUPLIMIT               NUMBER(8,2),
  WATTDOWNLIMIT             NUMBER(8,2),
  IAMAX                     NUMBER(8,2),
  IBMAX                     NUMBER(8,2),
  IAMIN                     NUMBER(8,2),
  IBMIN                     NUMBER(8,2),
  ICMAX                     NUMBER(8,2),
  ICMIN                     NUMBER(8,2),
  SAVETIME                  DATE default sysdate,
  COMMRANGE                 CLOB,
  RUNRANGE                  CLOB,
  WORKINGCONDITIONSTRING    CLOB
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPC_DISCRETE_HIST
  add constraint PK_T_DISCRETEDATA primary key (ID)
/
create index IDX_T_DISCRETE_CODE on TBL_RPC_DISCRETE_HIST (WORKINGCONDITIONCODE)
/
create index IDX_T_DISCRETE_COMMSTATUS on TBL_RPC_DISCRETE_HIST (COMMSTATUS)
/
create index IDX_T_DISCRETE_CONNEFF on TBL_RPC_DISCRETE_HIST (COMMTIMEEFFICIENCY)
/
create index IDX_T_DISCRETE_ENERGY on TBL_RPC_DISCRETE_HIST (TODAYKWATTH)
/
create index IDX_T_DISCRETE_RUNEFF on TBL_RPC_DISCRETE_HIST (RUNTIMEEFFICIENCY)
/
create index IDX_T_DISCRETE_RUNSTATUS on TBL_RPC_DISCRETE_HIST (RUNSTATUS)
/
create index IDX_T_DISCRETE_WELLID on TBL_RPC_DISCRETE_HIST (WELLID)
/
create index IDX_T_DISTRETE_ACQTIME on TBL_RPC_DISCRETE_HIST (ACQTIME)
/
create index IDX_T_DISTRETE_SAVETIME on TBL_RPC_DISCRETE_HIST (SAVETIME)
/

/*==============================================================*/
/* Table: TBL_RPC_DISCRETE_LATEST                                    */
/*==============================================================*/
create table TBL_RPC_DISCRETE_LATEST
(
  ID                        NUMBER(10) not null,
  WELLID                    NUMBER(10) not null,
  ACQTIME                   DATE,
  COMMSTATUS                NUMBER(2) default 0,
  COMMTIME                  NUMBER(8,2) default 0,
  COMMTIMEEFFICIENCY        NUMBER(10,4) default 0,
  RUNSTATUS                 NUMBER(2) default 0,
  RUNTIMEEFFICIENCY         NUMBER(10,4) default 0,
  RUNTIME                   NUMBER(8,2) default 0,
  IA                        NUMBER(8,2),
  IB                        NUMBER(8,2),
  IC                        NUMBER(8,2),
  VA                        NUMBER(8,2),
  VB                        NUMBER(8,2),
  VC                        NUMBER(8,2),
  TOTALKWATTH               NUMBER(10,2),
  TOTALKVARH                NUMBER(10,2),
  WATTSUM                   NUMBER(8,2),
  VARSUM                    NUMBER(8,2),
  REVERSEPOWER              NUMBER(8,2),
  PFSUM                     NUMBER(8,2),
  ACQCYCLE_DIAGRAM          NUMBER(6),
  FREQUENCYSETVALUE         NUMBER(8,2),
  FREQUENCYRUNVALUE         NUMBER(8,2),
  TUBINGPRESSURE            NUMBER(8,2),
  CASINGPRESSURE            NUMBER(8,2),
  BACKPRESSURE              NUMBER(8,2),
  WELLHEADFLUIDTEMPERATURE  NUMBER(8,2),
  TODAYKWATTH               NUMBER(10,2) default 0,
  WORKINGCONDITIONCODE      NUMBER(4),
  IAALARM                   VARCHAR2(20) default '0/0/0/0',
  IBALARM                   VARCHAR2(20) default '0/0/0/0',
  ICALARM                   VARCHAR2(20) default '0/0/0/0',
  VAALARM                   VARCHAR2(20) default '0/0/0/0',
  VBALARM                   VARCHAR2(20) default '0/0/0/0',
  VCALARM                   VARCHAR2(20) default '0/0/0/0',
  IAUPLIMIT                 NUMBER(10,2),
  IADOWNLIMIT               NUMBER(10,2),
  IBUPLIMIT                 NUMBER(10,2),
  IBDOWNLIMIT               NUMBER(10,2),
  ICUPLIMIT                 NUMBER(10,2),
  ICDOWNLIMIT               NUMBER(10,2),
  VAUPLIMIT                 NUMBER(10,2),
  VADOWNLIMIT               NUMBER(10,2),
  VBUPLIMIT                 NUMBER(10,2),
  VBDOWNLIMIT               NUMBER(10,2),
  VCUPLIMIT                 NUMBER(10,2),
  VCDOWNLIMIT               NUMBER(10,2),
  IAZERO                    NUMBER(8,2),
  IBZERO                    NUMBER(8,2),
  ICZERO                    NUMBER(8,2),
  VAZERO                    NUMBER(8,2),
  VBZERO                    NUMBER(8,2),
  VCZERO                    NUMBER(8,2),
  TOTALPKWATTH              NUMBER(10,2),
  TOTALNKWATTH              NUMBER(10,2),
  TOTALPKVARH               NUMBER(10,2),
  TOTALNKVARH               NUMBER(10,2),
  TOTALKVAH                 NUMBER(10,2),
  TODAYPKWATTH              NUMBER(10,2) default 0,
  TODAYNKWATTH              NUMBER(10,2) default 0,
  TODAYKVARH                NUMBER(10,2) default 0,
  TODAYPKVARH               NUMBER(10,2) default 0,
  TODAYNKVARH               NUMBER(10,2) default 0,
  TODAYKVAH                 NUMBER(10,2),
  VASUM                     NUMBER(8,2),
  SIGNAL                    NUMBER(8,2),
  INTERVAL                  NUMBER(10),
  DEVICEVER                 VARCHAR2(50),
  VAVG                      NUMBER(8,2),
  IAVG                      NUMBER(8,2),
  WATTA                     NUMBER(8,2),
  WATTB                     NUMBER(8,2),
  WATTC                     NUMBER(8,2),
  VARA                      NUMBER(8,2),
  VARB                      NUMBER(8,2),
  VARC                      NUMBER(8,2),
  VAA                       NUMBER(8,2),
  VAB                       NUMBER(8,2),
  VAC                       NUMBER(8,2),
  PFA                       NUMBER(8,2),
  PFB                       NUMBER(8,2),
  PFC                       NUMBER(8,2),
  BALANCECONTROLMODE        NUMBER(10) default 0,
  BALANCECALCULATEMODE      NUMBER(10) default 1,
  BALANCEAWAYTIME           NUMBER(10) default 0,
  BALANCECLOSETIME          NUMBER(10) default 0,
  BALANCESTROKECOUNT        NUMBER(10) default 1,
  BALANCEOPERATIONUPLIMIT   NUMBER(10) default 100,
  BALANCEOPERATIONDOWNLIMIT NUMBER(10) default 85,
  BALANCEAUTOCONTROL        NUMBER(1),
  BALANCEFRONTLIMIT         NUMBER(1),
  BALANCEAFTERLIMIT         NUMBER(1),
  SPMAUTOCONTROL            NUMBER(1),
  BALANCEAWAYTIMEPERBEAT    NUMBER(10) default 0,
  BALANCECLOSETIMEPERBEAT   NUMBER(10) default 0,
  ACQCYCLE_DISCRETE         NUMBER(10),
  WATTUPLIMIT               NUMBER(8,2),
  WATTDOWNLIMIT             NUMBER(8,2),
  IAMAX                     NUMBER(8,2),
  IBMAX                     NUMBER(8,2),
  IAMIN                     NUMBER(8,2),
  IBMIN                     NUMBER(8,2),
  ICMAX                     NUMBER(8,2),
  ICMIN                     NUMBER(8,2),
  SAVETIME                  DATE default sysdate,
  COMMRANGE                 CLOB,
  RUNRANGE                  CLOB,
  WORKINGCONDITIONSTRING    CLOB
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPC_DISCRETE_LATEST
  add constraint PK_T_DISCRETEDATA_RT primary key (ID, WELLID)
/
create index IDX_T_DISCRETE_RT_CODE on TBL_RPC_DISCRETE_LATEST (WORKINGCONDITIONCODE)
/
create index IDX_T_DISCRETE_RT_CONNEFF on TBL_RPC_DISCRETE_LATEST (COMMTIMEEFFICIENCY)
/
create index IDX_T_DISCRETE_RT_ENERGY on TBL_RPC_DISCRETE_LATEST (TODAYKWATTH)
/
create index IDX_T_DISCRETE_RT_RUNEFF on TBL_RPC_DISCRETE_LATEST (RUNTIMEEFFICIENCY)
/
create index IDX_T_DISCRETE_RT_RUNSTATUS on TBL_RPC_DISCRETE_LATEST (RUNSTATUS)
/
create index IDX_T_DISCRETE_RT_WELLID on TBL_RPC_DISCRETE_LATEST (WELLID)
/
create index IDX_T_DISTRETE_RT_ACQTIME on TBL_RPC_DISCRETE_LATEST (ACQTIME)
/
create index IDX_T_DISTRETE_RT_SAVETIME on TBL_RPC_DISCRETE_LATEST (SAVETIME)
/

/*==============================================================*/
/* Table: TBL_RPC_INVER_OPT                                    */
/*==============================================================*/
create table TBL_RPC_INVER_OPT
(
  ID                      NUMBER(10) not null,
  WELLID                  NUMBER(10),
  OFFSETANGLEOFCRANKPS    NUMBER(8,2) default -4,
  SURFACESYSTEMEFFICIENCY NUMBER(8,2) default 0.9,
  FS_LEFTPERCENT          NUMBER(8,2) default 1.0,
  FS_RIGHTPERCENT         NUMBER(8,2) default 4.5,
  FILTERTIME_WATT         NUMBER(3) default 3,
  FILTERTIME_I            NUMBER(3) default 3,
  FILTERTIME_FSDIAGRAM    NUMBER(3) default 3,
  FILTERTIME_RPM          NUMBER(3) default 0,
  FILTERTIME_FSDIAGRAM_L  NUMBER(3) default 100,
  FILTERTIME_FSDIAGRAM_R  NUMBER(3) default 0,
  WATTANGLE               NUMBER(8,2) default 89
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPC_INVER_OPT
  add constraint PK_T_INVER_OPTIMIZE primary key (ID)
/
create index IDX_T_INVER_OPTI_JBH on TBL_RPC_INVER_OPT (WELLID)
/

/*==============================================================*/
/* Table: TBL_RPC_MOTOR                                    */
/*==============================================================*/
create table TBL_RPC_MOTOR
(
  ID                 NUMBER(10) not null,
  WELLID             NUMBER(10),
  MANUFACTURER       VARCHAR2(200),
  MODEL              VARCHAR2(200),
  SYNCHROSPEED       NUMBER(8,2),
  BELTPULLEYDIAMETER NUMBER(10,4),
  PERFORMANCECURVER  CLOB
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/

/*==============================================================*/
/* Table: TBL_RPC_PRODUCTIONDATA_HIST                                    */
/*==============================================================*/
create table TBL_RPC_PRODUCTIONDATA_HIST
(
  ID                         NUMBER(10) not null,
  WELLID                     NUMBER(10) not null,
  ACQTIME                    DATE default SYSDATE,
  LIFTINGTYPE                NUMBER(10) default 201,
  DISPLACEMENTTYPE           NUMBER(1) default 1,
  RUNTIME                    NUMBER(8,2) default 24,
  CRUDEOILDENSITY            NUMBER(16,2) default 0.85,
  WATERDENSITY               NUMBER(16,2) default 1,
  NATURALGASRELATIVEDENSITY  NUMBER(16,2) default 0,
  SATURATIONPRESSURE         NUMBER(16,2) default 0,
  RESERVOIRDEPTH             NUMBER(16,2) default 0,
  RESERVOIRTEMPERATURE       NUMBER(16,2) default 0,
  WATERCUT                   NUMBER(8,2) default 1,
  WATERCUT_W                 NUMBER(8,2) default 1,
  TUBINGPRESSURE             NUMBER(8,2) default 0.5,
  CASINGPRESSURE             NUMBER(8,2) default 0.5,
  BACKPRESSURE               NUMBER(8,2) default 0,
  WELLHEADFLUIDTEMPERATURE   NUMBER(8,2) default 35,
  PRODUCINGFLUIDLEVEL        NUMBER(8,2),
  PUMPSETTINGDEPTH           NUMBER(8,2),
  PRODUCTIONGASOILRATIO      NUMBER(8,2) default 45,
  TUBINGSTRINGINSIDEDIAMETER NUMBER(8,2) default 62,
  CASINGSTRINGINSIDEDIAMETER NUMBER(8,2) default 73,
  RODSTRING                  VARCHAR2(200),
  PUMPGRADE                  NUMBER(1) default 1,
  PUMPBOREDIAMETER           NUMBER(8,2) default 38,
  PLUNGERLENGTH              NUMBER(8,2) default 1.2,
  PUMPTYPE                   VARCHAR2(20) default 'T',
  BARRELTYPE                 VARCHAR2(20) default 'L',
  BARRELLENGTH               NUMBER(8,2),
  BARRELSERIES               NUMBER(8,2),
  ROTORDIAMETER              NUMBER(8,2),
  QPR                        NUMBER(8,2),
  MANUALINTERVENTION         NUMBER(4) default 0,
  NETGROSSRATIO              NUMBER(8,2) default 1,
  ANCHORINGSTATE             NUMBER(1) default 1,
  RUNTIMEEFFICIENCYSOURCE    NUMBER(2) default 1,
  REMARK                     VARCHAR2(200)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPC_PRODUCTIONDATA_HIST
  add constraint PK_PRODUCTION primary key (ID)
/
create index IDX_PRODUCTIONDATA_ACQTIME on TBL_RPC_PRODUCTIONDATA_HIST (ACQTIME)
/
create index IDX_PRODUCTIONDATA_LIFTINGTYPE on TBL_RPC_PRODUCTIONDATA_HIST (LIFTINGTYPE)
/
create index IDX_PRODUCTIONDATA_WELLID on TBL_RPC_PRODUCTIONDATA_HIST (WELLID)
/

/*==============================================================*/
/* Table: TBL_RPC_PRODUCTIONDATA_LATEST                                    */
/*==============================================================*/
create table TBL_RPC_PRODUCTIONDATA_LATEST
(
  ID                         NUMBER(10) not null,
  WELLID                     NUMBER(10) not null,
  ACQTIME                    DATE default SYSDATE,
  LIFTINGTYPE                NUMBER(10) default 201,
  DISPLACEMENTTYPE           NUMBER(1) default 1,
  RUNTIME                    NUMBER(8,2) default 24,
  CRUDEOILDENSITY            NUMBER(16,2) default 0.85,
  WATERDENSITY               NUMBER(16,2) default 1,
  NATURALGASRELATIVEDENSITY  NUMBER(16,2) default 0,
  SATURATIONPRESSURE         NUMBER(16,2) default 0,
  RESERVOIRDEPTH             NUMBER(16,2) default 0,
  RESERVOIRTEMPERATURE       NUMBER(16,2) default 0,
  WATERCUT                   NUMBER(8,2) default 1,
  WATERCUT_W                 NUMBER(8,2) default 1,
  TUBINGPRESSURE             NUMBER(8,2) default 0.5,
  CASINGPRESSURE             NUMBER(8,2) default 0.5,
  BACKPRESSURE               NUMBER(8,2) default 0,
  WELLHEADFLUIDTEMPERATURE   NUMBER(8,2) default 35,
  PRODUCINGFLUIDLEVEL        NUMBER(8,2),
  PUMPSETTINGDEPTH           NUMBER(8,2),
  PRODUCTIONGASOILRATIO      NUMBER(8,2) default 45,
  TUBINGSTRINGINSIDEDIAMETER NUMBER(8,2) default 62,
  CASINGSTRINGINSIDEDIAMETER NUMBER(8,2) default 73,
  RODSTRING                  VARCHAR2(200),
  PUMPGRADE                  NUMBER(1) default 1,
  PUMPBOREDIAMETER           NUMBER(8,2) default 38,
  PLUNGERLENGTH              NUMBER(8,2) default 1.2,
  PUMPTYPE                   VARCHAR2(20) default 'T',
  BARRELTYPE                 VARCHAR2(20) default 'L',
  BARRELLENGTH               NUMBER(8,2),
  BARRELSERIES               NUMBER(8,2),
  ROTORDIAMETER              NUMBER(8,2),
  QPR                        NUMBER(8,2),
  MANUALINTERVENTION         NUMBER(4) default 0,
  NETGROSSRATIO              NUMBER(8,2) default 1,
  ANCHORINGSTATE             NUMBER(1) default 1,
  RUNTIMEEFFICIENCYSOURCE    NUMBER(2) default 1,
  REMARK                     VARCHAR2(200)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPC_PRODUCTIONDATA_LATEST
  add constraint PK_PRODUCTION_RT primary key (ID, WELLID)
/
create index IDX_PRODUCTIONDATA_RT_ACQTIME on TBL_RPC_PRODUCTIONDATA_LATEST (ACQTIME)
/
create index IDX_PRODUCTIONDATA_RT_LIFTYPE on TBL_RPC_PRODUCTIONDATA_LATEST (LIFTINGTYPE)
/
create index IDX_PRODUCTIONDATA_RT_WELLID on TBL_RPC_PRODUCTIONDATA_LATEST (WELLID)
/

/*==============================================================*/
/* Table: TBL_RPC_STATISTICS_CONF                                    */
/*==============================================================*/
create table TBL_RPC_STATISTICS_CONF
(
  ID      NUMBER(10) not null,
  S_LEVEL VARCHAR2(50),
  S_CODE  NUMBER(4),
  S_MIN   NUMBER(11,3),
  S_MAX   NUMBER(11,3),
  S_TYPE  VARCHAR2(20)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPC_STATISTICS_CONF
  add constraint PK_OUTPUTSTATISTICS primary key (ID)
/
create index IDX_039_CODE on TBL_RPC_STATISTICS_CONF (S_CODE)
/
create index IDX_039_LEVEL on TBL_RPC_STATISTICS_CONF (S_LEVEL)
/
create index IDX_039_MAX on TBL_RPC_STATISTICS_CONF (S_MAX)
/
create index IDX_039_MIN on TBL_RPC_STATISTICS_CONF (S_MIN)
/
create index IDX_039_TYPE on TBL_RPC_STATISTICS_CONF (S_TYPE)
/
create index IDX_039_UNION on TBL_RPC_STATISTICS_CONF (S_MIN, S_MAX, S_LEVEL)
/

/*==============================================================*/
/* Table: TBL_RPC_TOTAL_DAY                                    */
/*==============================================================*/
create table TBL_RPC_TOTAL_DAY
(
  ID                             NUMBER(10) not null,
  WELLID                         NUMBER(10),
  CALCULATEDATE                  DATE,
  COMMSTATUS                     NUMBER(2) default 0,
  RUNSTATUS                      NUMBER(2) default 0,
  COMMTIME                       NUMBER(8,2) default 0,
  COMMTIMEEFFICIENCY             NUMBER(12,3) default 0,
  RUNTIME                        NUMBER(8,2) default 0,
  RUNTIMEEFFICIENCY              NUMBER(12,3) default 0,
  WORKINGCONDITIONCODE           NUMBER(4),
  WORKINGCONDITIONCODE_E         NUMBER(4),
  FULLNESSCOEFFICIENT            NUMBER(10,4),
  FULLNESSCOEFFICIENTMAX         NUMBER(10,4),
  FULLNESSCOEFFICIENTMIN         NUMBER(10,4),
  STROKE                         NUMBER(8,2),
  STROKEMAX                      NUMBER(8,2),
  STROKEMIN                      NUMBER(8,2),
  SPM                            NUMBER(8,2),
  SPMMAX                         NUMBER(8,2),
  SPMMIN                         NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTION     NUMBER(8,2),
  OILVOLUMETRICPRODUCTION        NUMBER(8,2),
  WATERVOLUMETRICPRODUCTION      NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTION         NUMBER(8,2),
  OILWEIGHTPRODUCTION            NUMBER(8,2),
  WATERWEIGHTPRODUCTION          NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTIONMAX  NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTIONMIN  NUMBER(8,2),
  OILVOLUMETRICPRODUCTIONMAX     NUMBER(8,2),
  OILVOLUMETRICPRODUCTIONMIN     NUMBER(8,2),
  WATERVOLUMETRICPRODUCTIONMAX   NUMBER(8,2),
  WATERVOLUMETRICPRODUCTIONMIN   NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTIONMAX      NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTIONMIN      NUMBER(8,2),
  OILWEIGHTPRODUCTIONMAX         NUMBER(8,2),
  OILWEIGHTPRODUCTIONMIN         NUMBER(8,2),
  WATERWEIGHTPRODUCTIONMAX       NUMBER(8,2),
  WATERWEIGHTPRODUCTIONMIN       NUMBER(8,2),
  WATTDEGREEBALANCE              NUMBER(8,2),
  IDEGREEBALANCE                 NUMBER(8,2),
  WATTDEGREEBALANCEMAX           NUMBER(8,2),
  WATTDEGREEBALANCEMIN           NUMBER(8,2),
  IDEGREEBALANCEMAX              NUMBER(8,2),
  IDEGREEBALANCEMIN              NUMBER(8,2),
  DELTARADIUS                    NUMBER(8,2),
  DELTARADIUSMAX                 NUMBER(8,2),
  DELTARADIUSMIN                 NUMBER(8,2),
  WATERCUT                       NUMBER(10,4),
  WATERCUT_W                     NUMBER(10,4),
  WATERCUTMAX                    NUMBER(10,4),
  WATERCUTMIN                    NUMBER(10,4),
  WATERCUTMAX_W                  NUMBER(10,4),
  WATERCUTMIN_W                  NUMBER(10,4),
  TUBINGPRESSURE                 NUMBER(8,2),
  TUBINGPRESSUREMAX              NUMBER(8,2),
  CASINGPRESSURE                 NUMBER(8,2),
  CASINGPRESSUREMAX              NUMBER(8,2),
  CASINGPRESSUREMIN              NUMBER(8,2),
  WELLHEADFLUIDTEMPERATURE       NUMBER(8,2),
  WELLHEADFLUIDTEMPERATUREMAX    NUMBER(8,2),
  WELLHEADFLUIDTEMPERATUREMIN    NUMBER(8,2),
  PRODUCTIONGASOILRATIO          NUMBER(8,2),
  PRODUCTIONGASOILRATIOMAX       NUMBER(8,2),
  PRODUCTIONGASOILRATIOMIN       NUMBER(8,2),
  PRODUCINGFLUIDLEVEL            NUMBER(8,2),
  PRODUCINGFLUIDLEVELMAX         NUMBER(8,2),
  PRODUCINGFLUIDLEVELMIN         NUMBER(8,2),
  PUMPSETTINGDEPTH               NUMBER(8,2),
  PUMPSETTINGDEPTHMAX            NUMBER(8,2),
  PUMPSETTINGDEPTHMIN            NUMBER(8,2),
  SUBMERGENCE                    NUMBER(8,2),
  SUBMERGENCEMAX                 NUMBER(8,2),
  SUBMERGENCEMIN                 NUMBER(8,2),
  PUMPBOREDIAMETER               NUMBER(8,2),
  PUMPBOREDIAMETERMAX            NUMBER(8,2),
  PUMPBOREDIAMETERMIN            NUMBER(8,2),
  SYSTEMEFFICIENCY               NUMBER(10,4),
  SYSTEMEFFICIENCYMAX            NUMBER(10,4),
  SYSTEMEFFICIENCYMIN            NUMBER(10,4),
  SURFACESYSTEMEFFICIENCY        NUMBER(10,4),
  SURFACESYSTEMEFFICIENCYMAX     NUMBER(10,4),
  SURFACESYSTEMEFFICIENCYMIN     NUMBER(10,4),
  WELLDOWNSYSTEMEFFICIENCY       NUMBER(10,4),
  WELLDOWNSYSTEMEFFICIENCYMAX    NUMBER(10,4),
  WELLDOWNSYSTEMEFFICIENCYMIN    NUMBER(10,4),
  POWERCONSUMPTIONPERTHM         NUMBER(8,2),
  POWERCONSUMPTIONPERTHMMAX      NUMBER(8,2),
  POWERCONSUMPTIONPERTHMMIN      NUMBER(8,2),
  PUMPEFF                        NUMBER(10,4),
  PUMPEFFMAX                     NUMBER(10,4),
  PUMPEFFMIN                     NUMBER(10,4),
  IA                             NUMBER(8,2),
  IAMAX                          NUMBER(8,2),
  IAMIN                          NUMBER(8,2),
  IB                             NUMBER(8,2),
  IBMAX                          NUMBER(8,2),
  IBMIN                          NUMBER(8,2),
  IC                             NUMBER(8,2),
  ICMAX                          NUMBER(8,2),
  ICMIN                          NUMBER(8,2),
  VA                             NUMBER(8,2),
  VAMAX                          NUMBER(8,2),
  VAMIN                          NUMBER(8,2),
  VB                             NUMBER(8,2),
  VBMAX                          NUMBER(8,2),
  VBMIN                          NUMBER(8,2),
  VC                             NUMBER(8,2),
  VCMAX                          NUMBER(8,2),
  VCMIN                          NUMBER(8,2),
  WATTSUM                        NUMBER(8,2),
  WATTSUMMAX                     NUMBER(8,2),
  WATTSUMMIN                     NUMBER(8,2),
  VARSUM                         NUMBER(8,2),
  VARSUMMAX                      NUMBER(8,2),
  VARSUMMIN                      NUMBER(8,2),
  PFSUM                          NUMBER(8,2),
  PFSUMMAX                       NUMBER(8,2),
  FREQUENCY                      NUMBER(8,2),
  FREQUENCYMAX                   NUMBER(8,2),
  FREQUENCYMIN                   NUMBER(8,2),
  TODAYKWATTH                    NUMBER(10,2) default 0,
  TODAYPKWATTH                   NUMBER(10,2) default 0,
  TODAYNKWATTH                   NUMBER(10,2) default 0,
  TODAYKVARH                     NUMBER(10,2) default 0,
  TODAYPKVARH                    NUMBER(10,2) default 0,
  TODAYNKVARH                    NUMBER(10,2) default 0,
  TODAYKVAH                      NUMBER(10,2) default 0,
  TOTALKWATTH                    NUMBER(10,2) default 0,
  TOTALPKWATTH                   NUMBER(10,2) default 0,
  TOTALNKWATTH                   NUMBER(10,2) default 0,
  TOTALKVARH                     NUMBER(10,2) default 0,
  TOTALPKVARH                    NUMBER(10,2) default 0,
  TOTALNKVARH                    NUMBER(10,2) default 0,
  TOTALKVAH                      NUMBER(10,2) default 0,
  RPM                            NUMBER(8,2),
  RPMMAX                         NUMBER(8,2),
  RPMMIN                         NUMBER(8,2),
  EXTENDEDDAYS                   NUMBER(5),
  RESULTSTATUS                   NUMBER(2),
  SIGNAL                         NUMBER(8,2),
  SIGNALMAX                      NUMBER(8,2),
  SIGNALMIN                      NUMBER(8,2),
  F                              NUMBER(8,2),
  FMAX                           NUMBER(8,2),
  FMIN                           NUMBER(8,2),
  TUBINGPRESSUREMIN              NUMBER(8,2),
  PFSUMMIN                       NUMBER(8,2),
  SAVETIME                       DATE default sysdate,
  UPPERLOADLINE                  NUMBER(8,2),
  UPPERLOADLINEMAX               NUMBER(8,2),
  UPPERLOADLINEMIN               NUMBER(8,2),
  LOWERLOADLINE                  NUMBER(8,2),
  LOWERLOADLINEMAX               NUMBER(8,2),
  LOWERLOADLINEMIN               NUMBER(8,2),
  UPPERLOADLINEOFEXACT           NUMBER(8,2),
  UPPERLOADLINEOFEXACTMAX        NUMBER(8,2),
  UPPERLOADLINEOFEXACTMIN        NUMBER(8,2),
  DELTALOADLINE                  NUMBER(8,2),
  DELTALOADLINEMAX               NUMBER(8,2),
  DELTALOADLINEMIN               NUMBER(8,2),
  DELTALOADLINEOFEXACT           NUMBER(8,2),
  DELTALOADLINEOFEXACTMAX        NUMBER(8,2),
  DELTALOADLINEOFEXACTMIN        NUMBER(8,2),
  FMAX_AVG                       NUMBER(8,2),
  FMAX_MAX                       NUMBER(8,2),
  FMAX_MIN                       NUMBER(8,2),
  FMIN_AVG                       NUMBER(8,2),
  FMIN_MAX                       NUMBER(8,2),
  FMIN_MIN                       NUMBER(8,2),
  DELTAF                         NUMBER(8,2),
  DELTAFMAX                      NUMBER(8,2),
  DELTAFMIN                      NUMBER(8,2),
  AREA                           NUMBER(8,2),
  AREAMAX                        NUMBER(8,2),
  AREAMIN                        NUMBER(8,2),
  PLUNGERSTROKE                  NUMBER(8,2),
  PLUNGERSTROKEMAX               NUMBER(8,2),
  PLUNGERSTROKEMIN               NUMBER(8,2),
  AVAILABLEPLUNGERSTROKE         NUMBER(8,2),
  AVAILABLEPLUNGERSTROKEMAX      NUMBER(8,2),
  AVAILABLEPLUNGERSTROKEMIN      NUMBER(8,2),
  THEORETICALPRODUCTION          NUMBER(8,2),
  THEORETICALPRODUCTIONMAX       NUMBER(8,2),
  THEORETICALPRODUCTIONMIN       NUMBER(8,2),
  AVAILABLESTROKEPROD_V          NUMBER(8,2),
  AVAILABLESTROKEPROD_V_MAX      NUMBER(8,2),
  AVAILABLESTROKEPROD_V_MIN      NUMBER(8,2),
  PUMPCLEARANCELEAKPROD_V        NUMBER(8,2),
  PUMPCLEARANCELEAKPROD_V_MAX    NUMBER(8,2),
  PUMPCLEARANCELEAKPROD_V_MIN    NUMBER(8,2),
  TVLEAKVOLUMETRICPRODUCTION     NUMBER(8,2),
  TVLEAKVOLUMETRICPRODUCTIONMAX  NUMBER(8,2),
  TVLEAKVOLUMETRICPRODUCTIONMIN  NUMBER(8,2),
  SVLEAKVOLUMETRICPRODUCTION     NUMBER(8,2),
  SVLEAKVOLUMETRICPRODUCTIONMAX  NUMBER(8,2),
  SVLEAKVOLUMETRICPRODUCTIONMIN  NUMBER(8,2),
  GASINFLUENCEPROD_V             NUMBER(8,2),
  GASINFLUENCEPROD_V_MAX         NUMBER(8,2),
  GASINFLUENCEPROD_V_MIN         NUMBER(8,2),
  AVAILABLESTROKEPROD_W          NUMBER(8,2),
  AVAILABLESTROKEPROD_W_MAX      NUMBER(8,2),
  AVAILABLESTROKEPROD_W_MIN      NUMBER(8,2),
  PUMPCLEARANCELEAKPROD_W        NUMBER(8,2),
  PUMPCLEARANCELEAKPROD_W_MAX    NUMBER(8,2),
  PUMPCLEARANCELEAKPROD_W_MIN    NUMBER(8,2),
  TVLEAKWEIGHTPRODUCTION         NUMBER(8,2),
  TVLEAKWEIGHTPRODUCTIONMAX      NUMBER(8,2),
  TVLEAKWEIGHTPRODUCTIONMIN      NUMBER(8,2),
  SVLEAKWEIGHTPRODUCTION         NUMBER(8,2),
  SVLEAKWEIGHTPRODUCTIONMAX      NUMBER(8,2),
  SVLEAKWEIGHTPRODUCTIONMIN      NUMBER(8,2),
  GASINFLUENCEPROD_W             NUMBER(8,2),
  GASINFLUENCEPROD_W_MAX         NUMBER(8,2),
  GASINFLUENCEPROD_W_MIN         NUMBER(8,2),
  PUMPEFF1                       NUMBER(10,4),
  PUMPEFF1MAX                    NUMBER(10,4),
  PUMPEFF1MIN                    NUMBER(10,4),
  PUMPEFF2                       NUMBER(10,4),
  PUMPEFF2MAX                    NUMBER(10,4),
  PUMPEFF2MIN                    NUMBER(10,4),
  PUMPEFF3                       NUMBER(10,4),
  PUMPEFF3MAX                    NUMBER(10,4),
  PUMPEFF3MIN                    NUMBER(10,4),
  PUMPEFF4                       NUMBER(10,4),
  PUMPEFF4MAX                    NUMBER(10,4),
  PUMPEFF4MIN                    NUMBER(10,4),
  RODFLEXLENGTH                  NUMBER(8,2),
  RODFLEXLENGTHMAX               NUMBER(8,2),
  RODFLEXLENGTHMIN               NUMBER(8,2),
  TUBINGFLEXLENGTH               NUMBER(8,2),
  TUBINGFLEXLENGTHMAX            NUMBER(8,2),
  TUBINGFLEXLENGTHMIN            NUMBER(8,2),
  INERTIALENGTH                  NUMBER(8,2),
  INERTIALENGTHMAX               NUMBER(8,2),
  INERTIALENGTHMIN               NUMBER(8,2),
  PUMPINTAKEP                    NUMBER(8,2),
  PUMPINTAKEPMAX                 NUMBER(8,2),
  PUMPINTAKEPMIN                 NUMBER(8,2),
  PUMPINTAKET                    NUMBER(8,2),
  PUMPINTAKETMAX                 NUMBER(8,2),
  PUMPINTAKETMIN                 NUMBER(8,2),
  PUMPINTAKEGOL                  NUMBER(8,2),
  PUMPINTAKEGOLMAX               NUMBER(8,2),
  PUMPINTAKEGOLMIN               NUMBER(8,2),
  PUMPINTAKEVISL                 NUMBER(8,2),
  PUMPINTAKEVISLMAX              NUMBER(8,2),
  PUMPINTAKEVISLMIN              NUMBER(8,2),
  PUMPINTAKEBO                   NUMBER(8,2),
  PUMPINTAKEBOMAX                NUMBER(8,2),
  PUMPINTAKEBOMIN                NUMBER(8,2),
  PUMPOUTLETP                    NUMBER(8,2),
  PUMPOUTLETPMAX                 NUMBER(8,2),
  PUMPOUTLETPMIN                 NUMBER(8,2),
  PUMPOUTLETT                    NUMBER(8,2),
  PUMPOUTLETTMAX                 NUMBER(8,2),
  PUMPOUTLETTMIN                 NUMBER(8,2),
  PUMPOUTLETGOL                  NUMBER(8,2),
  PUMPOUTLETGOLMAX               NUMBER(8,2),
  PUMPOUTLETGOLMIN               NUMBER(8,2),
  PUMPOUTLETVISL                 NUMBER(8,2),
  PUMPOUTLETVISLMAX              NUMBER(8,2),
  PUMPOUTLETVISLMIN              NUMBER(8,2),
  PUMPOUTLETBO                   NUMBER(8,2),
  PUMPOUTLETBOMAX                NUMBER(8,2),
  PUMPOUTLETBOMIN                NUMBER(8,2),
  UPSTROKEWATTMAX_AVG            NUMBER(8,2),
  UPSTROKEWATTMAX_MAX            NUMBER(8,2),
  UPSTROKEWATTMAX_MIN            NUMBER(8,2),
  UPSTROKEIMAX_AVG               NUMBER(8,2),
  UPSTROKEIMAX_MAX               NUMBER(8,2),
  UPSTROKEIMAX_MIN               NUMBER(8,2),
  AVGWATT                        NUMBER(8,2),
  AVGWATTMAX                     NUMBER(8,2),
  AVGWATTMIN                     NUMBER(8,2),
  POLISHRODPOWER                 NUMBER(8,2),
  POLISHRODPOWERMAX              NUMBER(8,2),
  POLISHRODPOWERMIN              NUMBER(8,2),
  WATERPOWER                     NUMBER(8,2),
  WATERPOWERMAX                  NUMBER(8,2),
  WATERPOWERMIN                  NUMBER(8,2),
  VASUM                          NUMBER(8,2),
  VASUMMAX                       NUMBER(8,2),
  VASUMMIN                       NUMBER(8,2),
  DOWNSTROKEWATTMAX_AVG          NUMBER(8,2),
  DOWNSTROKEWATTMAX_MAX          NUMBER(8,2),
  DOWNSTROKEWATTMAX_MIN          NUMBER(8,2),
  DOWNSTROKEIMAX_AVG             NUMBER(8,2),
  DOWNSTROKEIMAX_MAX             NUMBER(8,2),
  DOWNSTROKEIMAX_MIN             NUMBER(8,2),
  LEVELCORRECTVALUE              NUMBER(8,2),
  LEVELCORRECTVALUEMAX           NUMBER(8,2),
  LEVELCORRECTVALUEMIN           NUMBER(8,2),
  NOLIQUIDFULLNESSCOEFFICIENT    NUMBER(10,4),
  NOLIQUIDFULLNESSCOEFFICIENTMAX NUMBER(10,4),
  NOLIQUIDFULLNESSCOEFFICIENTMIN NUMBER(10,4),
  NOLIQUIDAVAILABLESTROKE        NUMBER(10,4),
  NOLIQUIDAVAILABLESTROKEMAX     NUMBER(10,4),
  NOLIQUIDAVAILABLESTROKEMIN     NUMBER(10,4),
  COMMRANGE                      CLOB,
  RUNRANGE                       CLOB,
  WORKINGCONDITIONSTRING         CLOB,
  WORKINGCONDITIONSTRING_E       CLOB
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPC_TOTAL_DAY
  add constraint PK_T_AGGREGATION primary key (ID)
/
create index IDX_T_AGGREGATION_CODE on TBL_RPC_TOTAL_DAY (WORKINGCONDITIONCODE)
/
create index IDX_T_AGGREGATION_CODE_E on TBL_RPC_TOTAL_DAY (WORKINGCONDITIONCODE_E)
/
create index IDX_T_AGGREGATION_COMMEFF on TBL_RPC_TOTAL_DAY (COMMTIMEEFFICIENCY)
/
create index IDX_T_AGGREGATION_COMMSTATUS on TBL_RPC_TOTAL_DAY (COMMSTATUS)
/
create index IDX_T_AGGREGATION_DATE on TBL_RPC_TOTAL_DAY (CALCULATEDATE)
/
create index IDX_T_AGGREGATION_DOWNEFF on TBL_RPC_TOTAL_DAY (WELLDOWNSYSTEMEFFICIENCY)
/
create index IDX_T_AGGREGATION_ENERGY on TBL_RPC_TOTAL_DAY (TODAYKWATTH)
/
create index IDX_T_AGGREGATION_IBALANCE on TBL_RPC_TOTAL_DAY (IDEGREEBALANCE)
/
create index IDX_T_AGGREGATION_RUNEFF on TBL_RPC_TOTAL_DAY (RUNTIMEEFFICIENCY)
/
create index IDX_T_AGGREGATION_RUNSTATUS on TBL_RPC_TOTAL_DAY (RUNSTATUS)
/
create index IDX_T_AGGREGATION_SURFEFF on TBL_RPC_TOTAL_DAY (SURFACESYSTEMEFFICIENCY)
/
create index IDX_T_AGGREGATION_SYSEFF on TBL_RPC_TOTAL_DAY (SYSTEMEFFICIENCY)
/
create index IDX_T_AGGREGATION_WATTBALANCE on TBL_RPC_TOTAL_DAY (WATTDEGREEBALANCE)
/
create index IDX_T_AGGREGATION_WELLID on TBL_RPC_TOTAL_DAY (WELLID)
/
create index IDX_T_AGGREGATION_WELLID_DATE on TBL_RPC_TOTAL_DAY (CALCULATEDATE, WELLID)
/

/*==============================================================*/
/* Table: TBL_RPC_WORKTYPE                                    */
/*==============================================================*/
create table TBL_RPC_WORKTYPE
(
  ID                          NUMBER(10) not null,
  WORKINGCONDITIONCODE        NUMBER(4) not null,
  WORKINGCONDITIONNAME        VARCHAR2(200) not null,
  WORKINGCONDITIONDESCRIPTION VARCHAR2(200),
  WORKINGCONDITIONTEMPLATE    BLOB,
  OPTIMIZATIONSUGGESTION      VARCHAR2(200),
  REMARK                      VARCHAR2(200)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/

/*==============================================================*/
/* Table: TBL_USER                                    */
/*==============================================================*/
create table TBL_USER
(
  USER_NO         NUMBER(10) not null,
  USER_ID         VARCHAR2(20) not null,
  USER_PWD        VARCHAR2(20),
  USER_NAME       VARCHAR2(40) not null,
  USER_IN_EMAIL   VARCHAR2(40),
  USER_OUT_EMAIL  VARCHAR2(100),
  USER_PHONE      VARCHAR2(40),
  USER_MOBILE     VARCHAR2(40),
  USER_ADDRESS    VARCHAR2(200),
  USER_POSTCODE   CHAR(6),
  USER_TITLE      VARCHAR2(100),
  USER_TYPE       NUMBER(10) default 1,
  USER_ORGID      NUMBER(10) default 0 not null,
  USER_ISLEADER   CHAR(1) default '0',
  USER_REGTIME    DATE,
  USER_STYLE      VARCHAR2(20) default 'basic',
  USER_QUICKLOGIN NUMBER(1) default 0
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_USER
  add constraint PK_USER_NO primary key (USER_NO)
/
create index IDX_REQUIREPASS on TBL_USER (USER_QUICKLOGIN)
/
create index IDX_USER_ID_PWD on TBL_USER (USER_PWD, USER_ID)
/
create index IDX_USER_ORGID on TBL_USER (USER_ORGID)
/
create index IDX_USER_PWD on TBL_USER (USER_PWD)
/
create unique index UNI_USER_ID on TBL_USER (USER_ID)
/

/*==============================================================*/
/* Table: TBL_WELLBORETRAJECTORY                                    */
/*==============================================================*/
create table TBL_WELLBORETRAJECTORY
(
  ID             NUMBER(10) not null,
  WELLID         NUMBER(10) not null,
  MEASURINGDEPTH CLOB,
  VERTICALDEPTH  CLOB,
  DEVIATIONANGLE CLOB,
  AZIMUTHANGLE   CLOB,
  X              CLOB,
  Y              CLOB,
  Z              CLOB,
  SAVETIME       DATE default sysdate,
  RESULTSTATUS   NUMBER(4) default 0
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_WELLBORETRAJECTORY
  add constraint PK_WELLBORETRAJECTORY primary key (ID)
/
create index IDX_WELLBORETRAJECTORY_WELLID on TBL_WELLBORETRAJECTORY (WELLID)
/

/*==============================================================*/
/* Table: TBL_WELLINFORMATION                                    */
/*==============================================================*/
create table TBL_WELLINFORMATION
(
  ID                         NUMBER(10) not null,
  ORGID                      NUMBER(10),
  RESNAME                    VARCHAR2(200),
  WELLNAME                   VARCHAR2(200) not null,
  LIFTINGTYPE                NUMBER(10) default 200,
  DEVICEADDR                 VARCHAR2(200),
  DEVICEID                   VARCHAR2(200),
  PROTOCOLCODE               VARCHAR2(50),
  UNITCODE                   VARCHAR2(50),
  VIDEOURL                   VARCHAR2(400),
  SORTNUM                    NUMBER(10) default 9999,
  LEVELCORRECTVALUE          NUMBER(2) default 0,
  PROTOCOL                   NUMBER(2) default 1
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_WELLINFORMATION
  add constraint PK_WELLINFORMATION primary key (ID)
/
create index IDX_WELLINFORMATION_NAME on TBL_WELLINFORMATION (WELLNAME)
/
create index IDX_WELLINFORMATION_RESNAME on TBL_WELLINFORMATION (RESNAME)
/

/*==============================================================*/
/* Database package: MYPACKAGE                                  */
/*==============================================================*/
create or replace package MYPACKAGE as
   type MY_CURSOR is REF CURSOR;
end MYPACKAGE;
/

create or replace package body MYPACKAGE as
   
end MYPACKAGE;
/