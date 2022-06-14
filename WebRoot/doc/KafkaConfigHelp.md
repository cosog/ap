# 驱动配置

## RunMode		   运行模式	debug-调试	work-工作

## CommMode	  通信模式

### WiFi

#### AP							 	   热点模式

State										  开关		off-失效  on-使能

Wifi_interface						   针对该设备为固定值wlan1					

Interface_with_internet		  建立AP时的外网路由，eth0：网卡1 eth1：网卡2 eth2：代表4g wlan0：代表sta

SSID									   	名称			缺省：AP_ID

PSK											密码			缺省：1234567889

#### STA						   	   站模式

State										  开关			off-失效  on-使能

IP										  	 (DHCP时不需配置)

Mask										 掩码  (DHCP时不需配置)

GW										    网关  (DHCP时不需配置)

SSID									      名称											缺省：STA_TEST

PSK										   密码											缺省：1234567889

#### Eth0				   	       100/1000Mbps网卡

State									    开关			off-失效  on-使能

IP											  网卡1和网卡2设置不同网段																				

Mask									    掩码													

GW										   网关

DNS									     域名服务器	可以设置与网关一致

#### Eth1				   		   10/100Mbps网卡

State										开关			off-失效  on-使能

IP											  网卡1和网卡2设置不同网段						

Mask									    掩码

GW										   网关

DNS										 域名服务器	可以设置与网关一致

#### Eth2				   		   4G模组

State									   开关			off-失效  on-使能

## Server		  	 服务器

### RemoteKafka

State									       开关			off-失效  on-使能

IP 												缺省：39.98.64.56

Port											 缺省：9092

#### Topic							主题

##### Pub										    发布

Norm       								  标准数据

RawFES								      原始功图数据

RawFES								      原始含水率数据

Log         									日志数据

Resource         						  资源数据

##### Sub											订阅

### LocalApp								 APP

State										  开关			off-失效  on-使能

IP											    缺省：192.168.12.1

Port										   缺省：17100

## Terminal	       端子配置

### RS485

Num										端子号，最小值为1，端子1默认健丰含水仪，端子2默认健丰动液面仪

Driver  									驱动名称，jf-hs/50：健丰含水仪  jf-dym-01：健丰动液面仪

### RS232

Num									   端子号，最小值为1

Driver									 驱动名称

### DI

Num									   端子号，最小值为1，缺省端子1：抽油机运行状态

Driver	                                 驱动名称，rpc_run_status：抽油机运行状态

### DO

Num	                                   端子号，最小值为1，缺省端子1：启抽继电器  端子2：停抽继电器

Driver	                                 驱动名称，rpc_start_relay ：启抽继电器  rpc_stop_relay ：停抽继电器   

### AI

Num									   端子号，最小值为1，缺省端子1：载荷传感器  端子2：角位移传感器

Driver									 驱动名称，load ：载荷传感器 angle：角位移传感器 

### AO

Num										端子号，最小值为1

Driver									  驱动名称

## FRange			  载荷量程

MinCurrent							最小电流					缺省：4mA

MaxCurrent						   最大电流					缺省：20mA

MinF								        最小载荷		    		缺省：0kN								

MaxF								       最大载荷					缺省：150kN

## ARange			 角度量程

MinCurrent						    最小电流					缺省：4mA

MaxCurrent					       最大电流					缺省：20mA

MinAngle						        最小角度					缺省：-45度

MaxAngle						       最大角度					缺省：45度

## Ratio				 互感器变比

V											 电压变比					 缺省：1

I									 		 电流变比					 缺省：1

## Ctrl 				   控制配置

RPCStopPosition	              间开抽油机停抽位置 top：驴头上死点 bottom：驴头下死点 空：即时停抽

RPCStopAngleTHR	           抽油机停抽角度阈值						缺省：2度

RPCStartRelayDuration	   抽油机启抽继电器吸合时间			缺省：60秒

RPCStartSoundDuration	 抽油机启抽音乐时间						缺省：60秒

RPCStopRelayDuration	   抽油机停抽继电器吸合时间			 缺省：10秒

RPCStartSoundPath	        启抽音乐路径									 缺省：sound/rpc_start.wav

EnterWellSiteSoundPath    进入井场音乐路径							 缺省：sound/enter_wellsite.wav

## Timer			    定时器

StartOverload					  启抽过载定时器		缺省：5分钟	

TransferInterval				  传输间隔定时器		缺省：3分钟

HeartbeatInterval               心跳包间隔定时器    缺省：10秒

AcqSampleInterval             采样间隔                   缺省： 50毫秒

DailyStartTimer	               日报上传起始时间，区间内随机时间上传，填写1-24整型  缺省：2

DailyEndTimer	                 日报上传结束时间，区间内随机时间上传，填写1-24整型  缺省：5

## Counter		   计数器

StopCNT								停抽计数器									缺省：1000

UnderloadCNT					 欠载计数器									缺省：1000

OverloadCNT					    过载计数器									缺省：1

OpenPhaseCNT					缺相计数器									缺省：1000

Imbalance3ICNT				   三相电流不平衡计数器				 缺省：1000

## Limit				界限值

### I							 电流

#### A								 								

Zero									 零值界限，需考虑冬季电保温等接入		缺省：3A

#### B								  

Zero									 零值界限，需考虑冬季电保温等接入		缺省：3A

#### C								  

Zero									 零值界限，需考虑冬季电保温等接入		缺省：3A

## Length			记录数

FEAStack							采集载荷-电参-角度原始数据曲线点数			缺省：1000

StoreFEA							存储载荷-电参-角度功图记录数						缺省：100

StoreFES							存储载荷-电参-位移功图记录数						缺省：100

StoreFESR						  存储功图计算结果记录数								  缺省：100

StoreAcqWaterCut		   存储采集含水仪数据记录数			                  缺省：1000

StoreCalcWaterCut	      存储计算含水仪数据记录数			                  缺省：1000

StoreAcqLevel	               存储采集动液面仪记录数						          缺省：1000

StoreCalcLevel	              存储计算动液面仪记录数						          缺省：1000

StoreAcqTP					    存储采集油压记录数										 缺省：1000

StoreCalcTP					   存储计算油压记录数										 缺省：1000

StoreAcqTT					    存储采集井口温度记录数								 缺省：1000

StoreCalcTT					   存储计算井口温度记录数							     缺省：1000

StoreAcqCP					   存储采集套压记录数									     缺省：1000

StoreCalcCP					  存储计算套压记录数									     缺省：1000

StoreAcqFreq				   存储采集变频器频率记录数						      缺省：1000

StoreCalcFreq				  存储计算变频器频率记录数						      缺省：1000

StoreAcqE					     存储采集电参记录数										 缺省：1000

StoreCalcE					    存储计算电参记录数										 缺省：1000

StoreAcqRun				    存储采集运行状态记录数							     缺省：1000

StoreCalcRun				   存储计算运行状态记录数							     缺省：1000

StoreAcqComm			   存储采集通信状态记录数						         缺省：1000

StoreCalcComm			  存储计算通信状态记录数						         缺省：1000

StoreDaily					    存储日报记录数										         缺省：10

Log					   			 启动日志记录数										         缺省：10

## Filter			 滤波配置

FTimes							载荷滤波次数								缺省：0

ATimes						   角度滤波次数								缺省：0

WattTimes					 功率滤波次数							    缺省：0

ITimes							电流滤波次数								缺省：0

FZero						     载荷零值滤波数值						 缺省：0kN

AZero							 角度零值滤波数值						 缺省：0度

## Rate			 速率配置  20,45,90,175,330,600,1000 缺省：1000