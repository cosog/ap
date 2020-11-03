# 驱动配置

## Mode					模式	0-debug	1-work

## Server				  发布订阅服务器

IP 												IP								   缺省：39.98.64.56

Port											 端口							   缺省：9092

RTU2GWMode						  RTU到网关连接模式	 

​												    4g-移动网络   sta-wifi连接	eth-有线网卡连接

## WiFi

### AP

Wifi_interface						

Interface_with_internet

SSID

PSK										密码

### STA

IP

Mask

GW										网关

SSID

PSK										密码

## Eth0				网卡1

IP							

Mask

GW

DNS

## Eth1				网卡2

IP

Mask

GW

DNS

## FRange		载荷量程

MinCurrent					最小电流

MaxCurrent					最大电流

MinF								最小载荷								

MaxF								最大载荷

## ARange

MinCurrent					最小电流

MaxCurrent					最大电流

MinAngle						最小角度

MaxAngle						最大角度

## Ratio			互感器变比

V										电压变比

I										电流变比

## Limit

### I

#### A	

Noload	

Zero

#### B

Noload

Zero

#### C

Noload

Zero

## Counter	计数器

StopCNT	停抽计数器

UnderloadCNT	欠载计数器

OverloadCNT	过载计数器

OpenPhaseCNT	缺相计数器

Imbalance3ICNT	三相电流不平衡计数器

## Timer		定时器

StartOverload	启抽过载定时器

TransferIntervel	传输间隔定时器

## Length	记录数

Daily		日报记录数

LogReboot	启动日志记录数

FEAStack	采集载荷-电参-角度原始数据曲线点数

StoreFEA	存储载荷-电参-角度功图记录数

StoreFES	存储载荷-电参-位移功图记录数

StoreFESR	存储功图计算结果记录数

StoreAcqWaterCut	存储采集含水仪数据记录数

StoreCalcWaterCut	存储计算含水仪数据记录数

StoreAcqLevel	存储采集动液面仪记录数

StoreCalcLevel	存储计算动液面仪记录数

StoreAcqTP	存储采集油压记录数

StoreCalcTP	存储计算油压记录数

StoreAcqTT	存储采集井口流温记录数

StoreCalcTT	存储计算井口流温记录数

StoreAcqCP	存储采集套压记录数

StoreCalcCP	存储计算套压记录数

StoreAcqFreq	存储采集变频器频率记录数

StoreCalcFreq	存储计算变频器频率记录数

StoreAcqE	存储采集电参记录数

StoreCalcE	存储计算电参记录数

StoreAcqRun	存储采集运行状态记录数

StoreCalcRun	存储计算运行状态记录数

StoreAcqComm	存储采集通信状态记录数

StoreCalcComm	存储计算通信状态记录数

## Cron	时间计划配置

DailyStartTimer	日报上传起始时间，区间内随机时间上传

DailyEndTimer	日报上传结束时间，区间内随机时间上传

## Driver	端子配置

### RS485

Num	端子号，最小值为1

Model	驱动名称

### RS232

Num	端子号，最小值为1

Model	驱动名称

### DI

Num	端子号，最小值为1

Model	驱动名称

### DO

Num	端子号，最小值为1

Model	rpc_start_relay 启抽继电器，rpc_stop_relay

### AI

Num	端子号，最小值为1

Model	load 载荷传感器，angle角位移传感器

### AO

Num	端子号，最小值为1

Model	驱动名称

## Ctrl

RPCStopPosition	抽油机停抽位置 top-驴头上死点 bottom-驴头下死点，该设置对间开定时停抽有作用。

RPCStopAngleTHR	抽油机停抽角度阈值

RPCStartRelayDuration	抽油机启抽继电器吸合时间

RPCStartSoundDuration	抽油机启抽音乐时间

RPCStopRelayDuration	抽油机停抽继电器吸合时间

RPCStartSoundPath	启抽音乐路径

EnterWellSiteSoundPath	进入井场音乐路径

## Filter	滤波

FTimes	载荷滤波次数

ATimes	角度滤波次数

WattTimes	功率滤波次数

ITimes	电流滤波次数

FZero	载荷零值滤波数值

AZero	角度零值滤波数值

## Topic

UpData	上行数据

UpRawData	上行原始数据
