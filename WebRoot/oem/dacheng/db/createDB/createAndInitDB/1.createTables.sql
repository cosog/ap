/*==============================================================*/
/* Table: TBL_DEVICETYPEINFO                                    */
/*==============================================================*/
create table TBL_DEVICETYPEINFO
(
  id         NUMBER(10) not null,
  parentid   NUMBER(10) default 0 not null,
  sortnum    NUMBER(10),
  name_zh_cn VARCHAR2(100),
  name_en    VARCHAR2(100),
  name_ru    VARCHAR2(100)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DEVICETYPEINFO  add constraint PK_TABINFO primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PROTOCOL                                    */
/*==============================================================*/
create table TBL_PROTOCOL
(
  id         NUMBER(10) not null,
  name       VARCHAR2(500),
  code       VARCHAR2(500),
  devicetype NUMBER(10),
  items      CLOB,
  extendedfield CLOB,
  language   NUMBER(1),
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
alter table TBL_PROTOCOL
  add constraint PK_PROTOCOL primary key (ID)
/

/*==============================================================*/
/* Table: TBL_ORG                                    */
/*==============================================================*/
create table TBL_ORG
(
  org_id     NUMBER(10) not null,
  org_code   VARCHAR2(20),
  org_name_zh_CN   VARCHAR2(100),
  org_name_en   VARCHAR2(100),
  org_name_ru   VARCHAR2(100),
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
/* Table: TBL_ROLE                                    */
/*==============================================================*/
create table TBL_ROLE
(
  role_id           NUMBER(10) not null,
  role_name         VARCHAR2(200) not null,
  role_level        NUMBER(3) default 1,
  showlevel         NUMBER(10) default 0,
  role_videokeyedit NUMBER(10) default 0,
  role_languageedit NUMBER(10) default 0,
  remark            VARCHAR2(2000)
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
/* Table: TBL_DEVICETYPE2ROLE                                    */
/*==============================================================*/
create table TBL_DEVICETYPE2ROLE
(
  rd_id           NUMBER(10) not null,
  rd_devicetypeid NUMBER(10) not null,
  rd_roleid       NUMBER(10) not null,
  rd_matrix       VARCHAR2(8) not null
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DEVICETYPE2ROLE
  add constraint PK_RT_ID primary key (RD_ID)
/
alter table TBL_DEVICETYPE2ROLE
  add constraint FK_RT_ROLEID foreign key (RD_ROLEID)
  references TBL_ROLE (ROLE_ID) on delete cascade
/
alter table TBL_DEVICETYPE2ROLE
  add constraint FK_RT_TABID foreign key (RD_DEVICETYPEID)
  references TBL_DEVICETYPEINFO (ID) on delete cascade
/

/*==============================================================*/
/* Table: TBL_LANGUAGE2ROLE                                    */
/*==============================================================*/
create table TBL_LANGUAGE2ROLE
(
  id       NUMBER(10) not null,
  language NUMBER(1),
  roleid   NUMBER(10),
  matrix   VARCHAR2(8)
)
tablespace AP_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_LANGUAGE2ROLE add constraint PK_LANGUAGE2ROLE primary key (ID)
/
alter table TBL_LANGUAGE2ROLE add constraint FK_ROLEID foreign key (ROLEID) references TBL_ROLE (ROLE_ID) on delete cascade
/

/*==============================================================*/
/* Table: TBL_MODULE                                    */
/*==============================================================*/
create table TBL_MODULE
(
  md_id       NUMBER(10) not null,
  md_parentid NUMBER(10) default 0 not null,
  md_name_zh_CN     VARCHAR2(100),
  md_name_en     VARCHAR2(100),
  md_name_ru     VARCHAR2(100),
  md_showname_zh_CN VARCHAR2(100),
  md_showname_en VARCHAR2(100),
  md_showname_ru VARCHAR2(100),
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
  user_receivemail NUMBER(10) default 0,
  user_language NUMBER(1) default 1
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
/* Table: TBL_DIST_NAME                                    */
/*==============================================================*/
create table TBL_DIST_NAME
(
  sysdataid  VARCHAR2(32) not null,
  moduleid   NUMBER(10),
  tenantid   VARCHAR2(50),
  name_zh_CN      VARCHAR2(200),
  name_en      VARCHAR2(200),
  name_ru      VARCHAR2(200),
  code      VARCHAR2(50),
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
  name_zh_CN      VARCHAR2(200),
  name_en      VARCHAR2(200),
  name_ru      VARCHAR2(200),
  code      VARCHAR2(200),
  datavalue  VARCHAR2(200),
  sorts      NUMBER,
  status     NUMBER,
  columndatasource VARCHAR2(1) default 0,
  devicetype       NUMBER(10),
  datasource       VARCHAR2(1),
  dataunit         VARCHAR2(50),
  status_cn        NUMBER default 1,
  status_en        NUMBER default 1,
  status_ru        NUMBER default 1,
  configitemname   VARCHAR2(200),
  configitembitindex NUMBER(3),
  creator    VARCHAR2(200),
  updateuser VARCHAR2(200),
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
  name            VARCHAR2(200) not null,
  mappingcolumn   VARCHAR2(128) not null,
  protocoltype    NUMBER(1),
  calcolumn       VARCHAR2(128),
  repetitiontimes NUMBER(2),
  mappingmode     NUMBER(1) default 1,
  calculateenable NUMBER(1) default 0
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
/* Table: TBL_RUNSTATUSCONFIG                                    */
/*==============================================================*/
create table TBL_RUNSTATUSCONFIG
(
  id                NUMBER(10) not null,
  protocol          VARCHAR2(50),
  itemname          VARCHAR2(50),
  itemmappingcolumn VARCHAR2(50),
  resolutionmode    NUMBER(1) default 1,
  runvalue          VARCHAR2(50),
  stopvalue         VARCHAR2(50),
  runcondition      VARCHAR2(50),
  stopcondition     VARCHAR2(50),
  bitindex          NUMBER(3),
  protocoltype      NUMBER(1)
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

/*==============================================================*/
/* Table: TBL_ACQ_UNIT_CONF                                  */
/*==============================================================*/
create table TBL_ACQ_UNIT_CONF
(
  id        NUMBER(10) not null,
  unit_code VARCHAR2(200) not null,
  unit_name VARCHAR2(200),
  protocol  VARCHAR2(200),
  sort      NUMBER(10),
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
  id                  NUMBER(10) not null,
  group_code          VARCHAR2(50) not null,
  group_name          VARCHAR2(50),
  grouptiminginterval NUMBER(10) default 1,
  groupsavinginterval NUMBER(10) default 5,
  protocol            VARCHAR2(500),
  type                NUMBER(1) default 0,
  remark              VARCHAR2(2000)
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
  id                      NUMBER(10) not null,
  itemid                  NUMBER(10),
  itemname                NVARCHAR2(200),
  itemcode                VARCHAR2(200),
  groupid                 NUMBER(10) not null,
  bitindex                NUMBER(3),
  dailytotalcalculate     NUMBER(1) default 0,
  dailytotalcalculatename VARCHAR2(100),
  matrix                  VARCHAR2(8)
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
  id            NUMBER(10) not null,
  unit_code     VARCHAR2(200) not null,
  unit_name     VARCHAR2(200),
  protocol      VARCHAR2(200),
  calculatetype NUMBER(2) default 0,
  sort          NUMBER(10),
  remark        VARCHAR2(2000)
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
  itemname      VARCHAR2(200),
  itemcode      VARCHAR2(200),
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
  issendmail    NUMBER(1) default 0,
  retriggertime NUMBER(10)
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
  unit_code     VARCHAR2(200) not null,
  unit_name     VARCHAR2(200),
  protocol      VARCHAR2(200),
  acqunitid NUMBER(10),
  calculatetype NUMBER(2) default 0,
  sort          NUMBER(10),
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
  id                NUMBER(10) not null,
  itemid            NUMBER(10),
  itemname          VARCHAR2(200),
  itemcode          VARCHAR2(200),
  unitid            NUMBER(10) not null,
  realtimesort      NUMBER(10),
  realtimecolor     VARCHAR2(50),
  realtimebgcolor   VARCHAR2(50),
  historysort       NUMBER(10),
  historycolor      VARCHAR2(50),
  historybgcolor    VARCHAR2(50),
  bitindex          NUMBER(3),
  showlevel         NUMBER(10),
  realtimecurveconf VARCHAR2(4000),
  historycurveconf  VARCHAR2(4000),
  type              NUMBER(1) default 0,
  realtimeoverview     NUMBER(1),
  realtimedata         NUMBER(1),
  historyoverview      NUMBER(1),
  historydata          NUMBER(1),
  realtimeoverviewsort NUMBER(10),
  historyoverviewsort  NUMBER(10),
  switchingvalueshowtype NUMBER(1) default 0,
  matrix            VARCHAR2(8)
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
/* Table: TBL_REPORT_UNIT_CONF                                    */
/*==============================================================*/
create table TBL_REPORT_UNIT_CONF
(
  id                            NUMBER(10) not null,
  unit_code                     VARCHAR2(200) not null,
  unit_name                     VARCHAR2(200),
  singlewellrangereporttemplate VARCHAR2(200),
  productionreporttemplate      VARCHAR2(200),
  sort                          NUMBER(10),
  singlewelldailyreporttemplate VARCHAR2(200),
  calculatetype                 NUMBER(2) default 0
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
/* Table: TBL_REPORT_ITEMS2UNIT_CONF                                    */
/*==============================================================*/
create table TBL_REPORT_ITEMS2UNIT_CONF
(
  id              NUMBER(10) not null,
  itemid          NUMBER(10),
  itemname        VARCHAR2(200),
  itemcode        VARCHAR2(200),
  bitindex        NUMBER(3),
  unitid          NUMBER(10),
  sort            NUMBER(10),
  showlevel       NUMBER(10),
  sumsign         NUMBER(1) default 0,
  averagesign     NUMBER(1) default 0,
  reportcurveconf VARCHAR2(4000),
  curvestattype   NUMBER(1),
  datatype        NUMBER(10),
  reporttype      NUMBER(1) default 0,
  prec            NUMBER(2),
  totaltype       NUMBER(2),
  datasource      VARCHAR2(8),
  matrix          VARCHAR2(100)
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
/* Table: TBL_PROTOCOLINSTANCE                                    */
/*==============================================================*/
create table TBL_PROTOCOLINSTANCE
(
  id                       NUMBER(10) not null,
  name                     VARCHAR2(200),
  code                     VARCHAR2(200),
  acqprotocoltype          VARCHAR2(200),
  ctrlprotocoltype         VARCHAR2(200),
  signinprefixsuffixhex    NUMBER(1) default 1,
  signinprefix             VARCHAR2(200),
  signinsuffix             VARCHAR2(200),
  signinidhex              NUMBER(1) default 1,
  heartbeatprefixsuffixhex NUMBER(1) default 1,
  heartbeatprefix          VARCHAR2(200),
  heartbeatsuffix          VARCHAR2(200),
  packetsendinterval       NUMBER(10) default 100,
  unitid                   NUMBER(10),
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
  name        VARCHAR2(200),
  code        VARCHAR2(200),
  alarmunitid NUMBER(10),
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
  name          VARCHAR2(200),
  code          VARCHAR2(200),
  displayunitid NUMBER(10),
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
  id     NUMBER(10) not null,
  name   VARCHAR2(200),
  code   VARCHAR2(200),
  unitid NUMBER(10),
  sort   NUMBER(10)
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
  name             VARCHAR2(200),
  code             VARCHAR2(200),
  acqprotocoltype  VARCHAR2(200),
  ctrlprotocoltype VARCHAR2(200),
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
/* Table: TBL_DEVICE                                    */
/*==============================================================*/
create table TBL_DEVICE
(
  id                       NUMBER(10) not null,
  orgid                    NUMBER(10) not null,
  devicename               VARCHAR2(200) not null,
  devicetype               NUMBER(3) default 101,
  applicationscenarios     NUMBER(2) default 1,
  tcptype                  VARCHAR2(20),
  signinid                 VARCHAR2(200),
  ipport                   VARCHAR2(200),
  slave                    VARCHAR2(200),
  peakdelay                NUMBER(10),
  instancecode             VARCHAR2(50),
  alarminstancecode        VARCHAR2(50),
  displayinstancecode      VARCHAR2(50),
  reportinstancecode       VARCHAR2(50),
  videourl1                VARCHAR2(400),
  videourl2                VARCHAR2(400),
  videokeyid1              NUMBER(10),
  videokeyid2              NUMBER(10),
  videoaccesstoken         VARCHAR2(400),
  productiondata           VARCHAR2(4000) default '{}',
  productiondataupdatetime DATE default sysdate,
  pumpingmodelid           NUMBER(10),
  stroke                   NUMBER(8,2),
  balanceinfo              VARCHAR2(400),
  status                   NUMBER(1) default 1,
  sortnum                  NUMBER(10) default 9999,
  calculatetype            NUMBER(2) default 0,
  constructiondata         VARCHAR2(4000) default '{}',
  commissioningdate        DATE
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DEVICE add constraint PK_DEVICE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_SMSDEVICE                                    */
/*==============================================================*/
create table TBL_SMSDEVICE
(
  id           NUMBER(10) not null,
  orgid        NUMBER(10),
  devicename   VARCHAR2(200) not null,
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
/* Table: TBL_DEVICEADDINFO                                    */
/*==============================================================*/
create table TBL_DEVICEADDINFO
(
  id        NUMBER(10) not null,
  deviceid  NUMBER(10) not null,
  itemname  VARCHAR2(200) not null,
  itemvalue VARCHAR2(200),
  itemunit  VARCHAR2(200),
  overview     NUMBER(1) default 0,
  overviewsort NUMBER(10)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DEVICEADDINFO add constraint PK_DEVICEADDINFO primary key (ID)
/

/*==============================================================*/
/* Table: TBL_AUXILIARYDEVICE                                    */
/*==============================================================*/
create table TBL_AUXILIARYDEVICE
(
  id           NUMBER(10) not null,
  name         VARCHAR2(200),
  type         NUMBER(2) default 0,
  model        VARCHAR2(200),
  sort         NUMBER(10) not null,
  remark       VARCHAR2(2000),
  manufacturer VARCHAR2(200),
  specifictype NUMBER(2) default 0,
  prtf         CLOB
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_AUXILIARYDEVICE add constraint PK_AUXILIARYDEVICE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_AUXILIARYDEVICEADDINFO                                    */
/*==============================================================*/
create table TBL_AUXILIARYDEVICEADDINFO
(
  id        NUMBER(10) not null,
  deviceid  NUMBER(10) not null,
  itemname  VARCHAR2(200) not null,
  itemcode  VARCHAR2(200),
  itemvalue VARCHAR2(200),
  itemunit  VARCHAR2(200)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_AUXILIARYDEVICEADDINFO add constraint PK_AUXILIARYDEVICEADDINFO primary key (ID)
/

/*==============================================================*/
/* Table: TBL_AUXILIARY2MASTER                                    */
/*==============================================================*/
create table TBL_AUXILIARY2MASTER
(
  id          NUMBER(10) not null,
  masterid    NUMBER(10) not null,
  auxiliaryid NUMBER(10) not null,
  matrix      VARCHAR2(8) not null
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_AUXILIARY2MASTER add constraint PK_AUXILIARY2MASTER primary key (ID)
/

/*==============================================================*/
/* Table: TBL_DEVICEGRAPHICSET                                    */
/*==============================================================*/
create table TBL_DEVICEGRAPHICSET
(
  id           NUMBER(10) not null,
  deviceid     NUMBER(10) not null,
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
alter table TBL_DEVICEGRAPHICSET
  add constraint PK_DEVICEGRAPHICSET primary key (ID)
/

/*==============================================================*/
/* Table: TBL_ACQDATA_LATEST                                    */
/*==============================================================*/
create table TBL_ACQDATA_LATEST
(
  id                 NUMBER(10) not null,
  deviceid           NUMBER(10),
  acqtime            DATE,
  commstatus         NUMBER(2) default 0,
  commtime           NUMBER(8,2) default 0,
  commtimeefficiency NUMBER(10,4) default 0,
  commrange          CLOB,
  runstatus          NUMBER(2) default 0,
  runtimeefficiency  NUMBER(10,4) default 0,
  runtime            NUMBER(8,2) default 0,
  runrange           CLOB,
  acqdata            CLOB,
  checksign          NUMBER(2) default 1
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ACQDATA_LATEST
  add constraint PK_TBL_ACQDATA_LATEST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_ACQDATA_HIST                                    */
/*==============================================================*/
create table TBL_ACQDATA_HIST
(
  id                 NUMBER(10) not null,
  deviceid           NUMBER(10),
  acqtime            DATE,
  commstatus         NUMBER(2) default 0,
  commtime           NUMBER(8,2) default 0,
  commtimeefficiency NUMBER(10,4) default 0,
  commrange          CLOB,
  runstatus          NUMBER(2) default 0,
  runtimeefficiency  NUMBER(10,4) default 0,
  runtime            NUMBER(8,2) default 0,
  runrange           CLOB,
  acqdata            CLOB,
  checksign          NUMBER(2) default 1
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ACQDATA_HIST
  add constraint PK_TBL_ACQDATA_HIST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_ACQDATA_VACUATE                                   */
/*==============================================================*/
create table TBL_ACQDATA_VACUATE
(
  id                 NUMBER(10) not null,
  deviceid           NUMBER(10),
  acqtime            DATE,
  commstatus         NUMBER(2) default 0,
  commtime           NUMBER(8,2) default 0,
  commtimeefficiency NUMBER(10,4) default 0,
  commrange          CLOB,
  runstatus          NUMBER(2) default 0,
  runtimeefficiency  NUMBER(10,4) default 0,
  runtime            NUMBER(8,2) default 0,
  runrange           CLOB,
  acqdata            CLOB,
  checksign          NUMBER(2) default 1
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ACQDATA_VACUATE add constraint PK_ACQDATA_VACUATE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_ACQRAWDATA                                    */
/*==============================================================*/
create table TBL_ACQRAWDATA
(
  id       NUMBER(10) not null,
  deviceid NUMBER(10) not null,
  acqtime  DATE not null,
  rawdata  VARCHAR2(4000),
  acqgroupdata CLOB
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_ACQRAWDATA
  add constraint PK_ACQRAWDATA primary key (ID)
/

/*==============================================================*/
/* Table: TBL_ALARMINFO_LATEST                                    */
/*==============================================================*/
create table TBL_ALARMINFO_LATEST
(
  id            NUMBER(10) not null,
  deviceid      NUMBER(10),
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
alter table TBL_ALARMINFO_LATEST
  add constraint PK_ALARMINFO_LATEST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_ALARMINFO_HIST                                    */
/*==============================================================*/
create table TBL_ALARMINFO_HIST
(
  id            NUMBER(10) not null,
  deviceid      NUMBER(10),
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
alter table TBL_ALARMINFO_HIST
  add constraint PK_ALARMINFO_HIST primary key (ID)
/


/*==============================================================*/
/* Table: TBL_DAILYTOTALCALCULATE_LATEST                                    */
/*==============================================================*/
create table TBL_DAILYTOTALCALCULATE_LATEST
(
  id         NUMBER(10) not null,
  deviceid   NUMBER(10),
  acqtime    DATE,
  itemcolumn VARCHAR2(4000),
  totalvalue NUMBER(12,3),
  todayvalue NUMBER(12,3),
  itemname   VARCHAR2(200)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DAILYTOTALCALCULATE_LATEST
  add constraint PK_DAILYTOTALCALCULATE_LATEST primary key (ID)
/


/*==============================================================*/
/* Table: TBL_DAILYTOTALCALCULATE_HIST                                    */
/*==============================================================*/
create table TBL_DAILYTOTALCALCULATE_HIST
(
  id         NUMBER(10) not null,
  deviceid   NUMBER(10),
  acqtime    DATE,
  itemcolumn VARCHAR2(4000),
  totalvalue NUMBER(12,3),
  todayvalue NUMBER(12,3),
  itemname   VARCHAR2(200)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DAILYTOTALCALCULATE_HIST
  add constraint PK_DAILYTOTALCALCULATE_HIST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_DAILYCALCULATIONDATA                                    */
/*==============================================================*/
create table TBL_DAILYCALCULATIONDATA
(
  id                 NUMBER(10) not null,
  deviceid           NUMBER(10),
  caldate            DATE,
  commstatus         NUMBER(2) default 0,
  commtime           NUMBER(8,2) default 0,
  commtimeefficiency NUMBER(10,4) default 0,
  commrange          CLOB,
  runstatus          NUMBER(2) default 0,
  runtimeefficiency  NUMBER(10,4) default 0,
  runtime            NUMBER(8,2) default 0,
  runrange           CLOB,
  caldata            CLOB,
  headerlabelinfo    VARCHAR2(4000),
  reservedcol1       VARCHAR2(4000),
  reservedcol2       VARCHAR2(4000),
  reservedcol3       VARCHAR2(4000),
  reservedcol4       VARCHAR2(4000),
  reservedcol5       VARCHAR2(4000),
  remark             VARCHAR2(4000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DAILYCALCULATIONDATA
  add constraint PK_DAILYCALCULATIONDATA primary key (ID)
/

/*==============================================================*/
/* Table: TBL_TIMINGCALCULATIONDATA                                    */
/*==============================================================*/
create table TBL_TIMINGCALCULATIONDATA
(
  id                 NUMBER(10) not null,
  deviceid           NUMBER(10),
  caltime            DATE,
  commstatus         NUMBER(2) default 0,
  commtime           NUMBER(8,2) default 0,
  commtimeefficiency NUMBER(10,4) default 0,
  commrange          CLOB,
  runstatus          NUMBER(2) default 0,
  runtimeefficiency  NUMBER(10,4) default 0,
  runtime            NUMBER(8,2) default 0,
  runrange           CLOB,
  caldata            CLOB,
  headerlabelinfo    VARCHAR2(4000),
  reservedcol1       VARCHAR2(4000),
  reservedcol2       VARCHAR2(4000),
  reservedcol3       VARCHAR2(4000),
  reservedcol4       VARCHAR2(4000),
  reservedcol5       VARCHAR2(4000),
  remark             VARCHAR2(4000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_TIMINGCALCULATIONDATA
  add constraint PK_TIMINGALCULATIONDATA primary key (ID)
/

/*==============================================================*/
/* Table: TBL_REALTIMETOTALCALCULATIONDATA                                    */
/*==============================================================*/
create table TBL_REALTIMETOTALCALCULATIONDATA
(
  id                 NUMBER(10) not null,
  deviceid           NUMBER(10),
  caltime            DATE,
  commstatus         NUMBER(2) default 0,
  commtime           NUMBER(8,2) default 0,
  commtimeefficiency NUMBER(10,4) default 0,
  commrange          CLOB,
  runstatus          NUMBER(2) default 0,
  runtimeefficiency  NUMBER(10,4) default 0,
  runtime            NUMBER(8,2) default 0,
  runrange           CLOB,
  caldata            CLOB,
  remark             VARCHAR2(4000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_REALTIMETOTALCALCULATIONDATA
  add constraint PK_REALTIMETOTALCALCULATIONDATA primary key (ID)
/

/*==============================================================*/
/* Table: TBL_SRPACQDATA_LATEST                                    */
/*==============================================================*/
create table TBL_SRPACQDATA_LATEST
(
  id                                 NUMBER(10) not null,
  deviceid                           NUMBER(10),
  acqtime                            DATE,
  commstatus                         NUMBER(2) default 0,
  commtime                           NUMBER(8,2) default 0,
  commtimeefficiency                 NUMBER(10,4) default 0,
  commrange                          CLOB,
  runstatus                          NUMBER(2) default 0,
  runtimeefficiency                  NUMBER(10,4) default 0,
  runtime                            NUMBER(8,2) default 0,
  runrange                           CLOB,
  productiondata                     VARCHAR2(4000) default '{}',
  pumpingmodelid                     NUMBER(10),
  balanceinfo                        VARCHAR2(400),
  fesdiagramacqtime                  DATE,
  fesdiagramsrc                      NUMBER(2) default 0,
  stroke                             NUMBER(8,2),
  spm                                NUMBER(8,2),
  fmax                               NUMBER(8,2),
  fmin                               NUMBER(8,2),
  position_curve                     CLOB,
  angle_curve                        CLOB,
  load_curve                         CLOB,
  power_curve                        CLOB,
  current_curve                      CLOB,
  resultcode                         NUMBER(4),
  fullnesscoefficient                NUMBER(8,2),
  upperloadline                      NUMBER(8,2),
  upperloadlineofexact               NUMBER(8,2),
  lowerloadline                      NUMBER(8,2),
  pumpfsdiagram                      CLOB,
  theoreticalproduction              NUMBER(8,2),
  liquidvolumetricproduction         NUMBER(8,2),
  oilvolumetricproduction            NUMBER(8,2),
  watervolumetricproduction          NUMBER(8,2),
  availableplungerstrokeprod_v       NUMBER(8,2),
  pumpclearanceleakprod_v            NUMBER(8,2),
  tvleakvolumetricproduction         NUMBER(8,2),
  svleakvolumetricproduction         NUMBER(8,2),
  gasinfluenceprod_v                 NUMBER(8,2),
  liquidweightproduction             NUMBER(8,2),
  oilweightproduction                NUMBER(8,2),
  waterweightproduction              NUMBER(8,2),
  availableplungerstrokeprod_w       NUMBER(8,2),
  pumpclearanceleakprod_w            NUMBER(8,2),
  tvleakweightproduction             NUMBER(8,2),
  svleakweightproduction             NUMBER(8,2),
  gasinfluenceprod_w                 NUMBER(8,2),
  averagewatt                        NUMBER(8,2),
  polishrodpower                     NUMBER(8,2),
  waterpower                         NUMBER(8,2),
  surfacesystemefficiency            NUMBER(12,3),
  welldownsystemefficiency           NUMBER(12,3),
  systemefficiency                   NUMBER(12,3),
  energyper100mlift                  NUMBER(8,2),
  area                               NUMBER(8,2),
  rodflexlength                      NUMBER(8,2),
  tubingflexlength                   NUMBER(8,2),
  inertialength                      NUMBER(8,2),
  pumpeff1                           NUMBER(12,3),
  pumpeff2                           NUMBER(12,3),
  pumpeff3                           NUMBER(12,3),
  pumpeff4                           NUMBER(12,3),
  pumpeff                            NUMBER(12,3),
  pumpintakep                        NUMBER(8,2),
  pumpintaket                        NUMBER(8,2),
  pumpintakegol                      NUMBER(8,2),
  pumpintakevisl                     NUMBER(8,2),
  pumpintakebo                       NUMBER(8,2),
  pumpoutletp                        NUMBER(8,2),
  pumpoutlett                        NUMBER(8,2),
  pumpoutletgol                      NUMBER(8,2),
  pumpoutletvisl                     NUMBER(8,2),
  pumpoutletbo                       NUMBER(8,2),
  rodstring                          VARCHAR2(200),
  plungerstroke                      NUMBER(8,2),
  availableplungerstroke             NUMBER(8,2),
  leveldifferencevalue               NUMBER(8,2),
  calcproducingfluidlevel            NUMBER(8,2),
  noliquidfullnesscoefficient        NUMBER(10,4),
  noliquidavailableplungerstroke     NUMBER(10,4),
  smaxindex                          NUMBER(10),
  sminindex                          NUMBER(10),
  upstrokeimax                       NUMBER(8,2),
  downstrokeimax                     NUMBER(8,2),
  upstrokewattmax                    NUMBER(8,2),
  downstrokewattmax                  NUMBER(8,2),
  idegreebalance                     NUMBER(8,2),
  wattdegreebalance                  NUMBER(8,2),
  deltaradius                        NUMBER(8,2),
  crankangle                         CLOB,
  polishrodv                         CLOB,
  polishroda                         CLOB,
  pr                                 CLOB,
  tf                                 CLOB,
  loadtorque                         CLOB,
  cranktorque                        CLOB,
  currentbalancetorque               CLOB,
  currentnettorque                   CLOB,
  expectedbalancetorque              CLOB,
  expectednettorque                  CLOB,
  wellboreslice                      CLOB,
  resultstatus                       NUMBER(2) default 0,
  totalkwatth                        NUMBER(12,3),
  todaykwatth                        NUMBER(12,3),
  liquidvolumetricproduction_l       NUMBER(8,2),
  oilvolumetricproduction_l          NUMBER(8,2),
  watervolumetricproduction_l        NUMBER(8,2),
  liquidweightproduction_l           NUMBER(8,2),
  oilweightproduction_l              NUMBER(8,2),
  waterweightproduction_l            NUMBER(8,2),
  submergence                        NUMBER(8,2),
  savetime                           DATE default sysdate,
  gasvolumetricproduction            NUMBER(12,3),
  totalgasvolumetricproduction       NUMBER(12,3),
  totalwatervolumetricproduction     NUMBER(12,3),
  rpm                                NUMBER(8,2),
  realtimegasvolumetricproduction    NUMBER(12,3),
  realtimewatervolumetricproduction  NUMBER(12,3),
  realtimeoilvolumetricproduction    NUMBER(12,3),
  realtimeliquidvolumetricproduction NUMBER(12,3),
  realtimewaterweightproduction      NUMBER(12,3),
  realtimeoilweightproduction        NUMBER(12,3),
  realtimeliquidweightproduction     NUMBER(12,3)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_SRPACQDATA_LATEST
  add constraint PK_TBL_SRPACQDATA_LATEST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_SRPACQDATA_HIST                                    */
/*==============================================================*/
create table TBL_SRPACQDATA_HIST
(
  id                                 NUMBER(10) not null,
  deviceid                           NUMBER(10),
  acqtime                            DATE,
  commstatus                         NUMBER(2) default 0,
  commtime                           NUMBER(8,2) default 0,
  commtimeefficiency                 NUMBER(10,4) default 0,
  commrange                          CLOB,
  runstatus                          NUMBER(2) default 0,
  runtimeefficiency                  NUMBER(10,4) default 0,
  runtime                            NUMBER(8,2) default 0,
  runrange                           CLOB,
  productiondata                     VARCHAR2(4000) default '{}',
  pumpingmodelid                     NUMBER(10),
  balanceinfo                        VARCHAR2(400),
  fesdiagramacqtime                  DATE,
  fesdiagramsrc                      NUMBER(2) default 0,
  stroke                             NUMBER(8,2),
  spm                                NUMBER(8,2),
  fmax                               NUMBER(8,2),
  fmin                               NUMBER(8,2),
  position_curve                     CLOB,
  angle_curve                        CLOB,
  load_curve                         CLOB,
  power_curve                        CLOB,
  current_curve                      CLOB,
  resultcode                         NUMBER(4),
  fullnesscoefficient                NUMBER(8,2),
  upperloadline                      NUMBER(8,2),
  upperloadlineofexact               NUMBER(8,2),
  lowerloadline                      NUMBER(8,2),
  pumpfsdiagram                      CLOB,
  theoreticalproduction              NUMBER(8,2),
  liquidvolumetricproduction         NUMBER(8,2),
  oilvolumetricproduction            NUMBER(8,2),
  watervolumetricproduction          NUMBER(8,2),
  availableplungerstrokeprod_v       NUMBER(8,2),
  pumpclearanceleakprod_v            NUMBER(8,2),
  tvleakvolumetricproduction         NUMBER(8,2),
  svleakvolumetricproduction         NUMBER(8,2),
  gasinfluenceprod_v                 NUMBER(8,2),
  liquidweightproduction             NUMBER(8,2),
  oilweightproduction                NUMBER(8,2),
  waterweightproduction              NUMBER(8,2),
  availableplungerstrokeprod_w       NUMBER(8,2),
  pumpclearanceleakprod_w            NUMBER(8,2),
  tvleakweightproduction             NUMBER(8,2),
  svleakweightproduction             NUMBER(8,2),
  gasinfluenceprod_w                 NUMBER(8,2),
  averagewatt                        NUMBER(8,2),
  polishrodpower                     NUMBER(8,2),
  waterpower                         NUMBER(8,2),
  surfacesystemefficiency            NUMBER(12,3),
  welldownsystemefficiency           NUMBER(12,3),
  systemefficiency                   NUMBER(12,3),
  energyper100mlift                  NUMBER(8,2),
  area                               NUMBER(8,2),
  rodflexlength                      NUMBER(8,2),
  tubingflexlength                   NUMBER(8,2),
  inertialength                      NUMBER(8,2),
  pumpeff1                           NUMBER(12,3),
  pumpeff2                           NUMBER(12,3),
  pumpeff3                           NUMBER(12,3),
  pumpeff4                           NUMBER(12,3),
  pumpeff                            NUMBER(12,3),
  pumpintakep                        NUMBER(8,2),
  pumpintaket                        NUMBER(8,2),
  pumpintakegol                      NUMBER(8,2),
  pumpintakevisl                     NUMBER(8,2),
  pumpintakebo                       NUMBER(8,2),
  pumpoutletp                        NUMBER(8,2),
  pumpoutlett                        NUMBER(8,2),
  pumpoutletgol                      NUMBER(8,2),
  pumpoutletvisl                     NUMBER(8,2),
  pumpoutletbo                       NUMBER(8,2),
  rodstring                          VARCHAR2(200),
  plungerstroke                      NUMBER(8,2),
  availableplungerstroke             NUMBER(8,2),
  leveldifferencevalue               NUMBER(8,2),
  calcproducingfluidlevel            NUMBER(8,2),
  noliquidfullnesscoefficient        NUMBER(10,4),
  noliquidavailableplungerstroke     NUMBER(10,4),
  smaxindex                          NUMBER(10),
  sminindex                          NUMBER(10),
  upstrokeimax                       NUMBER(8,2),
  downstrokeimax                     NUMBER(8,2),
  upstrokewattmax                    NUMBER(8,2),
  downstrokewattmax                  NUMBER(8,2),
  idegreebalance                     NUMBER(8,2),
  wattdegreebalance                  NUMBER(8,2),
  deltaradius                        NUMBER(8,2),
  crankangle                         CLOB,
  polishrodv                         CLOB,
  polishroda                         CLOB,
  pr                                 CLOB,
  tf                                 CLOB,
  loadtorque                         CLOB,
  cranktorque                        CLOB,
  currentbalancetorque               CLOB,
  currentnettorque                   CLOB,
  expectedbalancetorque              CLOB,
  expectednettorque                  CLOB,
  wellboreslice                      CLOB,
  resultstatus                       NUMBER(2) default 0,
  totalkwatth                        NUMBER(12,3),
  todaykwatth                        NUMBER(12,3),
  liquidvolumetricproduction_l       NUMBER(8,2),
  oilvolumetricproduction_l          NUMBER(8,2),
  watervolumetricproduction_l        NUMBER(8,2),
  liquidweightproduction_l           NUMBER(8,2),
  oilweightproduction_l              NUMBER(8,2),
  waterweightproduction_l            NUMBER(8,2),
  submergence                        NUMBER(8,2),
  savetime                           DATE default sysdate,
  gasvolumetricproduction            NUMBER(12,3),
  totalgasvolumetricproduction       NUMBER(12,3),
  totalwatervolumetricproduction     NUMBER(12,3),
  rpm                                NUMBER(8,2),
  realtimegasvolumetricproduction    NUMBER(12,3),
  realtimewatervolumetricproduction  NUMBER(12,3),
  realtimeoilvolumetricproduction    NUMBER(12,3),
  realtimeliquidvolumetricproduction NUMBER(12,3),
  realtimewaterweightproduction      NUMBER(12,3),
  realtimeoilweightproduction        NUMBER(12,3),
  realtimeliquidweightproduction     NUMBER(12,3)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_SRPACQDATA_HIST add constraint PK_TBL_SRPACQDATA_HIST primary key (ID)
/

/*==============================================================*/
/* Table: TBL_SRPACQDATA_VACUATE                                    */
/*==============================================================*/
create table TBL_SRPACQDATA_VACUATE
(
  id                                 NUMBER(10) not null,
  deviceid                           NUMBER(10),
  acqtime                            DATE,
  commstatus                         NUMBER(2) default 0,
  commtime                           NUMBER(8,2) default 0,
  commtimeefficiency                 NUMBER(10,4) default 0,
  commrange                          CLOB,
  runstatus                          NUMBER(2) default 0,
  runtimeefficiency                  NUMBER(10,4) default 0,
  runtime                            NUMBER(8,2) default 0,
  runrange                           CLOB,
  productiondata                     VARCHAR2(4000) default '{}',
  pumpingmodelid                     NUMBER(10),
  balanceinfo                        VARCHAR2(400),
  fesdiagramacqtime                  DATE,
  fesdiagramsrc                      NUMBER(2) default 0,
  stroke                             NUMBER(8,2),
  spm                                NUMBER(8,2),
  fmax                               NUMBER(8,2),
  fmin                               NUMBER(8,2),
  position_curve                     CLOB,
  angle_curve                        CLOB,
  load_curve                         CLOB,
  power_curve                        CLOB,
  current_curve                      CLOB,
  resultcode                         NUMBER(4),
  fullnesscoefficient                NUMBER(8,2),
  upperloadline                      NUMBER(8,2),
  upperloadlineofexact               NUMBER(8,2),
  lowerloadline                      NUMBER(8,2),
  pumpfsdiagram                      CLOB,
  theoreticalproduction              NUMBER(8,2),
  liquidvolumetricproduction         NUMBER(8,2),
  oilvolumetricproduction            NUMBER(8,2),
  watervolumetricproduction          NUMBER(8,2),
  availableplungerstrokeprod_v       NUMBER(8,2),
  pumpclearanceleakprod_v            NUMBER(8,2),
  tvleakvolumetricproduction         NUMBER(8,2),
  svleakvolumetricproduction         NUMBER(8,2),
  gasinfluenceprod_v                 NUMBER(8,2),
  liquidweightproduction             NUMBER(8,2),
  oilweightproduction                NUMBER(8,2),
  waterweightproduction              NUMBER(8,2),
  availableplungerstrokeprod_w       NUMBER(8,2),
  pumpclearanceleakprod_w            NUMBER(8,2),
  tvleakweightproduction             NUMBER(8,2),
  svleakweightproduction             NUMBER(8,2),
  gasinfluenceprod_w                 NUMBER(8,2),
  averagewatt                        NUMBER(8,2),
  polishrodpower                     NUMBER(8,2),
  waterpower                         NUMBER(8,2),
  surfacesystemefficiency            NUMBER(12,3),
  welldownsystemefficiency           NUMBER(12,3),
  systemefficiency                   NUMBER(12,3),
  energyper100mlift                  NUMBER(8,2),
  area                               NUMBER(8,2),
  rodflexlength                      NUMBER(8,2),
  tubingflexlength                   NUMBER(8,2),
  inertialength                      NUMBER(8,2),
  pumpeff1                           NUMBER(12,3),
  pumpeff2                           NUMBER(12,3),
  pumpeff3                           NUMBER(12,3),
  pumpeff4                           NUMBER(12,3),
  pumpeff                            NUMBER(12,3),
  pumpintakep                        NUMBER(8,2),
  pumpintaket                        NUMBER(8,2),
  pumpintakegol                      NUMBER(8,2),
  pumpintakevisl                     NUMBER(8,2),
  pumpintakebo                       NUMBER(8,2),
  pumpoutletp                        NUMBER(8,2),
  pumpoutlett                        NUMBER(8,2),
  pumpoutletgol                      NUMBER(8,2),
  pumpoutletvisl                     NUMBER(8,2),
  pumpoutletbo                       NUMBER(8,2),
  rodstring                          VARCHAR2(200),
  plungerstroke                      NUMBER(8,2),
  availableplungerstroke             NUMBER(8,2),
  leveldifferencevalue               NUMBER(8,2),
  calcproducingfluidlevel            NUMBER(8,2),
  noliquidfullnesscoefficient        NUMBER(10,4),
  noliquidavailableplungerstroke     NUMBER(10,4),
  smaxindex                          NUMBER(10),
  sminindex                          NUMBER(10),
  upstrokeimax                       NUMBER(8,2),
  downstrokeimax                     NUMBER(8,2),
  upstrokewattmax                    NUMBER(8,2),
  downstrokewattmax                  NUMBER(8,2),
  idegreebalance                     NUMBER(8,2),
  wattdegreebalance                  NUMBER(8,2),
  deltaradius                        NUMBER(8,2),
  crankangle                         CLOB,
  polishrodv                         CLOB,
  polishroda                         CLOB,
  pr                                 CLOB,
  tf                                 CLOB,
  loadtorque                         CLOB,
  cranktorque                        CLOB,
  currentbalancetorque               CLOB,
  currentnettorque                   CLOB,
  expectedbalancetorque              CLOB,
  expectednettorque                  CLOB,
  wellboreslice                      CLOB,
  resultstatus                       NUMBER(2) default 0,
  totalkwatth                        NUMBER(12,3),
  todaykwatth                        NUMBER(12,3),
  liquidvolumetricproduction_l       NUMBER(8,2),
  oilvolumetricproduction_l          NUMBER(8,2),
  watervolumetricproduction_l        NUMBER(8,2),
  liquidweightproduction_l           NUMBER(8,2),
  oilweightproduction_l              NUMBER(8,2),
  waterweightproduction_l            NUMBER(8,2),
  submergence                        NUMBER(8,2),
  savetime                           DATE default sysdate,
  gasvolumetricproduction            NUMBER(12,3),
  totalgasvolumetricproduction       NUMBER(12,3),
  totalwatervolumetricproduction     NUMBER(12,3),
  rpm                                NUMBER(8,2),
  realtimegasvolumetricproduction    NUMBER(12,3),
  realtimewatervolumetricproduction  NUMBER(12,3),
  realtimeoilvolumetricproduction    NUMBER(12,3),
  realtimeliquidvolumetricproduction NUMBER(12,3),
  realtimewaterweightproduction      NUMBER(12,3),
  realtimeoilweightproduction        NUMBER(12,3),
  realtimeliquidweightproduction     NUMBER(12,3)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_SRPACQDATA_VACUATE add constraint PK_SRPACQDATA_VACUATE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_SRPDAILYCALCULATIONDATA                                    */
/*==============================================================*/
create table TBL_SRPDAILYCALCULATIONDATA
(
  id                             NUMBER(10) not null,
  deviceid                       NUMBER(10),
  caldate                        DATE,
  commstatus                     NUMBER(2) default 0,
  commtime                       NUMBER(8,2) default 0,
  commtimeefficiency             NUMBER(10,4) default 0,
  commrange                      CLOB,
  runstatus                      NUMBER(2) default 0,
  runtimeefficiency              NUMBER(10,4) default 0,
  runtime                        NUMBER(8,2) default 0,
  runrange                       CLOB,
  stroke                         NUMBER(8,2),
  spm                            NUMBER(8,2),
  fmax                           NUMBER(8,2),
  fmin                           NUMBER(8,2),
  resultcode                     NUMBER(4),
  fullnesscoefficient            NUMBER(8,2),
  theoreticalproduction          NUMBER(8,2),
  liquidvolumetricproduction     NUMBER(8,2),
  oilvolumetricproduction        NUMBER(8,2),
  watervolumetricproduction      NUMBER(8,2),
  liquidweightproduction         NUMBER(8,2),
  oilweightproduction            NUMBER(8,2),
  waterweightproduction          NUMBER(8,2),
  surfacesystemefficiency        NUMBER(12,3),
  welldownsystemefficiency       NUMBER(12,3),
  systemefficiency               NUMBER(12,3),
  energyper100mlift              NUMBER(8,2),
  pumpeff1                       NUMBER(12,3),
  pumpeff2                       NUMBER(12,3),
  pumpeff3                       NUMBER(12,3),
  pumpeff4                       NUMBER(12,3),
  pumpeff                        NUMBER(12,3),
  idegreebalance                 NUMBER(8,2),
  wattdegreebalance              NUMBER(8,2),
  deltaradius                    NUMBER(8,2),
  resultstatus                   NUMBER(2) default 0,
  totalkwatth                    NUMBER(12,3),
  todaykwatth                    NUMBER(12,3),
  volumewatercut                 NUMBER(8,2),
  weightwatercut                 NUMBER(8,2),
  resultstring                   CLOB,
  extendeddays                   NUMBER(5),
  tubingpressure                 NUMBER(8,2),
  casingpressure                 NUMBER(8,2),
  bottomholepressure             NUMBER(8,2),
  producingfluidlevel            NUMBER(8,2),
  pumpsettingdepth               NUMBER(8,2),
  submergence                    NUMBER(8,2),
  calcproducingfluidlevel        NUMBER(8,2),
  leveldifferencevalue           NUMBER(8,2),
  gasvolumetricproduction        NUMBER(8,2),
  totalgasvolumetricproduction   NUMBER(12,3),
  totalwatervolumetricproduction NUMBER(12,3),
  headerlabelinfo                VARCHAR2(4000),
  remark                         VARCHAR2(4000),
  rpm                            NUMBER(8,2),
  reservedcol1                   VARCHAR2(4000),
  reservedcol2                   VARCHAR2(4000),
  reservedcol3                   VARCHAR2(4000),
  reservedcol4                   VARCHAR2(4000),
  reservedcol5                   VARCHAR2(4000)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_SRPDAILYCALCULATIONDATA
  add constraint PK_SRPDAILYCALCULATIONDATA primary key (ID)
/

/*==============================================================*/
/* Table: TBL_SRPTIMINGCALCULATIONDATA                                    */
/*==============================================================*/
create table TBL_SRPTIMINGCALCULATIONDATA
(
  id                                 NUMBER(10) not null,
  deviceid                           NUMBER(10),
  caltime                            DATE,
  commstatus                         NUMBER(2) default 0,
  commtime                           NUMBER(8,2) default 0,
  commtimeefficiency                 NUMBER(10,4) default 0,
  commrange                          CLOB,
  runstatus                          NUMBER(2) default 0,
  runtimeefficiency                  NUMBER(10,4) default 0,
  runtime                            NUMBER(8,2) default 0,
  runrange                           CLOB,
  stroke                             NUMBER(8,2),
  spm                                NUMBER(8,2),
  fmax                               NUMBER(8,2),
  fmin                               NUMBER(8,2),
  resultcode                         NUMBER(4),
  fullnesscoefficient                NUMBER(8,2),
  theoreticalproduction              NUMBER(8,2),
  liquidvolumetricproduction         NUMBER(8,2),
  oilvolumetricproduction            NUMBER(8,2),
  watervolumetricproduction          NUMBER(8,2),
  liquidweightproduction             NUMBER(8,2),
  oilweightproduction                NUMBER(8,2),
  waterweightproduction              NUMBER(8,2),
  surfacesystemefficiency            NUMBER(12,3),
  welldownsystemefficiency           NUMBER(12,3),
  systemefficiency                   NUMBER(12,3),
  energyper100mlift                  NUMBER(8,2),
  pumpeff1                           NUMBER(12,3),
  pumpeff2                           NUMBER(12,3),
  pumpeff3                           NUMBER(12,3),
  pumpeff4                           NUMBER(12,3),
  pumpeff                            NUMBER(12,3),
  idegreebalance                     NUMBER(8,2),
  wattdegreebalance                  NUMBER(8,2),
  deltaradius                        NUMBER(8,2),
  resultstatus                       NUMBER(2) default 0,
  totalkwatth                        NUMBER(12,3),
  todaykwatth                        NUMBER(12,3),
  volumewatercut                     NUMBER(8,2),
  weightwatercut                     NUMBER(8,2),
  resultstring                       CLOB,
  extendeddays                       NUMBER(5),
  tubingpressure                     NUMBER(8,2),
  casingpressure                     NUMBER(8,2),
  bottomholepressure                 NUMBER(8,2),
  producingfluidlevel                NUMBER(8,2),
  gasvolumetricproduction            NUMBER(8,2),
  totalgasvolumetricproduction       NUMBER(12,3),
  totalwatervolumetricproduction     NUMBER(12,3),
  headerlabelinfo                    VARCHAR2(4000),
  remark                             VARCHAR2(4000),
  calcproducingfluidlevel            NUMBER(8,2),
  leveldifferencevalue               NUMBER(8,2),
  pumpsettingdepth                   NUMBER(8,2),
  submergence                        NUMBER(8,2),
  reservedcol1                       VARCHAR2(4000),
  reservedcol2                       VARCHAR2(4000),
  reservedcol3                       VARCHAR2(4000),
  reservedcol4                       VARCHAR2(4000),
  reservedcol5                       VARCHAR2(4000),
  rpm                                NUMBER(8,2),
  realtimegasvolumetricproduction    NUMBER(12,3),
  realtimewatervolumetricproduction  NUMBER(12,3),
  realtimeoilvolumetricproduction    NUMBER(12,3),
  realtimeliquidvolumetricproduction NUMBER(12,3),
  realtimewaterweightproduction      NUMBER(12,3),
  realtimeoilweightproduction        NUMBER(12,3),
  realtimeliquidweightproduction     NUMBER(12,3)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_SRPTIMINGCALCULATIONDATA
  add constraint PK_SRPTIMINGALCULATIONDATA primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PCPACQDATA_LATEST                                    */
/*==============================================================*/
create table TBL_PCPACQDATA_LATEST
(
  id                                 NUMBER(10) not null,
  deviceid                           NUMBER(10),
  acqtime                            DATE,
  commstatus                         NUMBER(2) default 0,
  commtime                           NUMBER(8,2) default 0,
  commtimeefficiency                 NUMBER(10,4) default 0,
  commrange                          CLOB,
  runstatus                          NUMBER(2) default 0,
  runtimeefficiency                  NUMBER(10,4) default 0,
  runtime                            NUMBER(8,2) default 0,
  runrange                           CLOB,
  productiondata                     VARCHAR2(4000) default '{}',
  rpm                                NUMBER(8,2),
  torque                             NUMBER(8,2),
  resultcode                         NUMBER(4),
  theoreticalproduction              NUMBER(8,2),
  liquidvolumetricproduction         NUMBER(8,2),
  oilvolumetricproduction            NUMBER(8,2),
  watervolumetricproduction          NUMBER(8,2),
  liquidweightproduction             NUMBER(8,2),
  oilweightproduction                NUMBER(8,2),
  waterweightproduction              NUMBER(8,2),
  averagewatt                        NUMBER(8,2),
  waterpower                         NUMBER(8,2),
  systemefficiency                   NUMBER(12,3),
  energyper100mlift                  NUMBER(8,2),
  pumpeff1                           NUMBER(12,3),
  pumpeff2                           NUMBER(12,3),
  pumpeff                            NUMBER(12,3),
  pumpintakep                        NUMBER(8,2),
  pumpintaket                        NUMBER(8,2),
  pumpintakegol                      NUMBER(8,2),
  pumpintakevisl                     NUMBER(8,2),
  pumpintakebo                       NUMBER(8,2),
  pumpoutletp                        NUMBER(8,2),
  pumpoutlett                        NUMBER(8,2),
  pumpoutletgol                      NUMBER(8,2),
  pumpoutletvisl                     NUMBER(8,2),
  pumpoutletbo                       NUMBER(8,2),
  rodstring                          VARCHAR2(200),
  savetime                           DATE default sysdate,
  resultstatus                       NUMBER(2) default 0,
  remark                             VARCHAR2(200),
  totalkwatth                        NUMBER(12,3),
  todaykwatth                        NUMBER(12,3),
  liquidvolumetricproduction_l       NUMBER(8,2),
  oilvolumetricproduction_l          NUMBER(8,2),
  watervolumetricproduction_l        NUMBER(8,2),
  liquidweightproduction_l           NUMBER(8,2),
  oilweightproduction_l              NUMBER(8,2),
  waterweightproduction_l            NUMBER(8,2),
  gasvolumetricproduction            NUMBER(12,3),
  totalgasvolumetricproduction       NUMBER(12,3),
  totalwatervolumetricproduction     NUMBER(12,3),
  submergence                        NUMBER(8,2),
  realtimegasvolumetricproduction    NUMBER(12,3),
  realtimewatervolumetricproduction  NUMBER(12,3),
  realtimeoilvolumetricproduction    NUMBER(12,3),
  realtimeliquidvolumetricproduction NUMBER(12,3),
  realtimewaterweightproduction      NUMBER(12,3),
  realtimeoilweightproduction        NUMBER(12,3),
  realtimeliquidweightproduction     NUMBER(12,3)
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
  id                                 NUMBER(10) not null,
  deviceid                           NUMBER(10),
  acqtime                            DATE,
  commstatus                         NUMBER(2) default 0,
  commtime                           NUMBER(8,2) default 0,
  commtimeefficiency                 NUMBER(10,4) default 0,
  commrange                          CLOB,
  runstatus                          NUMBER(2) default 0,
  runtimeefficiency                  NUMBER(10,4) default 0,
  runtime                            NUMBER(8,2) default 0,
  runrange                           CLOB,
  productiondata                     VARCHAR2(4000) default '{}',
  rpm                                NUMBER(8,2),
  torque                             NUMBER(8,2),
  resultcode                         NUMBER(4),
  theoreticalproduction              NUMBER(8,2),
  liquidvolumetricproduction         NUMBER(8,2),
  oilvolumetricproduction            NUMBER(8,2),
  watervolumetricproduction          NUMBER(8,2),
  liquidweightproduction             NUMBER(8,2),
  oilweightproduction                NUMBER(8,2),
  waterweightproduction              NUMBER(8,2),
  averagewatt                        NUMBER(8,2),
  waterpower                         NUMBER(8,2),
  systemefficiency                   NUMBER(12,3),
  energyper100mlift                  NUMBER(8,2),
  pumpeff1                           NUMBER(12,3),
  pumpeff2                           NUMBER(12,3),
  pumpeff                            NUMBER(12,3),
  pumpintakep                        NUMBER(8,2),
  pumpintaket                        NUMBER(8,2),
  pumpintakegol                      NUMBER(8,2),
  pumpintakevisl                     NUMBER(8,2),
  pumpintakebo                       NUMBER(8,2),
  pumpoutletp                        NUMBER(8,2),
  pumpoutlett                        NUMBER(8,2),
  pumpoutletgol                      NUMBER(8,2),
  pumpoutletvisl                     NUMBER(8,2),
  pumpoutletbo                       NUMBER(8,2),
  rodstring                          VARCHAR2(200),
  savetime                           DATE default sysdate,
  resultstatus                       NUMBER(2) default 0,
  remark                             VARCHAR2(200),
  totalkwatth                        NUMBER(12,3),
  todaykwatth                        NUMBER(12,3),
  liquidvolumetricproduction_l       NUMBER(8,2),
  oilvolumetricproduction_l          NUMBER(8,2),
  watervolumetricproduction_l        NUMBER(8,2),
  liquidweightproduction_l           NUMBER(8,2),
  oilweightproduction_l              NUMBER(8,2),
  waterweightproduction_l            NUMBER(8,2),
  gasvolumetricproduction            NUMBER(12,3),
  totalgasvolumetricproduction       NUMBER(12,3),
  totalwatervolumetricproduction     NUMBER(12,3),
  submergence                        NUMBER(8,2),
  realtimegasvolumetricproduction    NUMBER(12,3),
  realtimewatervolumetricproduction  NUMBER(12,3),
  realtimeoilvolumetricproduction    NUMBER(12,3),
  realtimeliquidvolumetricproduction NUMBER(12,3),
  realtimewaterweightproduction      NUMBER(12,3),
  realtimeoilweightproduction        NUMBER(12,3),
  realtimeliquidweightproduction     NUMBER(12,3)
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
/* Table: TBL_PCPACQDATA_VACUATE                                  */
/*==============================================================*/
create table TBL_PCPACQDATA_VACUATE
(
  id                                 NUMBER(10) not null,
  deviceid                           NUMBER(10),
  acqtime                            DATE,
  commstatus                         NUMBER(2) default 0,
  commtime                           NUMBER(8,2) default 0,
  commtimeefficiency                 NUMBER(10,4) default 0,
  commrange                          CLOB,
  runstatus                          NUMBER(2) default 0,
  runtimeefficiency                  NUMBER(10,4) default 0,
  runtime                            NUMBER(8,2) default 0,
  runrange                           CLOB,
  productiondata                     VARCHAR2(4000) default '{}',
  rpm                                NUMBER(8,2),
  torque                             NUMBER(8,2),
  resultcode                         NUMBER(4),
  theoreticalproduction              NUMBER(8,2),
  liquidvolumetricproduction         NUMBER(8,2),
  oilvolumetricproduction            NUMBER(8,2),
  watervolumetricproduction          NUMBER(8,2),
  liquidweightproduction             NUMBER(8,2),
  oilweightproduction                NUMBER(8,2),
  waterweightproduction              NUMBER(8,2),
  averagewatt                        NUMBER(8,2),
  waterpower                         NUMBER(8,2),
  systemefficiency                   NUMBER(12,3),
  energyper100mlift                  NUMBER(8,2),
  pumpeff1                           NUMBER(12,3),
  pumpeff2                           NUMBER(12,3),
  pumpeff                            NUMBER(12,3),
  pumpintakep                        NUMBER(8,2),
  pumpintaket                        NUMBER(8,2),
  pumpintakegol                      NUMBER(8,2),
  pumpintakevisl                     NUMBER(8,2),
  pumpintakebo                       NUMBER(8,2),
  pumpoutletp                        NUMBER(8,2),
  pumpoutlett                        NUMBER(8,2),
  pumpoutletgol                      NUMBER(8,2),
  pumpoutletvisl                     NUMBER(8,2),
  pumpoutletbo                       NUMBER(8,2),
  rodstring                          VARCHAR2(200),
  savetime                           DATE default sysdate,
  resultstatus                       NUMBER(2) default 0,
  remark                             VARCHAR2(200),
  totalkwatth                        NUMBER(12,3),
  todaykwatth                        NUMBER(12,3),
  liquidvolumetricproduction_l       NUMBER(8,2),
  oilvolumetricproduction_l          NUMBER(8,2),
  watervolumetricproduction_l        NUMBER(8,2),
  liquidweightproduction_l           NUMBER(8,2),
  oilweightproduction_l              NUMBER(8,2),
  waterweightproduction_l            NUMBER(8,2),
  gasvolumetricproduction            NUMBER(12,3),
  totalgasvolumetricproduction       NUMBER(12,3),
  totalwatervolumetricproduction     NUMBER(12,3),
  submergence                        NUMBER(8,2),
  realtimegasvolumetricproduction    NUMBER(12,3),
  realtimewatervolumetricproduction  NUMBER(12,3),
  realtimeoilvolumetricproduction    NUMBER(12,3),
  realtimeliquidvolumetricproduction NUMBER(12,3),
  realtimewaterweightproduction      NUMBER(12,3),
  realtimeoilweightproduction        NUMBER(12,3),
  realtimeliquidweightproduction     NUMBER(12,3)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCPACQDATA_VACUATE add constraint PK_PCPACQDATA_VACUATE primary key (ID)
/

/*==============================================================*/
/* Table: TBL_PCPDAILYCALCULATIONDATA                                    */
/*==============================================================*/
create table TBL_PCPDAILYCALCULATIONDATA
(
  id                             NUMBER(10) not null,
  deviceid                       NUMBER(10),
  caldate                        DATE,
  commstatus                     NUMBER(2) default 0,
  commtime                       NUMBER(8,2) default 0,
  commtimeefficiency             NUMBER(10,4) default 0,
  commrange                      CLOB,
  runstatus                      NUMBER(2) default 0,
  runtimeefficiency              NUMBER(10,4) default 0,
  runtime                        NUMBER(8,2) default 0,
  runrange                       CLOB,
  rpm                            NUMBER(8,2),
  resultcode                     NUMBER(4),
  theoreticalproduction          NUMBER(8,2),
  liquidvolumetricproduction     NUMBER(8,2),
  oilvolumetricproduction        NUMBER(8,2),
  watervolumetricproduction      NUMBER(8,2),
  liquidweightproduction         NUMBER(8,2),
  oilweightproduction            NUMBER(8,2),
  waterweightproduction          NUMBER(8,2),
  systemefficiency               NUMBER(13,4),
  energyper100mlift              NUMBER(8,2),
  pumpeff1                       NUMBER(13,4),
  pumpeff2                       NUMBER(13,4),
  pumpeff                        NUMBER(13,4),
  resultstatus                   NUMBER(2) default 0,
  totalkwatth                    NUMBER(13,4),
  todaykwatth                    NUMBER(13,4),
  volumewatercut                 NUMBER(8,2),
  weightwatercut                 NUMBER(8,2),
  extendeddays                   NUMBER(5),
  tubingpressure                 NUMBER(8,2),
  casingpressure                 NUMBER(8,2),
  bottomholepressure             NUMBER(8,2),
  producingfluidlevel            NUMBER(8,2),
  pumpsettingdepth               NUMBER(8,2),
  submergence                    NUMBER(8,2),
  gasvolumetricproduction        NUMBER(8,2),
  totalgasvolumetricproduction   NUMBER(12,3),
  totalwatervolumetricproduction NUMBER(12,3),
  headerlabelinfo                VARCHAR2(4000),
  reservedcol1                   VARCHAR2(4000),
  reservedcol2                   VARCHAR2(4000),
  reservedcol3                   VARCHAR2(4000),
  reservedcol4                   VARCHAR2(4000),
  reservedcol5                   VARCHAR2(4000),
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
/* Table: TBL_PCPTIMINGCALCULATIONDATA                                    */
/*==============================================================*/
create table TBL_PCPTIMINGCALCULATIONDATA
(
  id                                 NUMBER(10) not null,
  deviceid                           NUMBER(10),
  caltime                            DATE,
  commstatus                         NUMBER(2) default 0,
  commtime                           NUMBER(8,2) default 0,
  commtimeefficiency                 NUMBER(10,4) default 0,
  commrange                          CLOB,
  runstatus                          NUMBER(2) default 0,
  runtimeefficiency                  NUMBER(10,4) default 0,
  runtime                            NUMBER(8,2) default 0,
  runrange                           CLOB,
  rpm                                NUMBER(8,2),
  resultcode                         NUMBER(4),
  theoreticalproduction              NUMBER(8,2),
  liquidvolumetricproduction         NUMBER(8,2),
  oilvolumetricproduction            NUMBER(8,2),
  watervolumetricproduction          NUMBER(8,2),
  liquidweightproduction             NUMBER(8,2),
  oilweightproduction                NUMBER(8,2),
  waterweightproduction              NUMBER(8,2),
  systemefficiency                   NUMBER(13,4),
  energyper100mlift                  NUMBER(8,2),
  pumpeff1                           NUMBER(13,4),
  pumpeff2                           NUMBER(13,4),
  pumpeff                            NUMBER(13,4),
  resultstatus                       NUMBER(2) default 0,
  totalkwatth                        NUMBER(13,4),
  todaykwatth                        NUMBER(13,4),
  volumewatercut                     NUMBER(8,2),
  weightwatercut                     NUMBER(8,2),
  extendeddays                       NUMBER(5),
  tubingpressure                     NUMBER(8,2),
  casingpressure                     NUMBER(8,2),
  bottomholepressure                 NUMBER(8,2),
  producingfluidlevel                NUMBER(8,2),
  gasvolumetricproduction            NUMBER(8,2),
  totalgasvolumetricproduction       NUMBER(12,3),
  totalwatervolumetricproduction     NUMBER(12,3),
  headerlabelinfo                    VARCHAR2(4000),
  remark                             VARCHAR2(4000),
  pumpsettingdepth                   NUMBER(8,2),
  submergence                        NUMBER(8,2),
  reservedcol1                       VARCHAR2(4000),
  reservedcol2                       VARCHAR2(4000),
  reservedcol3                       VARCHAR2(4000),
  reservedcol4                       VARCHAR2(4000),
  reservedcol5                       VARCHAR2(4000),
  realtimegasvolumetricproduction    NUMBER(12,3),
  realtimewatervolumetricproduction  NUMBER(12,3),
  realtimeoilvolumetricproduction    NUMBER(12,3),
  realtimeliquidvolumetricproduction NUMBER(12,3),
  realtimewaterweightproduction      NUMBER(12,3),
  realtimeoilweightproduction        NUMBER(12,3),
  realtimeliquidweightproduction     NUMBER(12,3)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_PCPTIMINGCALCULATIONDATA
  add constraint PK_PCPTIMINGCALCULATIONDATA primary key (ID)
/

/*==============================================================*/
/* Table: TBL_DEVICEOPERATIONLOG                                    */
/*==============================================================*/
create table TBL_DEVICEOPERATIONLOG
(
  id         NUMBER(10) not null,
  devicename VARCHAR2(20),
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
  jedisstatus    NUMBER(2),
  cachemaxmemory  NUMBER(20),
  cacheusedmemory NUMBER(20),
  jvmmemoryusage        NUMBER(20),
  oraclephysicalmemory  NUMBER(20),
  totalmemoryusage      NUMBER(10,2),
  jvmheapmemoryusage    NUMBER(20),
  jvmnonheapmemoryusage NUMBER(20),
  tomcatphysicalmemory  NUMBER(20)
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
/* Table: TBL_DBMONITORING                                    */
/*==============================================================*/
create table TBL_DBMONITORING
(
  id          NUMBER(10) not null,
  acqtime     DATE,
  connstatus  NUMBER(2),
  tablespaceusedsize    NUMBER(10,2),
  tablespaceusedpercent NUMBER(10,2),
  tablesize             CLOB
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_DBMONITORING  add constraint PK_DBMONITORING primary key (ID)
/


/*==============================================================*/
/* Table: TBL_VIDEOKEY                                    */
/*==============================================================*/
create table TBL_VIDEOKEY
(
  id      NUMBER(10) not null,
  orgid   NUMBER(10) not null,
  account VARCHAR2(200),
  appkey  VARCHAR2(400),
  secret  VARCHAR2(400)
)
tablespace AP_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  )
/
alter table TBL_VIDEOKEY add constraint PK_VIDEOKEY primary key (ID)
/

create or replace package MYPACKAGE as
   type MY_CURSOR is REF CURSOR;
end MYPACKAGE;
/