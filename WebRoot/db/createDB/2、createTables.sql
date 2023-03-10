/*==============================================================*/
/* Table: TBL_ORG                                    */
/*==============================================================*/
create table TBL_ORG
(
  org_id     NUMBER(10) not null,
  org_code   VARCHAR2(20),
  org_name   VARCHAR2(100) not null,
  org_memo   VARCHAR2(4000),
  org_parent NUMBER(10) default 0 not null,
  org_seq    NUMBER(10)
)
tablespace AP_DATA
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
  user_no         NUMBER(10) not null,
  user_id         VARCHAR2(20) not null,
  user_pwd        VARCHAR2(50),
  user_name       VARCHAR2(40) not null,
  user_in_email   VARCHAR2(40),
  user_phone      VARCHAR2(40),
  user_type       NUMBER(10) default 1,
  user_orgid      NUMBER(10) default 0 not null,
  user_regtime    DATE,
  user_quicklogin NUMBER(1) default 0,
  user_enable      NUMBER(1) default 1,
  user_receivesms  NUMBER(10) default 0,
  user_receivemail NUMBER(10) default 0
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_USER add constraint PK_USER_NO primary key (USER_NO)
/

/*==============================================================*/
/* Table: TBL_ROLE                                    */
/*==============================================================*/
create table TBL_ROLE
(
  role_id     NUMBER(10) not null,
  role_name   VARCHAR2(40) not null,
  role_level NUMBER(3) default 1,
  role_flag   NUMBER(10),
  showlevel   NUMBER(10) default 0,
  role_reportedit NUMBER(10) default 0,
  remark      VARCHAR2(2000)
)
tablespace AP_DATA
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

/*==============================================================*/
/* Table: TBL_MODULE                                    */
/*==============================================================*/
create table TBL_MODULE
(
  md_id       NUMBER(10) not null,
  md_parentid NUMBER(10) default 0 not null,
  md_name     VARCHAR2(100) not null,
  md_showname VARCHAR2(100),
  md_url      VARCHAR2(200),
  md_code     VARCHAR2(200),
  md_seq      NUMBER(20),
  md_level    NUMBER(10),
  md_flag     NUMBER(10),
  md_icon     VARCHAR2(100),
  md_type     NUMBER(1) default 0,
  md_control  VARCHAR2(100)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_MODULE add constraint P_MD primary key (MD_ID)
/

/*==============================================================*/
/* Table: TBL_MODULE2ROLE                                    */
/*==============================================================*/
create table TBL_MODULE2ROLE
(
  rm_id       NUMBER(10) not null,
  rm_moduleid NUMBER(10) not null,
  rm_roleid   NUMBER(10) not null,
  rm_matrix   VARCHAR2(8) not null
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_MODULE2ROLE add constraint PK_RM_ID primary key (RM_ID)
/
alter table TBL_MODULE2ROLE
  add constraint FK_ORG_MODULEID foreign key (RM_MODULEID)
  references TBL_MODULE (MD_ID) on delete cascade
/
alter table TBL_MODULE2ROLE
  add constraint FK_ORG_ROLEID foreign key (RM_ROLEID)
  references TBL_ROLE (ROLE_ID) on delete cascade
/

/*==============================================================*/
/* Table: TBL_DIST_NAME                                    */
/*==============================================================*/
create table TBL_DIST_NAME
(
  sysdataid  VARCHAR2(32) not null,
  tenantid   VARCHAR2(50),
  cname      VARCHAR2(50),
  ename      VARCHAR2(50),
  sorts      NUMBER,
  status     NUMBER,
  creator    VARCHAR2(50),
  updateuser VARCHAR2(50),
  updatetime DATE default sysdate not null,
  createdate DATE default sysdate
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DIST_NAME add constraint PK_SYSTEMDATAINFO primary key (SYSDATAID)
/

/*==============================================================*/
/* Table: TBL_DIST_ITEM                                    */
/*==============================================================*/
create table TBL_DIST_ITEM
(
  dataitemid VARCHAR2(32) not null,
  tenantid   VARCHAR2(50),
  sysdataid  VARCHAR2(50),
  cname      VARCHAR2(50),
  ename      VARCHAR2(200),
  datavalue  VARCHAR2(200),
  sorts      NUMBER,
  status     NUMBER,
  creator    VARCHAR2(50),
  updateuser VARCHAR2(50),
  updatetime DATE default sysdate,
  createdate DATE default sysdate
)
tablespace AP_DATA
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
/* Table: TBL_CODE                                    */
/*==============================================================*/
create table TBL_CODE
(
  id        NUMBER(10) not null,
  itemcode  VARCHAR2(200),
  itemname  VARCHAR2(200),
  itemvalue VARCHAR2(20),
  tablecode VARCHAR2(200),
  state     NUMBER(10),
  remark    VARCHAR2(200)
)
tablespace AP_DATA
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

/*==============================================================*/
/* Table: TBL_DATAMAPPING                                    */
/*==============================================================*/
create table TBL_DATAMAPPING
(
  id              NUMBER(10) not null,
  name            VARCHAR2(50) not null,
  mappingcolumn   VARCHAR2(30) not null,
  protocoltype    NUMBER(1) not null,
  calcolumn       VARCHAR2(30),
  repetitiontimes NUMBER(2),
  mappingmode     NUMBER(1)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DATAMAPPING
  add constraint PK_DATAMAPPING primary key (ID, NAME)
/

/*==============================================================*/
/* Table: TBL_ACQ_UNIT_CONF                                  */
/*==============================================================*/
create table TBL_ACQ_UNIT_CONF
(
  id        NUMBER(10) not null,
  unit_code VARCHAR2(50) not null,
  unit_name VARCHAR2(50),
  protocol  VARCHAR2(50),
  remark    VARCHAR2(2000)
)
tablespace AP_DATA
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
/* Table: TBL_ACQ_GROUP_CONF                                  */
/*==============================================================*/
create table TBL_ACQ_GROUP_CONF
(
  id         NUMBER(10) not null,
  group_code VARCHAR2(50) not null,
  group_name VARCHAR2(50),
  grouptiminginterval  NUMBER(10) default 1,
  groupsavinginterval NUMBER(10) default 5,
  protocol   VARCHAR2(50),
  type       NUMBER(1) default 0,
  remark     VARCHAR2(2000)
)
tablespace AP_DATA
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
/* Table: TBL_ACQ_ITEM2GROUP_CONF                                  */
/*==============================================================*/
create table TBL_ACQ_ITEM2GROUP_CONF
(
  id       NUMBER(10) not null,
  itemid   NUMBER(10),
  itemname VARCHAR2(100),
  itemcode VARCHAR2(100),
  groupid  NUMBER(10) not null,
  bitindex NUMBER(3),
  matrix   VARCHAR2(8)
)
tablespace AP_DATA
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

/*==============================================================*/
/* Table: TBL_ACQ_GROUP2UNIT_CONF                                    */
/*==============================================================*/
create table TBL_ACQ_GROUP2UNIT_CONF
(
  id      NUMBER(10) not null,
  groupid NUMBER(10) not null,
  unitid  NUMBER(10) not null,
  matrix  VARCHAR2(8) not null
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ACQ_GROUP2UNIT_CONF add constraint PK_ACQ_UNIT_GROUP primary key (ID)
/

/*==============================================================*/
/* Table: TBL_ALARM_UNIT_CONF                                    */
/*==============================================================*/
create table TBL_ALARM_UNIT_CONF
(
  id        NUMBER(10) not null,
  unit_code VARCHAR2(50) not null,
  unit_name VARCHAR2(50),
  protocol  VARCHAR2(50),
  remark    VARCHAR2(2000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ALARM_UNIT_CONF
  add constraint PK_TBL_ALARM_UNIT_CONF primary key (ID)
/

/*==============================================================*/
/* Table: TBL_ALARM_ITEM2UNIT_CONF                                    */
/*==============================================================*/
create table TBL_ALARM_ITEM2UNIT_CONF
(
  id            NUMBER(10) not null,
  unitid        NUMBER(10) not null,
  itemid        NUMBER(10),
  itemname      VARCHAR2(100),
  itemcode      VARCHAR2(100),
  itemaddr      NUMBER(10),
  value         NUMBER(10,3),
  upperlimit    NUMBER(10,3),
  lowerlimit    NUMBER(10,3),
  hystersis     NUMBER(10,3),
  delay         NUMBER(10),
  alarmlevel    NUMBER(3),
  alarmsign     NUMBER(1),
  type          NUMBER(1),
  bitindex      NUMBER(3),
  issendmessage NUMBER(1) default 0,
  issendmail    NUMBER(1) default 0
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ALARM_ITEM2UNIT_CONF add constraint PK_ALARM_ITEM2UNIT_CONF primary key (ID)
/

/*==============================================================*/
/* Table: TBL_DISPLAY_UNIT_CONF                                    */
/*==============================================================*/
create table TBL_DISPLAY_UNIT_CONF
(
  id        NUMBER(10) not null,
  unit_code VARCHAR2(50) not null,
  unit_name VARCHAR2(50),
  protocol  VARCHAR2(50),
  acqunitid NUMBER(10),
  remark    VARCHAR2(2000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DISPLAY_UNIT_CONF add constraint PK_DISPLAY_UNIT_CONF primary key (ID)
/

/*==============================================================*/
/* Table: TBL_DISPLAY_ITEMS2UNIT_CONF                                    */
/*==============================================================*/
create table TBL_DISPLAY_ITEMS2UNIT_CONF
(
  id                 NUMBER(10) not null,
  itemid             NUMBER(10),
  itemname           VARCHAR2(100),
  itemcode           VARCHAR2(100),
  unitid             NUMBER(10) not null,
  sort               NUMBER(10),
  bitindex           NUMBER(3),
  showlevel          NUMBER(10),
  realtimecurve      NUMBER(10),
  historycurve       NUMBER(10),
  realtimecurvecolor VARCHAR2(20),
  historycurvecolor  VARCHAR2(20),
  type               NUMBER(1) default 0,
  matrix             VARCHAR2(8)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DISPLAY_ITEMS2UNIT_CONF add constraint PK_DISPLAY_ITEMS2UNIT_CONF primary key (ID)
/

/*==============================================================*/
/* Table: TBL_REPORT_ITEMS2UNIT_CONF                                    */
/*==============================================================*/
create table TBL_REPORT_ITEMS2UNIT_CONF
(
  id               NUMBER(10) not null,
  itemid           NUMBER(10),
  itemname         VARCHAR2(100),
  itemcode         VARCHAR2(100),
  unitid           NUMBER(10),
  sort             NUMBER(10),
  showlevel        NUMBER(10),
  sumsign          NUMBER(1) default 0,
  averagesign      NUMBER(1) default 0,
  reportcurve      NUMBER(10),
  reportcurvecolor VARCHAR2(20),
  curvestattype    NUMBER(1),
  datatype         NUMBER(10),
  reporttype       NUMBER(1) default 0,
  matrix           VARCHAR2(8)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_REPORT_ITEMS2UNIT_CONF add constraint PK_REPORT_ITEMS2UNIT_CONF primary key (ID)
/

/*==============================================================*/
/* Table: TBL_REPORT_UNIT_CONF                                    */
/*==============================================================*/
create table TBL_REPORT_UNIT_CONF
(
  id                       NUMBER(10) not null,
  unit_code                VARCHAR2(50) not null,
  unit_name                VARCHAR2(50),
  singlewellreporttemplate VARCHAR2(50),
  productionreporttemplate VARCHAR2(50),
  devicetype               NUMBER(1),
  sort                     NUMBER(10)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_REPORT_UNIT_CONF add constraint PK_REPORT_UNIT_CONF primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PROTOCOLINSTANCE                                    */
/*==============================================================*/
create table TBL_PROTOCOLINSTANCE
(
  id                       NUMBER(10) not null,
  name                     VARCHAR2(50),
  code                     VARCHAR2(50),
  acqprotocoltype          VARCHAR2(50),
  ctrlprotocoltype         VARCHAR2(50),
  signinprefixsuffixhex    NUMBER(1) default 1,
  signinprefix             VARCHAR2(50),
  signinsuffix             VARCHAR2(50),
  signinidhex              NUMBER(1) default 1,
  heartbeatprefixsuffixhex NUMBER(1) default 1,
  heartbeatprefix          VARCHAR2(50),
  heartbeatsuffix          VARCHAR2(50),
  packetsendinterval       NUMBER(10) default 100,
  unitid                   NUMBER(10),
  devicetype               NUMBER(1) default 0,
  sort                     NUMBER(10)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PROTOCOLINSTANCE  add constraint PK_PROTOCOLINSTANCE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PROTOCOLALARMINSTANCE                                    */
/*==============================================================*/
create table TBL_PROTOCOLALARMINSTANCE
(
  id          NUMBER(10) not null,
  name        VARCHAR2(50),
  code        VARCHAR2(50),
  alarmunitid NUMBER(10),
  devicetype  NUMBER(1) default 0,
  sort        NUMBER(10)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PROTOCOLALARMINSTANCE add constraint PK_PROTOCOLALARMINSTANCE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PROTOCOLDISPLAYINSTANCE                                    */
/*==============================================================*/
create table TBL_PROTOCOLDISPLAYINSTANCE
(
  id            NUMBER(10) not null,
  name          VARCHAR2(50),
  code          VARCHAR2(50),
  displayunitid NUMBER(10),
  devicetype    NUMBER(1) default 0,
  sort          NUMBER(10)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PROTOCOLDISPLAYINSTANCE add constraint PK_PROTOCOLDISPLAYINSTANCE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PROTOCOLREPORTINSTANCE                                    */
/*==============================================================*/
create table TBL_PROTOCOLREPORTINSTANCE
(
  id         NUMBER(10) not null,
  name       VARCHAR2(50),
  code       VARCHAR2(50),
  unitid     NUMBER(10),
  devicetype NUMBER(1) default 0,
  sort       NUMBER(10)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PROTOCOLREPORTINSTANCE add constraint PK_PROTOCOLREPORTINSTANCE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PROTOCOLSMSINSTANCE                                    */
/*==============================================================*/
create table TBL_PROTOCOLSMSINSTANCE
(
  id               NUMBER(10) not null,
  name             VARCHAR2(50),
  code             VARCHAR2(50),
  acqprotocoltype  VARCHAR2(50),
  ctrlprotocoltype VARCHAR2(50),
  sort             NUMBER(10)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PROTOCOLSMSINSTANCE add constraint PK_PROTOCOLSMSINSTANCE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_RPCDEVICE                                    */
/*==============================================================*/
create table TBL_RPCDEVICE
(
  id                       NUMBER(10) not null,
  orgid                    NUMBER(10) not null,
  wellname                 VARCHAR2(200) not null,
  devicetype               NUMBER(3) default 101,
  applicationscenarios     NUMBER(2) default 0,
  tcptype                  VARCHAR2(20),
  signinid                 VARCHAR2(200),
  ipport                   VARCHAR2(200),
  slave                    VARCHAR2(200),
  peakdelay                NUMBER(10),
  instancecode             VARCHAR2(50),
  alarminstancecode        VARCHAR2(50),
  displayinstancecode      VARCHAR2(50),
  reportinstancecode      VARCHAR2(50),
  videourl                 VARCHAR2(400),
  videoaccesstoken         VARCHAR2(400),
  productiondata           VARCHAR2(4000) default '{}',
  productiondataupdatetime DATE,
  pumpingmodelid           NUMBER(10),
  stroke                   NUMBER(8,2),
  levelcorrectvalue        NUMBER(8,2) default 0,
  balanceinfo              VARCHAR2(400),
  status                   NUMBER(1) default 1,
  sortnum                  NUMBER(10) default 9999
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPCDEVICE add constraint PK_RPCDEVICE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PCPDEVICE                                    */
/*==============================================================*/
create table TBL_PCPDEVICE
(
  id                       NUMBER(10) not null,
  orgid                    NUMBER(10) not null,
  wellname                 VARCHAR2(200) not null,
  devicetype               NUMBER(3) default 201,
  applicationscenarios     NUMBER(2),
  tcptype                  VARCHAR2(20),
  signinid                 VARCHAR2(200),
  ipport                   VARCHAR2(200),
  slave                    VARCHAR2(200),
  peakdelay                NUMBER(10),
  instancecode             VARCHAR2(50),
  alarminstancecode        VARCHAR2(50),
  displayinstancecode      VARCHAR2(50),
  reportinstancecode       VARCHAR2(50),
  videourl                 VARCHAR2(400),
  videoaccesstoken         VARCHAR2(400),
  productiondata           VARCHAR2(4000) default '{}',
  productiondataupdatetime DATE,
  status                   NUMBER(1) default 1,
  sortnum                  NUMBER(10) default 9999
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCPDEVICE add constraint PK_PCPDEVICE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_SMSDEVICE                                    */
/*==============================================================*/
create table TBL_SMSDEVICE
(
  id           NUMBER(10) not null,
  orgid        NUMBER(10),
  wellname     VARCHAR2(200) not null,
  signinid     VARCHAR2(200),
  instancecode VARCHAR2(50),
  sortnum      NUMBER(10) default 9999
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_SMSDEVICE add constraint PK_SMSDEVICE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PUMPINGMODEL                                    */
/*==============================================================*/
create table TBL_PUMPINGMODEL
(
  id                     NUMBER(10) not null,
  manufacturer           VARCHAR2(200),
  model                  VARCHAR2(200),
  stroke                 VARCHAR2(200),
  crankrotationdirection VARCHAR2(200),
  offsetangleofcrank     NUMBER(8,2),
  crankgravityradius     NUMBER(10,4),
  singlecrankweight      NUMBER(10,4),
  singlecrankpinweight   NUMBER(10,4),
  structuralunbalance    NUMBER(10,4),
  balanceweight          VARCHAR2(200),
  prtf                   CLOB
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PUMPINGMODEL
  add constraint PK_PUMPINGMODEL primary key (ID)
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
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPC_WORKTYPE
  add constraint PK_RPC_WORKTYPE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_RPCACQDATA_LATEST                                    */
/*==============================================================*/
create table TBL_RPCACQDATA_LATEST
(
  id                             NUMBER(10) not null,
  wellid                         NUMBER(10),
  acqtime                        DATE,
  commstatus                     NUMBER(2) default 0,
  commtime                       NUMBER(8,2) default 0,
  commtimeefficiency             NUMBER(10,4) default 0,
  commrange                      CLOB,
  runstatus                      NUMBER(2) default 0,
  runtimeefficiency              NUMBER(10,4) default 0,
  runtime                        NUMBER(8,2) default 0,
  runrange                       CLOB,
  productiondata                 VARCHAR2(4000) default '{}',
  pumpingmodelid                 NUMBER(10),
  balanceinfo                    VARCHAR2(400),
  fesdiagramacqtime              DATE,
  fesdiagramsrc                  NUMBER(2) default 0,
  stroke                         NUMBER(8,2),
  spm                            NUMBER(8,2),
  fmax                           NUMBER(8,2),
  fmin                           NUMBER(8,2),
  position_curve                 CLOB,
  angle_curve                    CLOB,
  load_curve                     CLOB,
  power_curve                    CLOB,
  current_curve                  CLOB,
  resultcode                     NUMBER(4),
  fullnesscoefficient            NUMBER(8,2),
  upperloadline                  NUMBER(8,2),
  upperloadlineofexact           NUMBER(8,2),
  lowerloadline                  NUMBER(8,2),
  pumpfsdiagram                  CLOB,
  theoreticalproduction          NUMBER(8,2),
  liquidvolumetricproduction     NUMBER(8,2),
  oilvolumetricproduction        NUMBER(8,2),
  watervolumetricproduction      NUMBER(8,2),
  availableplungerstrokeprod_v   NUMBER(8,2),
  pumpclearanceleakprod_v        NUMBER(8,2),
  tvleakvolumetricproduction     NUMBER(8,2),
  svleakvolumetricproduction     NUMBER(8,2),
  gasinfluenceprod_v             NUMBER(8,2),
  liquidweightproduction         NUMBER(8,2),
  oilweightproduction            NUMBER(8,2),
  waterweightproduction          NUMBER(8,2),
  availableplungerstrokeprod_w   NUMBER(8,2),
  pumpclearanceleakprod_w        NUMBER(8,2),
  tvleakweightproduction         NUMBER(8,2),
  svleakweightproduction         NUMBER(8,2),
  gasinfluenceprod_w             NUMBER(8,2),
  averagewatt                    NUMBER(8,2),
  polishrodpower                 NUMBER(8,2),
  waterpower                     NUMBER(8,2),
  surfacesystemefficiency        NUMBER(12,3),
  welldownsystemefficiency       NUMBER(12,3),
  systemefficiency               NUMBER(12,3),
  energyper100mlift              NUMBER(8,2),
  area                           NUMBER(8,2),
  rodflexlength                  NUMBER(8,2),
  tubingflexlength               NUMBER(8,2),
  inertialength                  NUMBER(8,2),
  pumpeff1                       NUMBER(12,3),
  pumpeff2                       NUMBER(12,3),
  pumpeff3                       NUMBER(12,3),
  pumpeff4                       NUMBER(12,3),
  pumpeff                        NUMBER(12,3),
  pumpintakep                    NUMBER(8,2),
  pumpintaket                    NUMBER(8,2),
  pumpintakegol                  NUMBER(8,2),
  pumpintakevisl                 NUMBER(8,2),
  pumpintakebo                   NUMBER(8,2),
  pumpoutletp                    NUMBER(8,2),
  pumpoutlett                    NUMBER(8,2),
  pumpoutletgol                  NUMBER(8,2),
  pumpoutletvisl                 NUMBER(8,2),
  pumpoutletbo                   NUMBER(8,2),
  rodstring                      VARCHAR2(200),
  plungerstroke                  NUMBER(8,2),
  availableplungerstroke         NUMBER(8,2),
  levelcorrectvalue              NUMBER(8,2),
  inverproducingfluidlevel       NUMBER(8,2),
  noliquidfullnesscoefficient    NUMBER(10,4),
  noliquidavailableplungerstroke NUMBER(10,4),
  smaxindex                      NUMBER(10),
  sminindex                      NUMBER(10),
  upstrokeimax                   NUMBER(8,2),
  downstrokeimax                 NUMBER(8,2),
  upstrokewattmax                NUMBER(8,2),
  downstrokewattmax              NUMBER(8,2),
  idegreebalance                 NUMBER(8,2),
  wattdegreebalance              NUMBER(8,2),
  deltaradius                    NUMBER(8,2),
  crankangle                     CLOB,
  polishrodv                     CLOB,
  polishroda                     CLOB,
  pr                             CLOB,
  tf                             CLOB,
  loadtorque                     CLOB,
  cranktorque                    CLOB,
  currentbalancetorque           CLOB,
  currentnettorque               CLOB,
  expectedbalancetorque          CLOB,
  expectednettorque              CLOB,
  wellboreslice                  CLOB,
  resultstatus                   NUMBER(2) default 0,
  totalkwatth                    NUMBER(12,3),
  todaykwatth                    NUMBER(12,3),
  liquidvolumetricproduction_l   NUMBER(8,2),
  oilvolumetricproduction_l      NUMBER(8,2),
  watervolumetricproduction_l    NUMBER(8,2),
  liquidweightproduction_l       NUMBER(8,2),
  oilweightproduction_l          NUMBER(8,2),
  waterweightproduction_l        NUMBER(8,2),
  submergence                    NUMBER(8,2),
  savetime                       DATE default sysdate,
  gasvolumetricproduction        NUMBER(12,3),
  totalgasvolumetricproduction   NUMBER(12,3),
  totalwatervolumetricproduction NUMBER(12,3),
  c_yxzt                         VARCHAR2(50),
  c_qtkz                         VARCHAR2(50),
  c_ggwd                         VARCHAR2(50),
  c_pghwd                        VARCHAR2(50),
  c_axdl                         VARCHAR2(50),
  c_bxdl                         VARCHAR2(50),
  c_cxdl                         VARCHAR2(50),
  c_axdy                         VARCHAR2(50),
  c_bxdy                         VARCHAR2(50),
  c_cxdy                         VARCHAR2(50),
  c_yggh                         VARCHAR2(50),
  c_wggh                         VARCHAR2(50),
  c_yggl                         VARCHAR2(50),
  c_wggl                         VARCHAR2(50),
  c_fxgl                         VARCHAR2(50),
  c_glys                         VARCHAR2(50),
  c_yy                           VARCHAR2(50),
  c_ty                           VARCHAR2(50),
  c_hy                           VARCHAR2(50),
  c_jkwd                         VARCHAR2(50),
  c_dym                          VARCHAR2(50),
  c_hsl                          VARCHAR2(50),
  c_bpszpl                       VARCHAR2(50),
  c_bpyxpl                       VARCHAR2(50),
  c_gtcjjg                       VARCHAR2(50),
  c_gtszds                       VARCHAR2(50),
  c_gtscds                       VARCHAR2(50),
  c_gtcjsj                       VARCHAR2(50),
  c_cc                           VARCHAR2(50),
  c_cc1                          VARCHAR2(50),
  c_gtsjwy                       VARCHAR2(4000),
  c_gtsjzh                       VARCHAR2(4000),
  c_gtsjdl                       VARCHAR2(4000),
  c_gtsjgl                       VARCHAR2(4000),
  c_sgbjkzw                      VARCHAR2(50)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPCACQDATA_LATEST
  add constraint PK_TBL_RPCACQDATA_LATEST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_RPCACQDATA_HIST                                    */
/*==============================================================*/
create table TBL_RPCACQDATA_HIST
(
  id                             NUMBER(10) not null,
  wellid                         NUMBER(10),
  acqtime                        DATE,
  commstatus                     NUMBER(2) default 0,
  commtime                       NUMBER(8,2) default 0,
  commtimeefficiency             NUMBER(10,4) default 0,
  commrange                      CLOB,
  runstatus                      NUMBER(2) default 0,
  runtimeefficiency              NUMBER(10,4) default 0,
  runtime                        NUMBER(8,2) default 0,
  runrange                       CLOB,
  productiondata                 VARCHAR2(4000) default '{}',
  pumpingmodelid                 NUMBER(10),
  balanceinfo                    VARCHAR2(400),
  fesdiagramacqtime              DATE,
  fesdiagramsrc                  NUMBER(2) default 0,
  stroke                         NUMBER(8,2),
  spm                            NUMBER(8,2),
  fmax                           NUMBER(8,2),
  fmin                           NUMBER(8,2),
  position_curve                 CLOB,
  angle_curve                    CLOB,
  load_curve                     CLOB,
  power_curve                    CLOB,
  current_curve                  CLOB,
  resultcode                     NUMBER(4),
  fullnesscoefficient            NUMBER(8,2),
  upperloadline                  NUMBER(8,2),
  upperloadlineofexact           NUMBER(8,2),
  lowerloadline                  NUMBER(8,2),
  pumpfsdiagram                  CLOB,
  theoreticalproduction          NUMBER(8,2),
  liquidvolumetricproduction     NUMBER(8,2),
  oilvolumetricproduction        NUMBER(8,2),
  watervolumetricproduction      NUMBER(8,2),
  availableplungerstrokeprod_v   NUMBER(8,2),
  pumpclearanceleakprod_v        NUMBER(8,2),
  tvleakvolumetricproduction     NUMBER(8,2),
  svleakvolumetricproduction     NUMBER(8,2),
  gasinfluenceprod_v             NUMBER(8,2),
  liquidweightproduction         NUMBER(8,2),
  oilweightproduction            NUMBER(8,2),
  waterweightproduction          NUMBER(8,2),
  availableplungerstrokeprod_w   NUMBER(8,2),
  pumpclearanceleakprod_w        NUMBER(8,2),
  tvleakweightproduction         NUMBER(8,2),
  svleakweightproduction         NUMBER(8,2),
  gasinfluenceprod_w             NUMBER(8,2),
  averagewatt                    NUMBER(8,2),
  polishrodpower                 NUMBER(8,2),
  waterpower                     NUMBER(8,2),
  surfacesystemefficiency        NUMBER(12,3),
  welldownsystemefficiency       NUMBER(12,3),
  systemefficiency               NUMBER(12,3),
  energyper100mlift              NUMBER(8,2),
  area                           NUMBER(8,2),
  rodflexlength                  NUMBER(8,2),
  tubingflexlength               NUMBER(8,2),
  inertialength                  NUMBER(8,2),
  pumpeff1                       NUMBER(12,3),
  pumpeff2                       NUMBER(12,3),
  pumpeff3                       NUMBER(12,3),
  pumpeff4                       NUMBER(12,3),
  pumpeff                        NUMBER(12,3),
  pumpintakep                    NUMBER(8,2),
  pumpintaket                    NUMBER(8,2),
  pumpintakegol                  NUMBER(8,2),
  pumpintakevisl                 NUMBER(8,2),
  pumpintakebo                   NUMBER(8,2),
  pumpoutletp                    NUMBER(8,2),
  pumpoutlett                    NUMBER(8,2),
  pumpoutletgol                  NUMBER(8,2),
  pumpoutletvisl                 NUMBER(8,2),
  pumpoutletbo                   NUMBER(8,2),
  rodstring                      VARCHAR2(200),
  plungerstroke                  NUMBER(8,2),
  availableplungerstroke         NUMBER(8,2),
  levelcorrectvalue              NUMBER(8,2),
  inverproducingfluidlevel       NUMBER(8,2),
  noliquidfullnesscoefficient    NUMBER(10,4),
  noliquidavailableplungerstroke NUMBER(10,4),
  smaxindex                      NUMBER(10),
  sminindex                      NUMBER(10),
  upstrokeimax                   NUMBER(8,2),
  downstrokeimax                 NUMBER(8,2),
  upstrokewattmax                NUMBER(8,2),
  downstrokewattmax              NUMBER(8,2),
  idegreebalance                 NUMBER(8,2),
  wattdegreebalance              NUMBER(8,2),
  deltaradius                    NUMBER(8,2),
  crankangle                     CLOB,
  polishrodv                     CLOB,
  polishroda                     CLOB,
  pr                             CLOB,
  tf                             CLOB,
  loadtorque                     CLOB,
  cranktorque                    CLOB,
  currentbalancetorque           CLOB,
  currentnettorque               CLOB,
  expectedbalancetorque          CLOB,
  expectednettorque              CLOB,
  wellboreslice                  CLOB,
  resultstatus                   NUMBER(2) default 0,
  totalkwatth                    NUMBER(12,3),
  todaykwatth                    NUMBER(12,3),
  liquidvolumetricproduction_l   NUMBER(8,2),
  oilvolumetricproduction_l      NUMBER(8,2),
  watervolumetricproduction_l    NUMBER(8,2),
  liquidweightproduction_l       NUMBER(8,2),
  oilweightproduction_l          NUMBER(8,2),
  waterweightproduction_l        NUMBER(8,2),
  submergence                    NUMBER(8,2),
  savetime                       DATE default sysdate,
  gasvolumetricproduction        NUMBER(12,3),
  totalgasvolumetricproduction   NUMBER(12,3),
  totalwatervolumetricproduction NUMBER(12,3),
  c_yxzt                         VARCHAR2(50),
  c_qtkz                         VARCHAR2(50),
  c_ggwd                         VARCHAR2(50),
  c_pghwd                        VARCHAR2(50),
  c_axdl                         VARCHAR2(50),
  c_bxdl                         VARCHAR2(50),
  c_cxdl                         VARCHAR2(50),
  c_axdy                         VARCHAR2(50),
  c_bxdy                         VARCHAR2(50),
  c_cxdy                         VARCHAR2(50),
  c_yggh                         VARCHAR2(50),
  c_wggh                         VARCHAR2(50),
  c_yggl                         VARCHAR2(50),
  c_wggl                         VARCHAR2(50),
  c_fxgl                         VARCHAR2(50),
  c_glys                         VARCHAR2(50),
  c_yy                           VARCHAR2(50),
  c_ty                           VARCHAR2(50),
  c_hy                           VARCHAR2(50),
  c_jkwd                         VARCHAR2(50),
  c_dym                          VARCHAR2(50),
  c_hsl                          VARCHAR2(50),
  c_bpszpl                       VARCHAR2(50),
  c_bpyxpl                       VARCHAR2(50),
  c_gtcjjg                       VARCHAR2(50),
  c_gtszds                       VARCHAR2(50),
  c_gtscds                       VARCHAR2(50),
  c_gtcjsj                       VARCHAR2(50),
  c_cc                           VARCHAR2(50),
  c_cc1                          VARCHAR2(50),
  c_gtsjwy                       VARCHAR2(4000),
  c_gtsjzh                       VARCHAR2(4000),
  c_gtsjdl                       VARCHAR2(4000),
  c_gtsjgl                       VARCHAR2(4000),
  c_sgbjkzw                      VARCHAR2(50)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPCACQDATA_HIST
  add constraint PK_TBL_RPCACQDATA_HIST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_RPCACQRAWDATA                                    */
/*==============================================================*/
create table TBL_RPCACQRAWDATA
(
  id      NUMBER(10) not null,
  wellid  NUMBER(10) not null,
  acqtime DATE not null,
  rawdata VARCHAR2(4000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPCACQRAWDATA
  add constraint PK_RPCACQRAWDATA primary key (ID)
/

/*==============================================================*/
/* Table: TBL_RPCALARMINFO_LATEST                                  */
/*==============================================================*/
create table TBL_RPCALARMINFO_LATEST
(
  id            NUMBER(10) not null,
  wellid        NUMBER(10),
  alarmtime     DATE,
  itemname      VARCHAR2(100),
  alarmtype     NUMBER(1),
  alarmvalue    NUMBER(10,3),
  alarminfo     VARCHAR2(100),
  alarmlimit    NUMBER(10,3),
  hystersis     NUMBER(10,3),
  alarmlevel    NUMBER(3),
  issendmessage NUMBER(1) default 0,
  issendmail    NUMBER(1) default 0,
  recoverytime  DATE
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPCALARMINFO_LATEST
  add constraint PK_RPCALARMINFO_LATEST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_RPCALARMINFO_HIST                               */
/*==============================================================*/
create table TBL_RPCALARMINFO_HIST
(
  id            NUMBER(10) not null,
  wellid        NUMBER(10),
  alarmtime     DATE,
  itemname      VARCHAR2(100),
  alarmtype     NUMBER(1),
  alarmvalue    NUMBER(10,3),
  alarminfo     VARCHAR2(100),
  alarmlimit    NUMBER(10,3),
  hystersis     NUMBER(10,3),
  alarmlevel    NUMBER(3),
  issendmessage NUMBER(1) default 0,
  issendmail    NUMBER(1) default 0,
  recoverytime  DATE
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPCALARMINFO_HIST
  add constraint PK_RPCALARMINFO_HIST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_RPCDAILYCALCULATIONDATA                               */
/*==============================================================*/
create table TBL_RPCDAILYCALCULATIONDATA
(
  id                         NUMBER(10) not null,
  wellid                     NUMBER(10),
  caldate                    DATE,
  commstatus                 NUMBER(2) default 0,
  commtime                   NUMBER(8,2) default 0,
  commtimeefficiency         NUMBER(10,4) default 0,
  commrange                  CLOB,
  runstatus                  NUMBER(2) default 0,
  runtimeefficiency          NUMBER(10,4) default 0,
  runtime                    NUMBER(8,2) default 0,
  runrange                   CLOB,
  stroke                     NUMBER(8,2),
  spm                        NUMBER(8,2),
  fmax                       NUMBER(8,2),
  fmin                       NUMBER(8,2),
  resultcode                 NUMBER(4),
  fullnesscoefficient        NUMBER(8,2),
  theoreticalproduction      NUMBER(8,2),
  liquidvolumetricproduction NUMBER(8,2),
  oilvolumetricproduction    NUMBER(8,2),
  watervolumetricproduction  NUMBER(8,2),
  liquidweightproduction     NUMBER(8,2),
  oilweightproduction        NUMBER(8,2),
  waterweightproduction      NUMBER(8,2),
  surfacesystemefficiency    NUMBER(12,3),
  welldownsystemefficiency   NUMBER(12,3),
  systemefficiency           NUMBER(12,3),
  energyper100mlift          NUMBER(8,2),
  pumpeff1                   NUMBER(12,3),
  pumpeff2                   NUMBER(12,3),
  pumpeff3                   NUMBER(12,3),
  pumpeff4                   NUMBER(12,3),
  pumpeff                    NUMBER(12,3),
  idegreebalance             NUMBER(8,2),
  wattdegreebalance          NUMBER(8,2),
  deltaradius                NUMBER(8,2),
  resultstatus               NUMBER(2) default 0,
  totalkwatth                NUMBER(12,3),
  todaykwatth                NUMBER(12,3),
  volumewatercut             NUMBER(8,2),
  weightwatercut             NUMBER(8,2),
  resultstring               CLOB,
  extendeddays               NUMBER(5),
  tubingpressure                 NUMBER(8,2),
  casingpressure                 NUMBER(8,2),
  bottomholepressure               NUMBER(8,2),
  producingfluidlevel            NUMBER(8,2),
  gasvolumetricproduction        NUMBER(8,2),
  totalgasvolumetricproduction   NUMBER(8,2),
  totalwatervolumetricproduction NUMBER(8,2),
  headerlabelinfo                VARCHAR2(4000),
  remark                         VARCHAR2(4000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPCDAILYCALCULATIONDATA
  add constraint PK_RPCDAILYCALCULATIONDATA primary key (ID)
/

/*==============================================================*/
/* Table: TBL_RPCDEVICEGRAPHICSET                                    */
/*==============================================================*/
create table TBL_RPCDEVICEGRAPHICSET
(
  id           NUMBER(10) not null,
  wellid       NUMBER(10) not null,
  graphicstyle VARCHAR2(4000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RPCDEVICEGRAPHICSET
  add constraint PK_RPCDEVICEGRAPHICSET primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PCPACQDATA_LATEST                                    */
/*==============================================================*/
create table TBL_PCPACQDATA_LATEST
(
  id                           NUMBER(10) not null,
  wellid                       NUMBER(10),
  acqtime                      DATE,
  commstatus                   NUMBER(2) default 0,
  commtime                     NUMBER(8,2) default 0,
  commtimeefficiency           NUMBER(10,4) default 0,
  commrange                    CLOB,
  runstatus                    NUMBER(2) default 0,
  runtimeefficiency            NUMBER(10,4) default 0,
  runtime                      NUMBER(8,2) default 0,
  runrange                     CLOB,
  productiondata               VARCHAR2(4000) default '{}',
  rpm                          NUMBER(8,2),
  torque                       NUMBER(8,2),
  resultcode                   NUMBER(4),
  theoreticalproduction        NUMBER(8,2),
  liquidvolumetricproduction   NUMBER(8,2),
  oilvolumetricproduction      NUMBER(8,2),
  watervolumetricproduction    NUMBER(8,2),
  liquidweightproduction       NUMBER(8,2),
  oilweightproduction          NUMBER(8,2),
  waterweightproduction        NUMBER(8,2),
  averagewatt                  NUMBER(8,2),
  waterpower                   NUMBER(8,2),
  systemefficiency             NUMBER(12,3),
  energyper100mlift            NUMBER(8,2),
  pumpeff1                     NUMBER(12,3),
  pumpeff2                     NUMBER(12,3),
  pumpeff                      NUMBER(12,3),
  pumpintakep                  NUMBER(8,2),
  pumpintaket                  NUMBER(8,2),
  pumpintakegol                NUMBER(8,2),
  pumpintakevisl               NUMBER(8,2),
  pumpintakebo                 NUMBER(8,2),
  pumpoutletp                  NUMBER(8,2),
  pumpoutlett                  NUMBER(8,2),
  pumpoutletgol                NUMBER(8,2),
  pumpoutletvisl               NUMBER(8,2),
  pumpoutletbo                 NUMBER(8,2),
  rodstring                    VARCHAR2(200),
  savetime                     DATE default sysdate,
  resultstatus                 NUMBER(2) default 0,
  remark                       VARCHAR2(200),
  totalkwatth                  NUMBER(12,3),
  todaykwatth                  NUMBER(12,3),
  liquidvolumetricproduction_l NUMBER(8,2),
  oilvolumetricproduction_l    NUMBER(8,2),
  watervolumetricproduction_l  NUMBER(8,2),
  liquidweightproduction_l     NUMBER(8,2),
  oilweightproduction_l        NUMBER(8,2),
  waterweightproduction_l      NUMBER(8,2),
  gasvolumetricproduction        NUMBER(12,3),
  totalgasvolumetricproduction   NUMBER(12,3),
  totalwatervolumetricproduction NUMBER(12,3),
  c_yxzt                       VARCHAR2(50),
  c_qtkz                       VARCHAR2(50),
  c_axdl                       VARCHAR2(50),
  c_bxdl                       VARCHAR2(50),
  c_cxdl                       VARCHAR2(50),
  c_axdy                       VARCHAR2(50),
  c_bxdy                       VARCHAR2(50),
  c_cxdy                       VARCHAR2(50),
  c_yggh                       VARCHAR2(50),
  c_wggh                       VARCHAR2(50),
  c_yggl                       VARCHAR2(50),
  c_wggl                       VARCHAR2(50),
  c_fxgl                       VARCHAR2(50),
  c_glys                       VARCHAR2(50),
  c_yy                         VARCHAR2(50),
  c_ty                         VARCHAR2(50),
  c_hy                         VARCHAR2(50),
  c_jkwd                       VARCHAR2(50),
  c_dym                        VARCHAR2(50),
  c_hsl                        VARCHAR2(50),
  c_bpszpl                     VARCHAR2(50),
  c_bpyxpl                     VARCHAR2(50),
  c_lgbzs                      VARCHAR2(50),
  c_lgbnj                      VARCHAR2(50)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCPACQDATA_LATEST
  add constraint PK_TBL_PCPACQDATA_LATEST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PCPACQDATA_HIST                                    */
/*==============================================================*/
create table TBL_PCPACQDATA_HIST
(
  id                           NUMBER(10) not null,
  wellid                       NUMBER(10),
  acqtime                      DATE,
  commstatus                   NUMBER(2) default 0,
  commtime                     NUMBER(8,2) default 0,
  commtimeefficiency           NUMBER(10,4) default 0,
  commrange                    CLOB,
  runstatus                    NUMBER(2) default 0,
  runtimeefficiency            NUMBER(10,4) default 0,
  runtime                      NUMBER(8,2) default 0,
  runrange                     CLOB,
  productiondata               VARCHAR2(4000) default '{}',
  rpm                          NUMBER(8,2),
  torque                       NUMBER(8,2),
  resultcode                   NUMBER(4),
  theoreticalproduction        NUMBER(8,2),
  liquidvolumetricproduction   NUMBER(8,2),
  oilvolumetricproduction      NUMBER(8,2),
  watervolumetricproduction    NUMBER(8,2),
  liquidweightproduction       NUMBER(8,2),
  oilweightproduction          NUMBER(8,2),
  waterweightproduction        NUMBER(8,2),
  averagewatt                  NUMBER(8,2),
  waterpower                   NUMBER(8,2),
  systemefficiency             NUMBER(12,3),
  energyper100mlift            NUMBER(8,2),
  pumpeff1                     NUMBER(12,3),
  pumpeff2                     NUMBER(12,3),
  pumpeff                      NUMBER(12,3),
  pumpintakep                  NUMBER(8,2),
  pumpintaket                  NUMBER(8,2),
  pumpintakegol                NUMBER(8,2),
  pumpintakevisl               NUMBER(8,2),
  pumpintakebo                 NUMBER(8,2),
  pumpoutletp                  NUMBER(8,2),
  pumpoutlett                  NUMBER(8,2),
  pumpoutletgol                NUMBER(8,2),
  pumpoutletvisl               NUMBER(8,2),
  pumpoutletbo                 NUMBER(8,2),
  rodstring                    VARCHAR2(200),
  savetime                     DATE default sysdate,
  resultstatus                 NUMBER(2) default 0,
  remark                       VARCHAR2(200),
  totalkwatth                  NUMBER(12,3),
  todaykwatth                  NUMBER(12,3),
  liquidvolumetricproduction_l NUMBER(8,2),
  oilvolumetricproduction_l    NUMBER(8,2),
  watervolumetricproduction_l  NUMBER(8,2),
  liquidweightproduction_l     NUMBER(8,2),
  oilweightproduction_l        NUMBER(8,2),
  waterweightproduction_l      NUMBER(8,2),
  gasvolumetricproduction        NUMBER(12,3),
  totalgasvolumetricproduction   NUMBER(12,3),
  totalwatervolumetricproduction NUMBER(12,3),
  c_yxzt                       VARCHAR2(50),
  c_qtkz                       VARCHAR2(50),
  c_axdl                       VARCHAR2(50),
  c_bxdl                       VARCHAR2(50),
  c_cxdl                       VARCHAR2(50),
  c_axdy                       VARCHAR2(50),
  c_bxdy                       VARCHAR2(50),
  c_cxdy                       VARCHAR2(50),
  c_yggh                       VARCHAR2(50),
  c_wggh                       VARCHAR2(50),
  c_yggl                       VARCHAR2(50),
  c_wggl                       VARCHAR2(50),
  c_fxgl                       VARCHAR2(50),
  c_glys                       VARCHAR2(50),
  c_yy                         VARCHAR2(50),
  c_ty                         VARCHAR2(50),
  c_hy                         VARCHAR2(50),
  c_jkwd                       VARCHAR2(50),
  c_dym                        VARCHAR2(50),
  c_hsl                        VARCHAR2(50),
  c_bpszpl                     VARCHAR2(50),
  c_bpyxpl                     VARCHAR2(50),
  c_lgbzs                      VARCHAR2(50),
  c_lgbnj                      VARCHAR2(50)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCPACQDATA_HIST
  add constraint PK_TBL_PCPACQDATA_HIST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PCPACQRAWDATA                                    */
/*==============================================================*/
create table TBL_PCPACQRAWDATA
(
  id      NUMBER(10) not null,
  wellid  NUMBER(10) not null,
  acqtime DATE not null,
  rawdata VARCHAR2(4000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCPACQRAWDATA
  add constraint PK_PCPACQRAWDATA primary key (ID)
/


/*==============================================================*/
/* Table: TBL_PCPALARMINFO_LATEST                                  */
/*==============================================================*/
create table TBL_PCPALARMINFO_LATEST
(
  id            NUMBER(10) not null,
  wellid        NUMBER(10),
  alarmtime     DATE,
  itemname      VARCHAR2(100),
  alarmtype     NUMBER(1),
  alarmvalue    NUMBER(10,3),
  alarminfo     VARCHAR2(100),
  alarmlimit    NUMBER(10,3),
  hystersis     NUMBER(10,3),
  alarmlevel    NUMBER(3),
  issendmessage NUMBER(1) default 0,
  issendmail    NUMBER(1) default 0,
  recoverytime  DATE
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCPALARMINFO_LATEST
  add constraint PK_PCPALARMINFO_LATEST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PCPALARMINFO_HIST                               */
/*==============================================================*/
create table TBL_PCPALARMINFO_HIST
(
  id            NUMBER(10) not null,
  wellid        NUMBER(10),
  alarmtime     DATE,
  itemname      VARCHAR2(100),
  alarmtype     NUMBER(1),
  alarmvalue    NUMBER(10,3),
  alarminfo     VARCHAR2(100),
  alarmlimit    NUMBER(10,3),
  hystersis     NUMBER(10,3),
  alarmlevel    NUMBER(3),
  issendmessage NUMBER(1) default 0,
  issendmail    NUMBER(1) default 0,
  recoverytime  DATE
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCPALARMINFO_HIST
  add constraint PK_PCPALARMINFO_HIST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PCPDAILYCALCULATIONDATA                               */
/*==============================================================*/
create table TBL_PCPDAILYCALCULATIONDATA
(
  id                         NUMBER(10) not null,
  wellid                     NUMBER(10),
  caldate                    DATE,
  commstatus                 NUMBER(2) default 0,
  commtime                   NUMBER(8,2) default 0,
  commtimeefficiency         NUMBER(10,4) default 0,
  commrange                  CLOB,
  runstatus                  NUMBER(2) default 0,
  runtimeefficiency          NUMBER(10,4) default 0,
  runtime                    NUMBER(8,2) default 0,
  runrange                   CLOB,
  rpm                        NUMBER(8,2),
  resultcode                 NUMBER(4),
  theoreticalproduction      NUMBER(8,2),
  liquidvolumetricproduction NUMBER(8,2),
  oilvolumetricproduction    NUMBER(8,2),
  watervolumetricproduction  NUMBER(8,2),
  liquidweightproduction     NUMBER(8,2),
  oilweightproduction        NUMBER(8,2),
  waterweightproduction      NUMBER(8,2),
  systemefficiency           NUMBER(13,4),
  energyper100mlift          NUMBER(8,2),
  pumpeff1                   NUMBER(13,4),
  pumpeff2                   NUMBER(13,4),
  pumpeff                    NUMBER(13,4),
  resultstatus               NUMBER(2) default 0,
  totalkwatth                NUMBER(13,4),
  todaykwatth                NUMBER(13,4),
  volumewatercut             NUMBER(8,2),
  weightwatercut             NUMBER(8,2),
  extendeddays               NUMBER(5),
  tubingpressure                 NUMBER(8,2),
  casingpressure                 NUMBER(8,2),
  bottomholepressure               NUMBER(8,2),
  producingfluidlevel            NUMBER(8,2),
  gasvolumetricproduction        NUMBER(8,2),
  totalgasvolumetricproduction   NUMBER(8,2),
  totalwatervolumetricproduction NUMBER(8,2),
  headerlabelinfo                VARCHAR2(4000),
  remark                         VARCHAR2(4000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCPDAILYCALCULATIONDATA
  add constraint PK_PCPDAILYCALCULATIONDATA primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PCPDEVICEGRAPHICSET                                    */
/*==============================================================*/
create table TBL_PCPDEVICEGRAPHICSET
(
  id           NUMBER(10) not null,
  wellid       NUMBER(10) not null,
  graphicstyle VARCHAR2(4000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCPDEVICEGRAPHICSET
  add constraint PK_PCPDEVICEGRAPHICSET primary key (ID)
/

/*==============================================================*/
/* Table: TBL_DEVICEOPERATIONLOG                                    */
/*==============================================================*/
create table TBL_DEVICEOPERATIONLOG
(
  id         NUMBER(10) not null,
  wellname   VARCHAR2(20),
  createtime DATE,
  user_id    VARCHAR2(20),
  loginip    VARCHAR2(20),
  action     NUMBER(2),
  devicetype NUMBER(3),
  remark     VARCHAR2(200)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DEVICEOPERATIONLOG
  add constraint PK_TBL_DEVICEOPERATIONLOG primary key (ID)
/

/*==============================================================*/
/* Table: TBL_SYSTEMLOG                                    */
/*==============================================================*/
create table TBL_SYSTEMLOG
(
  id         NUMBER(10) not null,
  createtime DATE,
  user_id    VARCHAR2(20),
  loginip    VARCHAR2(20),
  action     NUMBER(2),
  remark     VARCHAR2(200)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_SYSTEMLOG add constraint PK_TBL_SYSTEMLOG primary key (ID)
/

/*==============================================================*/
/* Table: TBL_RESOURCEMONITORING                                    */
/*==============================================================*/
create table TBL_RESOURCEMONITORING
(
  id             NUMBER(10) not null,
  acqtime        DATE,
  acrunstatus    NUMBER(2),
  acversion      VARCHAR2(50),
  adrunstatus    NUMBER(2),
  adversion      VARCHAR2(50),
  cpuusedpercent VARCHAR2(50),
  memusedpercent NUMBER(8,2),
  tablespacesize NUMBER(10,2),
  jedisstatus    NUMBER(2)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RESOURCEMONITORING add constraint PK_TBL_RESOURCEMONITORING primary key (ID)
/

/*==============================================================*/
/* Table: TBL_RUNSTATUSCONFIG                                    */
/*==============================================================*/
create table TBL_RUNSTATUSCONFIG
(
  id                NUMBER(10) not null,
  protocol          VARCHAR2(50),
  itemname          VARCHAR2(50),
  itemmappingcolumn VARCHAR2(50),
  runvalue          VARCHAR2(50),
  stopvalue         VARCHAR2(50),
  protocoltype      NUMBER(1) not null
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_RUNSTATUSCONFIG add constraint PK_RUNSTATUSCONFIG primary key (ID)
/

create or replace package MYPACKAGE as
   type MY_CURSOR is REF CURSOR;
end MYPACKAGE;
/