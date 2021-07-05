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
  id                       NUMBER(10) not null,
  wellid                   NUMBER(10) not null,
  acqtime                  DATE,
  commstatus               NUMBER(2) default 0,
  commtime                 NUMBER(8,2) default 0,
  commtimeefficiency       NUMBER(10,4) default 0,
  runstatus                NUMBER(2) default 0,
  runtimeefficiency        NUMBER(10,4) default 0,
  runtime                  NUMBER(8,2) default 0,
  ia                       NUMBER(8,2),
  ib                       NUMBER(8,2),
  ic                       NUMBER(8,2),
  va                       NUMBER(8,2),
  vb                       NUMBER(8,2),
  vc                       NUMBER(8,2),
  totalkwatth              NUMBER(8,2),
  totalkvarh               NUMBER(8,2),
  watt3                    NUMBER(8,2),
  var3                     NUMBER(8,2),
  reversepower             NUMBER(8,2),
  pf3                      NUMBER(8,2),
  setfrequency             NUMBER(8,2),
  runfrequency             NUMBER(8,2),
  tubingpressure           NUMBER(8,2),
  casingpressure           NUMBER(8,2),
  backpressure             NUMBER(8,2),
  wellheadfluidtemperature NUMBER(8,2),
  todaykwatth              NUMBER(8,2) default 0,
  resultcode               NUMBER(4) default 0,
  iaalarm                  VARCHAR2(20) default '0/0/0/0',
  ibalarm                  VARCHAR2(20) default '0/0/0/0',
  icalarm                  VARCHAR2(20) default '0/0/0/0',
  vaalarm                  VARCHAR2(20) default '0/0/0/0',
  vbalarm                  VARCHAR2(20) default '0/0/0/0',
  vcalarm                  VARCHAR2(20) default '0/0/0/0',
  iauplimit                NUMBER(8,2),
  iadownlimit              NUMBER(8,2),
  ibuplimit                NUMBER(8,2),
  ibdownlimit              NUMBER(8,2),
  icuplimit                NUMBER(8,2),
  icdownlimit              NUMBER(8,2),
  vauplimit                NUMBER(8,2),
  vadownlimit              NUMBER(8,2),
  vbuplimit                NUMBER(8,2),
  vbdownlimit              NUMBER(8,2),
  vcuplimit                NUMBER(8,2),
  vcdownlimit              NUMBER(8,2),
  iazero                   NUMBER(8,2),
  ibzero                   NUMBER(8,2),
  iczero                   NUMBER(8,2),
  vazero                   NUMBER(8,2),
  vbzero                   NUMBER(8,2),
  vczero                   NUMBER(8,2),
  totalpkwatth             NUMBER(10,2),
  totalnkwatth             NUMBER(10,2),
  totalpkvarh              NUMBER(10,2),
  totalnkvarh              NUMBER(10,2),
  totalkvah                NUMBER(10,2),
  todaypkwatth             NUMBER(10,2) default 0,
  todaynkwatth             NUMBER(10,2) default 0,
  todaykvarh               NUMBER(10,2) default 0,
  todaypkvarh              NUMBER(10,2) default 0,
  todaynkvarh              NUMBER(10,2) default 0,
  todaykvah                NUMBER(10,2),
  va3                      NUMBER(8,2),
  signal                   NUMBER(8,2),
  interval                 NUMBER(10),
  devicever                VARCHAR2(50),
  vavg                     NUMBER(8,2),
  iavg                     NUMBER(8,2),
  watta                    NUMBER(8,2),
  wattb                    NUMBER(8,2),
  wattc                    NUMBER(8,2),
  vara                     NUMBER(8,2),
  varb                     NUMBER(8,2),
  varc                     NUMBER(8,2),
  vaa                      NUMBER(8,2),
  vab                      NUMBER(8,2),
  vac                      NUMBER(8,2),
  pfa                      NUMBER(8,2),
  pfb                      NUMBER(8,2),
  pfc                      NUMBER(8,2),
  commrange                CLOB,
  runrange                 CLOB,
  resultstring   CLOB
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
  add constraint PK_PCP_DISCRETE_HIST primary key (ID)
/
create index IDX_PCP_DISCRETE_HIST_CODE on TBL_PCP_DISCRETE_HIST (RESULTCODE)
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
  id                       NUMBER(10) not null,
  wellid                   NUMBER(10) not null,
  acqtime                  DATE,
  commstatus               NUMBER(2) default 0,
  commtime                 NUMBER(8,2) default 0,
  commtimeefficiency       NUMBER(10,4) default 0,
  runstatus                NUMBER(2) default 0,
  runtimeefficiency        NUMBER(10,4) default 0,
  runtime                  NUMBER(8,2) default 0,
  ia                       NUMBER(8,2),
  ib                       NUMBER(8,2),
  ic                       NUMBER(8,2),
  va                       NUMBER(8,2),
  vb                       NUMBER(8,2),
  vc                       NUMBER(8,2),
  totalkwatth              NUMBER(8,2),
  totalkvarh               NUMBER(8,2),
  watt3                    NUMBER(8,2),
  var3                     NUMBER(8,2),
  reversepower             NUMBER(8,2),
  pf3                      NUMBER(8,2),
  setfrequency             NUMBER(8,2),
  runfrequency             NUMBER(8,2),
  tubingpressure           NUMBER(8,2),
  casingpressure           NUMBER(8,2),
  backpressure             NUMBER(8,2),
  wellheadfluidtemperature NUMBER(8,2),
  todaykwatth              NUMBER(8,2) default 0,
  resultcode               NUMBER(4) default 0,
  iaalarm                  VARCHAR2(20) default '0/0/0/0',
  ibalarm                  VARCHAR2(20) default '0/0/0/0',
  icalarm                  VARCHAR2(20) default '0/0/0/0',
  vaalarm                  VARCHAR2(20) default '0/0/0/0',
  vbalarm                  VARCHAR2(20) default '0/0/0/0',
  vcalarm                  VARCHAR2(20) default '0/0/0/0',
  iauplimit                NUMBER(8,2),
  iadownlimit              NUMBER(8,2),
  ibuplimit                NUMBER(8,2),
  ibdownlimit              NUMBER(8,2),
  icuplimit                NUMBER(8,2),
  icdownlimit              NUMBER(8,2),
  vauplimit                NUMBER(8,2),
  vadownlimit              NUMBER(8,2),
  vbuplimit                NUMBER(8,2),
  vbdownlimit              NUMBER(8,2),
  vcuplimit                NUMBER(8,2),
  vcdownlimit              NUMBER(8,2),
  iazero                   NUMBER(8,2),
  ibzero                   NUMBER(8,2),
  iczero                   NUMBER(8,2),
  vazero                   NUMBER(8,2),
  vbzero                   NUMBER(8,2),
  vczero                   NUMBER(8,2),
  totalpkwatth             NUMBER(10,2),
  totalnkwatth             NUMBER(10,2),
  totalpkvarh              NUMBER(10,2),
  totalnkvarh              NUMBER(10,2),
  totalkvah                NUMBER(10,2),
  todaypkwatth             NUMBER(10,2) default 0,
  todaynkwatth             NUMBER(10,2) default 0,
  todaykvarh               NUMBER(10,2) default 0,
  todaypkvarh              NUMBER(10,2) default 0,
  todaynkvarh              NUMBER(10,2) default 0,
  todaykvah                NUMBER(10,2),
  va3                      NUMBER(8,2),
  signal                   NUMBER(8,2),
  interval                 NUMBER(10),
  devicever                VARCHAR2(50),
  vavg                     NUMBER(8,2),
  iavg                     NUMBER(8,2),
  watta                    NUMBER(8,2),
  wattb                    NUMBER(8,2),
  wattc                    NUMBER(8,2),
  vara                     NUMBER(8,2),
  varb                     NUMBER(8,2),
  varc                     NUMBER(8,2),
  vaa                      NUMBER(8,2),
  vab                      NUMBER(8,2),
  vac                      NUMBER(8,2),
  pfa                      NUMBER(8,2),
  pfb                      NUMBER(8,2),
  pfc                      NUMBER(8,2),
  commrange                CLOB,
  runrange                 CLOB,
  resultstring             CLOB
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
  add constraint PK_PCP_DISCRETE_LATEST primary key (ID, WELLID)
/
create index IDX_PCP_DISCRETE_LATEST_CODE on TBL_PCP_DISCRETE_LATEST (RESULTCODE)
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
  VOLUMEWATERCUT             NUMBER(8,2) default 1,
  WEIGHTWATERCUT             NUMBER(8,2) default 1,
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
  VOLUMEWATERCUT             NUMBER(8,2) default 1,
  WEIGHTWATERCUT             NUMBER(8,2) default 1,
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
  RESULTCODE       NUMBER(4),
  THEORETICALPRODUCTION      NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTION NUMBER(8,2),
  OILVOLUMETRICPRODUCTION    NUMBER(8,2),
  WATERVOLUMETRICPRODUCTION  NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTION     NUMBER(8,2),
  OILWEIGHTPRODUCTION        NUMBER(8,2),
  WATERWEIGHTPRODUCTION      NUMBER(8,2),
  AVERAGEWATT      NUMBER(8,2),
  WATERPOWER                 NUMBER(8,2),
  SYSTEMEFFICIENCY           NUMBER(12,3),
  ENERGYPER100MLIFT     NUMBER(8,2),
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
  RESULTCODE       NUMBER(4),
  THEORETICALPRODUCTION      NUMBER(8,2),
  LIQUIDVOLUMETRICPRODUCTION NUMBER(8,2),
  OILVOLUMETRICPRODUCTION    NUMBER(8,2),
  WATERVOLUMETRICPRODUCTION  NUMBER(8,2),
  LIQUIDWEIGHTPRODUCTION     NUMBER(8,2),
  OILWEIGHTPRODUCTION        NUMBER(8,2),
  WATERWEIGHTPRODUCTION      NUMBER(8,2),
  AVERAGEWATT      NUMBER(8,2),
  WATERPOWER                 NUMBER(8,2),
  SYSTEMEFFICIENCY           NUMBER(12,3),
  ENERGYPER100MLIFT     NUMBER(8,2),
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
  id                            NUMBER(10) not null,
  wellid                        NUMBER(10),
  calculatedate                 DATE,
  commstatus                    NUMBER(2) default 0,
  runstatus                     NUMBER(2) default 0,
  commtime                      NUMBER(8,2) default 0,
  commtimeefficiency            NUMBER(12,3) default 0,
  runtime                       NUMBER(8,2) default 0,
  runtimeefficiency             NUMBER(12,3) default 0,
  resultcode                    NUMBER(4) default 0,
  rpm                           NUMBER(8,2),
  rpmmax                        NUMBER(8,2),
  rpmmin                        NUMBER(8,2),
  torque                        NUMBER(8,2),
  torquemax                     NUMBER(8,2),
  torquemin                     NUMBER(8,2),
  liquidvolumetricproduction    NUMBER(8,2),
  oilvolumetricproduction       NUMBER(8,2),
  watervolumetricproduction     NUMBER(8,2),
  liquidweightproduction        NUMBER(8,2),
  oilweightproduction           NUMBER(8,2),
  waterweightproduction         NUMBER(8,2),
  liquidvolumetricproductionmax NUMBER(8,2),
  liquidvolumetricproductionmin NUMBER(8,2),
  oilvolumetricproductionmax    NUMBER(8,2),
  oilvolumetricproductionmin    NUMBER(8,2),
  watervolumetricproductionmax  NUMBER(8,2),
  watervolumetricproductionmin  NUMBER(8,2),
  liquidweightproductionmax     NUMBER(8,2),
  liquidweightproductionmin     NUMBER(8,2),
  oilweightproductionmax        NUMBER(8,2),
  oilweightproductionmin        NUMBER(8,2),
  waterweightproductionmax      NUMBER(8,2),
  waterweightproductionmin      NUMBER(8,2),
  volumewatercut                NUMBER(10,4),
  weightwatercut                NUMBER(10,4),
  volumewatercutmax             NUMBER(10,4),
  volumewatercutmin             NUMBER(10,4),
  weightwatercutmax             NUMBER(10,4),
  weightwatercutmin             NUMBER(10,4),
  tubingpressure                NUMBER(8,2),
  tubingpressuremax             NUMBER(8,2),
  tubingpressuremin             NUMBER(8,2),
  casingpressure                NUMBER(8,2),
  casingpressuremax             NUMBER(8,2),
  casingpressuremin             NUMBER(8,2),
  wellheadfluidtemperature      NUMBER(8,2),
  wellheadfluidtemperaturemax   NUMBER(8,2),
  wellheadfluidtemperaturemin   NUMBER(8,2),
  productiongasoilratio         NUMBER(8,2),
  productiongasoilratiomax      NUMBER(8,2),
  productiongasoilratiomin      NUMBER(8,2),
  producingfluidlevel           NUMBER(8,2),
  producingfluidlevelmax        NUMBER(8,2),
  producingfluidlevelmin        NUMBER(8,2),
  pumpsettingdepth              NUMBER(8,2),
  pumpsettingdepthmax           NUMBER(8,2),
  pumpsettingdepthmin           NUMBER(8,2),
  submergence                   NUMBER(8,2),
  submergencemax                NUMBER(8,2),
  submergencemin                NUMBER(8,2),
  pumpborediameter              NUMBER(8,2),
  pumpborediametermax           NUMBER(8,2),
  pumpborediametermin           NUMBER(8,2),
  systemefficiency              NUMBER(10,4),
  systemefficiencymax           NUMBER(10,4),
  systemefficiencymin           NUMBER(10,4),
  surfacesystemefficiency       NUMBER(10,4),
  surfacesystemefficiencymax    NUMBER(10,4),
  surfacesystemefficiencymin    NUMBER(10,4),
  welldownsystemefficiency      NUMBER(10,4),
  welldownsystemefficiencymax   NUMBER(10,4),
  welldownsystemefficiencymin   NUMBER(10,4),
  energyper100mlift             NUMBER(8,2),
  energyper100mliftmax          NUMBER(8,2),
  energyper100mliftmin          NUMBER(8,2),
  pumpeff                       NUMBER(10,4),
  pumpeffmax                    NUMBER(10,4),
  pumpeffmin                    NUMBER(10,4),
  ia                            NUMBER(8,2),
  iamax                         NUMBER(8,2),
  iamin                         NUMBER(8,2),
  ib                            NUMBER(8,2),
  ibmax                         NUMBER(8,2),
  ibmin                         NUMBER(8,2),
  ic                            NUMBER(8,2),
  icmax                         NUMBER(8,2),
  icmin                         NUMBER(8,2),
  va                            NUMBER(8,2),
  vamax                         NUMBER(8,2),
  vamin                         NUMBER(8,2),
  vb                            NUMBER(8,2),
  vbmax                         NUMBER(8,2),
  vbmin                         NUMBER(8,2),
  vc                            NUMBER(8,2),
  vcmax                         NUMBER(8,2),
  vcmin                         NUMBER(8,2),
  watt3                         NUMBER(8,2),
  watt3max                      NUMBER(8,2),
  watt3min                      NUMBER(8,2),
  var3                          NUMBER(8,2),
  var3max                       NUMBER(8,2),
  var3min                       NUMBER(8,2),
  pf3                           NUMBER(8,2),
  pf3max                        NUMBER(8,2),
  pf3min                        NUMBER(8,2),
  frequency                     NUMBER(8,2),
  frequencymax                  NUMBER(8,2),
  frequencymin                  NUMBER(8,2),
  todaykwatth                   NUMBER(10,2) default 0,
  todaypkwatth                  NUMBER(10,2) default 0,
  todaynkwatth                  NUMBER(10,2) default 0,
  todaykvarh                    NUMBER(10,2) default 0,
  todaypkvarh                   NUMBER(10,2) default 0,
  todaynkvarh                   NUMBER(10,2) default 0,
  todaykvah                     NUMBER(10,2) default 0,
  totalkwatth                   NUMBER(10,2) default 0,
  totalpkwatth                  NUMBER(10,2) default 0,
  totalnkwatth                  NUMBER(10,2) default 0,
  totalkvarh                    NUMBER(10,2) default 0,
  totalpkvarh                   NUMBER(10,2) default 0,
  totalnkvarh                   NUMBER(10,2) default 0,
  totalkvah                     NUMBER(10,2) default 0,
  extendeddays                  NUMBER(5),
  resultstatus                  NUMBER(2),
  signal                        NUMBER(8,2),
  signalmax                     NUMBER(8,2),
  signalmin                     NUMBER(8,2),
  savetime                      DATE default sysdate,
  theoreticalproduction         NUMBER(8,2),
  theoreticalproductionmax      NUMBER(8,2),
  theoreticalproductionmin      NUMBER(8,2),
  avgwatt                       NUMBER(8,2),
  avgwattmax                    NUMBER(8,2),
  avgwattmin                    NUMBER(8,2),
  waterpower                    NUMBER(8,2),
  waterpowermax                 NUMBER(8,2),
  waterpowermin                 NUMBER(8,2),
  pumpeff1                      NUMBER(10,4),
  pumpeff1max                   NUMBER(10,4),
  pumpeff1min                   NUMBER(10,4),
  pumpeff2                      NUMBER(10,4),
  pumpeff2max                   NUMBER(10,4),
  pumpeff2min                   NUMBER(10,4),
  va3                           NUMBER(8,2),
  va3max                        NUMBER(8,2),
  va3min                        NUMBER(8,2),
  commrange                     CLOB,
  runrange                      CLOB,
  resultstring                  CLOB
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
  id         NUMBER(10) not null,
  resultcode NUMBER(10) not null,
  alarmtype  NUMBER(3) not null,
  alarmlevel NUMBER(3) not null,
  alarmsign  NUMBER(1) default 0,
  remark     VARCHAR2(200)
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
create index IDX_RPC_ALARMTYPE_CONF_SIGN on TBL_RPC_ALARMTYPE_CONF (ALARMSIGN)
/
create index IDX_RPC_ALARMTYPE_CONF_TYPE on TBL_RPC_ALARMTYPE_CONF (ALARMTYPE)
/
create index IDX_RPC_ALARMTYPE_CONF_CODE on TBL_RPC_ALARMTYPE_CONF (resultcode)
/
create index IDX_RPC_ALARMTYPE_CONF_COMP on TBL_RPC_ALARMTYPE_CONF (ALARMTYPE, resultcode)
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
  RESULTCODE                     NUMBER(4),
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
  AVERAGEWATT                    NUMBER(8,2),
  POLISHRODPOWER                 NUMBER(8,2),
  WATERPOWER                     NUMBER(8,2),
  SURFACESYSTEMEFFICIENCY        NUMBER(12,3),
  WELLDOWNSYSTEMEFFICIENCY       NUMBER(12,3),
  SYSTEMEFFICIENCY               NUMBER(12,3),
  ENERGYPER100MLIFT              NUMBER(8,2),
  AREA                           NUMBER(8,2),
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
create index IDX_T_FSDIAGRAM_CODE on TBL_RPC_DIAGRAM_HIST (RESULTCODE)
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
  RESULTCODE                     NUMBER(4),
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
  AVERAGEWATT                    NUMBER(8,2),
  POLISHRODPOWER                 NUMBER(8,2),
  WATERPOWER                     NUMBER(8,2),
  SURFACESYSTEMEFFICIENCY        NUMBER(12,3),
  WELLDOWNSYSTEMEFFICIENCY       NUMBER(12,3),
  SYSTEMEFFICIENCY               NUMBER(12,3),
  ENERGYPER100MLIFT              NUMBER(8,2),
  AREA                           NUMBER(8,2),
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
create index IDX_T_FSDIAGRAM_R_CODE on TBL_RPC_DIAGRAM_LATEST (RESULTCODE)
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
  id                        NUMBER(10) not null,
  wellid                    NUMBER(10) not null,
  acqtime                   DATE,
  commstatus                NUMBER(2) default 0,
  commtime                  NUMBER(8,2) default 0,
  commtimeefficiency        NUMBER(10,4) default 0,
  runstatus                 NUMBER(2) default 0,
  runtimeefficiency         NUMBER(10,4) default 0,
  runtime                   NUMBER(8,2) default 0,
  ia                        NUMBER(8,2),
  ib                        NUMBER(8,2),
  ic                        NUMBER(8,2),
  va                        NUMBER(8,2),
  vb                        NUMBER(8,2),
  vc                        NUMBER(8,2),
  totalkwatth               NUMBER(10,2),
  totalkvarh                NUMBER(10,2),
  watt3                     NUMBER(8,2),
  var3                      NUMBER(8,2),
  reversepower              NUMBER(8,2),
  pf3                       NUMBER(8,2),
  acqcycle_diagram          NUMBER(6),
  setfrequency              NUMBER(8,2),
  runfrequency              NUMBER(8,2),
  tubingpressure            NUMBER(8,2),
  casingpressure            NUMBER(8,2),
  backpressure              NUMBER(8,2),
  wellheadfluidtemperature  NUMBER(8,2),
  todaykwatth               NUMBER(10,2) default 0,
  resultcode                NUMBER(4),
  iaalarm                   VARCHAR2(20) default '0/0/0/0',
  ibalarm                   VARCHAR2(20) default '0/0/0/0',
  icalarm                   VARCHAR2(20) default '0/0/0/0',
  vaalarm                   VARCHAR2(20) default '0/0/0/0',
  vbalarm                   VARCHAR2(20) default '0/0/0/0',
  vcalarm                   VARCHAR2(20) default '0/0/0/0',
  iauplimit                 NUMBER(10,2),
  iadownlimit               NUMBER(10,2),
  ibuplimit                 NUMBER(10,2),
  ibdownlimit               NUMBER(10,2),
  icuplimit                 NUMBER(10,2),
  icdownlimit               NUMBER(10,2),
  vauplimit                 NUMBER(10,2),
  vadownlimit               NUMBER(10,2),
  vbuplimit                 NUMBER(10,2),
  vbdownlimit               NUMBER(10,2),
  vcuplimit                 NUMBER(10,2),
  vcdownlimit               NUMBER(10,2),
  iazero                    NUMBER(8,2),
  ibzero                    NUMBER(8,2),
  iczero                    NUMBER(8,2),
  vazero                    NUMBER(8,2),
  vbzero                    NUMBER(8,2),
  vczero                    NUMBER(8,2),
  totalpkwatth              NUMBER(10,2),
  totalnkwatth              NUMBER(10,2),
  totalpkvarh               NUMBER(10,2),
  totalnkvarh               NUMBER(10,2),
  totalkvah                 NUMBER(10,2),
  todaypkwatth              NUMBER(10,2) default 0,
  todaynkwatth              NUMBER(10,2) default 0,
  todaykvarh                NUMBER(10,2) default 0,
  todaypkvarh               NUMBER(10,2) default 0,
  todaynkvarh               NUMBER(10,2) default 0,
  todaykvah                 NUMBER(10,2),
  va3                       NUMBER(8,2),
  signal                    NUMBER(8,2),
  interval                  NUMBER(10),
  devicever                 VARCHAR2(50),
  vavg                      NUMBER(8,2),
  iavg                      NUMBER(8,2),
  watta                     NUMBER(8,2),
  wattb                     NUMBER(8,2),
  wattc                     NUMBER(8,2),
  vara                      NUMBER(8,2),
  varb                      NUMBER(8,2),
  varc                      NUMBER(8,2),
  vaa                       NUMBER(8,2),
  vab                       NUMBER(8,2),
  vac                       NUMBER(8,2),
  pfa                       NUMBER(8,2),
  pfb                       NUMBER(8,2),
  pfc                       NUMBER(8,2),
  balancecontrolmode        NUMBER(10) default 0,
  balancecalculatemode      NUMBER(10) default 1,
  balanceawaytime           NUMBER(10) default 0,
  balanceclosetime          NUMBER(10) default 0,
  balancestrokecount        NUMBER(10) default 1,
  balanceoperationuplimit   NUMBER(10) default 100,
  balanceoperationdownlimit NUMBER(10) default 85,
  balanceautocontrol        NUMBER(1),
  balancefrontlimit         NUMBER(1),
  balanceafterlimit         NUMBER(1),
  spmautocontrol            NUMBER(1),
  balanceawaytimeperbeat    NUMBER(10) default 0,
  balanceclosetimeperbeat   NUMBER(10) default 0,
  acqcycle_discrete         NUMBER(10),
  wattuplimit               NUMBER(8,2),
  wattdownlimit             NUMBER(8,2),
  iamax                     NUMBER(8,2),
  ibmax                     NUMBER(8,2),
  iamin                     NUMBER(8,2),
  ibmin                     NUMBER(8,2),
  icmax                     NUMBER(8,2),
  icmin                     NUMBER(8,2),
  savetime                  DATE default sysdate,
  commrange                 CLOB,
  runrange                  CLOB,
  resultstring              CLOB
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
create index IDX_T_DISCRETE_CODE on TBL_RPC_DISCRETE_HIST (RESULTCODE)
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
  id                        NUMBER(10) not null,
  wellid                    NUMBER(10) not null,
  acqtime                   DATE,
  commstatus                NUMBER(2) default 0,
  commtime                  NUMBER(8,2) default 0,
  commtimeefficiency        NUMBER(10,4) default 0,
  runstatus                 NUMBER(2) default 0,
  runtimeefficiency         NUMBER(10,4) default 0,
  runtime                   NUMBER(8,2) default 0,
  ia                        NUMBER(8,2),
  ib                        NUMBER(8,2),
  ic                        NUMBER(8,2),
  va                        NUMBER(8,2),
  vb                        NUMBER(8,2),
  vc                        NUMBER(8,2),
  totalkwatth               NUMBER(10,2),
  totalkvarh                NUMBER(10,2),
  watt3                     NUMBER(8,2),
  var3                      NUMBER(8,2),
  reversepower              NUMBER(8,2),
  pf3                       NUMBER(8,2),
  acqcycle_diagram          NUMBER(6),
  setfrequency              NUMBER(8,2),
  runfrequency              NUMBER(8,2),
  tubingpressure            NUMBER(8,2),
  casingpressure            NUMBER(8,2),
  backpressure              NUMBER(8,2),
  wellheadfluidtemperature  NUMBER(8,2),
  todaykwatth               NUMBER(10,2) default 0,
  resultcode                NUMBER(4),
  iaalarm                   VARCHAR2(20) default '0/0/0/0',
  ibalarm                   VARCHAR2(20) default '0/0/0/0',
  icalarm                   VARCHAR2(20) default '0/0/0/0',
  vaalarm                   VARCHAR2(20) default '0/0/0/0',
  vbalarm                   VARCHAR2(20) default '0/0/0/0',
  vcalarm                   VARCHAR2(20) default '0/0/0/0',
  iauplimit                 NUMBER(10,2),
  iadownlimit               NUMBER(10,2),
  ibuplimit                 NUMBER(10,2),
  ibdownlimit               NUMBER(10,2),
  icuplimit                 NUMBER(10,2),
  icdownlimit               NUMBER(10,2),
  vauplimit                 NUMBER(10,2),
  vadownlimit               NUMBER(10,2),
  vbuplimit                 NUMBER(10,2),
  vbdownlimit               NUMBER(10,2),
  vcuplimit                 NUMBER(10,2),
  vcdownlimit               NUMBER(10,2),
  iazero                    NUMBER(8,2),
  ibzero                    NUMBER(8,2),
  iczero                    NUMBER(8,2),
  vazero                    NUMBER(8,2),
  vbzero                    NUMBER(8,2),
  vczero                    NUMBER(8,2),
  totalpkwatth              NUMBER(10,2),
  totalnkwatth              NUMBER(10,2),
  totalpkvarh               NUMBER(10,2),
  totalnkvarh               NUMBER(10,2),
  totalkvah                 NUMBER(10,2),
  todaypkwatth              NUMBER(10,2) default 0,
  todaynkwatth              NUMBER(10,2) default 0,
  todaykvarh                NUMBER(10,2) default 0,
  todaypkvarh               NUMBER(10,2) default 0,
  todaynkvarh               NUMBER(10,2) default 0,
  todaykvah                 NUMBER(10,2),
  va3                       NUMBER(8,2),
  signal                    NUMBER(8,2),
  interval                  NUMBER(10),
  devicever                 VARCHAR2(50),
  vavg                      NUMBER(8,2),
  iavg                      NUMBER(8,2),
  watta                     NUMBER(8,2),
  wattb                     NUMBER(8,2),
  wattc                     NUMBER(8,2),
  vara                      NUMBER(8,2),
  varb                      NUMBER(8,2),
  varc                      NUMBER(8,2),
  vaa                       NUMBER(8,2),
  vab                       NUMBER(8,2),
  vac                       NUMBER(8,2),
  pfa                       NUMBER(8,2),
  pfb                       NUMBER(8,2),
  pfc                       NUMBER(8,2),
  balancecontrolmode        NUMBER(10) default 0,
  balancecalculatemode      NUMBER(10) default 1,
  balanceawaytime           NUMBER(10) default 0,
  balanceclosetime          NUMBER(10) default 0,
  balancestrokecount        NUMBER(10) default 1,
  balanceoperationuplimit   NUMBER(10) default 100,
  balanceoperationdownlimit NUMBER(10) default 85,
  balanceautocontrol        NUMBER(1),
  balancefrontlimit         NUMBER(1),
  balanceafterlimit         NUMBER(1),
  spmautocontrol            NUMBER(1),
  balanceawaytimeperbeat    NUMBER(10) default 0,
  balanceclosetimeperbeat   NUMBER(10) default 0,
  acqcycle_discrete         NUMBER(10),
  wattuplimit               NUMBER(8,2),
  wattdownlimit             NUMBER(8,2),
  iamax                     NUMBER(8,2),
  ibmax                     NUMBER(8,2),
  iamin                     NUMBER(8,2),
  ibmin                     NUMBER(8,2),
  icmax                     NUMBER(8,2),
  icmin                     NUMBER(8,2),
  savetime                  DATE default sysdate,
  commrange                 CLOB,
  runrange                  CLOB,
  resultstring              CLOB
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
create index IDX_T_DISCRETE_RT_CODE on TBL_RPC_DISCRETE_LATEST (RESULTCODE)
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
  VOLUMEWATERCUT             NUMBER(8,2) default 1,
  WEIGHTWATERCUT             NUMBER(8,2) default 1,
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
  VOLUMEWATERCUT             NUMBER(8,2) default 1,
  WEIGHTWATERCUT             NUMBER(8,2) default 1,
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
  id                             NUMBER(10) not null,
  wellid                         NUMBER(10),
  calculatedate                  DATE,
  commstatus                     NUMBER(2) default 0,
  runstatus                      NUMBER(2) default 0,
  commtime                       NUMBER(8,2) default 0,
  commtimeefficiency             NUMBER(12,3) default 0,
  runtime                        NUMBER(8,2) default 0,
  runtimeefficiency              NUMBER(12,3) default 0,
  resultcode                     NUMBER(4),
  resultcode_e                   NUMBER(4),
  fullnesscoefficient            NUMBER(10,4),
  fullnesscoefficientmax         NUMBER(10,4),
  fullnesscoefficientmin         NUMBER(10,4),
  stroke                         NUMBER(8,2),
  strokemax                      NUMBER(8,2),
  strokemin                      NUMBER(8,2),
  spm                            NUMBER(8,2),
  spmmax                         NUMBER(8,2),
  spmmin                         NUMBER(8,2),
  liquidvolumetricproduction     NUMBER(8,2),
  oilvolumetricproduction        NUMBER(8,2),
  watervolumetricproduction      NUMBER(8,2),
  liquidweightproduction         NUMBER(8,2),
  oilweightproduction            NUMBER(8,2),
  waterweightproduction          NUMBER(8,2),
  liquidvolumetricproductionmax  NUMBER(8,2),
  liquidvolumetricproductionmin  NUMBER(8,2),
  oilvolumetricproductionmax     NUMBER(8,2),
  oilvolumetricproductionmin     NUMBER(8,2),
  watervolumetricproductionmax   NUMBER(8,2),
  watervolumetricproductionmin   NUMBER(8,2),
  liquidweightproductionmax      NUMBER(8,2),
  liquidweightproductionmin      NUMBER(8,2),
  oilweightproductionmax         NUMBER(8,2),
  oilweightproductionmin         NUMBER(8,2),
  waterweightproductionmax       NUMBER(8,2),
  waterweightproductionmin       NUMBER(8,2),
  wattdegreebalance              NUMBER(8,2),
  idegreebalance                 NUMBER(8,2),
  wattdegreebalancemax           NUMBER(8,2),
  wattdegreebalancemin           NUMBER(8,2),
  idegreebalancemax              NUMBER(8,2),
  idegreebalancemin              NUMBER(8,2),
  deltaradius                    NUMBER(8,2),
  deltaradiusmax                 NUMBER(8,2),
  deltaradiusmin                 NUMBER(8,2),
  volumewatercut                 NUMBER(10,4),
  weightwatercut                 NUMBER(10,4),
  volumewatercutmax              NUMBER(10,4),
  volumewatercutmin              NUMBER(10,4),
  weightwatercutmax              NUMBER(10,4),
  weightwatercutmin              NUMBER(10,4),
  tubingpressure                 NUMBER(8,2),
  tubingpressuremax              NUMBER(8,2),
  casingpressure                 NUMBER(8,2),
  casingpressuremax              NUMBER(8,2),
  casingpressuremin              NUMBER(8,2),
  wellheadfluidtemperature       NUMBER(8,2),
  wellheadfluidtemperaturemax    NUMBER(8,2),
  wellheadfluidtemperaturemin    NUMBER(8,2),
  productiongasoilratio          NUMBER(8,2),
  productiongasoilratiomax       NUMBER(8,2),
  productiongasoilratiomin       NUMBER(8,2),
  producingfluidlevel            NUMBER(8,2),
  producingfluidlevelmax         NUMBER(8,2),
  producingfluidlevelmin         NUMBER(8,2),
  pumpsettingdepth               NUMBER(8,2),
  pumpsettingdepthmax            NUMBER(8,2),
  pumpsettingdepthmin            NUMBER(8,2),
  submergence                    NUMBER(8,2),
  submergencemax                 NUMBER(8,2),
  submergencemin                 NUMBER(8,2),
  pumpborediameter               NUMBER(8,2),
  pumpborediametermax            NUMBER(8,2),
  pumpborediametermin            NUMBER(8,2),
  systemefficiency               NUMBER(10,4),
  systemefficiencymax            NUMBER(10,4),
  systemefficiencymin            NUMBER(10,4),
  surfacesystemefficiency        NUMBER(10,4),
  surfacesystemefficiencymax     NUMBER(10,4),
  surfacesystemefficiencymin     NUMBER(10,4),
  welldownsystemefficiency       NUMBER(10,4),
  welldownsystemefficiencymax    NUMBER(10,4),
  welldownsystemefficiencymin    NUMBER(10,4),
  energyper100mlift              NUMBER(8,2),
  energyper100mliftmax           NUMBER(8,2),
  energyper100mliftmin           NUMBER(8,2),
  pumpeff                        NUMBER(10,4),
  pumpeffmax                     NUMBER(10,4),
  pumpeffmin                     NUMBER(10,4),
  ia                             NUMBER(8,2),
  iamax                          NUMBER(8,2),
  iamin                          NUMBER(8,2),
  ib                             NUMBER(8,2),
  ibmax                          NUMBER(8,2),
  ibmin                          NUMBER(8,2),
  ic                             NUMBER(8,2),
  icmax                          NUMBER(8,2),
  icmin                          NUMBER(8,2),
  va                             NUMBER(8,2),
  vamax                          NUMBER(8,2),
  vamin                          NUMBER(8,2),
  vb                             NUMBER(8,2),
  vbmax                          NUMBER(8,2),
  vbmin                          NUMBER(8,2),
  vc                             NUMBER(8,2),
  vcmax                          NUMBER(8,2),
  vcmin                          NUMBER(8,2),
  watt3                          NUMBER(8,2),
  watt3max                       NUMBER(8,2),
  watt3min                       NUMBER(8,2),
  var3                           NUMBER(8,2),
  var3max                        NUMBER(8,2),
  var3min                        NUMBER(8,2),
  pf3                            NUMBER(8,2),
  pf3max                         NUMBER(8,2),
  frequency                      NUMBER(8,2),
  frequencymax                   NUMBER(8,2),
  frequencymin                   NUMBER(8,2),
  todaykwatth                    NUMBER(10,2) default 0,
  todaypkwatth                   NUMBER(10,2) default 0,
  todaynkwatth                   NUMBER(10,2) default 0,
  todaykvarh                     NUMBER(10,2) default 0,
  todaypkvarh                    NUMBER(10,2) default 0,
  todaynkvarh                    NUMBER(10,2) default 0,
  todaykvah                      NUMBER(10,2) default 0,
  totalkwatth                    NUMBER(10,2) default 0,
  totalpkwatth                   NUMBER(10,2) default 0,
  totalnkwatth                   NUMBER(10,2) default 0,
  totalkvarh                     NUMBER(10,2) default 0,
  totalpkvarh                    NUMBER(10,2) default 0,
  totalnkvarh                    NUMBER(10,2) default 0,
  totalkvah                      NUMBER(10,2) default 0,
  rpm                            NUMBER(8,2),
  rpmmax                         NUMBER(8,2),
  rpmmin                         NUMBER(8,2),
  extendeddays                   NUMBER(5),
  resultstatus                   NUMBER(2),
  signal                         NUMBER(8,2),
  signalmax                      NUMBER(8,2),
  signalmin                      NUMBER(8,2),
  f                              NUMBER(8,2),
  fmax                           NUMBER(8,2),
  fmin                           NUMBER(8,2),
  tubingpressuremin              NUMBER(8,2),
  pf3min                         NUMBER(8,2),
  savetime                       DATE default sysdate,
  upperloadline                  NUMBER(8,2),
  upperloadlinemax               NUMBER(8,2),
  upperloadlinemin               NUMBER(8,2),
  lowerloadline                  NUMBER(8,2),
  lowerloadlinemax               NUMBER(8,2),
  lowerloadlinemin               NUMBER(8,2),
  upperloadlineofexact           NUMBER(8,2),
  upperloadlineofexactmax        NUMBER(8,2),
  upperloadlineofexactmin        NUMBER(8,2),
  deltaloadline                  NUMBER(8,2),
  deltaloadlinemax               NUMBER(8,2),
  deltaloadlinemin               NUMBER(8,2),
  deltaloadlineofexact           NUMBER(8,2),
  deltaloadlineofexactmax        NUMBER(8,2),
  deltaloadlineofexactmin        NUMBER(8,2),
  fmax_avg                       NUMBER(8,2),
  fmax_max                       NUMBER(8,2),
  fmax_min                       NUMBER(8,2),
  fmin_avg                       NUMBER(8,2),
  fmin_max                       NUMBER(8,2),
  fmin_min                       NUMBER(8,2),
  deltaf                         NUMBER(8,2),
  deltafmax                      NUMBER(8,2),
  deltafmin                      NUMBER(8,2),
  area                           NUMBER(8,2),
  areamax                        NUMBER(8,2),
  areamin                        NUMBER(8,2),
  plungerstroke                  NUMBER(8,2),
  plungerstrokemax               NUMBER(8,2),
  plungerstrokemin               NUMBER(8,2),
  availableplungerstroke         NUMBER(8,2),
  availableplungerstrokemax      NUMBER(8,2),
  availableplungerstrokemin      NUMBER(8,2),
  theoreticalproduction          NUMBER(8,2),
  theoreticalproductionmax       NUMBER(8,2),
  theoreticalproductionmin       NUMBER(8,2),
  availablestrokeprod_v          NUMBER(8,2),
  availablestrokeprod_v_max      NUMBER(8,2),
  availablestrokeprod_v_min      NUMBER(8,2),
  pumpclearanceleakprod_v        NUMBER(8,2),
  pumpclearanceleakprod_v_max    NUMBER(8,2),
  pumpclearanceleakprod_v_min    NUMBER(8,2),
  tvleakvolumetricproduction     NUMBER(8,2),
  tvleakvolumetricproductionmax  NUMBER(8,2),
  tvleakvolumetricproductionmin  NUMBER(8,2),
  svleakvolumetricproduction     NUMBER(8,2),
  svleakvolumetricproductionmax  NUMBER(8,2),
  svleakvolumetricproductionmin  NUMBER(8,2),
  gasinfluenceprod_v             NUMBER(8,2),
  gasinfluenceprod_v_max         NUMBER(8,2),
  gasinfluenceprod_v_min         NUMBER(8,2),
  availablestrokeprod_w          NUMBER(8,2),
  availablestrokeprod_w_max      NUMBER(8,2),
  availablestrokeprod_w_min      NUMBER(8,2),
  pumpclearanceleakprod_w        NUMBER(8,2),
  pumpclearanceleakprod_w_max    NUMBER(8,2),
  pumpclearanceleakprod_w_min    NUMBER(8,2),
  tvleakweightproduction         NUMBER(8,2),
  tvleakweightproductionmax      NUMBER(8,2),
  tvleakweightproductionmin      NUMBER(8,2),
  svleakweightproduction         NUMBER(8,2),
  svleakweightproductionmax      NUMBER(8,2),
  svleakweightproductionmin      NUMBER(8,2),
  gasinfluenceprod_w             NUMBER(8,2),
  gasinfluenceprod_w_max         NUMBER(8,2),
  gasinfluenceprod_w_min         NUMBER(8,2),
  pumpeff1                       NUMBER(10,4),
  pumpeff1max                    NUMBER(10,4),
  pumpeff1min                    NUMBER(10,4),
  pumpeff2                       NUMBER(10,4),
  pumpeff2max                    NUMBER(10,4),
  pumpeff2min                    NUMBER(10,4),
  pumpeff3                       NUMBER(10,4),
  pumpeff3max                    NUMBER(10,4),
  pumpeff3min                    NUMBER(10,4),
  pumpeff4                       NUMBER(10,4),
  pumpeff4max                    NUMBER(10,4),
  pumpeff4min                    NUMBER(10,4),
  rodflexlength                  NUMBER(8,2),
  rodflexlengthmax               NUMBER(8,2),
  rodflexlengthmin               NUMBER(8,2),
  tubingflexlength               NUMBER(8,2),
  tubingflexlengthmax            NUMBER(8,2),
  tubingflexlengthmin            NUMBER(8,2),
  inertialength                  NUMBER(8,2),
  inertialengthmax               NUMBER(8,2),
  inertialengthmin               NUMBER(8,2),
  pumpintakep                    NUMBER(8,2),
  pumpintakepmax                 NUMBER(8,2),
  pumpintakepmin                 NUMBER(8,2),
  pumpintaket                    NUMBER(8,2),
  pumpintaketmax                 NUMBER(8,2),
  pumpintaketmin                 NUMBER(8,2),
  pumpintakegol                  NUMBER(8,2),
  pumpintakegolmax               NUMBER(8,2),
  pumpintakegolmin               NUMBER(8,2),
  pumpintakevisl                 NUMBER(8,2),
  pumpintakevislmax              NUMBER(8,2),
  pumpintakevislmin              NUMBER(8,2),
  pumpintakebo                   NUMBER(8,2),
  pumpintakebomax                NUMBER(8,2),
  pumpintakebomin                NUMBER(8,2),
  pumpoutletp                    NUMBER(8,2),
  pumpoutletpmax                 NUMBER(8,2),
  pumpoutletpmin                 NUMBER(8,2),
  pumpoutlett                    NUMBER(8,2),
  pumpoutlettmax                 NUMBER(8,2),
  pumpoutlettmin                 NUMBER(8,2),
  pumpoutletgol                  NUMBER(8,2),
  pumpoutletgolmax               NUMBER(8,2),
  pumpoutletgolmin               NUMBER(8,2),
  pumpoutletvisl                 NUMBER(8,2),
  pumpoutletvislmax              NUMBER(8,2),
  pumpoutletvislmin              NUMBER(8,2),
  pumpoutletbo                   NUMBER(8,2),
  pumpoutletbomax                NUMBER(8,2),
  pumpoutletbomin                NUMBER(8,2),
  upstrokewattmax                NUMBER(8,2),
  upstrokewattmax_max            NUMBER(8,2),
  upstrokewattmax_min            NUMBER(8,2),
  upstrokeimax                   NUMBER(8,2),
  upstrokeimax_max               NUMBER(8,2),
  upstrokeimax_min               NUMBER(8,2),
  avgwatt                        NUMBER(8,2),
  avgwattmax                     NUMBER(8,2),
  avgwattmin                     NUMBER(8,2),
  polishrodpower                 NUMBER(8,2),
  polishrodpowermax              NUMBER(8,2),
  polishrodpowermin              NUMBER(8,2),
  waterpower                     NUMBER(8,2),
  waterpowermax                  NUMBER(8,2),
  waterpowermin                  NUMBER(8,2),
  va3                            NUMBER(8,2),
  va3max                         NUMBER(8,2),
  va3min                         NUMBER(8,2),
  downstrokewattmax              NUMBER(8,2),
  downstrokewattmax_max          NUMBER(8,2),
  downstrokewattmax_min          NUMBER(8,2),
  downstrokeimax                 NUMBER(8,2),
  downstrokeimax_max             NUMBER(8,2),
  downstrokeimax_min             NUMBER(8,2),
  levelcorrectvalue              NUMBER(8,2),
  levelcorrectvaluemax           NUMBER(8,2),
  levelcorrectvaluemin           NUMBER(8,2),
  noliquidfullnesscoefficient    NUMBER(10,4),
  noliquidfullnesscoefficientmax NUMBER(10,4),
  noliquidfullnesscoefficientmin NUMBER(10,4),
  noliquidavailablestroke        NUMBER(10,4),
  noliquidavailablestrokemax     NUMBER(10,4),
  noliquidavailablestrokemin     NUMBER(10,4),
  commrange                      CLOB,
  runrange                       CLOB,
  resultstring                   CLOB,
  resultstring_e                 CLOB
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
create index IDX_T_AGGREGATION_CODE on TBL_RPC_TOTAL_DAY (RESULTCODE)
/
create index IDX_T_AGGREGATION_CODE_E on TBL_RPC_TOTAL_DAY (RESULTCODE_E)
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
  id                     NUMBER(10) not null,
  resultcode             NUMBER(4) not null,
  resultname             VARCHAR2(200) not null,
  resultdescription      VARCHAR2(200),
  resulttemplate         BLOB,
  optimizationsuggestion VARCHAR2(200),
  remark                 VARCHAR2(200)
)
tablespace AGILE_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
create index IDX_RPC_WORKTYPE_CODE on TBL_RPC_WORKTYPE (RESULTCODE);
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
  SIGNINID                   VARCHAR2(200),
  SLAVE                      VARCHAR2(200),
  PROTOCOLCODE               VARCHAR2(50),
  UNITCODE                   VARCHAR2(50),
  VIDEOURL                   VARCHAR2(400),
  SORTNUM                    NUMBER(10) default 9999,
  LEVELCORRECTVALUE          NUMBER(2) default 0
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